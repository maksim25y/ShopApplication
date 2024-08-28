package ru.mudan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mudan.dto.UserDTO;
import ru.mudan.facafe.UserFacade;
import ru.mudan.models.User;
import ru.mudan.models.enums.Role;
import ru.mudan.payload.request.SignInRequest;
import ru.mudan.payload.request.SignUpRequest;
import ru.mudan.payload.response.JwtAuthenticationResponse;
import ru.mudan.security.JwtService;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserFacade userFacade;
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public AuthenticationService(UserFacade userFacade, UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userFacade = userFacade;
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
    public void signUp(SignUpRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .lastname(request.getLastname())
                .firstname(request.getFirstname())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        userService.create(user);
    }
    public Optional<JwtAuthenticationResponse> signIn(SignInRequest request) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
        }catch (BadCredentialsException e){
            return Optional.empty();
        }
        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return Optional.of(new JwtAuthenticationResponse(jwt));
    }
}
