package service;

import info.localhost.recipebook.controllers.Ingredient;
import model.Ingredient;

public interface IngredientService {

    Ingredient addIngredient(Ingredient ingredient);
    Ingredient getIngredient(Integer id);
}
