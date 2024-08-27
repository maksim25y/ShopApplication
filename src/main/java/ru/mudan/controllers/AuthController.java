package ru.mudan.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mudan.payload.request.SignInRequest;
import ru.mudan.payload.request.SignUpRequest;
import ru.mudan.services.AuthenticationService;
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody @Valid SignUpRequest request) {
        return new ResponseEntity<>(authenticationService.signUp(request), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> signIn(@RequestBody @Valid SignInRequest request) {
        return new ResponseEntity<>(authenticationService.signIn(request), HttpStatusCode.valueOf(200));
    }
}
