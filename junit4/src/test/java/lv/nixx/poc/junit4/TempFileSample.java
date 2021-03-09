package lv.nixx.poc.junit4;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TempFileSample {

    @Test
    public void createAndDeleteTempFiles() {
        try {
            Path folderPath = Files.createTempDirectory(Paths.get("C:\\tmp"), "tmpDirPrefix");

            Path filePath = Files.createTempFile(folderPath, "testfile", ".txt");
            if (filePath != null) {
                filePath.toFile().deleteOnExit();
            }

           folderPath.toFile().deleteOnExit();

        } catch (IOException ioe) {
            System.err.println("error creating temporary test file");
        }
    }
}
