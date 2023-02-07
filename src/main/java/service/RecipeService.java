package service;

import model.Recipe;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe);
    Recipe getRecipe(Integer id);
}
