package com.multi.backend5_1_multi_fc.match.mapper;

import com.multi.backend5_1_multi_fc.match.dto.MatchRoomCreateReq;
import com.multi.backend5_1_multi_fc.match.dto.MatchRoomDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MatchRoomMapper {

    void insert(MatchRoomCreateReq req);  // PK 자동 주입됨

    MatchRoomDto findById(Long roomId);

    List<MatchRoomDto> findByStadium(Long stadiumId);
}
