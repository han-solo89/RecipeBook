package info.localhost.recipebook.service;

public interface FilesService {
    boolean saveToFile(String json);

    String readFromFile();

    boolean cleanDataFile();
}
