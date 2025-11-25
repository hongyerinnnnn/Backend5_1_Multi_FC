package com.multi.backend5_1_multi_fc.match.service;

import com.multi.backend5_1_multi_fc.match.dto.MatchRoomCreateReq;
import com.multi.backend5_1_multi_fc.match.dto.MatchRoomDto;
import com.multi.backend5_1_multi_fc.match.mapper.MatchRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchRoomService {

    private final MatchRoomMapper matchRoomMapper;
    private final MatchEventPublisher eventPublisher;

    @Transactional
    public MatchRoomDto create(MatchRoomCreateReq req) {

        // ⭐⭐⭐ [수정된 부분: endTime 자동 계산 로직 추가] ⭐⭐⭐
        if (req.getEndTime() == null || req.getEndTime().trim().isEmpty()) {
            try {
                // matchTime을 LocalTime 객체로 파싱 (DB/DTO에서 "HH:mm" 형식으로 가정)
                LocalTime startTime = LocalTime.parse(req.getMatchTime(), DateTimeFormatter.ofPattern("HH:mm"));

                // 기본 2시간을 더한 종료 시간 계산
                LocalTime defaultEndTime = startTime.plusHours(2);

                // 다시 문자열로 포맷하여 DTO에 설정 (DB 형식에 맞춰 HH:mm으로 가정)
                req.setEndTime(defaultEndTime.format(DateTimeFormatter.ofPattern("HH:mm")));

            } catch (Exception e) {
                // 만약 matchTime 파싱에 실패하면 (형식 오류), 예외를 던져 매치 생성을 중단합니다.
                throw new IllegalArgumentException("경기 시작 시간(matchTime) 처리에 실패했습니다. 형식을 확인해주세요.", e);
            }
        }
        // ⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐

        matchRoomMapper.insert(req);
        MatchRoomDto newRoom = matchRoomMapper.findById(req.getRoomId());
        eventPublisher.publishNewMatchForStadium(newRoom.getStadiumId(), newRoom);
        return newRoom;
    }

    public MatchRoomDto findById(Long roomId) {
        return matchRoomMapper.findById(roomId);
    }

    public List<MatchRoomDto> findByStadium(Long stadiumId) {
        return matchRoomMapper.findByStadium(stadiumId);
    }

    // ⭐️ [추가] 내가 참가/생성한 경기 목록 조회
    public List<MatchRoomDto> findByUserId(Long userId) {
        return matchRoomMapper.findByUserId(userId);
    }

    @Transactional
    public void closeMatch(Long roomId) {
        matchRoomMapper.updateStatus(roomId, "CLOSED");
    }
}