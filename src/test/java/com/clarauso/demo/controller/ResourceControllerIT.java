package com.clarauso.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class ResourceControllerIT {

  @Autowired MockMvc mockMvc;

  static final String GET_PATH = "/resources/{filename}";

  @Test
  public void getResource_resourceNotFound_returnsNotFound() throws Exception {

    mockMvc.perform(get(GET_PATH, "not-found-123")).andExpect(status().isNotFound());
  }
}
