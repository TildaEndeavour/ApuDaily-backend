package com.example.ApuDaily.shared.validation.annotation;

import com.example.ApuDaily.shared.validation.ValidationUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdValidator implements ConstraintValidator<IdValidation, Long> {
  @Autowired ValidationUtil validationUtil;

  @Override
  public void initialize(IdValidation constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(Long value, ConstraintValidatorContext context) {
    return validationUtil.isValidId(value);
  }
}
