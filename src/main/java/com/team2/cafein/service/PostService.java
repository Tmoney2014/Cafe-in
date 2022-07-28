package com.team2.cafein.service;

import com.team2.cafein.dto.PostRequestDto;
import com.team2.cafein.dto.PostResponseDto;
import com.team2.cafein.dto.ResponseMessageDto;
import com.team2.cafein.model.Bookmark;
import com.team2.cafein.model.Post;
import com.team2.cafein.model.User;
import com.team2.cafein.repository.BookmarkRepository;
import com.team2.cafein.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserService userService;

    /**
     * 게시글 등록 - 해결
     */
    @Transactional
//    public ResponseMessageDto createPost(PostRequestDto requestDto, MultipartFile file) throws IOException {
    public ResponseMessageDto createPost(PostRequestDto postRequestDto, Long userId) throws IOException {

        /* 파일을 업로드 하지 않았을 경우 처리 */
        if (postRequestDto.getImageUrl() == "") {
            return new ResponseMessageDto(false, "파일 첨부는 필수입니다");
        }
        // 회원 조회
        User user = userService.findOne(userId);

        // 게시글 등록
//        Post Post = requestDto.toEntity();
//        Post savePost = Post.createPost(Post, user);
        Post savePost = Post.createPost(postRequestDto, user);
        postRepository.save(savePost);

        // 게시글 이미지 등록
//        CoffeeImg coffeeImg = coffeeImgService.savePostImage(savePost);
//        savePost.setCoffeeImg(coffeeImg);

        return new ResponseMessageDto(true, "게시글 등록 성공");
    }

//    /**
//     * 게시글 수정
//     */
//    public UpdatePostDto getUpdatePostDto(Long postId) {
//        Post post = postRepository.findById(postId).orElseThrow(
//                () -> new NullPointerException("아이디가 존재하지 않습니다."));
//
//        CoffeeImg coffeeImg = coffeeImgService.findByPost(post);
//        return UpdatePostDto.of(post, coffeeImg);
//    }

    /**
     * 하나의 게시글 정보 가져가기
     */
//    public UpdatePostDto.PostImageDto getPostImageDto(Long postId) {
//        Post post = postRepository.findById(postId).orElseThrow(
//                () -> new NullPointerException("존재하지 않는 게시글입니다."));
//
////        CoffeeImg coffeeImg = coffeeImgService.findByItemOrderByItemImageIdAsc(item);
//        CoffeeImg coffeeImg = coffeeImgService.findByPost(post);
//        return UpdatePostDto.PostImageDto.of(coffeeImg);
//    }

    /**
     * 게시글 수정 - 해결
     */
    @Transactional
    public ResponseMessageDto updatePost(PostRequestDto postRequestDto, Long postId, String nickName) throws IOException {
        // 게시글 업데이트
//        Post post = updatePostInfo(updatePostDto);
//        // 커피 이미지 업데이트
//        updatePostImage(updatePostDto, post, file);
//        PostResponseDto.of(post, postRequestDto.getImageUrl());

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시판압니다"));
        if (Objects.equals(post.getUser().getNickname(), nickName)) {
            // 수정 로직
            post.updatePost(postRequestDto);
            return new ResponseMessageDto(true, "게시글 수정 성공");
        }
        return new ResponseMessageDto(false, "게시글을 작성한 사용자가 아닙니다");
    }

//    private Post updatePostInfo(UpdatePostDto updatePostDto) {
//        Post updatePost = updatePostDto.toEntity();
//        Post updatedPost = updatePostInformation(updatePostDto.getPostId(), updatePost);
//        return updatedPost;
//    }

//    @Transactional
//    public Post updatePostInformation(Long postId, Post updatePost) {
//        Post savedPost = postRepository.findById(postId)
//                .orElseThrow(() -> new NullPointerException("없는 게시판"));
//        savedPost.updatePost(updatePost);
//        return savedPost;
//    }

//    private void updatePostImage(UpdatePostDto updatePostDto, Post post, MultipartFile file) throws IOException {
//
//        // 데이터베이스에 저장된 상품 이미지 정보
//        CoffeeImg coffeeImg = coffeeImgService.findByPost(post);
//        String originalImageName = updatePostDto.getOriginalImageName(); // 상품 수정 화면 조회 시에 있던 상품 이미지명 정보
//        MultipartFile postImageFile = file; // 상품 파일 이미지 정보
//
//        if (!postImageFile.isEmpty()) {  // 기존 파일 수정 or 신규 파일 등록 처리
//            coffeeImgService.updatePostImage(coffeeImg, postImageFile);
//        } else if (!StringUtils.hasText(originalImageName) &&
//                StringUtils.hasText(coffeeImg.getOriginalImageName())) { // 기존 파일 삭제
//            coffeeImgService.deletePostImage(coffeeImg);
//        }
//    }

    /**
     * 게시글 삭제 - 해결
     */
    @Transactional
    public ResponseMessageDto deletePost(Long postId, String loginNickName) throws IOException {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

//        CoffeeImg coffeeImg = coffeeImgService.findByPost(post);

        String userNickName = post.getUser().getNickname();

        if (Objects.equals(userNickName, loginNickName)) {
            // 삭제
//            coffeeImgService.deletePostImage(coffeeImg);
//            coffeeImgRepository.deleteById(coffeeImg.getId());
            bookmarkRepository.deleteAllByPostId(postId);
            postRepository.deleteById(postId);

        } else new IllegalArgumentException("작성한 유저가 아닙니다.");

        return new ResponseMessageDto(true, "게시글 삭제 성공");
    }

    /**
     * 전체 게시글 조회 - 하나를 북마크 하면 로그인한 유저 ID로 등록한 게시글들이 다 북마크가 true로 바뀌는 문제
     * 북마크에 추가되는 칼럼은 하나이다.
     * 123456 중에서 3번을 북마크하면 123이 다 북마크된다.
     */
    public List<PostResponseDto> getPosts(Long userId) {
        // 전체 게시글을 작성시간 순서로 추출
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(); // OK
        return getPostResponseDtoList(posts, userId);
    }

    /**
     * 내가 올린 게시글 조회
     */
    public List<PostResponseDto> getMyPosts(User user) {
//        List<Post> myPosts = postRepository.findAllByUserByOrderByCreatedAtDesc(user); // X

        // 내가 올린 게시글을 작성시간 순서로 추출
        List<Post> myPosts = postRepository.findByUserOrderByCreatedAtDesc(user); // OK
        List<PostResponseDto> postResponseDtoResult = getPostResponseDtoList(myPosts, user.getId());
        return postResponseDtoResult;
    }

    // 응답 리스트 만드는 메소드
    private List<PostResponseDto> getPostResponseDtoList(List<Post> posts, Long userId) {

        // 접속한 사용자가 북마크한 Bookmark 리스트 얻어오기
        List<Bookmark> bookmarks = bookmarkRepository.findAllByUserId(userId); // OK

        // 접속한 사용자가 북마크한 Bookmark의 게시글 ID 리스트 만들기
//        List<Long> bookmarkedPostIds = new ArrayList<>();
        HashSet<Long> bookmarkedPostIds = new HashSet<>();
        for (Bookmark bookmark : bookmarks) {
//            bookmarkedPostIds.add(bookmark.getPostId());
            bookmarkedPostIds.add(bookmark.getPostId());
        }

        Boolean bookMark;

        // 프론트에 보내줄 빈 객체 선언
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        //---------------------------------------------------------//
        for (Post post : posts) {
//            for (Long bookmarkedPostId : bookmarkedPostIds) {
//                // 여기 부분에서 같은 숫자가 맞으면 변하는건 맞는데, 그때 true이 변하지 않고
//                // 계속 true로 유지가 되서 4번을 북마크하면 2번3번까지 true로 변하는것 같습니다
//                if (bookmarkedPostId == post.getId()) {
//                    bookMark = true;
////                    break;
//                }
//            }
            if (bookmarkedPostIds.contains(post.getId())) {
                bookMark = true;
            } else {
                bookMark = false;
            }
            String imageUrl = post.getImageUrl();
            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .post(post)
                    .imageUrl(imageUrl)
                    .bookMark(bookMark)
                    .build();
            postResponseDtos.add(postResponseDto);
        }
        //---------------------------------------------------------//
        return postResponseDtos;
    }

    /**
     * 게시글 디테일 조회 - true 고정값이 아니라 북마크가 맞는지 확인해야함
     */
    public PostResponseDto findOne(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다."));
//        String imageUrl = post.getCoffeeImg().getImageUrl();

        PostResponseDto postResponseDto = PostResponseDto.builder()
                .post(post)
                .imageUrl(post.getImageUrl())
                .bookMark(true)
                .build();
        return postResponseDto;
    }
}

