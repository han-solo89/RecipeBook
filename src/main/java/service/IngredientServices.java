package service;
import info.localhost.recipebook.controllers.Ingredient;
import model.Ingredient;

import java.util.HashMap;
import java.util.Map;

public class IngredientServices implements IngredientService{

    private final Map<Integer,model.Ingredient> ingredientMap = new HashMap<>();
    private static Integer id = 0;
    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        ingredientMap.put(id++,ingredient);
        return ingredient;
    }


    @Override
    public Ingredient getIngredient(Integer id) {
        return ingredientMap.getOrDefault(id,null);
    }
}
