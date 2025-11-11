package com.multi.backend5_1_multi_fc.chat.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageDto {
    private Long messageId;
    private Long roomId;
    private Long senderId;
    private String senderNickname;
    private String content;
    private Boolean isRead;
    private LocalDateTime sentAt;
}
