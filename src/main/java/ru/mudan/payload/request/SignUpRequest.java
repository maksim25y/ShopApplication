package ru.mudan.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @Size(min = 2, max = 20, message = "Имя должно быть от 2 до 20 символов длиной")
    @NotBlank(message = "Имя не должно быть пустым или состоять только из пробелов")
    private String firstname;
    @Size(min = 2, max = 20, message = "Фамилия должна быть от 2 до 20 символов длиной")
    @NotBlank(message = "Фамилия не должна быть пустой или состоять только из пробелов")
    private String lastname;
    @Size(min = 6, max = 15, message = "username должен быть от 6 до 15 символов длиной")
    @NotBlank(message = "username не должен быть пустым или состоять только из пробелов")
    private String username;
    @Email(message = "email должен быть в формате example@test.com")
    @NotBlank(message = "email не должен быть пустым или состоять только из пробелов")
    private String email;
    @Size(min = 8, max = 20, message = "Пароль должен быть от 8 до 20 символов длиной")
    @NotBlank(message = "Пароль не должен быть пустым или состоять только из пробелов")
    private String password;
}
