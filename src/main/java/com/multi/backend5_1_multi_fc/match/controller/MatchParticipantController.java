// 경기 참가/취소/참가자 조회 REST API
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

    /** 참가 */
    @PostMapping("/{roomId}/join")
    public void join(@PathVariable Long roomId, @RequestBody JoinReq req) {
        participantService.join(roomId, req.getUserId());
    }

    /** 참가 취소 (원하면 사용) */
    @PostMapping("/{roomId}/cancel")
    public void cancel(@PathVariable Long roomId, @RequestBody JoinReq req) {
        participantService.cancel(roomId, req.getUserId());
    }

    /** 참가자 목록 */
    @GetMapping("/{roomId}/participants")
    public List<Long> getParticipants(@PathVariable Long roomId) {
        return participantService.getParticipants(roomId);
    }
}
