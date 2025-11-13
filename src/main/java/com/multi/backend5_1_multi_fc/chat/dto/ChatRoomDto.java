package com.multi.backend5_1_multi_fc.chat.dto;

import lombok.Data;

@Data
public class ChatRoomDto {
    private Long roomId;
    private String roomType;
    private String roomName;
    private int memberCount;
}
