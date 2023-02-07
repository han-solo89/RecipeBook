package model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ingredient {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Positive
    private Integer count;
    private String measureUnit;
}
