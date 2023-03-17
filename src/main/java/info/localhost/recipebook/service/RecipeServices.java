package info.localhost.recipebook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.localhost.recipebook.model.Recipe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServices implements RecipeService{

    private Map<Integer, Recipe> recipeMap = new HashMap<>();
    private static Integer id = 0;

    final private FilesService filesService;

    public RecipeServices(@Qualifier("recipeFilesService") FilesService filesService) {
        this.filesService = filesService;
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        recipeMap.put(id++, recipe);
        saveToFile();
        return recipe;
    }
    @Override
    public Recipe getRecipe(Integer id) throws NotFoundException {
        if (!recipeMap.containsKey(id)){
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        return recipeMap.get(id);
    }
    @Override
    public Collection<Recipe> getAll() {
        return recipeMap.values();
    }
    @Override
    public Recipe removeRecipe(int id) throws NotFoundException {
        if (recipeMap.containsKey(id)){
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        saveToFile();
        return recipeMap.remove(id);
    }
    @Override
    public Recipe updateRecipe(int id, Recipe recipe) throws NotFoundException {
        if (recipeMap.containsKey(id)){
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        saveToFile();
        return recipeMap.put(id, recipe);

    }
    @PostConstruct
    private void initRecipe() throws RuntimeException{
        readFromFile();
    }

    private void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            filesService.saveToFile(json);
        }catch (JsonProcessingException o){
            throw new RuntimeException("фаил не удалось сохранить");
        }
    }
    private void readFromFile(){
        try {
            String json = filesService.readFromFile();
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<Map<Integer, Recipe>>() {

            });
        }catch (JsonProcessingException o){
            throw new RuntimeException("фаил не удалось прочитать");
        }
    }

}
