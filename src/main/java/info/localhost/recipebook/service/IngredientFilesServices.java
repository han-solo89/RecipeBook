package info.localhost.recipebook.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service("ingredientFilesService")
public class IngredientFilesServices implements FilesService{
    @Value("${path.to.data.file}")
    private String dataFilePathIngredient;
    @Value("${name.of.ingredient.file}")
    private String dataFileNameIngredient;

    @Override
    public void saveToFile(String json){
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePathIngredient, dataFileNameIngredient), json);
        }catch (IOException ignored){

        }

    }
    @Override
    public String readFromFile(){
        if (Files.exists(Path.of(dataFilePathIngredient,dataFileNameIngredient))) {
            try {
                return Files.readString(Path.of(dataFilePathIngredient, dataFileNameIngredient));

            } catch (IOException o) {
                o.printStackTrace();
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
            o.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public File getDataFileTxt() {
        return new File(dataFilePathIngredient + "/" + dataFileNameIngredient);
    }
}
