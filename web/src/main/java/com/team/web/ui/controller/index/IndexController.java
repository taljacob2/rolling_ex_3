package com.team.web.ui.controller.index;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController @RequestMapping("index") public class IndexController {

    @GetMapping public ModelAndView index(Model model) {

        // Show the 'signin' form:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mainweb/index");
        return modelAndView;
    }

}
