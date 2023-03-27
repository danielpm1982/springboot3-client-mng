package com.danielpm1982.springboot3clientmng.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class WelcomeController{
    @GetMapping({"", "home", "home/", "welcome", "welcome/"})
    private ModelAndView welcome(){
        ModelAndView modalAndView = new ModelAndView("welcome");
        Map<String, String> modalMap = new HashMap<>();
        modalMap.put("message1", "Welcome to the Client Management Application !!");
        modalMap.put("message2", "Access the REST paths at \"/api\" for testing our Client RESTful Service API !!");
        modalMap.put("clientAPIBasePath", "http://localhost:8080/api/clients");
        modalAndView.addAllObjects(modalMap);
        return modalAndView;
    }
}
