package com.clarauso.demo.storage;

import com.clarauso.demo.dto.StorageObject;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StorageReader {

  private final Storage storage;

  private final String bucket;

  public StorageReader(Storage storage, @Value("${config.storage.default-bucket}") String bucket) {
    this.storage = storage;
    this.bucket = bucket;
  }

  public StorageObject rawRead(@NonNull String filename) {

    final Blob blob = storage.get(bucket, filename);

    return Optional.ofNullable(blob)
        .map(b -> new StorageObject(bucket, filename, b.getSize(), b.getContent()))
        .orElse(null);
  }
}
