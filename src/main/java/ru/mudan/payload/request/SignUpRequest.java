package ru.mudan.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotEmpty
    @NotBlank
    private String firstname;
    @NotEmpty
    @NotBlank
    private String lastname;
    @NotEmpty
    @NotBlank
    private String username;
    @NotEmpty
    @NotBlank
    private String email;
    @NotEmpty
    @NotBlank
    private String password;
}
