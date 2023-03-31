package info.localhost.recipebook.service;

import info.localhost.recipebook.model.Recipe;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
public interface RecipeService {
    Recipe addRecipe(Recipe recipe);
    Recipe getRecipe(Integer id) throws NotFoundException;
    Collection<Recipe> getAll();
    Recipe removeRecipe(int id) throws NotFoundException;
    Recipe updateRecipe(int id, Recipe recipe) throws NotFoundException;

    void addRecipesFromInputStream(InputStream inputStream) throws IOException;
}
