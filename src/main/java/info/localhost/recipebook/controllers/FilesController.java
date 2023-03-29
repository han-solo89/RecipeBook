package info.localhost.recipebook.controllers;

import info.localhost.recipebook.service.FilesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/files")
@Tag(name = "Files", description = "CRUD - операции для работы с файлами")
public class FilesController {
    private final FilesService recipeFilesServices;
    private final FilesService ingredientFilesServices;

    public FilesController(@Qualifier("recipeFilesService") FilesService recipeFilesServices, @Qualifier("ingredientFilesService") FilesService ingredientFilesServices) {
        this.recipeFilesServices = recipeFilesServices;
        this.ingredientFilesServices = ingredientFilesServices;
    }
    @GetMapping("/ingredient/export")
    @Operation(description = "экспорт файлов ингредиентов")
    public ResponseEntity<InputStreamResource> downloadIngredientFile() throws IOException{
        InputStreamResource inputStreamResource = ingredientFilesServices.exportFile();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(Files.size(ingredientFilesServices.getPath()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename =\"Ingredients.json\"")
                .body(inputStreamResource);
    }
    @PostMapping(value = "/ingredient/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "импорт файла ингредиентов")
    public ResponseEntity<Void> uploadDataFileIngredient(@RequestParam MultipartFile file) throws FileNotFoundException{
        ingredientFilesServices.importFile(file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recipe/export")
    @Operation(description = "экспорт файлов рецептов")
    public ResponseEntity<InputStreamResource> downloadRecipeFile() throws IOException{
        InputStreamResource inputStreamResource = recipeFilesServices.exportFile();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(Files.size(ingredientFilesServices.getPath()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename =\"Recipes.json\"")
                .body(inputStreamResource);
    }
    @PostMapping(value = "/recipe/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "импорт файла рецептов")
    public ResponseEntity<Void> uploadDataFileRecipe(@RequestParam MultipartFile file) throws FileNotFoundException{
        recipeFilesServices.importFile(file);
        return ResponseEntity.ok().build();
    }

}
