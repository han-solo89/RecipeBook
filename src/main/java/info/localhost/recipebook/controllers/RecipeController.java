package info.localhost.recipebook.controllers;


import info.localhost.recipebook.model.Recipe;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static info.localhost.recipebook.controllers.IngredientController.getStringStringMap;
@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
@Tag(name = "рецепты",description = "CRUD- операции для работы с рецептами")
public class RecipeController {
    private final RecipeController recipeService;
    @Operation(summary = "Поиск рецептов по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "рецепт был обнаружен")})
    @Parameters(value = {@Parameter(name = "id",example = "1")})
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Integer id) {
        Recipe recipe = recipeService.getRecipe(id).getBody();
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @PostMapping
    @Operation(summary = "Получение всех рецептов", description = "Поиск производится без параметров")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "рецепт добавлен")})
    public ResponseEntity<ResponseEntity<Long>> addRecipe(@Valid @RequestBody Recipe recipe) {
        ResponseEntity<Long> id = recipeService.addRecipe(recipe).getBody();
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "изменение рецептов по id")
    @ApiResponses(value = {
            @ApiResponse(
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
    @Parameters(value = {
            @Parameter(name = "id",
            example = "1")
    })
    public ResponseEntity<Object> addRecipe(@PathVariable long id, @RequestBody ResponseEntity<Object> recipe) {
        recipe = recipeService.addRecipe(id, recipe);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }
    @GetMapping(value = "/import",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addRecipesFromFile(@RequestParam MultipartFile file){
        try (InputStream stream = file.getInputStream()){
            recipeService.addRecipesFromFile((MultipartFile) stream);
        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
        return null;
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "удаление рецептов по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "рецепт удален"
            )
    })
    @Parameters(value = {@Parameter(name = "id",example = "1")})
    public boolean removeRecipe(@PathVariable Integer id){
        if (recipeService.removeRecipe(id)) {
            return ResponseEntity.ok().build().hasBody();
        }
        return ResponseEntity.notFound().build().hasBody();
    }
    @DeleteMapping
    @Operation(summary = "удалени всех рецептов", description = "Удаление производится без параметров")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "рецепты удалены")})
    public ResponseEntity<Void>removeAllRecipe(){
        recipeService.removeAllRecipe();
        return ResponseEntity.ok().build();
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String , String> handValidationException(
            MethodArgumentNotValidException e) {
        return getStringStringMap(e);
    }

}


