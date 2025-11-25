package com.multi.backend5_1_multi_fc.match.mapper;

import com.multi.backend5_1_multi_fc.match.dto.MatchRoomCreateReq;
import com.multi.backend5_1_multi_fc.match.dto.MatchRoomDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime; // 💡 import 추가
import java.util.List;

@Mapper
public interface MatchRoomMapper {

    void insert(MatchRoomCreateReq req);

    MatchRoomDto findById(Long roomId);

    List<MatchRoomDto> findByStadium(Long stadiumId);

    // ⭐️ [추가] 특정 사용자가 참여/생성한 매치룸 조회
    List<MatchRoomDto> findByUserId(@Param("userId") Long userId);

    void updateStatus(@Param("roomId") Long roomId, @Param("status") String status);

    // ✨ 자동 마감 로직을 위한 메서드 추가
    int updateStatusToClosedIfExpired(@Param("currentTime") LocalDateTime currentTime);
}