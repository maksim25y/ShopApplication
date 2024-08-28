package ru.mudan.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    @NotBlank
    @Column(name = "firstname")
    private String firstname;
    @NotEmpty
    @NotBlank
    @Column(name = "lastname")
    private String lastname;
    @NotEmpty
    @NotBlank
    @Column(name = "username",unique = true)
    private String username;
    @NotEmpty
    @NotBlank
    @Column(name = "email",unique = true)
    private String email;
    @NotEmpty
    @NotBlank
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