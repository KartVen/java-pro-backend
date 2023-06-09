package pl.kartven.javaprobackend.restapi;

import lombok.*;

@Value
public class AuthResponse {
    String nickname;
    String bearerToken;
    String refreshToken;
}
