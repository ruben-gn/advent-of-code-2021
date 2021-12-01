package nl.grootnibbelink.advent2021.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ResourceLoader {
    private ResourceLoader() {
    }

    public static List<String> getLines(String resource) throws IOException {
        var path = Paths.get("src/main/resources/" + resource);
        return Files.readAllLines(path);
    }
}
