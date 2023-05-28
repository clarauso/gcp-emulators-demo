package com.clarauso.demo.controller;

import com.clarauso.demo.model.business.StorageObject;
import com.clarauso.demo.model.exceptions.NotFoundException;
import com.clarauso.demo.model.dto.ResourceReference;
import com.clarauso.demo.service.StorageService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/resources")
public class ResourceController {

  private static final Logger log = LoggerFactory.getLogger(ResourceController.class);

  private final StorageService storageService;

  public ResourceController(StorageService storageService) {
    this.storageService = storageService;
  }

  @GetMapping("/{filename}")
  public void get(@PathVariable String filename, HttpServletResponse response) {

    final StorageObject object =
        Optional.ofNullable(storageService.rawRead(filename)).orElseThrow(NotFoundException::new);

    writeToResponse(object.content(), response);
  }

  @PostMapping
  public ResourceReference post(@RequestParam("file") MultipartFile file) {

    StorageObject storageObject;
    try {
      storageObject = storageService.rawWrite(file.getOriginalFilename(), file.getBytes());
    } catch (IOException e) {
      log.error("Error storing resource ", e);
      throw new RuntimeException(e);
    }

    return new ResourceReference(storageObject.filename(), storageObject.size());
  }

  /** Handles response output stream. */
  private void writeToResponse(byte[] dataToWrite, HttpServletResponse response) {

    if (dataToWrite.length == 0) {
      response.setStatus(HttpStatus.NO_CONTENT.value());
      return;
    }

    try (OutputStream out = response.getOutputStream()) {

      final var bos = new ByteArrayOutputStream();
      bos.write(dataToWrite);
      bos.writeTo(out);

    } catch (Exception e) {
      log.error("Error writing response to stream ", e);
      throw new RuntimeException(e);
    }
  }
}
