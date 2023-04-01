package com.clarauso.demo.storage;

import com.clarauso.demo.dto.StorageObject;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

@Service
public class StorageService {

  private final StorageReader reader;

  private final StorageWriter writer;

  public StorageService(StorageReader reader, StorageWriter writer) {
    this.reader = reader;
    this.writer = writer;
  }

  public StorageObject rawRead(@NonNull String filename) {
    return reader.rawRead(filename);
  }

  public StorageObject rawWrite(String filename, @NonNull byte[] content) {

    final String encodedFilename =
        Optional.ofNullable(filename)
            .map(name -> UriUtils.encode(name, StandardCharsets.UTF_8))
            .orElse(UUID.randomUUID().toString());

    return writer.rawWrite(encodedFilename, content);
  }
}
