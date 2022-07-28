package com.team2.cafein.dto;

import com.team2.cafein.model.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private Long id;

    private String nickName;
    private String cafeName;
    private String content;
    private int bookmarkCount;
    private Boolean bookMark;
    private LocalDateTime createdAt;
    private String imageUrl;

    @Builder
    public PostResponseDto(Post post, String imageUrl, Boolean bookMark) {

        this.id = post.getId();
        this.nickName = post.getUser().getNickname();
        this.createdAt = post.getCreatedAt();
        this.cafeName = post.getCafeName();
        this.content = post.getContent();
        this.bookmarkCount = post.getBookmarkCount();
        this.bookMark = bookMark;
        this.imageUrl = imageUrl;
    }
}
