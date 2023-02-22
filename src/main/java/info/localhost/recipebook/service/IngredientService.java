package info.localhost.recipebook.service;

import info.localhost.recipebook.model.Ingredient;
import javassist.NotFoundException;

import java.util.Collection;

public interface IngredientService {

    Ingredient addIngredient(Ingredient ingredient);
    Ingredient getIngredient(Integer id) throws NotFoundException;
    Collection<Ingredient> getAll();
    Ingredient removeIngredient(int id) throws NotFoundException;
    Ingredient updateIngredient(int id, Ingredient ingredient) throws NotFoundException;
}
