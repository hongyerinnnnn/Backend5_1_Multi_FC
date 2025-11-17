package com.multi.backend5_1_multi_fc.match.service;

import com.multi.backend5_1_multi_fc.match.domain.Stadium;
import com.multi.backend5_1_multi_fc.match.dto.KakaoPlaceRes;
import com.multi.backend5_1_multi_fc.match.dto.StadiumSummaryRes;
import com.multi.backend5_1_multi_fc.match.mapper.StadiumMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StadiumService {

    private final KakaoMapService kakaoMapService;
    private final StadiumMapper stadiumMapper;

    /** 전체 경기장 조회 */
    public List<StadiumSummaryRes> listAll() {
        return stadiumMapper.findAll();
    }

    /** 🔥 검색 후 DB 저장 + 검색된 구장만 반환 */
    @Transactional
    public List<StadiumSummaryRes> searchAndSave(String areaKeyword) {

        // Kakao 검색 키워드 3종
        String[] keywords = {
                areaKeyword + " 풋살장",
                areaKeyword + " 축구장",
                areaKeyword + " 풋볼"
        };

        // 중복 제거된 검색 결과 저장용
        Map<String, KakaoPlaceRes> unique = new HashMap<>();

        // ① 카카오 검색
        for (String key : keywords) {
            List<KakaoPlaceRes> found = kakaoMapService.searchPlacesByArea(key);
            for (KakaoPlaceRes p : found) {
                // place_id 로 중복 제거
                unique.put(p.getId(), p);
            }
        }

        // ② DB 저장 (기존 없을 때만)
        for (KakaoPlaceRes p : unique.values()) {
            if (stadiumMapper.countByName(p.getPlaceName()) == 0) {

                Stadium stadium = new Stadium();
                stadium.setName(p.getPlaceName());
                stadium.setAddress(p.getAddressName());
                stadium.setLatitude(p.getLatitude());
                stadium.setLongitude(p.getLongitude());

                stadiumMapper.insertFromKakao(stadium);
            }
        }

        // ③ 🔥 "검색된 구장만" 반환
        List<StadiumSummaryRes> resultList = new ArrayList<>();

        for (KakaoPlaceRes p : unique.values()) {
            resultList.add(new StadiumSummaryRes(
                    null, // DB 저장 후 ID는 필요 시 SELECT 해서 넣어도 됨
                    p.getPlaceName(),
                    p.getAddressName(),
                    p.getLatitude(),
                    p.getLongitude()
            ));
        }

        return resultList;
    }
}
