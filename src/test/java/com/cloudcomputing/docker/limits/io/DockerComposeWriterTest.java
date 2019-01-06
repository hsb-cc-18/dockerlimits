package com.cloudcomputing.docker.limits.io;

import com.cloudcomputing.docker.limits.model.io.DockerCompose;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.not;


@SpringBootTest
@RunWith(SpringRunner.class)
public class DockerComposeWriterTest {

    static final String DOCKER_COMPOSE_YML = "docker-compose-with-username-and-service-labeled.yml";

    @Autowired
    DockerComposeReader dockerComposeReader;

    @Autowired
    DockerComposeWriter dockerComposeWriter;

    @ClassRule
    public static TemporaryFolder folder = new TemporaryFolder();
    static File target;

    private DockerCompose dockerCompose;
    private DockerCompose dockerComposeRed;

    @BeforeClass
    public static void setUpTest() throws IOException {
        folder.create();
        target = folder.newFile("out.yml");
    }

    @Before
    public void setUp() throws IOException {
        final InputStream dockerComposeYML = getClass().getResource(DOCKER_COMPOSE_YML).openStream();
        dockerCompose = dockerComposeReader.read(dockerComposeYML);
        dockerComposeWriter.write(target, dockerCompose);
        dockerComposeRed = dockerComposeReader.read(FileUtils.openInputStream(target));
    }

    @Test
    public void testWrite() throws IOException {
        assertThat(target).as("The target file should be created").exists();
        assertThat(FileUtils.readLines(target, Charset.forName("UTF-8")).size())
                .as("The file should contain more that 3 lines").isGreaterThan(3);
    }

    @Test
    public void testWrittenFileIsSameAsRed() {
        Assert.assertThat("The origin and red objects should be equal", dockerComposeRed, sameBeanAs(dockerCompose).ignoring("hsbUsername"));
        assertThat(dockerComposeRed.getHsbUsername()).as("The username should be removed").isNullOrEmpty();
    }

    @Test
    public void testWrittenFileIsNotSameAsRedIfModified() {
        dockerCompose.getServices().get("web").mem_limit = "12M";
        Assert.assertThat("The objects are not equal", dockerComposeRed, not(sameBeanAs(dockerCompose).ignoring("hsbUsername")));
        assertThat(dockerComposeRed.getHsbUsername()).as("The username should be removed").isNullOrEmpty();
    }

}