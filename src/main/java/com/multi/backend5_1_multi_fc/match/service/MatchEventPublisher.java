package com.multi.backend5_1_multi_fc.match.service;

import com.multi.backend5_1_multi_fc.match.dto.MatchRoomDto;
import com.multi.backend5_1_multi_fc.match.dto.ParticipantEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchEventPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    /** 🔥 경기 생성 (stadiumId 별 topic) */
    public void publishNewMatchForStadium(Long stadiumId, MatchRoomDto room) {
        messagingTemplate.convertAndSend("/topic/matches/" + stadiumId, room);
    }

    /** 🔥 특정 경기방 참가자 업데이트 topic */
    public void publishNewParticipant(Long roomId, Long userId) {
        messagingTemplate.convertAndSend(
                "/topic/matchroom/" + roomId + "/participants",
                new ParticipantEvent(roomId, userId)
        );
    }
}
