package com.multi.backend5_1_multi_fc.match.service;

import com.multi.backend5_1_multi_fc.match.mapper.MatchParticipantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchParticipantService {

    private final MatchParticipantMapper participantMapper;
    private final MatchEventPublisher eventPublisher;

    /** 🔥 경기방 참여 + WebSocket 방송 */
    @Transactional
    public void join(Long roomId, Long userId) {

        if (participantMapper.existsByRoomAndUser(roomId, userId) == 0) {

            participantMapper.insert(roomId, userId);

            // 실시간 참가자 방송
            eventPublisher.publishNewParticipant(roomId, userId);
        }
    }

    /** 🔥 특정 경기방 기존 참여자 목록 조회 */
    public List<Long> getParticipants(Long roomId) {
        return participantMapper.findUserIdsByRoom(roomId);
    }
}
