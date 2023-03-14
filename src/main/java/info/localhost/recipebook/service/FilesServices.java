package info.localhost.recipebook.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServices implements FilesService{
    @Value("${path.to.data.file}")
    private String dataFilePath;
    @Value("${name.to.data.file}")
    private String dataFileName;

    @Override
    public boolean saveToFile(String json){
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePath, dataFileName), json);
            return true;
        }catch (IOException o){
            return false;
        }

    }
    @Override
    public String readFromFile(){
        try {
            return Files.readString(Path.of(dataFilePath, dataFileName));
        }catch (IOException o){
            throw new RuntimeException(o);
        }

    }
    @Override
    public boolean cleanDataFile() {
        boolean result;
        try {
            Path path = Path.of(dataFilePath, dataFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            result = true;
        } catch (IOException o) {
            result = false;
        }
        return result;
    }
}
