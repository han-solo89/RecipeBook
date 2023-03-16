package info.localhost.recipebook.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service("ingredientFilesService")
public class IngredientFilesServices implements FilesService{
    @Value("${path.to.files}")
    private String dataFilePathIngredient;
    @Value("${name.to.ingredient.file}")
    private String dataFileNameIngredient;

    @Override
    public boolean saveToFile(String json){
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePathIngredient, dataFileNameIngredient), json);
            return true;
        }catch (IOException o){
            return false;
        }

    }
    @Override
    public String readFromFile(){
        if (Files.exists(Path.of(dataFilePathIngredient,dataFileNameIngredient))) {
            try {
                return Files.readString(Path.of(dataFilePathIngredient, dataFileNameIngredient));

            } catch (IOException o) {
                throw new RuntimeException("не удалось прочитать файл");
            }
        }else {
            return null;
        }

    }
    @Override
    public boolean cleanDataFile() {
        boolean result;
        try {
            Path path = Path.of(dataFilePathIngredient, dataFileNameIngredient);
            Files.deleteIfExists(path);
            Files.createFile(path);
            result = true;
        } catch (IOException o) {
            result = false;
        }
        return result;
    }

    @Override
    public File getDataFileTxt() {
        return new File(dataFilePathIngredient + "/" + dataFileNameIngredient);
    }
}
