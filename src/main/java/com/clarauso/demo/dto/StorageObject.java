package com.clarauso.demo.dto;

/** Object in a generic cloud storage service. */
public record StorageObject(String bucket, String filename, Long size, byte[] content) {}
