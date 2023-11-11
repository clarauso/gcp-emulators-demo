package com.clarauso.demo.storage;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty("config.storage.emulator-host")
@Configuration
public class Config {

  private final String EMULATOR_PROTOCOL = "http://";

  @Value("${config.storage.emulator-host}")
  private String emulatorHost;

  @Bean
  public Storage storage() {
    // empty credentials to avoid authentication
    final var credentials = GoogleCredentials.create(AccessToken.newBuilder().build());

    return StorageOptions.newBuilder()
        .setHost(EMULATOR_PROTOCOL + emulatorHost)
        .setCredentials(credentials)
        .build()
        .getService();
  }
}
