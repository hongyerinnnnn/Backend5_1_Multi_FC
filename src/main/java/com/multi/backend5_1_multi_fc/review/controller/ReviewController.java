package com.multi.backend5_1_multi_fc.review.controller;

import com.multi.backend5_1_multi_fc.review.dto.ReviewCreateReq;
import com.multi.backend5_1_multi_fc.review.dto.ReviewDto;
import com.multi.backend5_1_multi_fc.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 편의상 임시 사용자 ID를 가져오는 메서드 (실제로는 Spring Security 등으로 대체 필요)
    private Long getCurrentUserId() {
        // 이 로직은 실제 인증 시스템에 맞게 구현되어야 합니다.
        // 여기서는 임시로 1L을 반환하거나, 요청 DTO에서 가져온다고 가정합니다.
        return 1L;
    }

    /** POST /api/reviews : 후기 등록 */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReview(@RequestBody ReviewCreateReq req) {
        // [TODO] 실제 구현 시, 세션/JWT에서 userId를 가져와 req.setUserId(userId) 해야 합니다.
        // [TODO] 경기가 확정/종료되었는지 확인하는 로직이 Service에 있어야 합니다.
        reviewService.createReview(req);
    }

    /** GET /api/reviews/stadium/{stadiumId} : 구장별 후기 목록 조회 */
    @GetMapping("/stadium/{stadiumId}")
    public List<ReviewDto> getReviewsByStadium(@PathVariable Long stadiumId) {
        return reviewService.getReviewsByStadium(stadiumId);
    }

    // --- [신규/수정] ---

    /** PUT /api/reviews/{reviewId} : 후기 수정 (본인만 가능) */
    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long reviewId, @RequestBody ReviewCreateReq req) {
        Long userId = req.getUserId(); // 프론트에서 임시로 userId를 같이 보낸다고 가정
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 정보가 필요합니다.");
        }

        try {
            reviewService.updateReview(reviewId, userId, req.getRating(), req.getContent());
            return ResponseEntity.ok().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /** DELETE /api/reviews/{reviewId} : 후기 삭제 (본인만 가능) */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId, @RequestParam Long userId) {
        // 쿼리 파라미터로 userId를 받아서 본인 확인 (실제로는 Security Context 사용)

        try {
            reviewService.deleteReview(reviewId, userId);
            return ResponseEntity.ok().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("후기를 찾을 수 없습니다.");
        }
    }
}