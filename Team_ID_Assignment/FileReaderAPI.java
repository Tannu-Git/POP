import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class FileReaderAPI {
    public static List<String> readAllFiles(String dirPath) {
        try (Stream<Path> paths = Files.walk(Paths.get(dirPath))) {
            return paths
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".txt"))
                .map(FileReaderAPI::readFileToString)
                .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private static String readFileToString(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            return "";
        }
    }
}
