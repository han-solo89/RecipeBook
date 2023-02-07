package info.localhost.recipebook.controllers;


import lombok.RequiredArgsConstructor;
import model.Recipe;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor

public class RecipeController {
    private final RecipeController recipeService;
    @GetMapping
    Recipe getRecipe(@PathVariable Integer id){return recipeService.getRecipe(id);}
    @PostMapping
    Recipe addRecipe(@RequestBody Recipe recipe){return recipeService.addRecipe(recipe);}


}
