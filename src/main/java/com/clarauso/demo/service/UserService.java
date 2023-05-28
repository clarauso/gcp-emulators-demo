package com.clarauso.demo.service;

import com.clarauso.demo.firestore.UserRepository;
import com.clarauso.demo.model.exceptions.InvalidInputException;
import com.clarauso.demo.model.business.User;

import java.util.Objects;
import java.util.Optional;

import com.clarauso.demo.model.entity.UserDocument;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {

  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  public User get(String id) {

    if (!StringUtils.hasText(id)) {
      throw new InvalidInputException();
    }

    return repository
        .findById(id)
        .map(doc -> new User(doc.getId(), doc.getName(), doc.getSurname()))
        .block();
  }

  public User add(User user) {

    UserDocument doc =
        Optional.ofNullable(user)
            .map(u -> new UserDocument(u.getName(), u.getSurname()))
            .orElseThrow(InvalidInputException::new);

    final var id = Objects.requireNonNull(repository.save(doc).block()).getId();
    user.setId(id);

    return user;
  }
}
