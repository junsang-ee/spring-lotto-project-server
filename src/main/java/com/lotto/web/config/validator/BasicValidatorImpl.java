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

        switch (fieldName) {
            case "Email": validEmail(value); break;
            case "AuthCode": validAuthCode(value, fieldName); break;
            case "PostPassword": validPostPassword(value, fieldName); break;
            default: break;
        }
        return true;
    }

    private void validEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches())
            throw new InvalidBasicFormatException(ErrorMessage.REQUEST_INVALID_EMAIL, null);
    }

    private void validAuthCode(String value, String fieldName) {
        int authCode = getToNumber(value, fieldName);
        if (!(10000 <= authCode && authCode <= 99999))
            throw new InvalidBasicFormatException(ErrorMessage.REQUEST_LENGTH_INVALID_AUTH_CODE, null);
    }

    private void validPostPassword(String value, String fieldName) {
        int password = getToNumber(value, fieldName);
        if (!(1000 <= password && password <= 9999))
            throw new InvalidBasicFormatException(ErrorMessage.REQUEST_LENGTH_INVALID_POST_PASSWORD, null);
    }

    private Integer getToNumber(String value, String fieldName) {
        int result = 0;
        try {
            result = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidBasicFormatException(ErrorMessage.REQUEST_FIELD_ONLY_NUMBER, fieldName);
        }
        return result;
    }
}
