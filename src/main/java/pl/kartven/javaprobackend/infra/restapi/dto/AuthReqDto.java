package pl.kartven.javaprobackend.infra.restapi.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AuthReqDto {
    @Data
    @NoArgsConstructor
    public static class Login {
        @Email
        private String email;

        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}\\[\\]:;<>,.?/~_+-=|]).{8,32}$", message = "Regex password validation error")
        @Size(min = 8, max = 32)
        private String password;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    public static class Register extends Login {
        @Size(min = 5, max = 125)
        @NotBlank
        private String nickname;
    }
}
