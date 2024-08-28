package ru.mudan.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mudan.payload.request.SignInRequest;
import ru.mudan.payload.request.SignUpRequest;
import ru.mudan.payload.response.JwtAuthenticationResponse;
import ru.mudan.services.AuthenticationService;
import ru.mudan.validation.ResponseErrorValidation;
import ru.mudan.validation.SignUpValidator;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final ResponseErrorValidation responseErrorValidation;
    private final SignUpValidator signUpValidator;
    private final AuthenticationService authenticationService;
    @Autowired
    public AuthController(ResponseErrorValidation responseErrorValidation, SignUpValidator signUpValidator, AuthenticationService authenticationService) {
        this.responseErrorValidation = responseErrorValidation;
        this.signUpValidator = signUpValidator;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody @Valid SignUpRequest request, BindingResult bindingResult) {
        signUpValidator.validate(request,bindingResult);
        ResponseEntity<Object>errors = responseErrorValidation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors))return errors;
        authenticationService.signUp(request);
        return new ResponseEntity<>("Пользователь успешно зарегистрирован", HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> signIn(@RequestBody @Valid SignInRequest request, BindingResult bindingResult) {
        ResponseEntity<Object>errors = responseErrorValidation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors))return errors;
        Optional<JwtAuthenticationResponse>optionalJwtAuthenticationResponse = authenticationService.signIn(request);
        return optionalJwtAuthenticationResponse.<ResponseEntity<Object>>map(jwtAuthenticationResponse -> new ResponseEntity<>(jwtAuthenticationResponse, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>("Неверное имя пользователя или пароль", HttpStatus.UNAUTHORIZED));
    }
}
