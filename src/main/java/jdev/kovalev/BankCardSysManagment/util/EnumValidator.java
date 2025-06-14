package jdev.kovalev.BankCardSysManagment.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jdev.kovalev.BankCardSysManagment.util.annotations.EnumValue;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumValue, String> {
    private Class<? extends Enum<?>> enumClass;
    private boolean nullable;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
        this.nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return nullable;
        }

        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(enumValue -> enumValue.name().equalsIgnoreCase(value));
    }
}
