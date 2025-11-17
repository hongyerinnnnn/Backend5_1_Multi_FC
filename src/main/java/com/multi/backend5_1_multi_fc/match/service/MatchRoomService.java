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

    /** 경기방 생성 */
    @Transactional
    public MatchRoomDto create(MatchRoomCreateReq req) {

        // insert 시 자동 생성된 roomId가 req.roomId 에 채워짐
        matchRoomMapper.insert(req);

        // 방금 생성한 roomId 로 다시 조회
        return matchRoomMapper.findById(req.getRoomId());
    }

    public MatchRoomDto findById(Long roomId) {
        return matchRoomMapper.findById(roomId);
    }

    public List<MatchRoomDto> findByStadium(Long stadiumId) {
        return matchRoomMapper.findByStadium(stadiumId);
    }
}
