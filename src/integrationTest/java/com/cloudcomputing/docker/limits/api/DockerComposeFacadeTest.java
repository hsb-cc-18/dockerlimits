package com.cloudcomputing.docker.limits.api;

import com.cloudcomputing.docker.limits.io.DockerComposeReader;
import com.cloudcomputing.docker.limits.io.DockerComposeWriter;
import com.cloudcomputing.docker.limits.model.validator.DockerComposeValidator;
import com.cloudcomputing.docker.limits.services.compose.DockerComposeService;
import com.cloudcomputing.docker.limits.services.resource.ResourceAuthorizeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DockerComposeFacadeTest {

    @SpyBean
    private DockerComposeReader dockerComposeReader;
    @SpyBean
    private DockerComposeWriter dockerComposeWriter;
    @MockBean
    private DockerComposeService dockerComposeServiceMock;
    @MockBean
    private DockerComposeValidator dockerComposeValidator;
    @MockBean
    private ResourceAuthorizeService resourceAuthorizeService;
    @Autowired
    private DockerComposeFacade dockerComposeFacade;

    @Test
    public void startDockerComposeFile() throws IOException {
        final File dockerComposeFile = new File(getClass().getResource("../io/docker-compose-with-username.yml").getFile());

        when(resourceAuthorizeService.isAuthorized(any())).thenReturn(true);

        dockerComposeFacade.startDockerComposeFile(dockerComposeFile);

        // Verify Interaction with spies: they call the real method
        verify(dockerComposeReader, times(1)).read(any());
        verifyNoMoreInteractions(dockerComposeReader);
        verify(dockerComposeWriter, times(1)).write(any(), any());
        verifyNoMoreInteractions(dockerComposeWriter);
        verify(dockerComposeValidator, times(1)).validate(any());
        verifyNoMoreInteractions(dockerComposeValidator);
        verify(resourceAuthorizeService, times(1)).isAuthorized(any());
        verifyNoMoreInteractions(resourceAuthorizeService);
        // Verify Interaction with mock: they don't call the real method
        verify(dockerComposeServiceMock, times(1)).startComposeFile(any());
        verifyNoMoreInteractions(dockerComposeServiceMock);
    }
}