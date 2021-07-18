package com.team.web.ui.controller.sign;

import com.team.web.service.UserService;
import com.team.web.shared.dto.UserDTO;
import engine.Engine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import user.User;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j @RestController @RequestMapping("signin") public class SignInController {

    @Autowired UserService userService;

    @GetMapping public ModelAndView showForm(Model model) {

        /*
         * Create a new 'requestUserDTO', and send it to the 'signin' form
         * to be filled via thymeleaf.
         */
        UserDTO requestUserDTO = new UserDTO();
        model.addAttribute("requestUserDTO", requestUserDTO);

        /*
         * Get all a list of all the userNames from the Engine and send it to
         * thymeleaf.
         */
        boolean usersNameListIsPresent = Engine.isUsers();
        try {
            List<String> usersNameList = Engine.getUsers().getCollection().
                    stream().map(User::getName).collect(Collectors.toList());
            model.addAttribute("usersNameList", usersNameList);
        } catch (java.io.IOException ignored) {}

        model.addAttribute("usersNameListIsPresent", usersNameListIsPresent);

        // Show the 'signin' form:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sign/signin");
        return modelAndView;
    }

    /**
     * <ul>
     *     <li>Gets the {@code userName} of the signed in {@code User} by a
     *     {@link UserDTO#getName()}.</li>
     *     <li>Finds the according {@link User} <i>found by name</i> of this
     *     {@code userName}.
     *     <li>Inserts the found {@link User} to the
     *     {@link Engine#getSignedInUsers()} list.</li>
     * </ul>
     *
     * @param requestUserDTO the signed in {@code User} <b>NOTE: it has only
     *                       {@link UserDTO#getName()} initialized</b>.
     * @return the next <tt>HTML</tt> page.
     */
    @PostMapping public ModelAndView submitForm(
            @ModelAttribute("requestUserDTO") UserDTO requestUserDTO) {

        // Get the User from the given "requestUserDTO.getName()":
        User user = Engine.findUserByNameForced(requestUserDTO.getName());

        // Set the requestUserDTO with its found Role.
        requestUserDTO.setRole(user.getUserRole().toString());

        // Add the UserDTO to the Engine's signedInUsers List:
        Engine.getSignedInUsers().add(requestUserDTO);

        // Redirect to the "signed" page:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/signed");
        return modelAndView;
    }


}
