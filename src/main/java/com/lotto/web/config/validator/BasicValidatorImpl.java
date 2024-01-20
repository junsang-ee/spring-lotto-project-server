package com.lotto.web.config.validator;

import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.InvalidBasicFormatException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BasicValidatorImpl implements ConstraintValidator<BasicValidator, String> {

    private String fieldName;

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    @Override
    public void initialize(BasicValidator constraintAnnotation) {
        fieldName = constraintAnnotation.fieldName();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            throw new InvalidBasicFormatException(ErrorMessage.REQUEST_ARGUMENTS_NOT_NULLABLE, fieldName);
        } else if (value.isEmpty()) {
            throw new InvalidBasicFormatException(ErrorMessage.REQUEST_ARGUMENTS_NOT_EMPTY, fieldName);
        } else if (value.isBlank()) {
            throw new InvalidBasicFormatException(ErrorMessage.REQUEST_ARGUMENTS_NOT_BLANK, fieldName);
        }

        if (fieldName.equals("Email")) {
            if (!isValidEmail(value))
                throw new InvalidBasicFormatException(ErrorMessage.REQUEST_INVALID_EMAIL, null);
        }

        if (fieldName.equals("AuthCode")) {
            isValidAuthCode(value, fieldName);
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void isValidAuthCode(String value, String fieldName) {
        try {
            int result = Integer.parseInt(value);
            if (!(10000 <= result && result <= 99999))
                throw new InvalidBasicFormatException(ErrorMessage.REQUEST_AUTH_CODE_LENGTH_INVALID, null);
        } catch (NumberFormatException e) {
            throw new InvalidBasicFormatException(ErrorMessage.REQUEST_FIELD_ONLY_NUMBER, fieldName);
        }
    }
}
