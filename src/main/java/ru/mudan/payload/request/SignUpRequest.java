package ru.mudan.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
}
