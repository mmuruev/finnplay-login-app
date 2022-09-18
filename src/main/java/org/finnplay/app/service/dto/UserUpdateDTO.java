package org.finnplay.app.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class UserUpdateDTO {
    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;

    @JsonIgnore
    private String email;
}
