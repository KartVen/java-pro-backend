package pl.kartven.javaprobackend.infra.restapi.dto;

import lombok.*;

@Value
public class AuthResponse {
    String nickname;
    String bearerToken;
    String refreshToken;
}
