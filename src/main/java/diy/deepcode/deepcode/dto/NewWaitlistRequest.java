package diy.deepcode.deepcode.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record NewWaitlistRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Must be a valid email address")
        String email,

        @NotBlank(message = "Phone number cannot be blank")
        @Pattern(regexp="^\\+?[0-9. ()-]{7,25}$",
                message="must be a valid phone number")
        String phoneNumber,

        @NotBlank(message = "Company cannot be blank")
        String company,

        @NotBlank(message = "Role cannot be blank")
        String role,

        String description
) {}
