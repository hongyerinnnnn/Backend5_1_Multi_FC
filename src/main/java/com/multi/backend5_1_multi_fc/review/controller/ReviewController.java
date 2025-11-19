package com.multi.backend5_1_multi_fc.review.controller;

import com.multi.backend5_1_multi_fc.review.dto.ReviewCreateReq;
import com.multi.backend5_1_multi_fc.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReview(@RequestBody ReviewCreateReq req) {
        // ⭐ 중요: 실제 환경에서는 JWT/Session을 통해 로그인된 사용자 ID를 가져와 DTO에 설정해야 합니다.
        // 현재는 클라이언트에서 임시로 userId를 전송하는 것으로 가정하고 서비스를 호출합니다.

        reviewService.createReview(req);
    }
}