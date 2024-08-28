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
import ru.mudan.services.AuthenticationService;
import ru.mudan.validation.ResponseErrorValidation;
import ru.mudan.validation.SignUpValidator;

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
        return new ResponseEntity<>(authenticationService.signUp(request), HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> signIn(@RequestBody @Valid SignInRequest request, BindingResult bindingResult) {
        ResponseEntity<Object>errors = responseErrorValidation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors))return errors;
        return new ResponseEntity<>(authenticationService.signIn(request), HttpStatus.OK);
    }
}
