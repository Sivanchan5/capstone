package com.example.capstone.validator;

import com.example.capstone.dto.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class PasswordValidator implements ConstraintValidator<ValidPassword, RegisterRequest>{
    @Override
    public boolean isValid(RegisterRequest request, ConstraintValidatorContext context) {
        String password = request.password();
        return password !=null && password.length()>=8;
    }

}
