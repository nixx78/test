package lv.nixx.poc.test;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.Test;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertTrue;

public class FileCrudTest {

    private FileRepository fileRepository = new FileRepository();

    @Test
    public void fileCreateTest () {
        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.windows());
        String fileName = "newFile.txt";

        Path pathToStore = fileSystem.getPath("");

        fileRepository.create(pathToStore, fileName);

        assertTrue(Files.exists(pathToStore.resolve(fileName)));



    }


}
