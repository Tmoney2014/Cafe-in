package com.team2.cafein.dto;

import com.team2.cafein.model.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private Long id;

//    private String nickName;
    private String cafeName;
    private String content;
    private int bookmarkCount;
    private Boolean bookMark;
    private String imageUrl;

    @Builder
    public PostResponseDto(Post post, String imageUrl) {
        this.id = post.getId();
        this.cafeName = post.getCafeName();
        this.content = post.getContent();
        this.bookmarkCount = post.getBookmarkCount();
        this.bookMark = true;
        this.imageUrl = imageUrl;
    }
}
