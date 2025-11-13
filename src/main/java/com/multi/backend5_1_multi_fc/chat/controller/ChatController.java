package com.multi.backend5_1_multi_fc.chat.controller;

import com.multi.backend5_1_multi_fc.chat.dto.ChatMessageDto;
import com.multi.backend5_1_multi_fc.chat.dto.ChatParticipantDto;
import com.multi.backend5_1_multi_fc.chat.dto.ChatRoomDto;
import com.multi.backend5_1_multi_fc.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    //1. 채팅방 생성
    @PostMapping("/makechat")
    public void createChatRoom(@RequestBody ChatRoomDto chatRoomDto){
        chatService.createChatRoom(chatRoomDto);
    }

    //2. 채팅방 단일 조회 (방의 정보를 응답받는 형태 - 방 제목, 멤버 )
    @GetMapping("/chatroom/{id}")
    public ChatRoomDto getChatRoom(@PathVariable("id") Long roomId){
        return chatService.findChatRoomById(roomId);
    }

    //3. 채팅방 메세지 목록
    @GetMapping("/chatroom/{id}/messages")
    public List<ChatMessageDto> getChatMessages(@PathVariable("id") Long roomId){
        return chatService.getMessagesByRoomId(roomId);
    }

    //4. 채팅방 참가자 목록
    @GetMapping("/chatroom/{id}/participants")
    public List<ChatParticipantDto> getChatParticipants(@PathVariable("id") Long roomId){
        return chatService.getParticipantsByRoomId(roomId);
    }

    //5. 채팅방 참가자 추가
    @PostMapping("/chatroom/{id}}/invite")
    public void inviteParticipant(@PathVariable("id") Long roomId, @RequestBody ChatParticipantDto participant){
        participant.setRoomId(roomId);
        chatService.addParticipant(participant);
    }

    @MessageMapping("/chatroom/{roomId}/send")
    public void sendMessage(@DestinationVariable Long roomId, @Payload ChatMessageDto messageDto, SimpMessageHeaderAccessor headerAccessor){
        messageDto.setRoomId(roomId);
        chatService.sendMessage(messageDto);
    }
}
