package com.example.capstone.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImage {
    String message() default "Invalid image format. Only JPG and PNG are allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
