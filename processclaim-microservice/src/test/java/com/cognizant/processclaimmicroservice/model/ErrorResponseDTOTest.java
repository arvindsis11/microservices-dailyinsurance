package com.cognizant.processclaimmicroservice.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class ErrorResponseDTOTest {

	ErrorResponseDTO error = new ErrorResponseDTO(new Date(), 200, "error", "testmsg", "/api/v1");

	@Test
	void testGetTimestamp() {
		error.setTimestamp(new Date());
		assertThat(error.getTimestamp()).isEqualTo(error.getTimestamp());
	}

	@Test
	void testGetStatus() {
		error.setStatus(200);
		assertThat(error.getStatus()).isEqualTo(200);
	}

	@Test
	void testGetError() {
		error.setError("not found");
		assertThat(error.getError()).isEqualTo("not found");
	}

	@Test
	void testGetMessage() {
		error.setMessage("msg");
		assertThat(error.getMessage()).isEqualTo("msg");
	}

	@Test
	void testGetPath() {
		error.setPath("/api");
		assertThat(error.getPath()).isEqualTo("/api");
	}

	
}
