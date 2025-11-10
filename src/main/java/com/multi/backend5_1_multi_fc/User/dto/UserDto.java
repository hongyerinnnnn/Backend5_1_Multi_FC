package com.multi.backend5_1_multi_fc.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String username;
    private String email;
    private String password;
    private String nickname;
    private String profileImage;
    private String lastCheckedCommentId;
    private String address;
    private String level;                   //'초급', '중급', '고급', '미기입'
    private String position;                //'전체 (ALL)', '픽수 (FIXO)', '비보 (PIVO)', '골레이로(GOLEIRO)'
    private String gender;                  //'남성','여성'
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String resetCode;
    private LocalDateTime resetCodeExpires;
    private Integer loginFailCount;
    private LocalDateTime lockedUntil;
}
