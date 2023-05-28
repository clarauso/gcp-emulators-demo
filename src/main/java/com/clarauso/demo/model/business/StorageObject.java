package com.clarauso.demo.model.business;

/** Object in a generic cloud storage service. */
public record StorageObject(String bucket, String filename, Long size, byte[] content) {}
