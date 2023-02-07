package service;

import model.Ingredient;

public interface IngredientService {

    Ingredient addIngredient(Ingredient ingredient);
    Ingredient getIngredient(Integer id);
}
