package com.multi.backend5_1_multi_fc.match.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDto {
    private Long userId;
    private String nickname;
    private String position; // 포지션 (없으면 "미정" 처리)
    private String role;     // "Host" 또는 "Player"
}