package com.team2.cafein.controller;

import com.team2.cafein.config.auth.UserDetailsImpl;
import com.team2.cafein.dto.PostRequestDto;
import com.team2.cafein.dto.PostResponseDto;
import com.team2.cafein.dto.ResponseMessageDto;
import com.team2.cafein.dto.UpdatePostDto;
import com.team2.cafein.model.Post;
import com.team2.cafein.model.User;
import com.team2.cafein.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    // 게시글 등록 페이지 - 프론트에서 해결
//    @GetMapping("/api/post")
//    public PostRequestDto postForm(Model model) {
//        PostRequestDto postRequestDto = new PostRequestDto();
//        model.addAttribute("postRequestDto", postRequestDto);
//        return postRequestDto;
//    }

    // 게시글 디테일 조회 --> OK --> 북마크 수정
    @GetMapping("/api/post/{postId}")
    public PostResponseDto get(@PathVariable Long postId) {
        return postService.findOne(postId);
    }

    // 게시글 등록 --> OK
    @PostMapping("/api/post")
    public ResponseMessageDto createPost(
            @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            return postService.createPost(postRequestDto, userId);
        }
        return new ResponseMessageDto(false, "로그인이 필요합니다.");
    }

    // 게시글 수정 페이지 --> 프론트에서 한다. 게시글 디테일 조회 API 활용
//    @GetMapping("/api/post/{postId}/edit")
//    public UpdatePostDto updateForm(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return postService.getUpdatePostDto(postId);
//    }

    // 게시글 수정 --> OK
    @PutMapping("/api/post/{postId}")
    public ResponseMessageDto updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        System.out.println(userDetails.getUsername());

        if (userDetails != null) {
            String nickname = userDetails.getUser().getNickname();
            return postService.updatePost(postRequestDto, postId, nickname);
        }
        return new ResponseMessageDto(false, "로그인이 필요합니다.");
    }
//        /* 파일을 업로드 하지 않았을 경우 처리 */
//        if (file.isEmpty()) {
//            UpdatePostDto.PostImageDto postImageDto = postService.getPostImageDto(postId);
//            updatePostDto.setPostImageDto(postImageDto);
//        }
//        try {
//            postService.updatePost(postRequestDto, postId, userId);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
//        responseMessageDto.setStatus(true);
//        responseMessageDto.setMessage("게시글 !! 수정 !! 성공");
//        return responseMessageDto;
//    }

    // 게시글 삭제 --> OK
    // PropertyValueException : not-null property references a null or transient value
    // --> @Transactional & 그리고 삭제 순서로 해결
    @DeleteMapping("/api/post/{postId}")
    public ResponseMessageDto deletePost(@PathVariable Long postId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        if (userDetails != null) {
            String loginNickName = userDetails.getUser().getNickname();
            return postService.deletePost(postId, loginNickName);
        }
        return new ResponseMessageDto(false, "로그인이 필요합니다.");
    }

    // 게시글 전체 조회 --> Boolean 지역변수 문제였다.
    @GetMapping("/api/posts")
    public List<PostResponseDto> getPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<PostResponseDto> posts = new ArrayList<>();
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            posts = postService.getPosts(userId);
        }
        return posts;
    }

    // 내가 올린 게시글 조회 --> OK -->
    @GetMapping("/api/post/myposts")
    public List<PostResponseDto> getMyPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
////  Resolved [org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: failed to lazily initialize a collection of role: com.team2.cafein.model.User.posts, could not initialize proxy - no Session; nested exception is com.fasterxml.jackson.databind.JsonMappingException:
////  failed to lazily initialize a collection of role: com.team2.cafein.model.User.posts, could not initialize proxy - no Session]
////        User user = userDetails.getUser();
////        return user.getPosts();

        return postService.getMyPosts(userDetails.getUser());
////  Failure while trying to resolve exception [org.springframework.http.converter.HttpMessageNotWritableException]
////  Cannot call sendError() after the response has been committed
    }
}
