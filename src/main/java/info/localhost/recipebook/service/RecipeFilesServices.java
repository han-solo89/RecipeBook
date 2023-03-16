package info.localhost.recipebook.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service("recipeFilesService")
public class RecipeFilesServices implements FilesService{
    @Value("${path.to.data.file}")
    private String dataFilesPathIngredient;
    @Value("${name.of.recipe.file}")
    private String dataFilesNameRecipe;
    @Override
    public void saveToFile(String json) {
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilesPathIngredient, dataFilesNameRecipe), json);
        }catch (IOException o){
            o.printStackTrace();
        }

    }

    @Override
    public String readFromFile() { if (Files.exists(Path.of(dataFilesPathIngredient, dataFilesNameRecipe))){
        try {
            return Files.readString(Path.of(dataFilesPathIngredient, dataFilesNameRecipe));
        } catch (IOException o ){
            o.printStackTrace();
            throw new RuntimeException("не удалось прочитать файл");
        }
    }else {
        return null;
    }

    }

    @Override
    public boolean cleanDataFile() { try {
        Path path = Path.of(dataFilesPathIngredient, dataFilesNameRecipe);
        Files.deleteIfExists(path);
        Files.createFile(path);
        return true;
    }catch (IOException o ){
        o.printStackTrace();
        return false;
    }
    }


    public File getDataFileTxt(){
        return new File(dataFilesPathIngredient + "/" + dataFilesNameRecipe);
    }

}
