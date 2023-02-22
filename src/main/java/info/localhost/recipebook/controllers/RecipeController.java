package info.localhost.recipebook.controllers;


import jakarta.servlet.annotation.WebInitParam;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import jdk.jfr.Percentage;
import lombok.RequiredArgsConstructor;
import info.localhost.recipebook.model.Recipe;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.management.ConstructorParameters;
import javax.persistence.TableGenerator;
import java.lang.annotation.Target;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
@Tag(name = "Рецепты",description = "CRUD-операции для работы с рецептами")
public class RecipeController {
    private final RecipeController recipeService;
    @Operation(summary = "Поиск рецепта по id")
    @ApiResponses(value = {
            @ApiResponses(responceCode = "200",description = "Рецепт был найден")})
    @Parameter(value = {@Parameter(name = "id",example = "1")})
    @GetMapping("/{id}")
    ResponseEntity<Recipe>getRecipe(@PathVariable Integer id) {
        return ResponseEntity.ok(recipeService.getRecipe(id));
    }
    @PostMapping("/all")
    @Operation(summary = "Получение всех рецептов", description = "Поиск производится без параметров")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "Рецепты получены")})
    ResponseEntity<Collection<Recipe>> getRecipe(){return ResponseEntity.ok(recipeService.getAll());}

    @PostMapping
    @Operation(summary = "Добавлен рецепт")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "Рецепт добавлен")})
    ResponseEntity<Recipe> addRecipe(@Valid @RequestBody Recipe recipe) {
        return ResponseEntity.ok(recipeService.addRecipe(recipe));
    }
    @GetMapping("/{id}")
    @Operation(summary = "Изменение рецептов по id")
    @ApiResponses(value = {
            @ApiResponses(
                    responseCode = "200",
                    description = "Рецепт изменен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }
            )
    })
    @Parameter(value = {
            @Parameter(name = "id",
            example = "1")
    })
    ResponseEntity<Recipe>updateRecipe(@PathVariable Integer id, @Valid @RequestBody Recipe recipe){
        return ResponseEntity.ok(recipeService.updateRecipe(id, recipe));

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление рецептов по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт удален"
            )
    })
    @Parameters(value = {@Parameter(name = "id", example = "1")})
    ResponseEntity<Recipe>removeRecipe(@PathVariable Integer id){
        return ResponseEntity.ok(recipeService.removeRecipe(id));
    }

    @GetMapping
    @Operation(summary = "Получение всех рецептов", description = "Поиск производится без параметров")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "Рецепты удалены")})
    ResponseEntity<Collection<Recipe>>getRecipeByIngredientId(){return ResponseEntity.ok(recipeService.getAll());}


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex){
        return getStringStringMap(ex);
    }


}


