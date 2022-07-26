package com.team2.cafein.controller;

import com.team2.cafein.dto.PostRequestDto;
import com.team2.cafein.dto.ResponseMessageDto;
import com.team2.cafein.model.Post;
import com.team2.cafein.model.User;
import com.team2.cafein.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/api/post")
    public PostRequestDto postForm(Model model) {
        PostRequestDto postRequestDto = new PostRequestDto();
        model.addAttribute("postRequestDto", postRequestDto);
        return postRequestDto;
    }

    @PostMapping("/api/post")
    public ResponseMessageDto createPost(
            @RequestPart(value = "postRequestDto") PostRequestDto requestDto,
            @RequestPart(value = "file") MultipartFile file) throws Exception {

        /* 파일을 업로드 하지 않았을 경우 처리 */
        if (file.isEmpty()) {
        }
//        String nickname = userDetails.getNicename();
//        return postService.createPostTest(requestDto, nickname);
        return postService.createPostTest(requestDto, file);

    }

    // 게시글 수정
//    @PutMapping("/api/post/{postId}")
//    public ResponseMessageDto updatePost(@RequestBody PostRequestDto requestDto, @PathVariable Long postId, MultipartFile file) throws Exception{
//        /* 파일을 업로드 하지 않았을 경우 처리 */
//        if (file.isEmpty()) {
//        }
//        return postService.updatePost(requestDto, postId,file);
//    }

    // 게시글 삭제
    @DeleteMapping("/api/post/{postId}")
    public ResponseMessageDto deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }


    // 게시글 전체 조회
    @GetMapping("/api/posts")
    public List<Post> getPosts(Model model) {

        // 게시판 번호와 model에 담긴 리스트에 번호와 일치하면 채워지게끔
//        List<Long> bookMarkIds = bookMarkService.findBookMarksIds(userDetails.getUser().getId());
//        model.addAttribute("bookMarkList", bookMarkIds);
        return postService.getPosts();
    }

//    // 내가 올린 게시글 조회
//    @GetMapping("/api/post/myposts")
//    public List<Post> getMyPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return postService.getMyPosts(userDetails.getUser());

//        // 아니면 -> 이게 맞는듯하다.
//        User user = userDetails.getUser();
//        return user.getPosts();
//    }
}
