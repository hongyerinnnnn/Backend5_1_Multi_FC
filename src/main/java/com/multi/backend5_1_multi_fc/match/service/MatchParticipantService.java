// 경기 참가/취소 비즈니스 로직
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

    /** 참가 */
    @Transactional
    public void join(Long roomId, Long userId) {
        if (participantMapper.existsByRoomAndUser(roomId, userId) == 0) {
            participantMapper.insert(roomId, userId);
            int currentCount = participantMapper.countByRoom(roomId);
            eventPublisher.publishParticipantEvent(roomId, userId, "JOIN", currentCount);
        }
    }

    /** 참가 취소 */
    @Transactional
    public void cancel(Long roomId, Long userId) {
        if (participantMapper.existsByRoomAndUser(roomId, userId) > 0) {
            participantMapper.delete(roomId, userId);
            int currentCount = participantMapper.countByRoom(roomId);
            eventPublisher.publishParticipantEvent(roomId, userId, "LEAVE", currentCount);
        }
    }

    /** 현재 참여자 목록 */
    public List<Long> getParticipants(Long roomId) {
        return participantMapper.findUserIdsByRoom(roomId);
    }
}
