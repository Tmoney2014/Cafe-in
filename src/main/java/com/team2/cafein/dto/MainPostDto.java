package com.team2.cafein.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainPostDto {

    private Long id;
    private String title;
    private String content;
    private String imgUrl;
}
