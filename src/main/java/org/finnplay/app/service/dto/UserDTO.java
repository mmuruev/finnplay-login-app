package org.finnplay.app.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Accessors(chain = true)
public class UserDTO {
    @NotEmpty
    @NotNull
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String password;

    @NotNull
    @Email
    private String email;

    private Instant birthday;

}
