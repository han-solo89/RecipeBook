package info.localhost.recipebook.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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
    @Override
    public InputStreamResource exportFile() throws FileNotFoundException{
        File file = getDataFileTxt();
        return new InputStreamResource(new FileInputStream(file));
    }
    @Override
    public void importFile(MultipartFile file) throws FileNotFoundException{
        cleanDataFile();
        FileOutputStream fos = new FileOutputStream(getDataFileTxt());
        try {
            IOUtils.copy(file.getInputStream(),fos);
        }catch (IOException o){
            throw  new  RuntimeException("Проблема сохранения файла");
        }
    }
    @Override
    public Path getPath(){
        return getPath();}

}
