package com.fullStack.to_do.list.model.dto;

import com.fullStack.to_do.list.model.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.message.Message;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserRequestDto {

    @NotNull(message = "Username Must Not Be null!")
    @NotBlank(message = "Username Must Not Be Blank!")
    @Pattern(regexp = "^[A-Za-z0-9@#$_-]{5,}$",
            message = "Invalid Pattern!")
    private String username;

    @NotNull(message = "Email Must Not Be null!")
    @Email(message = "Invalid Email")
    private String email;

    @Pattern(regexp = "^[A-Za-z0-9@#$_-]{5,}$",
            message = "Invalid Pattern!")
    private String password;

    private Role role;
}
