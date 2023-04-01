package com.clarauso.demo.storage;

import com.clarauso.demo.dto.StorageObject;
import com.google.cloud.storage.BlobInfo;
import java.io.IOException;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StorageWriter {

  private static final Logger log = LoggerFactory.getLogger(StorageWriter.class);

  private final ApplicationContext context;

  private final String bucket;

  public StorageWriter(
      ApplicationContext context, @Value("${config.storage.default-bucket}") String bucket) {
    this.context = context;
    this.bucket = bucket;
  }

  public StorageObject rawWrite(@NonNull String filename, @NonNull byte[] content) {

    final BlobInfo blobInfo = BlobInfo.newBuilder(bucket, filename).build();
    final String uri = blobInfo.getBlobId().toGsUtilUri();

    Resource resource = context.getResource(uri);
    try (OutputStream os = ((WritableResource) resource).getOutputStream()) {
      os.write(content);
    } catch (IOException e) {
      log.error("Error writing content filename={} ", filename, e);
      throw new RuntimeException(e);
    }

    return new StorageObject(bucket, filename, (long) content.length, content);
  }
}
