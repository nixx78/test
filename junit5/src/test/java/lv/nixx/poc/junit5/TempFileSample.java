package lv.nixx.poc.junit5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TempFileSample {

    Path path1, path2;
    File file1, file2;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        try {
            path1 = tempDir.resolve("tst_file1.txt");
            path2 = tempDir.resolve("tst_file2.txt");
        } catch (InvalidPathException ipe) {
            System.err.println("error creating temporary test file in " +
                    this.getClass().getSimpleName());
        }

        file1 = path1.toFile();
        file2 = path2.toFile();
    }

    @Test
    public void writeAndReadFile() {

        try {

            BufferedWriter bw1 = new BufferedWriter(new FileWriter(file1));
            bw1.write("file1 content");
            bw1.close();

            BufferedWriter bw2 = new BufferedWriter(new FileWriter(file2));
            bw2.write("file2 content");
            bw2.close();

         } catch (IOException ioe) {
            System.err.println("error creating temporary test file in " + this.getClass().getSimpleName());
        }

        assertTrue(file1.exists());
        assertTrue(file2.isFile());

        assertEquals(file1.length(), 13L);
        assertEquals(file1.length(), file2.length());

        assertTrue(file1.getAbsolutePath().endsWith("tst_file1.txt"));
    }

}