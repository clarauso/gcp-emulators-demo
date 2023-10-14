package com.clarauso.demo.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

  @Autowired MockMvc mockMvc;

  static final String GET_PATH = "/users/{user-id}";

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
