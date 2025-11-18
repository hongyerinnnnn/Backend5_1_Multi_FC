package com.multi.backend5_1_multi_fc.match.mapper;

import com.multi.backend5_1_multi_fc.match.dto.ParticipantDto; // ✅ DTO import
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MatchParticipantMapper {

    void insert(@Param("roomId") Long roomId, @Param("userId") Long userId);

    int existsByRoomAndUser(@Param("roomId") Long roomId, @Param("userId") Long userId);

    List<Long> findUserIdsByRoom(@Param("roomId") Long roomId);

    void delete(@Param("roomId") Long roomId, @Param("userId") Long userId);

    int countByRoom(@Param("roomId") Long roomId);

    // ▼▼▼ [새로 추가된 메서드] User 테이블과 조인하여 정보 가져오기 ▼▼▼

    /** 방장(Host) 정보 조회 */
    ParticipantDto findHostInfo(@Param("roomId") Long roomId);

    /** 일반 참가자(Player) 목록 조회 */
    List<ParticipantDto> findParticipantsInfo(@Param("roomId") Long roomId);
}