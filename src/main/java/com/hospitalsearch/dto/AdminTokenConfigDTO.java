package com.hospitalsearch.dto;


import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.hospitalsearch.config.security.SecurityConfiguration.REMEMBER_ME_TOKEN_EXPIRATION;
import static com.hospitalsearch.entity.PasswordResetToken.RESET_PASSWORD_TOKEN_EXPIRATION;
import static com.hospitalsearch.entity.VerificationToken.VERIFICATION_TOKEN_EXPIRATION;

/**
 * @author Andrew Jasinskiy on 23.06.16
 */

public class AdminTokenConfigDTO {

    @NotNull(message = "Token cannot be empty")
    @Digits(integer=3, fraction = 0, message = "The value must only contain digits.")
    @Min(value = 24, message = "Token should be at least 24 hours")
    @Max(value = 168, message = "Maximum token duration is 168 hours")
    private Integer resetPasswordToken;

    @NotNull(message = "Token cannot be empty")
    @Digits(integer=3, fraction = 0, message = "The value must only contain digits.")
    @Min(value = 24, message = "Token should be at least 24 hours")
    @Max(value = 168, message = "Maximum token duration is 168 hours")
    private Integer verificationToken;

    @NotNull(message = "Token cannot be empty")
    @Digits(integer=3, fraction = 0, message = "The value must only contain digits.")
    @Min(value = 24, message = "Token should be at least 24 hours")
    @Max(value = 168, message = "Maximum token duration is 168 hours")
    private Integer rememberMeToken;

    public AdminTokenConfigDTO() {
        super();
        this.resetPasswordToken = RESET_PASSWORD_TOKEN_EXPIRATION;
        this.verificationToken = VERIFICATION_TOKEN_EXPIRATION;
        this.rememberMeToken = REMEMBER_ME_TOKEN_EXPIRATION;
    }

    public int getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(int resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public int getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(int verificationToken) {
        this.verificationToken = verificationToken;
    }

    public int getRememberMeToken() {
        return rememberMeToken;
    }

    public void setRememberMeToken(int rememberMeToken) {
        this.rememberMeToken = rememberMeToken;
    }

    @Override
    public String toString() {
        return "AdminTokenConfigDTO{" +
                "resetPasswordToken=" + resetPasswordToken +
                ", verificationToken=" + verificationToken +
                ", rememberMeToken=" + rememberMeToken +
                '}';
    }
}
