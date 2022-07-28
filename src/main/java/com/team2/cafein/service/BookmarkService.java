package com.team2.cafein.service;

import com.team2.cafein.dto.PostResponseDto;
import com.team2.cafein.dto.ResponseMessageDto;
import com.team2.cafein.model.Bookmark;
import com.team2.cafein.model.Post;
import com.team2.cafein.repository.BookmarkRepository;
import com.team2.cafein.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;

    @Transactional
//    public List<Post> getPosts(Long userId) {
//
//        // 로그인 되어있는 userId로 Bookmark 테이블에서 select로 리스트 배열 받아오기
//        List<Bookmark> bookmarks = bookmarkRepository.findAllByUserId(userId);
//
//        List<Post> responsePosts = new ArrayList<>();   // 여기는 응답할 게시글 목록을 위한 리스트 선언
//
//        for (Bookmark bookmark : bookmarks) { //for 문을 돌리면서 POST ID 에 대응되는 post의 내용을 List<post> 에 담아서 리턴
//            Long postId = bookmark.getPostId();
//            Post post = postRepository.findById(postId)
//                    .orElseThrow(() -> new NullPointerException("ID값 확인해주세여"));
//            responsePosts.add(post);
//        }
//        return responsePosts;
//
//    }

    public List<PostResponseDto> getPosts(Long userId) {
        //북마크된 포스트 아이디 찾기 (userId로)
        List<Bookmark> bookmarks = bookmarkRepository.findByUserId(userId);

        //찾은 postId 로 post 찾아 리스트로 저장
        List<Post> bookmarkPosts = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            Long postId = bookmark.getPostId();
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new NullPointerException("ID값 확인해주세요"));
            bookmarkPosts.add(post);
        }
        //post 위의 포스트 리스트를 리스폰스Dto 형식에 맞게 데이터 get하여 리스트로 저장
        List<Post> posts;
        posts = bookmarkPosts;
        List<PostResponseDto> listPost = new ArrayList<>();
        Boolean bookMark = true;
        for (Post post : posts) {
            String imageUrl = post.getImageUrl();
            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .post(post)
                    .imageUrl(imageUrl)
                    .bookMark(bookMark)
                    .build();
            listPost.add(postResponseDto);
        }
        return listPost;  //컨트롤러로 리턴
    }

    public ResponseMessageDto savePost(Long userId, Long postId) {

        //로그인 정보에서 userid 갖고 오고, 넘겨 받은 postId 로 새로운 bookmark 만들고 저장
        Bookmark bookmark = new Bookmark(userId, postId);
        bookmarkRepository.save(bookmark);

        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
        responseMessageDto.setStatus(true);
        if ((responseMessageDto.isStatus())) {
            responseMessageDto.setMessage("등록성공");
        } else {
            responseMessageDto.setMessage("등록실패");

        }
        return responseMessageDto;
    }

    public ResponseMessageDto deleteBookmark(Long userId, Long postId) {
        // 비지니스 로직 구간
        //북마크된 포스트 아이디 찾기 (userId로)
        // 1
        List<Bookmark> bookmarks = bookmarkRepository.findByUserId(userId);

        // 2. 4:1 2:1 6:1 7:1
        for (Bookmark bookmark : bookmarks) {
            if (bookmark.getPostId() == postId) {
                bookmarkRepository.deleteById(bookmark.getId());
            }
        }

//        List<Bookmark> bookmarks = bookmarkRepository.findByUserIdAndPostId(userId,postId);
//        for (Bookmark bookmark : bookmarks) {
//            Long bookmarkId = bookmark.getId(); // 여기여? 네 bookmarkID 가 나와여 잠시만여
//            bookmarkRepository.deleteById(bookmarkId);
//        }
        // --------------------------- 네?? 그렇게 되면 userid 같은 놈들 다 나와서 그거 get id 하면
        // 그사람 이갖고있는 모든 북마크 해제 되는거 아닌가요 ?? 아 맞네여 흠.........
        //  저기서 getId 하면 bookmarkId  가 나오는게 맞죠 ?

        // 응답 객체 만들기
        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
        responseMessageDto.setStatus(true);
        if (responseMessageDto.isStatus()) {
            responseMessageDto.setMessage("등록성공");
        } else {
            responseMessageDto.setMessage("등록실패");

        }
        return responseMessageDto;
    }
}


