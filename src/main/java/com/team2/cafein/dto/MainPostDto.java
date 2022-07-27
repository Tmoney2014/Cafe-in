package com.team2.cafein.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MainPostDto {

    private Long id;        // 게시글 ID
    private String cafeName;   // 카페이름
    private String content; // 내용
    private String imgUrl;  // 이미지 경로
    //
}
