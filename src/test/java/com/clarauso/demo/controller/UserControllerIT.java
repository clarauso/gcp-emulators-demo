package com.clarauso.demo.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserControllerIT {

  @Autowired WebApplicationContext webApplicationContext;

  MockMvc mockMvc;

  static final String GET_PATH = "/users/{user-id}";

  @BeforeAll
  public void init() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

  @Test
  public void postUser_validData_returnsCreatedStatus() throws Exception {

    final var requestBody = """
{"name": "John","surname": "Doe"}""";

    mockMvc
        .perform(post("/users").contentType(APPLICATION_JSON_VALUE).content(requestBody))
        .andExpect(status().isCreated())
        .andReturn();
  }

  @Test
  public void getUser_userNotFound_returnsNotFoundStatus() throws Exception {

    mockMvc
        .perform(get(GET_PATH, "not-found-123").contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }
}
