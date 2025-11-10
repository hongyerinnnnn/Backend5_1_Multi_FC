package com.multi.backend5_1_multi_fc.security;

import com.multi.backend5_1_multi_fc.user.dao.UserDao;
import com.multi.backend5_1_multi_fc.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDao userDao; // UserDao 생성

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new CustomUserDetails(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getNickname(),
                user.getLevel(),
                user.getProfileImage(),
                user.getPosition(),
                user.getGender(),
                user.getLoginFailCount(),
                user.getLockedUntil(),
                user.getLastCheckedCommentId()
        );
    }
}
