package com.multi.backend5_1_multi_fc.review.service;

import com.multi.backend5_1_multi_fc.review.dto.ReviewCreateReq;
import com.multi.backend5_1_multi_fc.review.dto.ReviewDto;
import com.multi.backend5_1_multi_fc.review.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;

    @Transactional
    public void createReview(ReviewCreateReq req) {
        if (req.getRating() == null || req.getRating() < 1 || req.getRating() > 5) {
            throw new IllegalArgumentException("평점은 1에서 5 사이여야 합니다.");
        }
        // [TODO] 경기 확정/종료 여부 및 참여자 여부 검증 로직 추가
        reviewMapper.insert(req);
    }

    public List<ReviewDto> getReviewsByStadium(Long stadiumId) {
        return reviewMapper.findByStadiumId(stadiumId);
    }

    // --- [신규/수정] ---

    /** 후기 수정 */
    @Transactional
    public void updateReview(Long reviewId, Long userId, Integer rating, String content) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("평점은 1에서 5 사이여야 합니다.");
        }

        // Mapper의 WHERE 절에서 userId를 이용해 권한을 확인합니다.
        int updatedRows = reviewMapper.update(reviewId, userId, rating, content);

        if (updatedRows == 0) {
            // 해당 ID의 후기가 없거나 (404), 작성자가 아님 (403)
            throw new SecurityException("해당 후기를 수정할 권한이 없거나 후기를 찾을 수 없습니다.");
        }
    }

    /** 후기 삭제 */
    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        // Mapper의 WHERE 절에서 userId를 이용해 권한을 확인합니다.
        int deletedRows = reviewMapper.delete(reviewId, userId);

        if (deletedRows == 0) {
            // 해당 ID의 후기가 없거나 (404), 작성자가 아님 (403)
            throw new SecurityException("해당 후기를 삭제할 권한이 없거나 후기를 찾을 수 없습니다.");
        }
    }
}