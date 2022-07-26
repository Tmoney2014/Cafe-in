package com.team2.cafein.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Entity
@NoArgsConstructor
public class CoffeeImg {

    @Id
    @GeneratedValue
    private Long id;

    private String imageName;     // 실제 로컬에 저장된 이미지 파일 이름 // 실제로 로컬에 저장할 이미지 파일명

    private String originalImageName;  // 업로드했던 이미지 파일 초기 이름 // 원본 이미지 파일명

    private String imageUrl;      // 업로드 결과 로컬에 저장된 이미지 파일을 불러올 경로 // 이미지 조회 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable = false)
    private Post post;

    @Builder
    public CoffeeImg(String imageName, String originalImageName, String imageUrl, Post post) {
        this.imageName = imageName;
        this.originalImageName = originalImageName;
        this.imageUrl = imageUrl;
        this.post = post;
    }

    public static CoffeeImg createPostImage(CoffeeImg itemImage, Post post) {
        return CoffeeImg.builder()
                .imageName(itemImage.getImageName())
                .originalImageName(itemImage.getOriginalImageName())
                .imageUrl(itemImage.getImageUrl())
                .post(post)
                .build();
    }

    public void initPostInfo() {
        this.originalImageName = "";
        this.imageName = "";
        this.imageUrl = "";
    }
}
