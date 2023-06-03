package com.clarauso.demo.model.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;

@Document(collectionName = "users")
public class UserDocument {

  @DocumentId private String id;

  private String name;

  private String surname;

  public UserDocument() {
    super();
  }

  public UserDocument(String name, String surname) {
    this.name = name;
    this.surname = surname;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }
}
