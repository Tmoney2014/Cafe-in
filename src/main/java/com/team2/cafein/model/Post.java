package com.team2.cafein.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(nullable = false)
    private String imageUrl;

    private int bookmarkCount;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToOne(mappedBy = "post")
//    private CoffeeImg coffeeImg;

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
    public Post(String cafeName, String content, int bookmarkCount, String imageUrl, User user) {
        this.cafeName = cafeName;
        this.content = content;
        this.imageUrl = imageUrl;
        setUser(user);
        this.bookmarkCount = bookmarkCount;
    }
    public static Post createPost(PostRequestDto postRequestDto, User user) {
        return Post.builder()
                .cafeName(postRequestDto.getCafeName())
                .content(postRequestDto.getContent())
                .imageUrl(postRequestDto.getImageUrl())
                .user(user)
                .bookmarkCount(0)
                .build();
    }

    public void updatePost(PostRequestDto postRequestDto) {
        this.cafeName = postRequestDto.getCafeName();
        this.content = postRequestDto.getContent();
        this.imageUrl = postRequestDto.getImageUrl();
    }
}
