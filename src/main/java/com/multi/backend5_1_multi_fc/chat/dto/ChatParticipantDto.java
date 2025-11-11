package com.multi.backend5_1_multi_fc.chat.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatParticipantDto {
    private Long chatPartId;
    private Long roomId;
    private Long userId;
    private String nickname;
    private String profileImg;
    private LocalDateTime joinedAt;
}
