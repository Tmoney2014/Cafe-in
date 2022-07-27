package com.team2.cafein.service;

import com.team2.cafein.dto.ResponseMessageDto;
import com.team2.cafein.model.Likes;
import com.team2.cafein.repository.LikesRepository;
import org.springframework.stereotype.Service;

@Service
public class LikesService {

    private final LikesRepository likesRepository;

    public LikesService(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }

    public ResponseMessageDto saveLikes(Long userId, Long postId) {

        //로그인 정보에서 userid 갖고 와서 +
        Likes likes = new Likes(userId, postId);
        likesRepository.save(likes);

        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
        responseMessageDto.setStatus(true);
        if (responseMessageDto.isStatus() == true) {
            responseMessageDto.setMessage("등록성공");
        } else {
            responseMessageDto.setMessage("등록실패");

        }
        return responseMessageDto;
    }

    public ResponseMessageDto deletelikes(Long likesId) {
        // 비지니스 로직 구간
        likesRepository.deleteById(likesId);
        // ---------------------------

        // 응답 객체 만들기
        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
        responseMessageDto.setStatus(true);
        if (responseMessageDto.isStatus() == true) {
            responseMessageDto.setMessage("등록성공");
        } else {
            responseMessageDto.setMessage("등록실패");

        }
        return responseMessageDto;
    }
}





