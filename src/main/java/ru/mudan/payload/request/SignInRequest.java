package ru.mudan.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Вход")
public class SignInRequest {
    @Schema(description = "username",example = "username1")
    @NotBlank(message = "username не должен быть пустым или состоять только из пробелов")
    private String username;
    @Schema(description = "Пароль",example = "test_password")
    @NotBlank(message = "Пароль не должен быть пустым или состоять только из пробелов")
    private String password;
}
