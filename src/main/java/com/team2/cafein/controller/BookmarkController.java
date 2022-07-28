package com.team2.cafein.controller;


import com.team2.cafein.config.auth.UserDetailsImpl;
import com.team2.cafein.dto.PostResponseDto;
import com.team2.cafein.dto.ResponseMessageDto;
import com.team2.cafein.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    //UserId 를  갖고 모든 북마크 포스트 갖고오기
    @GetMapping("/api/bookmark/")
   public List<PostResponseDto> getBookmarkedPost(@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        Long userId = userDetails.getUser().getId();
        return bookmarkService.getPosts(userId); //서비스의 getPosts 실행 결과값을 리턴으로 받는다. (List<post>) 의형식으로
    }
//북마크  저장하기 북마크 버튼을 누른 사용자디테일에서 userId를 갖고와서 그걸 postId 와 함께 북마크로 저장
    @GetMapping("/api/bookmark/{postId}")
    public ResponseMessageDto savePost(@PathVariable Long postId,@AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails.getUser().getId();
        return bookmarkService.savePost(userId,postId);
    }
//북마크 삭제 bookmarkId 값으로 북마크 삭제
    @DeleteMapping("/api/bookmark/{postId}")
    public ResponseMessageDto deleteBookmark(@PathVariable Long postId,@AuthenticationPrincipal UserDetailsImpl userDetails)  {
        Long userId = userDetails.getUser().getId();
        return bookmarkService.deleteBookmark(userId,postId);
    }
}
// 북마크 보기. = POST ID 의 리스트를 주는것이 아니고 for 문을 돌려서 POST 데이터를 주어야한다.
// 북마크 설정은 POST ID 와 USER ID 만 저장하면 된다.
// 북마크 제거는 북마크 ID 로 제거 하면 된다.