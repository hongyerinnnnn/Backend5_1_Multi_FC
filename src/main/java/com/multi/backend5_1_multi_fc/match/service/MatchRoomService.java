package com.multi.backend5_1_multi_fc.match.service;

import com.multi.backend5_1_multi_fc.match.dto.MatchRoomCreateReq;
import com.multi.backend5_1_multi_fc.match.dto.MatchRoomDto;
import com.multi.backend5_1_multi_fc.match.mapper.MatchRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchRoomService {

    private final MatchRoomMapper matchRoomMapper;
    private final MatchEventPublisher eventPublisher; // ✅ 이벤트 발행기 주입

    @Transactional
    public MatchRoomDto create(MatchRoomCreateReq req) {
        matchRoomMapper.insert(req);
        MatchRoomDto newRoom = matchRoomMapper.findById(req.getRoomId());

        // ✅ [추가] 실시간 경기 생성 알림 전송
        eventPublisher.publishNewMatchForStadium(newRoom.getStadiumId(), newRoom);

        return newRoom;
    }

    public MatchRoomDto findById(Long roomId) {
        return matchRoomMapper.findById(roomId);
    }

    public List<MatchRoomDto> findByStadium(Long stadiumId) {
        return matchRoomMapper.findByStadium(stadiumId);
    }
}