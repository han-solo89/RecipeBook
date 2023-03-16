package info.localhost.recipebook.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

public interface FilesService {
    boolean saveToFile(String json);

    String readFromFile();

    boolean cleanDataFile();
    File getDataFileTxt();

    InputStreamResource exportFile() throws FileNotFoundException;

    void importFile(MultipartFile file) throws FileNotFoundException;

    public Path getPath();
}
