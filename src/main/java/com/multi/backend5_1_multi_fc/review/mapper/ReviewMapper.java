package com.multi.backend5_1_multi_fc.review.mapper;

import com.multi.backend5_1_multi_fc.review.dto.ReviewCreateReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {
    /** 새로운 후기 삽입 */
    // keyProperty="reviewId"를 통해 생성된 PK를 ReviewCreateReq 객체에 다시 주입할 수 있습니다.
    void insert(ReviewCreateReq req);
}