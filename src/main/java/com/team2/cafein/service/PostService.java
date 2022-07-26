package com.team2.cafein.service;

import com.team2.cafein.dto.PostRequestDto;
import com.team2.cafein.dto.ResponseMessageDto;
import com.team2.cafein.model.Post;
import com.team2.cafein.model.User;
import com.team2.cafein.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final CoffeeImgService coffeeImgService;

    /**
     * 게시글 등록
     */
//    @Transactional
//    public ResponseMessageDto createPost(PostRequestDto requestDto, MultipartFile file) throws Exception{
//        // 저장 경로 설정
//        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
//        // 중복 되지 않는 파일명 설정
//        UUID uuid = UUID.randomUUID();
//        // uuid + 받아온 파일 이름
//        String fileName = uuid + "_" + file.getOriginalFilename();
//        String filePath = "/files/" + fileName;
//
//        File saveFile = new File(projectPath, fileName);
//
//        file.transferTo(saveFile);
//
////        Post post = new Post(requestDto, fileName, filePath, user);
//        Post post = new Post(requestDto, fileName, filePath);
//
//        // 게시글 저장
//        postRepository.save(post);
//
//        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
//        responseMessageDto.setStatus(true);
//        responseMessageDto.setMessage("게시글 등록 성공");
//        return responseMessageDto;
//    }

    /**
     * 게시글 수정
     */
    @Transactional
    public ResponseMessageDto updatePost(PostRequestDto requestDto, Long postId ,MultipartFile file) throws Exception{

        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
        responseMessageDto.setStatus(true);
        responseMessageDto.setMessage("게시글 수정 성공");
        return responseMessageDto;
    }

    /**
     * 내가 올린 게시글 조회
     */
    public List<Post> getMyPosts(User user) {
        return postRepository.findAllByUser(user);
    }

    /**
     * 전체 게시글 조회
     */
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    /**
     * 게시글 삭제
     */
    public ResponseMessageDto deletePost(Long postId) {
        postRepository.deleteById(postId);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
        responseMessageDto.setStatus(true);
        responseMessageDto.setMessage("게시글 삭제 성공");
        return responseMessageDto;
    }

    @Transactional
    public ResponseMessageDto createPostTest(PostRequestDto requestDto, MultipartFile file) throws IOException {

        // 회원 조회
//        User user = userService.findMemberByEmail(nickname)
//                .orElseThrow(() -> new NullPointerException("해당 회원은 존재하지 않습니다."));

        // 게시글 등록
        Post post = requestDto.toEntity();
//        Post savePost = Post.createPost(post, user);
        Post savePost = Post.createPost(post);
        postRepository.save(savePost);

        // 게시글 이미지 등록
        coffeeImgService.savePostImage(savePost, file);

        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
        responseMessageDto.setStatus(true);
        responseMessageDto.setMessage("게시글 등록 성공");
        return responseMessageDto;
    }
}

