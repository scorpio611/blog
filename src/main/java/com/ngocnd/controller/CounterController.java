package com.ngocnd.controller;

import com.ngocnd.model.MyCounter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("mycounter")
public class CounterController {
    @ModelAttribute("mycounter")
    public MyCounter setUpCounter() {
        return new MyCounter();
    }

    @GetMapping("/")
    public ModelAndView get(@ModelAttribute("mycounter") MyCounter myCounter) {
        myCounter.increment();
        ModelAndView modelAndView = new ModelAndView("/blog/view");
        return modelAndView;
    }
}
