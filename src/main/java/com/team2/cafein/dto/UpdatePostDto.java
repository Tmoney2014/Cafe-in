package com.team2.cafein.dto;


import com.team2.cafein.model.CoffeeImg;
import com.team2.cafein.model.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class UpdatePostDto {

    private Long postId;

    private String userName;
    private String cafeName;

    private String content;

    private PostImageDto postImageDto;

    private String originalImageName;

    public static UpdatePostDto of(Post post, CoffeeImg coffeeImg) {

        PostImageDto postImageDto = PostImageDto.of(coffeeImg);

        UpdatePostDto updatePostDto = UpdatePostDto.builder()
                .postId(post.getId())
                .cafeName(post.getCafeName())
                .content(post.getContent())
                .postImageDto(postImageDto)
                .build();

        return updatePostDto;
    }
    public Post toEntity() {
        return Post.builder()
                .cafeName(cafeName)
                .content(content)
                .bookmarkCount(0)
                .build();
    }

    @Builder
    @Getter @Setter
    public static class PostImageDto {

        private Long coffeeImgId;
        private String originalImageName;
        private String imageUrl;
        public static PostImageDto of(CoffeeImg coffeeImg) {
            return PostImageDto.builder()
                    .coffeeImgId(coffeeImg.getId())
                    .originalImageName(coffeeImg.getOriginalImageName())
                    .imageUrl(coffeeImg.getImageUrl())
                    .build();
        }
    }
    //
}
