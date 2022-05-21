package com.lookatthis.flora.controller;

import com.lookatthis.flora.model.ChatMessage;
import com.lookatthis.flora.storage.UserStorage.UserStorage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/Websocket")
@Api(tags = {"Chatting API"})
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @ApiOperation(value = "메세지 보내기")
    @MessageMapping("/sendto/{loginId}")
    public void sendMessage(@DestinationVariable String loginID, ChatMessage chatMessage) {
        //chatMessage를 loginID 에게 보낸다 (로그 출력)
        log.info("handling send message : "+chatMessage + " #to :"+loginID );
        //loginID가 <채팅 리스트>에 존재하면 전송
        boolean isExists = UserStorage.getInstance().getUsers().contains(loginID);

        if(isExists) {
            simpMessagingTemplate.convertAndSend("/topic/messages"+loginID, chatMessage);
        }

    }
}
