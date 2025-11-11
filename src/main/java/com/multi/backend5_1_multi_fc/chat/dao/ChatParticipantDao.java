package com.multi.backend5_1_multi_fc.chat.dao;

import com.multi.backend5_1_multi_fc.chat.dto.ChatParticipantDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatParticipantDao {
    List<ChatParticipantDto> findParticipantsByRoomId(Long roomId);
    void insertParticipant(ChatParticipantDto participant);
    void deleteParticipant(Long roomId, Long userId); //참가자 강퇴
}
