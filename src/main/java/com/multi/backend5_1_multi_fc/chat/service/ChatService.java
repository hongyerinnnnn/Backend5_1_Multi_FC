package com.multi.backend5_1_multi_fc.chat.service;

import com.multi.backend5_1_multi_fc.chat.dao.ChatMessageDao;
import com.multi.backend5_1_multi_fc.chat.dao.ChatParticipantDao;
import com.multi.backend5_1_multi_fc.chat.dao.ChatRoomDao;
import com.multi.backend5_1_multi_fc.chat.dto.ChatMessageDto;
import com.multi.backend5_1_multi_fc.chat.dto.ChatParticipantDto;
import com.multi.backend5_1_multi_fc.chat.dto.ChatRoomDto;
import com.multi.backend5_1_multi_fc.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomDao chatRoomDao;
    private final ChatMessageDao chatMessageDao;
    private  final ChatParticipantDao chatParticipantDao;
    private final RabbitTemplate rabbitTemplate;
    private final NotificationService notificationService;

    //채팅방 생성
    public void createChatRoom(ChatRoomDto chatRoomDto){
        chatRoomDao.insertChatRoom(chatRoomDto);
    }

    //채팅방 조회
    public ChatRoomDto findChatRoomById(Long roomId){
        return chatRoomDao.findChatRoomById(roomId);
    }

    //채팅 메세지 저장 + MQ publish
    public void sendMessage(ChatMessageDto messageDto){
        //DB 메세지 저장
        chatMessageDao.insertMessage(messageDto);

        //RabbitMQ로 publish
        rabbitTemplate.convertAndSend("chat.exchange", "room." + messageDto.getRoomId(), messageDto);

       ChatRoomDto chatRoom = chatRoomDao.findChatRoomById(messageDto.getRoomId());

       List<ChatParticipantDto> participants = chatParticipantDao.findParticipantsByRoomId(messageDto.getRoomId());

       for(ChatParticipantDto participant : participants){
           if(!participant.getUserId().equals(messageDto.getSenderId())){
               notificationService.createOrUpdateChatNotification(
                       participant.getUserId(),
                       chatRoom.getRoomName(),
                       messageDto.getRoomId()
               );
           }
       }
    }

    //채팅방 메세지 목록 조회
    public List<ChatMessageDto> getMessagesByRoomId(Long roomId){
        return chatMessageDao.findMessagesByRoomId(roomId);
    }

    //채팅방 참가자 목록 조회
    public List<ChatParticipantDto> getParticipantsByRoomId(Long roomId){
        return chatParticipantDao.findParticipantsByRoomId(roomId);
    }

    //채팅방 참가자 추가
    public void addParticipant(ChatParticipantDto participant){
        chatParticipantDao.insertParticipant(participant);

        //초대받은 사용자에게 알림
        notificationService.createAndSendNotification(
                participant.getUserId(),
                "채팅방에 초대되었습니다.",
                "채팅",
                participant.getRoomId()
        );
    }

    //채팅방 참가자 퇴장
    public void removeParticipant(Long roomId, Long chatPartId){
        chatParticipantDao.deleteParticipant(roomId,chatPartId);
    }
}
