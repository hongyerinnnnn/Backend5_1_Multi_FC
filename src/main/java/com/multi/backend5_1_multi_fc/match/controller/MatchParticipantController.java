package com.multi.backend5_1_multi_fc.match.controller;

import com.multi.backend5_1_multi_fc.match.dto.JoinReq;
import com.multi.backend5_1_multi_fc.match.service.MatchParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matchrooms")
@RequiredArgsConstructor
public class MatchParticipantController {

    private final MatchParticipantService participantService;

    /** 🔥 참가자가 특정 경기방 참여 */
    @PostMapping("/{roomId}/join")
    public void join(@PathVariable Long roomId, @RequestBody JoinReq req) {
        participantService.join(roomId, req.getUserId());
    }

    /** 🔥 특정 경기방의 기존 참여자 목록 조회 */
    @GetMapping("/{roomId}/participants")
    public List<Long> getParticipants(@PathVariable Long roomId) {
        return participantService.getParticipants(roomId);
    }
}
