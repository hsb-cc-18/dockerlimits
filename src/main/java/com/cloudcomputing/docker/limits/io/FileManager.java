package com.cloudcomputing.docker.limits.io;

import com.github.rozidan.springboot.logger.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 *
 */
@Component
@Loggable
public class FileManager {

    private static final Logger logger = LoggerFactory.getLogger(FileManager.class);
    public static final String CONFIG_FILE_ORIGIN_NAME = "config.yml";
    public static final String CONFIG_FILE_ORIGIN_LOCATION = "/" + CONFIG_FILE_ORIGIN_NAME;
    public static final Path userAppDirectory = Paths.get(System.getProperty("user.home") + File.separator + "dockerlimits");
    public static final Path userConfigFilePath = Paths.get(userAppDirectory + CONFIG_FILE_ORIGIN_LOCATION);

    @Autowired
    public FileManager() {}

    public File getConfigFile() throws IOException {

        if(!Files.exists(userAppDirectory) || !Files.isDirectory(userAppDirectory)){
            createDirectory();
        }
        if(!Files.exists(userConfigFilePath)) {
            createFile();
        }

        logger.debug("Read File");

        return userConfigFilePath.toFile();

    }

    private void createDirectory() throws IOException {
        logger.debug("Create Directory");
        boolean directoryMade = Files.exists(Files.createDirectory(userAppDirectory));
        if (!directoryMade) {
            throw new IOException("Could not create path: " + userAppDirectory.toString());
        }
    }

    private void createFile() throws IOException {
        logger.debug("Create File");
        try {
            Files.copy(getClass().getResourceAsStream(CONFIG_FILE_ORIGIN_LOCATION), userConfigFilePath);
        } catch (IOException e) {
            throw new IOException("Could not create config File: " + userConfigFilePath.toString(), e);
        }
    }

}
