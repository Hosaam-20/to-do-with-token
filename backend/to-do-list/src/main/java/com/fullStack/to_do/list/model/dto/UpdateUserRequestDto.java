package com.fullStack.to_do.list.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserRequestDto {

    @NotNull(message = "Username Must Not Be null!")
    @NotBlank(message = "Username Must Not Be Blank!")
    @Pattern(regexp = "^[A-Za-z0-9@#$_-]{5,}$",
            message = "Invalid Pattern!")
    private String username;

    @NotNull(message = "Email Must Not Be null!")
    @Email(message = "Invalid Email!")
    private String email;

}
