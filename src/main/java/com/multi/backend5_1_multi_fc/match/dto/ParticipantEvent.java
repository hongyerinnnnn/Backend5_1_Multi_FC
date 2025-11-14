package com.multi.backend5_1_multi_fc.match.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipantEvent {
    private Long roomId;
    private Long userId;
}
