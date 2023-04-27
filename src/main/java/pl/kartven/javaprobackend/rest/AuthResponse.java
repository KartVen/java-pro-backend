package pl.kartven.javaprobackend.rest;

import lombok.*;

@Value
public class AuthResponse {
    String nickname;
    String bearerToken;
    String refreshToken;
}
