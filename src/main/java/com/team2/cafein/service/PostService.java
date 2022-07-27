package com.team2.cafein.service;

import com.team2.cafein.dto.PostRequestDto;
import com.team2.cafein.dto.PostResponseDto;
import com.team2.cafein.dto.ResponseMessageDto;
import com.team2.cafein.dto.UpdatePostDto;
import com.team2.cafein.model.Bookmark;
import com.team2.cafein.model.CoffeeImg;
import com.team2.cafein.model.Post;
import com.team2.cafein.model.User;
import com.team2.cafein.repository.BookmarkRepository;
import com.team2.cafein.repository.CoffeeImgRepository;
import com.team2.cafein.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final CoffeeImgService coffeeImgService;
    private final BookmarkRepository bookmarkRepository;

    /**
     * 게시글 등록 - 해결
     */
    @Transactional
//    public ResponseMessageDto createPost(PostRequestDto requestDto, MultipartFile file) throws IOException {
    public ResponseMessageDto createPost(String cafeName, String content, MultipartFile file) throws IOException {

        // 회원 조회
//        User user = userService.findMemberByEmail(nickname)
//                .orElseThrow(() -> new NullPointerException("해당 회원은 존재하지 않습니다."));

        // 게시글 등록
//        Post Post = requestDto.toEntity();
//        Post savePost = Post.createPost(Post, user);
        Post savePost = Post.createPost(cafeName, content);
        postRepository.save(savePost);

        // 게시글 이미지 등록
        coffeeImgService.savePostImage(savePost, file);

        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
        responseMessageDto.setStatus(true);
        responseMessageDto.setMessage("게시글 등록 성공");
        return responseMessageDto;
    }

    /**
     * 게시글 수정
     */
    public UpdatePostDto getUpdatePostDto(Long PostId) {
        Post Post = postRepository.findById(PostId).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다."));

        CoffeeImg coffeeImg = coffeeImgService.findByPost(Post);
        return UpdatePostDto.of(Post, coffeeImg);
    }

    public UpdatePostDto.PostImageDto getPostImageDto(Long PostId) {
        Post Post = postRepository.findById(PostId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다."));

//        CoffeeImg coffeeImg = coffeeImgService.findByItemOrderByItemImageIdAsc(item);
        CoffeeImg coffeeImg = coffeeImgService.findByPost(Post);
        return UpdatePostDto.PostImageDto.of(coffeeImg);
    }

    @Transactional
    public void updatePost(UpdatePostDto updatePostDto, MultipartFile file) throws IOException {
        // 게시글 업데이트
        Post Post = updatePostInfo(updatePostDto);

        // 커피 이미지 업데이트
        updatePostImage(updatePostDto, Post, file);
    }

    private Post updatePostInfo(UpdatePostDto updatePostDto) {
        Post updatePost = updatePostDto.toEntity();
        Post updatedPost = updatePostInformation(updatePostDto.getPostId(), updatePost);
        return updatedPost;
    }

    @Transactional
    public Post updatePostInformation(Long PostId, Post updatePost) {
        Post savedPost = postRepository.findById(PostId)
                .orElseThrow(() -> new NullPointerException("없는 게시판"));
        savedPost.updatePost(updatePost);
        return savedPost;
    }

    private void updatePostImage(UpdatePostDto updatePostDto, Post Post, MultipartFile file) throws IOException {

        // 데이터베이스에 저장된 상품 이미지 정보
        CoffeeImg coffeeImg = coffeeImgService.findByPost(Post);
        String originalImageName = updatePostDto.getOriginalImageName(); // 상품 수정 화면 조회 시에 있던 상품 이미지명 정보
        MultipartFile PostImageFile = file; // 상품 파일 이미지 정보

        if (!PostImageFile.isEmpty()) {  // 기존 파일 수정 or 신규 파일 등록 처리
            coffeeImgService.updatePostImage(coffeeImg, PostImageFile);
        } else if (!StringUtils.hasText(originalImageName) &&
                StringUtils.hasText(coffeeImg.getOriginalImageName())) { // 기존 파일 삭제
            coffeeImgService.deletePostImage(coffeeImg);
        }
    }

    /**
     * 내가 올린 게시글 조회 - 해결
     */
    public List<Post> getMyPosts(User user) {
        return postRepository.findAllByUser(user);
    }

    /**
     * 전체 게시글 조회 - ResponseDto 를 따로 만들어서 담고 보내준다.
     */
    public List<PostResponseDto> getPosts() {
        // 북마크 처리랑, 닉네임 넣어야함
        // 로그인 되어있는 userId로 Bookmark 테이블에서 select로 리스트 배열 받아오기
//        List<Bookmark> bookmarks = bookmarkRepository.findAllByUserId(userId);
//
//        // 접속한 유저가 북마크한 Post의 리스트
//        List<Post> responsePosts = new ArrayList<>();   // 여기는 응답할 게시글 목록을 위한 리스트 선언
//
//        for (Bookmark bookmark : bookmarks) { //for 문을 돌리면서 POST ID 에 대응되는 post의 내용을 List<post> 에 담아서 리턴
//            Long postId = bookmark.getPostId();
//            Post post = postRepository.findById(postId)
//                    .orElseThrow(() -> new NullPointerException("ID값 확인해주세여"));
//            responsePosts.add(post);
//        }

        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();

        List<PostResponseDto> listPost = new ArrayList<>();
        for (Post post : posts) {
            String imageUrl = post.getCoffeeImg().getImageUrl();
            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .post(post)
                    .imageUrl(imageUrl)
                    .build();
            listPost.add(postResponseDto);
        }
        return listPost;
    }

    /**
     * 게시글 삭제
     */
    public ResponseMessageDto deletePost(Long PostId) {
        postRepository.deleteById(PostId);
//        PostRepository.find
        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
        responseMessageDto.setStatus(true);
        responseMessageDto.setMessage("게시글 삭제 성공");
        return responseMessageDto;
    }

    public Post findOne(Long PostId) {
        Post Post = postRepository.findById(PostId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다."));
        return Post;
    }
    //
}

