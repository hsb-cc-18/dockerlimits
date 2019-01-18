package com.cloudcomputing.docker.limits.io;


import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class FileManagerTest {
    private Path userAppDirectory = Paths.get(System.getProperty("user.home") + "/dockerlimits");
    private Path userConfigFilePath = Paths.get(userAppDirectory + "/config.yml");

    @Autowired
    FileManager fileManager;


    @Test
    public void testGetConfigFile() throws IOException {
        this.fileManager.getConfigFile();
    }

    @Test
    public void testGetConfigFileFileNotExisting() throws IOException {
        File file = userConfigFilePath.toFile();
        file.delete();
        File configFile = this.fileManager.getConfigFile();
        assert configFile.exists();
    }
    @Test
    public void testGetConfigFileDirectoryNotExisting() throws IOException {
        File file = userConfigFilePath.toFile();
        FileUtils.deleteDirectory(userAppDirectory.toFile());
        File configFile = this.fileManager.getConfigFile();
        assert configFile.exists();
    }
}