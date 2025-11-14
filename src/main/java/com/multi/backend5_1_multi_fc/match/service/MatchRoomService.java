package com.multi.backend5_1_multi_fc.match.service;

import com.multi.backend5_1_multi_fc.match.dto.MatchRoomCreateReq;
import com.multi.backend5_1_multi_fc.match.dto.MatchRoomDto;
import com.multi.backend5_1_multi_fc.match.mapper.MatchRoomMapper;
import com.multi.backend5_1_multi_fc.match.service.MatchEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MatchRoomService {

    private final MatchRoomMapper matchRoomMapper;

    @Transactional
    public MatchRoomDto create(MatchRoomCreateReq req) {
        matchRoomMapper.insert(req);
        return matchRoomMapper.findLatest();
    }

    public MatchRoomDto findById(Long roomId) {
        return matchRoomMapper.findById(roomId);
    }

    public List<MatchRoomDto> findByStadium(Long stadiumId) {
        return matchRoomMapper.findByStadium(stadiumId);
    }
}
