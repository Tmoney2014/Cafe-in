package com.team2.cafein.model;

import com.team2.cafein.base.Timestamped;
import com.team2.cafein.dto.PostRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends Timestamped {

    @Id
    @GeneratedValue
    @Column(name = "post_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String cafeName;

    @Column(nullable = false)
    private String content;

    private int bookmarkCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "post")
    private CoffeeImg coffeeImg;

    // 안쓸듯
    public Post(PostRequestDto requestDto) {
        this.cafeName = requestDto.getCafeName();
        this.content = requestDto.getContent();
        this.bookmarkCount = 0;
//        setUser(user);
    }

    //== 연관관계 (편의) 메서드==// 양방향 연관관계 세팅을 까먹지않고 할수있는 장점
    public void setUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }

    // 북마크 증가
    public void addCount() {
        this.bookmarkCount++;
    }

    @Builder
    public Post(String cafeName, String content, int bookmarkCount) {
        this.cafeName = cafeName;
        this.content = content;
//        setUser(user);
        this.bookmarkCount = bookmarkCount;
    }

//    public static Post createPost(Post post) {
//        return Post.builder()
//                .cafeName(post.getCafeName())
//                .content(post.getContent())
////                .user(user)
//                .bookmarkCount(0)
//                .build();
//    }
    public static Post createPost(String cafeName, String content) {
        return Post.builder()
                .cafeName(cafeName)
                .content(content)
//                .user(user)
                .bookmarkCount(0)
                .build();
    }

    public void updatePost(Post updatePost) {
        this.cafeName = updatePost.getCafeName();
        this.content = updatePost.getContent();
        this.bookmarkCount = updatePost.getBookmarkCount();
    }
    //
}
