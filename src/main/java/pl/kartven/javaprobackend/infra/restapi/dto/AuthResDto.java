package pl.kartven.javaprobackend.infra.restapi.dto;

import lombok.Value;

@Value
public class AuthResDto {
    Long id;
    String nickname;
    String email;
    String bearerToken;
    String refreshToken;
}
