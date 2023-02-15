package com.reddot.validator

import com.reddot.common.ValidPassword
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.passay.*

class PasswordConstraintValidator : ConstraintValidator<ValidPassword, String> {
    // NOT YET WORK
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        val rules = ArrayList<Rule>();
        rules.add(LengthRule(8, 6))
        rules.add(WhitespaceRule())
        rules.add(CharacterRule(EnglishCharacterData.UpperCase, 1))
        rules.add(CharacterRule(EnglishCharacterData.LowerCase, 1))
        rules.add(CharacterRule(EnglishCharacterData.Digit, 1))
        rules.add(CharacterRule(EnglishCharacterData.Special, 1))

        val validator = PasswordValidator(rules)
        val result = validator.validate(PasswordData(value))
        if (result.isValid) {
            return true;
        }
        return false
    }
}