package com.multi.backend5_1_multi_fc.Notification.service;


import com.multi.backend5_1_multi_fc.Notification.dao.NotificationDao;
import com.multi.backend5_1_multi_fc.Notification.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationDao notificationDao;
    private final SimpMessagingTemplate simpMessagingTemplate;

    //알림 생성 + DB 저장 + 실시간 전송
    public void createAndSendNotification(Long userId, String content,String type, String redirectUrl, Long relatedId){
        //알림 생성
        NotificationDto notification = NotificationDto.builder()
                .userId(userId)
                .content(content)
                .type(type)
                .redirectUrl(redirectUrl)
                .relatedId(relatedId)
                .isRead(false)
                .build();

        //데이터베이스 저장
        notificationDao.insert(notification);

        //WebSocket 실시간 전송
        simpMessagingTemplate.convertAndSendToUser(
                String.valueOf(userId),
                "/queue/notifications",
                notification
        );
    }

    //사용자 알림 목록 조회
    public List<NotificationDto> getUserNotifications(Long userId){
        return notificationDao.findByUserId(userId);
    }

    //읽지 않은 알림 조회
    public List<NotificationDto> getUnreadNotifications(Long userId){
        return notificationDao.findByUserId(userId);
    }

    //읽지 않은 알림 개수
    public int getUnreadCount(Long userId){
        return notificationDao.countUnreadByUserId(userId);
    }

    //알림 읽음 처리
    public void markAsRead(Long notificationId){
        notificationDao.updateReadStatus(notificationId);
    }

    //전체 읽음 처리
    public void markAllAsRead(Long userId){
        notificationDao.updateAllReadByUserId(userId);
    }

    //알림 삭제
    public void deleteNotification(Long notificationId){
        notificationDao.delete(notificationId);
    }
}
