package com.multi.backend5_1_multi_fc.match.controller;

import com.multi.backend5_1_multi_fc.match.dto.JoinReq;
import com.multi.backend5_1_multi_fc.match.service.MatchParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matchrooms")
@RequiredArgsConstructor
public class MatchParticipantController {

    private final MatchParticipantService participantService;

    @PostMapping("/{roomId}/join")
    public void join(@PathVariable Long roomId, @RequestBody JoinReq req) {
        participantService.join(roomId, req.getUserId());
    }
}
