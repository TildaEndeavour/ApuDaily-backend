package com.example.ApuDaily.shared.validation.annotation;

import com.example.ApuDaily.shared.validation.ValidationUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdListValidator implements ConstraintValidator<IdValidation, List<Long>> {

  @Autowired private ValidationUtil validationUtil;

  @Override
  public boolean isValid(List<Long> values, ConstraintValidatorContext context) {
    return values.stream().allMatch(validationUtil::isValidId);
  }
}
