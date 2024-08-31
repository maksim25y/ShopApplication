package ru.mudan.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    Long id;
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
}