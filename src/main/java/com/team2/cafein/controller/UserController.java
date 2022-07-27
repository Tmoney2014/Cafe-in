package com.team2.cafein.controller;

import com.team2.cafein.dto.LoginRequestDto;
import com.team2.cafein.dto.ResponseMessageDto;
import com.team2.cafein.dto.SignupRequestDto;
import com.team2.cafein.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test() {
        return "<h1>Test page</h1>";
    }

    @PostMapping("/user/signup")
    public ResponseMessageDto signup(@RequestBody SignupRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }

    @PostMapping("/user/login")
    public ResponseMessageDto login(@RequestBody LoginRequestDto requestDto) {
        return userService.login(requestDto);
    }
}
