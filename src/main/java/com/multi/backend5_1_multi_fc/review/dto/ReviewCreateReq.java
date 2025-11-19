package com.multi.backend5_1_multi_fc.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateReq {
    // Review 테이블 ERD 기반 필드
    private Long stadiumId; // 구장 ID (FK)
    private Long userId;    // 사용자 ID (FK)
    private Integer rating; // 평점 (1~5)
    private String content; // 후기 내용

    // ⭐⭐⭐ [수정 필요] reviewId 필드 추가 및 Setter 허용 ⭐⭐⭐
    private Long reviewId;
}