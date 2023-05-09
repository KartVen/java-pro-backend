package pl.kartven.javaprobackend.http;

import lombok.*;

@Value
public class AuthResponse {
    String nickname;
    String bearerToken;
    String refreshToken;
}
