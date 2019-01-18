package com.cloudcomputing.docker.limits.io;


import org.apache.commons.io.FileUtils;
import org.junit.*;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class FileManagerTest {
    public static Path userAppDirectory = Paths.get(System.getProperty("user.home") + "/dockerlimits");
    public static Path userConfigFilePath = Paths.get(userAppDirectory + "/config.yml");
    public static Path configFileOriginPath = Paths.get("src/main/resources/config.yml");

    @Autowired
    FileManager fileManager;

    public static Boolean keepFolder = false;
    public static Boolean keepFile = false;


    @BeforeClass
    public static void setUpTest() throws IOException {
        if(userAppDirectory.toFile().exists())
            keepFolder= true;
        if(userConfigFilePath.toFile().exists())
            keepFile = true;
    }

    @Before
    public void setUp() throws IOException {
        if(!userAppDirectory.toFile().exists())
            userAppDirectory.toFile().mkdirs();
        if(!userConfigFilePath.toFile().exists())
            Files.copy(configFileOriginPath, userConfigFilePath, StandardCopyOption.COPY_ATTRIBUTES);
    }
    @After
    public void tearDown() throws IOException {
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
        assert br.readLine() != null;
        assert FileUtils.contentEquals(configFile, configFileOriginPath.toFile());
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
        assert br.readLine() != null;
        assert FileUtils.contentEquals(configFile, configFileOriginPath.toFile());
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
        assert br.readLine() != null;
        assert FileUtils.contentEquals(configFile, configFileOriginPath.toFile());
    }
}