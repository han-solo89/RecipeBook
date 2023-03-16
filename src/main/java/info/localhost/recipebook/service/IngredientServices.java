package info.localhost.recipebook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.localhost.recipebook.model.Ingredient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class IngredientServices implements IngredientService{
    private Map<Integer, Ingredient> ingredientMap = new HashMap<Integer, Ingredient>();
    private static Integer id = 0;
    final  private FilesService filesService;

    public IngredientServices(@Qualifier("ingredientFilesService") FilesService filesService) {
        this.filesService = filesService;

    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        ingredientMap.put(id++,ingredient);
        saveToFileIngredient();
        return ingredient;
    }
    @Override
    public Ingredient getIngredient(Integer id) throws NotFoundException {
        if (!ingredientMap.containsKey(id)){
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        saveToFileIngredient();
        return ingredientMap.get(id);
    }

    @Override
    public Collection<Ingredient> getAll() {
        return ingredientMap.values();
    }

    @Override
    public Ingredient removeIngredient(int id) throws NotFoundException {
        if (ingredientMap.containsKey(id)){
        throw new NotFoundException("рецепт с заданным id не найден");
    }
        saveToFileIngredient();
        return ingredientMap.remove(id);
    }
    @Override
    public Ingredient updateIngredient(int id, Ingredient ingredient) throws NotFoundException {
        if (ingredientMap.containsKey(id)){
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        saveToFileIngredient();
        return ingredientMap.put(id, ingredient);
    }

    @PostConstruct
    private void initIngredient() throws RuntimeException{
        readFromFile();

    }

    private void readFromFile() throws RuntimeException{
        try {
            String json = filesService.readFromFile();
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<Map<Integer, Ingredient>>() {
            });
        }catch (JsonProcessingException o){
            throw new RuntimeException("фаил не удалось прочитать ");
        }
    }
    private void saveToFileIngredient() throws RuntimeException{
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            filesService.saveToFile(json);

        }catch (JsonProcessingException o){
            throw new RuntimeException("фаил не удалось сохранить");
        }
    }

}


