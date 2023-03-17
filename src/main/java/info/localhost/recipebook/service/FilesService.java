package info.localhost.recipebook.service;

import java.io.File;

public interface FilesService {
    void saveToFile(String json);

    String readFromFile();

    boolean cleanDataFile();
    File getDataFileTxt();
}
