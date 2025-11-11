package com.multi.backend5_1_multi_fc.chat.dao;

import com.multi.backend5_1_multi_fc.chat.dto.ChatRoomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatRoomDao {
    List<ChatRoomDto> findChatRoomsByType(String roomType);
    ChatRoomDto findChatRoomById(Long roomId);
    void insertChatRoom(ChatRoomDto chatRoom);
    void updateMemberCount(Long roomId, int memberCount);
    void deleteChatRoom(Long roomId);

}
