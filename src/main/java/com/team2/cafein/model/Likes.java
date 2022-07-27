package com.team2.cafein.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "likes_id", nullable = false)
    private Long id;

    private Long postId;

    private Long userId;

    public Likes(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;

    }
}
