package com.multi.backend5_1_multi_fc.review.mapper;

import com.multi.backend5_1_multi_fc.review.dto.ReviewCreateReq;
import com.multi.backend5_1_multi_fc.review.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {
    /** 새로운 후기 삽입 */
    void insert(ReviewCreateReq req);

    /** 특정 구장의 후기 목록 조회 */
    List<ReviewDto> findByStadiumId(Long stadiumId);

    // --- [신규] ---

    /** 후기 수정 (ID와 USER_ID로 검증) */
    int update(@Param("reviewId") Long reviewId,
               @Param("userId") Long userId,
               @Param("rating") Integer rating,
               @Param("content") String content);

    /** 후기 삭제 (ID와 USER_ID로 검증) */
    int delete(@Param("reviewId") Long reviewId, @Param("userId") Long userId);
}