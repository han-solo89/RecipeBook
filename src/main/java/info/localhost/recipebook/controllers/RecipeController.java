package info.localhost.recipebook.controllers;


import info.localhost.recipebook.model.Recipe;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeController recipeService;
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Integer id) {
        Recipe recipe = recipeService.getRecipe(id).getBody();
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @PostMapping
    public ResponseEntity<ResponseEntity<Long>> addRecipe(@Valid @RequestBody Recipe recipe) {
        ResponseEntity<Long> id = recipeService.addRecipe(recipe).getBody();
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> addRecipe(@PathVariable long id, @RequestBody ResponseEntity<Object> recipe) {
        recipe = recipeService.addRecipe(id, recipe);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @DeleteMapping("/{id}")
    public boolean removeRecipe(@PathVariable Integer id){
        if (recipeService.removeRecipe(id)) {
            return ResponseEntity.ok().build().hasBody();
        }
        return ResponseEntity.notFound().build().hasBody();
    }
    @DeleteMapping
    public ResponseEntity<Void>removeAllRecipe(){
        recipeService.removeAllRecipe();
        return ResponseEntity.ok().build();
    }


}


