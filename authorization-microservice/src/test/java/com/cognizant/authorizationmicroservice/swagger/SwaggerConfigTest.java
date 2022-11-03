package com.cognizant.authorizationmicroservice.swagger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SwaggerConfigTest {

	@Autowired
	MockMvc mvc;
	
	@Test
	public void testSwaggerEndPoint() throws Exception {
		this.mvc.perform(get("/v2/api-docs")).andExpect(status().isOk());
	}
	
	@Test
	public void testSwaggerUIEndPoint() throws Exception {
		this.mvc.perform(get("/swagger-ui.html")).andExpect(status().isOk());
	}
}

