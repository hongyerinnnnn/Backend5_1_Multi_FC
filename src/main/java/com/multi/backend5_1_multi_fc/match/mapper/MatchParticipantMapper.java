package com.multi.backend5_1_multi_fc.match.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MatchParticipantMapper {

    void insert(@Param("roomId") Long roomId, @Param("userId") Long userId);

    int existsByRoomAndUser(@Param("roomId") Long roomId, @Param("userId") Long userId);

    List<Long> findUserIdsByRoom(@Param("roomId") Long roomId);
}
