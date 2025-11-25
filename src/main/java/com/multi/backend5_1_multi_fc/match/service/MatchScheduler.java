package com.multi.backend5_1_multi_fc.match.service;

import com.multi.backend5_1_multi_fc.match.mapper.MatchRoomMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchScheduler {

    private final MatchRoomMapper matchRoomMapper;

    /**
     * 매 1분마다 실행되어 종료 시각이 지난 경기를 자동으로 'CLOSED'로 처리합니다.
     */
    // 매분 0초에 실행
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void closeExpiredMatches() {
        // 현재 위치 (대한민국/서울 시간) 기준으로 작동합니다.
        LocalDateTime now = LocalDateTime.now();

        // MatchRoomMapper를 호출하여 시간이 지난 매칭방의 상태를 'CLOSED'로 업데이트
        int updatedCount = matchRoomMapper.updateStatusToClosedIfExpired(now);

        if (updatedCount > 0) {
            log.info("✅ 스케줄러: 경기 종료 시간 초과로 {}개의 매칭방이 'CLOSED'로 업데이트되었습니다.", updatedCount);
        }
    }
}