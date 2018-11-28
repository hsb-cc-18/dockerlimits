package com.cloudcomputing.docker.limits;

import com.cloudcomputing.docker.limits.io.DockerComposeFile;
import com.cloudcomputing.docker.limits.io.DockerComposeReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private final DockerComposeReader dockerComposeReader;

	@Autowired
	public Application(DockerComposeReader dockerComposeReader) {
		this.dockerComposeReader = dockerComposeReader;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Callback used to run the bean.
	 *
	 * @param args incoming main method arguments
	 * @throws Exception on error
	 */
	@Override
	public void run(String... args) throws Exception {
		try {
			final DockerComposeFile dockerComposeFile = dockerComposeReader.read(
					getClass().getResource("io/docker-compose-with-username.yml")
							  .openStream());
			System.out.println(dockerComposeFile.getHsbUsername());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
