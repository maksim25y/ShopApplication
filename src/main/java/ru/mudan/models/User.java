package ru.mudan.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.mudan.models.enums.Role;

import java.util.Collection;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 20, message = "Имя должно быть от 2 до 20 символов длиной")
    @NotBlank(message = "Имя не должно быть пустым или состоять только из пробелов")
    @Column(name = "firstname")
    private String firstname;
    @Size(min = 2, max = 20, message = "Фамилия должна быть от 2 до 20 символов длиной")
    @NotBlank(message = "Фамилия не должна быть пустой или состоять только из пробелов")
    @Column(name = "lastname")
    private String lastname;
    @Size(min = 6, max = 15, message = "username должен быть от 6 до 15 символов длиной")
    @NotBlank(message = "username не должен быть пустым или состоять только из пробелов")
    @Column(name = "username",unique = true)
    private String username;
    @Email(message = "email должен быть в формате example@test.com")
    @NotBlank(message = "email не должен быть пустым или состоять только из пробелов")
    @Column(name = "email",unique = true)
    private String email;
    @Size(min = 8, max = 20, message = "Пароль должен быть от 8 до 20 символов длиной")
    @NotBlank(message = "Пароль не должен быть пустым или состоять только из пробелов")
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}