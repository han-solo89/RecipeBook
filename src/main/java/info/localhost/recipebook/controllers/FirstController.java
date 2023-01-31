package info.localhost.recipebook.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class FirstController {


    @GetMapping
    String greeting() {
        return "Application is running";
    }
    @GetMapping
    String showInfo(){
        return "Author: Han <br> " +
                "Project name: RecipeBook <br> " +
                "Was created at 31.01.2023 <br> " +
                "This project is dedicated to recipes description" ;

    }

}
