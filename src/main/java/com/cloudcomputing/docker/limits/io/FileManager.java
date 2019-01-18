package com.cloudcomputing.docker.limits.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;


/**
 *
 */
@Component
public class FileManager {
    private Path userAppDirectory = Paths.get(System.getProperty("user.home") + "/dockerlimits");
    private Path userConfigFilePath = Paths.get(userAppDirectory + "/config.yml");
    private Path configFileOriginPath = Paths.get("src/main/resources/config.yml");


    @Autowired
    public FileManager() {}

    public File getConfigFile() throws IOException {
        //System.out.println(configFileOriginPath.toAbsolutePath().toString());
        //System.out.println(userConfigFilePath);
        File appDirectory = new File(userAppDirectory.toString());
        File configFile = new File(userConfigFilePath.toString());

        if(!appDirectory.exists() || !appDirectory.isDirectory()){
            System.out.println("Create Directory");
            Boolean directoryMade = (new File(userAppDirectory.toString())).mkdirs();
            if (!directoryMade) {
                throw new IOException("Could not create path: " + userAppDirectory.toString());
            }
        }
        if(!(configFile.exists())) {
            System.out.println("Create File");

            try{
                CopyOption[] options = new CopyOption[]{
                        StandardCopyOption.COPY_ATTRIBUTES
                };
                Files.copy(configFileOriginPath, userConfigFilePath, options);

            } catch (IOException e) {
                throw  new IOException("Could not create config File: " + userConfigFilePath.toString(), e);
            }
        }
        System.out.println("Read File");


        return configFile;

    }
}
