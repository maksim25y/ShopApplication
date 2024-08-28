package ru.mudan.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mudan.payload.request.SignUpRequest;
import ru.mudan.services.UserService;

@Component
public class SignUpValidator implements Validator {
    private final UserService userService;
    @Autowired
    public SignUpValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SignUpRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpRequest input = (SignUpRequest) target;
        if(userService.emailAlreadyExists(input.getEmail())){
            errors.rejectValue("email","","Пользователь с данным email уже существует");
        }
        if(userService.usernameAlreadyExists(input.getUsername())){
            errors.rejectValue("username","","Пользователь с данным username уже существует");
        }
    }
}
