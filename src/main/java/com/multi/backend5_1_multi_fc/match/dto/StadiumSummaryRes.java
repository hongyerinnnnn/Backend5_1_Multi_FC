package com.multi.backend5_1_multi_fc.match.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class StadiumSummaryRes {
    private Long stadiumId;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
}
