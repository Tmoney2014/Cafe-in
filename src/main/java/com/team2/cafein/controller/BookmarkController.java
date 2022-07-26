package com.team2.cafein.controller;


import com.team2.cafein.dto.ResponseMessageDto;
import com.team2.cafein.model.Post;
import com.team2.cafein.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookmarkController {
    private final BookmarkService bookmarkService;

    //모든 북마크 포스트 갖고오기
    @GetMapping("/api/bookmark/{userId}")
   public List<Post> getBookmarkedPost(@PathVariable Long userId) throws IOException {

        return bookmarkService.getPosts(userId); //서비스의 getPosts 실행 결과값을 리턴으로 받는다. (List<post>) 의형식으로
    }

    @GetMapping("/api/bookmark/{postId}") // 유저 아이디 어디서 갖고오냐 ...
    public ResponseMessageDto savePost(@PathVariable Long postId,Long userId){
        return bookmarkService.savePost(userId,postId);
    }

    @DeleteMapping("/api/bookmark/{bookmarkId}")
    public ResponseMessageDto deleteBookmark(@PathVariable Long bookmarkId)  {
        return bookmarkService.deleteBookmark(bookmarkId);
    }
}
// 북마크 보기. = POST ID 의 리스트를 주는것이 아니고 for 문을 돌려서 POST 데이터를 줘야한다
// 북마크 설정은 POST ID 와 USER ID 만 저장하면 된다.
// 북마크 제거는 북마크 ID 로 제거 하면 끝이난다.