package com.sample.validator;

import com.sample.entities.User;
import com.sample.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.validation.Validation;
@Component
public class UserValidator implements Validator {

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;



        if(userService.findByUsername(user.getUsername())!=null){
            errors.rejectValue("username", "Пользователь с таким имененм уже существует.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username","Required" );
        if(user.getUsername().length()<5 || user.getUsername().length() > 25){
            errors.rejectValue("username", "Слишком короткое или длинное имя пользователя.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","Required" );
        if(user.getPassword().length() < 6 || user.getPassword().length() > 45){
            errors.rejectValue("password", "Пароль должен быть от 6 до 45 символов.");
        }



    }
}
