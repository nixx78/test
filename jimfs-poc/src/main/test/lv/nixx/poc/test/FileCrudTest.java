package lv.nixx.poc.test;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileCrudTest {

    private FileSystem fileSystem = Jimfs.newFileSystem(Configuration.windows());
    private FileRepository fileRepository = new FileRepository();

    @Test
    public void fileCreateTest() {
        String fileName = "newFile.txt";

        Path pathToStore = fileSystem.getPath("");

        fileRepository.create(pathToStore, fileName);

        assertTrue(Files.exists(pathToStore.resolve(fileName)));
    }

    @Test
    public void fileReadTest() throws IOException {
        Path dataDir = Files.createDirectory(fileSystem.getPath("c:\\data"));

        final Path abcFilePath = fileRepository.createFileWithContent(dataDir, "abc.txt", "file content");
        String content = fileRepository.read(abcFilePath);

        assertEquals("file content", content);
    }

    @Test
    public void getPathContentTest() throws IOException {
        Path dataDir = Files.createDirectory(fileSystem.getPath("c:\\cnt"));

        fileRepository.createFileWithContent(dataDir, "f1.txt", "file content1");
        fileRepository.createFileWithContent(dataDir, "f2.txt", "file content2");
        fileRepository.createFileWithContent(dataDir, "f3.txt", "file content3");

        final Collection<String> pathContent = fileRepository.getPathContent(dataDir, 1);
        System.out.println(pathContent);

        assertEquals(3, pathContent.size());
    }

}
