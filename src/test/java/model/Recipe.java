package model;

import info.localhost.recipebook.model.Ingredient;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Recipe {
    private String name;
    @Positive
    private Integer cookingTime;
    private List<Ingredient>ingredients;
    private List<String>steps;

}
