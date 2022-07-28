package com.team2.cafein.dto;

import com.team2.cafein.model.CoffeeImg;
import com.team2.cafein.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    private String cafeName;

    private String content;

    private String imageUrl;

    public Post toEntity() {
        return Post.builder()
                .cafeName(cafeName)
                .content(content)
                .imageUrl(imageUrl)
                .build();
    }
}
