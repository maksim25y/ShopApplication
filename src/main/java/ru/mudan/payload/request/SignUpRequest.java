package ru.mudan.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Регистрация")
public class SignUpRequest {
    @Schema(description = "Имя",example = "Тестовое имя")
    @Size(min = 2, max = 20, message = "Имя должно быть от 2 до 20 символов длиной")
    @NotBlank(message = "Имя не должно быть пустым или состоять только из пробелов")
    private String firstname;
    @Schema(description = "Фамилия",example = "Тестовая фамилия")
    @Size(min = 2, max = 20, message = "Фамилия должна быть от 2 до 20 символов длиной")
    @NotBlank(message = "Фамилия не должна быть пустой или состоять только из пробелов")
    private String lastname;
    @Schema(description = "username",example = "username1")
    @Size(min = 6, max = 15, message = "username должен быть от 6 до 15 символов длиной")
    @NotBlank(message = "username не должен быть пустым или состоять только из пробелов")
    private String username;
    @Schema(description = "email",example = "ggg12as@mail.ru")
    @Email(message = "email должен быть в формате example@test.com")
    @NotBlank(message = "email не должен быть пустым или состоять только из пробелов")
    private String email;
    @Schema(description = "Пароль",example = "test_password")
    @Size(min = 8, max = 20, message = "Пароль должен быть от 8 до 20 символов длиной")
    @NotBlank(message = "Пароль не должен быть пустым или состоять только из пробелов")
    private String password;
}
