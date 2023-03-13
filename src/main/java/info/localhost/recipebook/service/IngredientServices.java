package info.localhost.recipebook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.localhost.recipebook.model.Ingredient;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class IngredientServices implements IngredientService{
    private Map<Integer, Ingredient> ingredientMap = new HashMap<Integer, Ingredient>();
    private static Integer id = 0;
    final  private FilesServices filesServices;

    public IngredientServices(FilesServices filesServices) {
        this.filesServices = filesServices;
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        ingredientMap.put(id++,ingredient);
        return ingredient;
    }
    @Override
    public Ingredient getIngredient(Integer id) throws NotFoundException {
        if (!ingredientMap.containsKey(id)){
            throw new NotFoundException("рецепт с заданным id не найден");
        }
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
        return ingredientMap.remove(id);
    }
    @Override
    public Ingredient updateIngredient(int id, Ingredient ingredient) throws NotFoundException {
        if (ingredientMap.containsKey(id)){
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        return ingredientMap.put(id, ingredient);
    }
    private void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            filesServices.saveToFile(json);
        }catch (JsonProcessingException o){
            throw new RuntimeException(o);
        }
    }
    private void readFromFile(){
        try {
            String json = filesServices.readFromFile();
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<Map<Integer, Ingredient>>() {

            });
        }catch (JsonProcessingException o){
            throw new RuntimeException(o);
        }
    }
    @PostConstruct
    private void initIngredient() {
        readFromFile();

    }
}


