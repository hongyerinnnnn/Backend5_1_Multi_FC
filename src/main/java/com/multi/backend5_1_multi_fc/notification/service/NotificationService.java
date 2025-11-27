package com.multi.backend5_1_multi_fc.notification.service;


import com.multi.backend5_1_multi_fc.notification.dao.NotificationDao;
import com.multi.backend5_1_multi_fc.notification.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // import 추가

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationDao notificationDao;
    private final SimpMessagingTemplate simpMessagingTemplate;

    //알림 생성 + DB 저장 + 실시간 전송
    public void createAndSendNotification(Long userId, String content, String type, Long referenceId){
        NotificationDto notification = NotificationDto.builder()
                .userId(userId)
                .content(content)
                .type(type)
                .referenceId(referenceId)
                .isRead(false)
                .build();

        notificationDao.insert(notification);

        simpMessagingTemplate.convertAndSendToUser(
                String.valueOf(userId),
                "/queue/notifications",
                notification
        );
    }

    public void createOrUpdateChatNotification(Long userId, String roomName, Long roomId){
        NotificationDto existingNotification = notificationDao.findUnreadChatNotification(userId, roomId);

        if(existingNotification != null){
            int currentCount = extractMessageCount(existingNotification.getContent());
            int newCount = currentCount + 1;

            String updatedContent = roomName + "방에 새로운 메세지 " + newCount + "건";
            existingNotification.setContent(updatedContent);

            notificationDao.updateContent(existingNotification.getNotificationId(), updatedContent);

            simpMessagingTemplate.convertAndSendToUser(
                    String.valueOf(userId),
                    "/queue/notifications",
                    existingNotification
            );
        } else {
            NotificationDto notification = NotificationDto.builder()
                    .userId(userId)
                    .content(roomName + " 방에 새로운 메시지 1건")
                    .type("채팅")
                    .referenceId(roomId)
                    .isRead(false)
                    .createdAt(LocalDateTime.now())
                    .build();

            notificationDao.insert(notification);

            simpMessagingTemplate.convertAndSendToUser(
                    String.valueOf(userId),
                    "/queue/notifications",
                    notification
            );
        }
    }
    private int extractMessageCount(String content){
        try{
            String[] parts = content.split(" ");
            for(int i =0; i < parts.length; i++){
                if(parts[i].endsWith("건")){
                    return Integer.parseInt(parts[i].replace("건",""));
                }
            }
        } catch (Exception e){
            return 1;
        }
        return 1;
    }

    public List<NotificationDto> getUserNotifications(Long userId){
        return notificationDao.findByUserId(userId);
    }

    public List<NotificationDto> getUnreadNotifications(Long userId){
        return notificationDao.findUnreadByUserId(userId);
    }

    public int getUnreadCount(Long userId){
        return notificationDao.countUnreadByUserId(userId);
    }

    public void markAsRead(Long notificationId){
        notificationDao.updateReadStatus(notificationId);
    }

    public void markAllAsRead(Long userId){
        notificationDao.updateAllReadByUserId(userId);
    }

    public void deleteNotification(Long notificationId){
        notificationDao.delete(notificationId);
    }

    public void createOrUpdatePostCommentNotification(Long userId,
                                                      Long postId,
                                                      Long lastCheckedCommentId,
                                                      Long currentCommentId) {

        if (currentCommentId == null
                || (lastCheckedCommentId != null && currentCommentId <= lastCheckedCommentId)) {
            return;
        }

        NotificationDto existing =
                notificationDao.findUnreadCommentNotification(userId, postId);

        if (existing != null) {
            int count = extractCommentCount(existing.getContent());
            int newCount = count + 1;

            String updatedContent = "내 게시글에 새로운 댓글 " + newCount + "건";
            existing.setContent(updatedContent);

            notificationDao.updateContent(existing.getNotificationId(), updatedContent);

            simpMessagingTemplate.convertAndSendToUser(
                    String.valueOf(userId),
                    "/queue/notifications",
                    existing
            );
        } else {
            NotificationDto notification = NotificationDto.builder()
                    .userId(userId)
                    .type("댓글")
                    .referenceId(postId)
                    .content("내 게시글에 새로운 댓글 1건")
                    .isRead(false)
                    .createdAt(LocalDateTime.now())
                    .build();

            notificationDao.insert(notification);

            simpMessagingTemplate.convertAndSendToUser(
                    String.valueOf(userId),
                    "/queue/notifications",
                    notification
            );
        }
    }

    public void createReplyNotification(Long userId, Long postId) {
        NotificationDto notification = NotificationDto.builder()
                .userId(userId)
                .type("대댓글")
                .referenceId(postId)
                .content("내 댓글에 새로운 대댓글이 달렸습니다.")
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        notificationDao.insert(notification);

        simpMessagingTemplate.convertAndSendToUser(
                String.valueOf(userId),
                "/queue/notifications",
                notification
        );
    }

    // ⭐⭐⭐ [수정] 경기 후기 알림 일괄 전송 (중복 방지 로직 추가) ⭐⭐⭐
    @Transactional
    public void sendReviewNotificationForMatch(Long stadiumId, List<Long> userIds) {
        String content = "참여했던 경기장 후기를 작성해 주세요! (구장 ID: " + stadiumId + ")";
        String type = "후기";

        for (Long userId : userIds) {

            // 핵심 중복 방지: 이미 읽지 않은 동일한 후기 알림이 있는지 확인
            NotificationDto existing = notificationDao.findUnreadNotificationByTypeAndReference(userId, type, stadiumId);

            if (existing != null) {
                continue; // 이미 알림이 있으므로 생성하지 않음
            }

            // 알림 생성
            NotificationDto notification = NotificationDto.builder()
                    .userId(userId)
                    .content(content)
                    .type(type)
                    .referenceId(stadiumId)
                    .isRead(false)
                    .createdAt(LocalDateTime.now())
                    .build();

            // DB 저장
            notificationDao.insert(notification);

            // WebSocket 실시간 전송
            simpMessagingTemplate.convertAndSendToUser(
                    String.valueOf(userId),
                    "/queue/notifications",
                    notification
            );
        }
    }
    // ⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐

    private int extractCommentCount(String content) {
        try {
            String[] parts = content.split(" ");
            for (String part : parts) {
                if (part.endsWith("건")) {
                    return Integer.parseInt(part.replace("건", ""));
                }
            }
        } catch (Exception e) {

        }
        return 1;
    }
}