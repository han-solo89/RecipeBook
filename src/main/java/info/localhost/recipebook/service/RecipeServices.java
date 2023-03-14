package info.localhost.recipebook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.localhost.recipebook.model.Recipe;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServices implements RecipeService{

    private Map<Integer, Recipe> recipeMap = new HashMap<>();
    private static Integer id = 0;

    final private FilesServices filesServices;

    public RecipeServices(FilesServices filesServices) {
        this.filesServices = filesServices;
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
        saveToFile();
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
        return recipeMap.remove(id);
    }
    @Override
    public Recipe updateRecipe(int id, Recipe recipe) throws NotFoundException {
        if (recipeMap.containsKey(id)){
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        return recipeMap.put(id, recipe);

    }
    private void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            filesServices.saveToFile(json);
        }catch (JsonProcessingException o){
            throw new RuntimeException(o);
        }
    }
    private void readFromFile(){
        try {
            String json = filesServices.readFromFile();
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<Map<Integer, Recipe>>() {

            });
        }catch (JsonProcessingException o){
            throw new RuntimeException(o);
        }
    }
    @PostConstruct
    private void initRecipe(){
        readFromFile();
    }
}
