package com.team2.cafein.controller;

import com.team2.cafein.dto.ResponseMessageDto;

import com.team2.cafein.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RequiredArgsConstructor
@RestController
public class LikesController {

    private final LikesService likesService;


    @GetMapping("/api/likes/{postId}") // 유저 아이디 어디서 갖고오냐 ...
    public ResponseMessageDto saveLikes(@PathVariable Long postId, Long userId){
        return likesService.saveLikes(userId,postId);
    }

    @DeleteMapping("/api/likes/{likesId}")
    public ResponseMessageDto deletelikes(@PathVariable Long likesId)  {
        return likesService.deletelikes(likesId);
    }

}
