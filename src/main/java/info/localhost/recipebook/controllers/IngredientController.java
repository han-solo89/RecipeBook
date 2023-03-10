package info.localhost.recipebook.controllers;

import info.localhost.recipebook.model.Ingredient;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientController ingredientService;

    public IngredientController(IngredientController ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Integer id) {
        Ingredient ingredient = ingredientService.getIngredient(id).getBody();
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @PostMapping
    public ResponseEntity<ResponseEntity<Long>> addIngredient(@Valid @RequestBody Ingredient ingredient) {
        ResponseEntity<Long> id = ingredientService.addIngredient(ingredient).getBody();
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient>addIngredient(@PathVariable long id, @RequestBody Ingredient ingredient) {
        ingredient = ingredientService.addIngredient(id, ingredient).getBody();
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{id}")
    public boolean removeIngredient(@PathVariable Integer id){
        if (ingredientService.removeIngredient(id)) {
            return ResponseEntity.ok().build().hasBody();
        }
        return ResponseEntity.notFound().build().hasBody();
    }
    @DeleteMapping
    public ResponseEntity<Void>removeAllIngredient(){
        ingredientService.removeAllIngredient();
        return ResponseEntity.ok().build();
    }


}

