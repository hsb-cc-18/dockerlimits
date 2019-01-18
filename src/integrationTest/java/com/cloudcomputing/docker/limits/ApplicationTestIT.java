package com.cloudcomputing.docker.limits;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
		InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
		ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class ApplicationTestIT {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private Shell shell;

	@Test
	public void contextLoads() {
		assertThat(context).isNotNull();
	}

	@Test
	public void printsHelp() {
		assertThat(shell).isNotNull();

		Object help = shell.evaluate(() -> "help");
		assertThat(help).isNotNull();
		System.out.println(help);
	}
}