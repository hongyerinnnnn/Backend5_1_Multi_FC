package com.multi.backend5_1_multi_fc.match.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class KakaoPlaceRes {
    private String id;            // 카카오 place_id
    private String placeName;
    private String addressName;
    private double latitude;
    private double longitude;
}
