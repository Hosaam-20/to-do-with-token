package com.fullStack.to_do.list.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginDto {

    @NotNull(message = "Username Or Email Must Not Be null!")
    @NotBlank(message = "Username Or Email Must Not Be Blank!")
    private String usernameOrEmail;

    @Pattern(regexp = "^[A-Za-z0-9@#$_-]{5,}$",
            message = "Invalid Pattern!")
    private String password;
}
