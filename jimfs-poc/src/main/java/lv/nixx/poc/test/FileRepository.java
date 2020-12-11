package lv.nixx.poc.test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileRepository {

    Path create(Path path, String fileName) {
        Path filePath = path.resolve(fileName);
        try {
            return Files.createFile(filePath);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    String read(Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    String update(Path path, String newContent) {
        try {
            Files.write(path, newContent.getBytes());
            return newContent;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    void delete(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    Path createFileWithContent(Path path, String fileName, String content) {
        final Path p = create(path, fileName);
        update(p, content);

        return p;
    }

    public Collection<String> getPathContent(Path path, int depth) throws IOException {
        try (Stream<Path> stream = Files.walk(path, depth)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

}