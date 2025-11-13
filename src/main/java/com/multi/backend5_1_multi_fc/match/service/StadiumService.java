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

    public List<StadiumSummaryRes> listAll() {
        return stadiumMapper.findAll();
    }

    @Transactional
    public List<StadiumSummaryRes> searchAndSave(String areaKeyword) {

        String[] keywords = {
                areaKeyword + " 풋살장",
                areaKeyword + " 축구장",
                areaKeyword + " 풋볼"
        };

        Map<String, KakaoPlaceRes> unique = new HashMap<>();

        for (String key : keywords) {
            List<KakaoPlaceRes> found = kakaoMapService.searchPlacesByArea(key);
            for (KakaoPlaceRes p : found) {
                unique.put(p.getId(), p);
            }
        }

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

        return stadiumMapper.findAll();
    }
}
