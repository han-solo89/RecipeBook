package info.localhost.recipebook.controllers;

import info.localhost.recipebook.model.Ingredient;
import info.localhost.recipebook.model.Recipe;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientController ingredientService;
    @Operation(summary = "Поиск ингредиента по id")
    @ApiResponses(value = {
            @ApiResponses(responceCode = "200",description = "ингредиент был найден")})
    @Parameter(value = {@Parameter(name = "id",example = "1")})
    @GetMapping("/{id}")
    ResponseEntity<Ingredient> getIngredient(@PathVariable Integer id) {
        return (ResponseEntity<Ingredient>) ResponseEntity.ok(ingredientService.getIngredient(id));
    }
    @PostMapping("/all")
    @Operation(summary = "Получение всех ингредиентов", description = "Поиск производится без параметров")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "Интредиент получены")})
    ResponseEntity<Collection<Ingredient>> getIngredient(){return ResponseEntity.ok(ingredientService.getAll());}

    @PostMapping
    @Operation(summary = "Добавлен ингредиент")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "ингрединт добавлен")})
    ResponseEntity<Ingredient> addIngredient(@Valid @RequestBody Recipe recipe) {
        return ResponseEntity.ok(ingredientService.getIngredient(ingredient));
    }
    @GetMapping("/{id}")
    @Operation(summary = "Изменение интредиентов по id")
    @ApiResponses(value = {
            @ApiResponses(
                    responseCode = "200",
                    description = "ингредиент изменен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ingredient.class)
                            )
                    }
            )
    })
    @Parameter(value = {
            @Parameter(name = "id",
                    example = "1")
    })
    ResponseEntity<Ingredient>updateIngredient(@PathVariable Integer id, @Valid @RequestBody Ingredient ingredient){
        return ResponseEntity.ok(ingredientService.updateIngredient(id, ingredient));

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление ингредиентов по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Игредиент удален"
            )
    })
    @Parameters(value = {@Parameter(name = "id", example = "1")})
    ResponseEntity<Recipe>removeIngredient(@PathVariable Integer id){
        return ResponseEntity.ok(ingredientService.removeIngredient(id));
    }

    @GetMapping
    @Operation(summary = "Получение всех ингредиентов", description = "Поиск производится без параметров")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "Ингредииент удалены")})
    ResponseEntity<Collection<Ingredient>>getIngredientByRecipeId(){return ResponseEntity.ok(ingredientService.getAll());}


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex){
        return getStringStringMap(ex);
    }

    @NotNull
    static Map<String, String> getStringStringMap(MethodArgumentNotValidException ex) {
        Map<String,String>errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return errors;
    }


}
}
