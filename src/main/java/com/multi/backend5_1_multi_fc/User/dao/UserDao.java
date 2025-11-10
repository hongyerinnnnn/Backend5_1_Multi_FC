package com.multi.backend5_1_multi_fc.user.dao;

import com.multi.backend5_1_multi_fc.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserDao {
    //username으로 사용자 조회
    Optional<UserDto> findByUsername(String username);

    //userId로 사용자 조회
    Optional<UserDto> findByUserId(Long userId);

    //email로 사용자 조회
    Optional<UserDto> findByEmail(String email);

    //사용자 등록
    void insert(UserDto user);

    //사용자 정보 수정
    void update(UserDto user);

    //로그인 실패 횟수 증가
    void incrementLoginFailCount(String username);

    //로그인 실패 횟수 초기화
    void resetLoginFailCount(String username);
}
