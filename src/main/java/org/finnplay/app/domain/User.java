package org.finnplay.app.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString                       
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Email
    @Size(min = 5, max = 250)
    @Column(length = 250, unique = true, nullable = false)
    private String loginEmail;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "birthday_date")
    private Instant birthdayDate = null;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;
}
