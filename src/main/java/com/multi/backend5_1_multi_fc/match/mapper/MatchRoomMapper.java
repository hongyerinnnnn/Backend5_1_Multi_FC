package com.multi.backend5_1_multi_fc.match.mapper;

import com.multi.backend5_1_multi_fc.match.dto.MatchRoomCreateReq;
import com.multi.backend5_1_multi_fc.match.dto.MatchRoomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MatchRoomMapper {

    void insert(MatchRoomCreateReq req);

    MatchRoomDto findLatest();

    List<MatchRoomDto> findByStadium(Long stadiumId);

    MatchRoomDto findById(Long roomId);
}

