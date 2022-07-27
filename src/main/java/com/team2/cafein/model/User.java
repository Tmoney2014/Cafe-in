package com.team2.cafein.model;

import com.team2.cafein.base.Timestamped;
import com.team2.cafein.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    // 여기서의 "user"는 Post에 있는 user필드에 의해서 매핑된 거울 역할
    // mappedBy 속성 : 나는 주인이 아니에요. 나는 연견관계의 거울이에요.(읽기 전용)
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public User(SignupRequestDto requestDto) {
        this.nickname = requestDto.getNickname();
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
    }

    public User(String nickname, String email, String password, String role) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    //
}
