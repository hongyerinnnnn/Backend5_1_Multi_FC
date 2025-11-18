// 참가자 테이블 CRUD 정의
package com.multi.backend5_1_multi_fc.match.mapper;

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
}
