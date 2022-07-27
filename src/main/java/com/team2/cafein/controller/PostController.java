package com.team2.cafein.controller;

import com.team2.cafein.dto.PostRequestDto;
import com.team2.cafein.dto.PostResponseDto;
import com.team2.cafein.dto.ResponseMessageDto;
import com.team2.cafein.dto.UpdatePostDto;
import com.team2.cafein.model.Post;
import com.team2.cafein.model.User;
import com.team2.cafein.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    // 게시글 등록 페이지
    @GetMapping("/api/post")
    public PostRequestDto postForm(Model model) {
        PostRequestDto postRequestDto = new PostRequestDto();
        model.addAttribute("postRequestDto", postRequestDto);
        return postRequestDto;
    }

    // 게시글 디테일 조회
    @GetMapping("/api/post/{postId}")
    public Post get(@PathVariable Long postId, Model model) {
        return postService.findOne(postId);
    }

    // 게시글 등록
    @PostMapping("/api/post")
    public ResponseMessageDto createPost(
            @RequestParam String cafeName,
            @RequestParam String content,
//            @RequestPart(value = "postRequestDto") PostRequestDto postRequestDto,
            @RequestPart(value = "file") MultipartFile file) throws Exception {

        /* 파일을 업로드 하지 않았을 경우 처리 */
        if (file.isEmpty()) {
            ResponseMessageDto responseMessageDto = new ResponseMessageDto();
            responseMessageDto.setStatus(false);
            responseMessageDto.setMessage("파일 첨부는 필수입니다");
            return responseMessageDto;
        }
//        String nickname = userDetails.getNickname();
//        return postService.createPost(postRequestDto, nickname);
//        return postService.createPost(postRequestDto, file);
        return postService.createPost(cafeName, content, file);

    }

    // 게시글 수정 페이지
    @GetMapping("/api/post/{postId}/edit")
    public UpdatePostDto updateForm(@PathVariable Long postId, Model model) {
        return postService.getUpdatePostDto(postId);
    }

    // 게시글 수정
    @PutMapping("/api/post/{postId}")
    public ResponseMessageDto updatePost(@PathVariable Long postId,
                                         @RequestPart(value = "updatePostDto") UpdatePostDto updatePostDto,
                                         @RequestPart(value = "file") MultipartFile file,
                                         Model model) throws Exception{
        /* 파일을 업로드 하지 않았을 경우 처리 */
        if (file.isEmpty()) {
            UpdatePostDto.PostImageDto postImageDto = postService.getPostImageDto(postId);
            updatePostDto.setPostImageDto(postImageDto);
        }
//        return postService.updatePost(updatePostDto, postId,file);

        try {
            postService.updatePost(updatePostDto, file);
        } catch (Exception e) {
            log.error(e.getMessage());
//            bindingResult.reject("globalError", "상품 수정 중 에러가 발생하였습니다.");
//            return "adminitem/updateitemform";
        }
        return new ResponseMessageDto();
    }

    // 게시글 삭제
    @DeleteMapping("/api/post/{postId}")
    public ResponseMessageDto deletePost(@PathVariable Long postId) {

        return postService.deletePost(postId);
    }

    // 게시글 전체 조회
    @GetMapping("/api/posts")
    public List<PostResponseDto> getPosts() {
        // Response 용 Dto를 만들어서 보내주자
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
    //

}
