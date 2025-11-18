package com.multi.backend5_1_multi_fc.match.controller;

import com.multi.backend5_1_multi_fc.match.dto.JoinReq;
import com.multi.backend5_1_multi_fc.match.dto.ParticipantDto; // ✅ DTO
import com.multi.backend5_1_multi_fc.match.service.MatchParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matchrooms")
@RequiredArgsConstructor
public class MatchParticipantController {

    private final MatchParticipantService participantService;

    @PostMapping("/{roomId}/join")
    public void join(@PathVariable Long roomId, @RequestBody JoinReq req) {
        participantService.join(roomId, req.getUserId());
    }

    @PostMapping("/{roomId}/cancel")
    public void cancel(@PathVariable Long roomId, @RequestBody JoinReq req) {
        participantService.cancel(roomId, req.getUserId());
    }

    @GetMapping("/{roomId}/participants")
    public List<Long> getParticipants(@PathVariable Long roomId) {
        return participantService.getParticipants(roomId);
    }

    /** ✅ [수정됨] 상세페이지용 전체 목록 조회 */
    @GetMapping("/{roomId}/participants/all")
    public List<ParticipantDto> getAllParticipants(@PathVariable Long roomId) {
        return participantService.getParticipantsWithHost(roomId);
    }
}