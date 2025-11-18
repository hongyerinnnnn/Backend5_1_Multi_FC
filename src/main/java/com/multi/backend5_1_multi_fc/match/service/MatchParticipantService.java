package com.multi.backend5_1_multi_fc.match.service;

import com.multi.backend5_1_multi_fc.match.dto.MatchRoomDto;
import com.multi.backend5_1_multi_fc.match.dto.ParticipantDto; // ✅ DTO
import com.multi.backend5_1_multi_fc.match.mapper.MatchParticipantMapper;
import com.multi.backend5_1_multi_fc.match.mapper.MatchRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchParticipantService {

    private final MatchParticipantMapper participantMapper;
    private final MatchRoomMapper matchRoomMapper;
    private final MatchEventPublisher eventPublisher;
    // private final UserMapper userMapper;  <-- ❌ UserMapper 필요 없음!

    /** 참가 */
    @Transactional
    public void join(Long roomId, Long userId) {
        MatchRoomDto room = matchRoomMapper.findById(roomId);

        if (room.getHostId().equals(userId)) {
            return;
        }

        if (participantMapper.existsByRoomAndUser(roomId, userId) == 0) {
            participantMapper.insert(roomId, userId);

            int currentCount = participantMapper.countByRoom(roomId);
            // ✅ 실시간 알림
            eventPublisher.publishParticipantEvent(roomId, userId, "JOIN", currentCount);
        }
    }

    /** 참가 취소 */
    @Transactional
    public void cancel(Long roomId, Long userId) {
        MatchRoomDto room = matchRoomMapper.findById(roomId);

        if (room.getHostId().equals(userId)) {
            return;
        }

        if (participantMapper.existsByRoomAndUser(roomId, userId) > 0) {
            participantMapper.delete(roomId, userId);

            int currentCount = participantMapper.countByRoom(roomId);
            // ✅ 실시간 알림
            eventPublisher.publishParticipantEvent(roomId, userId, "LEAVE", currentCount);
        }
    }

    public List<Long> getParticipants(Long roomId) {
        return participantMapper.findUserIdsByRoom(roomId);
    }

    /** * ✅ [수정됨] Host + Player 포함 전체 정보 반환
     * UserMapper 없이 Match Mapper의 JOIN 쿼리로 해결
     */
    public List<ParticipantDto> getParticipantsWithHost(Long roomId) {

        List<ParticipantDto> allParticipants = new ArrayList<>();

        // 1. 방장(Host) 정보 가져오기
        ParticipantDto host = participantMapper.findHostInfo(roomId);
        if (host != null) {
            allParticipants.add(host);
        }

        // 2. 참가자(Player) 목록 가져오기
        List<ParticipantDto> players = participantMapper.findParticipantsInfo(roomId);
        if (players != null) {
            allParticipants.addAll(players);
        }

        return allParticipants;
    }
}