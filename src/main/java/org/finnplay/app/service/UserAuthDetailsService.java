package org.finnplay.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.finnplay.app.domain.User;
import org.finnplay.app.repository.UserRepository;
import org.finnplay.app.service.dto.UserDTO;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAuthDetailsService implements UserDetailsService {
    final private UserRepository userRepository;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Search user by login: {}", login);

        if (new EmailValidator().isValid(login, null)) {
            return userRepository
                    .findOneByLoginEmailIgnoreCase(login)
                    .map(this::createSpringSecurityUser)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User with email " + login + " not here!")
                    );
        }

        throw new UsernameNotFoundException("User with login " + login + " not here!");
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(final User user) {
        List<GrantedAuthority> grantedAuthorities = Stream.of("USER")
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getLoginEmail(), user.getPassword(), grantedAuthorities);
    }
}
