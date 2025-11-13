package com.multi.backend5_1_multi_fc.match.service;

import com.multi.backend5_1_multi_fc.match.mapper.MatchParticipantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchParticipantService {
    private final MatchParticipantMapper participantMapper;

    @Transactional
    public void join(Long roomId, Long userId) {
        if (participantMapper.existsByRoomAndUser(roomId, userId) == 0) {
            participantMapper.insert(roomId, userId);
        }
    }
}
