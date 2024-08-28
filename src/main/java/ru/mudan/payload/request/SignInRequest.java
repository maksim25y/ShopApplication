package ru.mudan.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignInRequest {
    @NotEmpty
    @NotBlank
    private String username;
    @NotEmpty
    @NotBlank
    private String password;
}
