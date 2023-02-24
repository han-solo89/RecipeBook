package info.localhost.recipebook.service;

import info.localhost.recipebook.model.Ingredient;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class IngredientServices implements IngredientService{
    private final Map<Integer, Ingredient> ingredientMap = new HashMap<Integer, Ingredient>();
    private static Integer id = 0;
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
}


