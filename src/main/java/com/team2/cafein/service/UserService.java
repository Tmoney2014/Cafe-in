package com.team2.cafein.service;

import com.team2.cafein.dto.LoginRequestDto;
import com.team2.cafein.dto.ResponseMessageDto;
import com.team2.cafein.dto.SignupRequestDto;
import com.team2.cafein.model.User;
import com.team2.cafein.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional  // 요거 붙여놨습니다 -승한
    public ResponseMessageDto registerUser(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        User duplicatedUser = userRepository.findByEmail(requestDto.getEmail());
        if (duplicatedUser != null) {
            throw new IllegalArgumentException("중복된 사용자 E-Mail.");
        }

        String password = bCryptPasswordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getNickname(), email, password, "ROLE_USER");

        userRepository.save(user);

        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
        responseMessageDto.setStatus(true);
        responseMessageDto.setMessage("회원가입 성공");
        return responseMessageDto;
    }

    public ResponseMessageDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail());

        ResponseMessageDto responseMessageDto = new ResponseMessageDto();

        if (user==null||!bCryptPasswordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("아이디나 비밀번호 확인");
        }

        responseMessageDto.setStatus(true);
        responseMessageDto.setMessage("로그인 성공");
        return responseMessageDto;
    }
}
