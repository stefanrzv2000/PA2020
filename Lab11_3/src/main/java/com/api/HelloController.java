package com.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String sayHello(){
        return "hello";
    }

    @GetMapping("/{name}")
    public String sayHello(@PathVariable String name, Model model){
        model.addAttribute("name",name);
        return "hellos";
    }

}
