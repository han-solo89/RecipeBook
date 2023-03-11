package info.localhost.recipebook.service;

import info.localhost.recipebook.model.Recipe;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServices implements RecipeService{

    private final Map<Integer, Recipe> recipeMap = new HashMap<>();
    private static Integer id = 0;

    @Override
    public Recipe addRecipe(Recipe recipe) {
        recipeMap.put(id++, recipe);
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
        return recipeMap.remove(id);
    }
    @Override
    public Recipe updateRecipe(int id, Recipe recipe) throws NotFoundException {
        if (recipeMap.containsKey(id)){
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        return recipeMap.put(id, recipe);

    }
}
