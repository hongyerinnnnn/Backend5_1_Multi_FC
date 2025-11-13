package com.multi.backend5_1_multi_fc.match.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MatchRoomDto {
    private Long roomId;
    private Long stadiumId;
    private Long hostId;
    private String matchDate;
    private String matchTime;
    private Integer maxPlayers;
    private String level;
    private String status;
}
