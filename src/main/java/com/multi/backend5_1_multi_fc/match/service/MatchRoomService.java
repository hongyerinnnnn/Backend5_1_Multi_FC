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
    private final MatchEventPublisher eventPublisher;

    @Transactional
    public MatchRoomDto create(MatchRoomCreateReq req) {

        matchRoomMapper.insert(req);
        MatchRoomDto created = matchRoomMapper.findLatest();

        // stadiumId 기반 방송 (프론트 WebSocket 경로와 일치)
        eventPublisher.publishNewMatchForStadium(
                req.getStadiumId(),
                created
        );

        return created;
    }

    public List<MatchRoomDto> findByStadium(Long stadiumId) {
        return matchRoomMapper.findByStadium(stadiumId);
    }
}
