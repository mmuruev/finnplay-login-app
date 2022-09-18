package org.finnplay.app.controller;


import lombok.RequiredArgsConstructor;
import org.finnplay.app.service.UserService;
import org.finnplay.app.service.dto.UserDTO;
import org.finnplay.app.service.dto.UserUpdateDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserCredentialsController {

    final private UserService userService;

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of("message", "pong");
    }

    @GetMapping("/user")
    public UserDTO getUserData(Authentication authentication) {
        return userService.getUser(getUserEmail(authentication))
                .orElseThrow(() -> new UsernameNotFoundException("No Such Email"));
    }

    @PostMapping("/user")
    public UserDTO userDataUpdate(@RequestBody UserUpdateDTO userDTO, Authentication authentication) {
        userDTO.setEmail(getUserEmail(authentication));
        return userService.updateUser(userDTO);
    }


    private String getUserEmail(Authentication authentication) {
        return Objects.requireNonNull(authentication).getName();
    }

}
