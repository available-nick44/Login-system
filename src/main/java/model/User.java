package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import validators.Password;
import validators.PhoneNumber;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotNull(message = "Login cannot be null")
    @Size(min = 3, message = "Login should be at least 3 characters long")
    private String login;

    @NotNull(message = "Password cannot be null")
    @Password
    //actual password, in DB in should by some password hash
    private String password;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "Phone number cannot be null")
    @PhoneNumber
    private String phoneNumber;
}
