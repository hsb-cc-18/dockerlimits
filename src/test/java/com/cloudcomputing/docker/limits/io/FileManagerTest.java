package com.cloudcomputing.docker.limits.io;


import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class FileManagerTest {
    private static final Path userAppDirectory = FileManager.userAppDirectory;
    private static final Path userConfigFilePath = FileManager.userConfigFilePath;
    private static final File configFileOriginFile = new File(FileManagerTest.class.getResource(FileManager.CONFIG_FILE_ORIGIN_LOCATION).getFile());

    @Autowired
    FileManager fileManager;

    public static Boolean keepFolder = false;
    public static Boolean keepFile = false;


    @BeforeClass
    public static void setUpTest() {
        if(userAppDirectory.toFile().exists())
            keepFolder = true;
        if(userConfigFilePath.toFile().exists())
            keepFile = true;
    }

    @Before
    public void setUp() throws IOException, URISyntaxException {
        if(!userAppDirectory.toFile().exists())
            userAppDirectory.toFile().mkdirs();
        if(!userConfigFilePath.toFile().exists())
            Files.copy(Paths.get(getClass().getResource(FileManager.CONFIG_FILE_ORIGIN_LOCATION).toURI()), userConfigFilePath, StandardCopyOption.COPY_ATTRIBUTES);
    }
    @After
    public void tearDown() {
        if(userConfigFilePath.toFile().exists() && !keepFile){
            userConfigFilePath.toFile().delete();
            System.out.println("deleted file");
        }

        if(userAppDirectory.toFile().exists() && !keepFolder){
            userAppDirectory.toFile().delete();
            System.out.println("deleted directory");
        }

    }

    @Test
    public void testGetConfigFile() throws IOException {
        File configFile = this.fileManager.getConfigFile();
        BufferedReader br = new BufferedReader(new FileReader(configFile.toString()));
        assertThat(br.readLine()).isNotNull();
        assertThat(configFile).hasSameContentAs(configFileOriginFile);
    }

    @Test
    public void testGetConfigFileFileNotExisting() throws IOException {
        File file = userConfigFilePath.toFile();
        if(!keepFile)
            file.delete();
        else {
            System.out.println("Did not test this case because test would harm existing directory structure");
            System.out.println("Test without deleting file");
        }
        File configFile = this.fileManager.getConfigFile();
        BufferedReader br = new BufferedReader(new FileReader(configFile.toString()));
        assertThat(br.readLine()).isNotNull();
        assertThat(configFile).hasSameContentAs(configFileOriginFile);
    }
    @Test
    public void testGetConfigFileDirectoryNotExisting() throws IOException {
        File file = userConfigFilePath.toFile();
        if(!keepFolder)
            FileUtils.deleteDirectory(userAppDirectory.toFile());
        else {
            System.out.println("Did not test this case because test would harm existing directory structure");
            System.out.println("Test without deleting directory");
        }
        File configFile = this.fileManager.getConfigFile();
        BufferedReader br = new BufferedReader(new FileReader(configFile.toString()));
        assertThat(br.readLine()).isNotNull();
        assertThat(configFile).hasSameContentAs(configFileOriginFile);
    }
}