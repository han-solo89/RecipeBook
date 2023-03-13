package info.localhost.recipebook.controllers;

import info.localhost.recipebook.model.Ingredient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "ингредиенты", description = "CRUD - операции для работы с ингредиентами")
public class IngredientController {
    private final IngredientController ingredientService;

    public IngredientController(IngredientController ingredientService) {
        this.ingredientService = ingredientService;
    }
    @Operation(summary = "поиск ингредиентов по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "ингредиент был обнаружен")})
    @Parameters(value = {@Parameter(name = "id",example = "1")})
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Integer id) {
        Ingredient ingredient = ingredientService.getIngredient(id).getBody();
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @PostMapping
    @Operation(summary = "Получение всех ингредиентов", description = "Поиск производится без параметров")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "ингредиент добавлен")})
    public ResponseEntity<ResponseEntity<Long>> addIngredient(@Valid @RequestBody Ingredient ingredient) {
        ResponseEntity<Long> id = ingredientService.addIngredient(ingredient).getBody();
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "изменение ингредиентов по id")
    @ApiResponses(value = {
            @ApiResponse(
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
    @Parameters(value = {
            @Parameter(name = "id",
                    example = "1")
    })
    public ResponseEntity<Ingredient>addIngredient(@PathVariable long id, @RequestBody Ingredient ingredient) {
        ingredient = ingredientService.addIngredient(id, ingredient).getBody();
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "удаление ингредиентов по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ингредиент удален"
            )
    })
    @Parameters(value = {@Parameter(name = "id",example = "1")})
    public boolean removeIngredient(@PathVariable Integer id){
        if (ingredientService.removeIngredient(id)) {
            return ResponseEntity.ok().build().hasBody();
        }
        return ResponseEntity.notFound().build().hasBody();
    }
    @DeleteMapping
    @Operation(summary = "удалени всех ингредиентов", description = "Удаление производится без параметров")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "ингредиенты удалены")})
    public ResponseEntity<Void>removeAllIngredient(){
        ingredientService.removeAllIngredient();
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String , String> handValidationException(
            MethodArgumentNotValidException e) {
        return getStringStringMap(e);
    }
    @NotNull
    static Map<String, String> getStringStringMap(MethodArgumentNotValidException e) {
        Map<String,String> errors = new HashMap<>();
        assert e.getBindingResult() != null;
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);

        });
        return errors;
    }


}

