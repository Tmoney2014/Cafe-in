package com.team2.cafein.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
