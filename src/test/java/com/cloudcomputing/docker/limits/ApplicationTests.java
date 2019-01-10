package com.cloudcomputing.docker.limits;


import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ApplicationTests {

	@Test
	public void applicationStarts() {
		Application.main(new String[] {});
		Assertions.assertThat(true).describedAs("Silly assertion").isTrue();
	}

}
