package ru.mudan.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignInRequest {
    @NotBlank(message = "username не должен быть пустым или состоять только из пробелов")
    private String username;
    @NotBlank(message = "Пароль не должен быть пустым или состоять только из пробелов")
    private String password;
}
