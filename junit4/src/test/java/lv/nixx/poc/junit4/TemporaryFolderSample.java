package lv.nixx.poc.junit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

import java.io.*;

public class TemporaryFolderSample {

    File file1, file2;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() {
        try {
            file1 = folder.newFile("tst_file1.txt");
            file2 = folder.newFile("tst_file2.txt");
        } catch (IOException ioe) {
            System.err.println("error creating temporary test file in " + this.getClass().getSimpleName());
        }
    }

    @Test
    public void fileWriteAndReadSample() {

        try {
            FileWriter fw1 = new FileWriter(file1);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            bw1.write("content for file1");
            bw1.close();

            FileWriter fw2 = new FileWriter(file2);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            bw2.write("content for file2");
            bw2.close();
        } catch (IOException ioe) {
            System.err.println("error creating temporary test file in " + this.getClass().getSimpleName());
        }

        assertTrue(file1.exists());
        assertTrue(file2.isFile());

        assertEquals(file1.length(), 17L);
        assertEquals(file1.length(), file2.length());

        assertTrue(file1.getAbsolutePath().endsWith("tst_file1.txt"));
    }
}
