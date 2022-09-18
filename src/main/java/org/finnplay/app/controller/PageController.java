package org.finnplay.app.controller;

import lombok.RequiredArgsConstructor;
import org.finnplay.app.service.UserService;
import org.finnplay.app.service.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PageController {
    final private UserService userService;

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping("/user/registration")
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDTO userDto, ModelAndView modelAndView, BindingResult result) {
        if(result.hasErrors()){
            return new ModelAndView("registration" ,"user", userDto);
        }

        if(userService.checkUserExistence(userDto.getEmail())) {
            modelAndView.addObject("message", "The email already exists.");
            return modelAndView;
        }

        userService.createUser(userDto);

        return new ModelAndView("successRegister", "user", userDto);
    }
    

}
