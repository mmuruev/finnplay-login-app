package org.finnplay.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.finnplay.app.domain.User;
import org.finnplay.app.repository.UserRepository;
import org.finnplay.app.service.dto.UserDTO;
import org.finnplay.app.service.dto.UserUpdateDTO;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    final private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public boolean checkUserExistence(final String login) {

        if (new EmailValidator().isValid(login, null)) {
            return userRepository.existsByLoginEmailIgnoreCase(login);
        }

        return false;
    }

    public Optional<UserDTO> getUser(final String login) {
        return userRepository.findOneByLoginEmailIgnoreCase(login)
                .map(this::toUserDTO);
    }

    @Transactional
    public UserDTO updateUser(UserUpdateDTO userDTO) {
        var userEmail = userDTO.getEmail();

        if (new EmailValidator().isValid(userEmail, null)) {
            return userRepository
                    .findOneByLoginEmailIgnoreCase(userEmail)
                    .map(user -> updateCredentials(user, userDTO))
                    .map(this::toUserDTO)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User with email " + userEmail + " not here!")
                    );
        }

        throw new UsernameNotFoundException("User with login " + userEmail + " not here!");
    }


    @Transactional
    public User createUser(UserDTO userDTO) {
        User newUser = new User();
        newUser.setLoginEmail(userDTO.getEmail().toLowerCase());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setActivated(true);

        userRepository.save(newUser);

        log.debug("Created User: {}", newUser);

        return newUser;
    }

    private User updateCredentials(User user, UserUpdateDTO newUserData) {
        user.setFirstName(newUserData.getFirstName());
        user.setLastName(newUserData.getLastName());
        user.setBirthdayDate(newUserData.getBirthday().toInstant());
        return userRepository.save(user);
    }

    private UserDTO toUserDTO(User user) {
        var birthday = user.getBirthdayDate() != null ? Date.from(user.getBirthdayDate()) : null;
        return new UserDTO()
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getLoginEmail())
                .setBirthday(birthday);
    }
}
