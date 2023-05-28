package com.clarauso.demo.controller;

import com.clarauso.demo.model.exceptions.NotFoundException;
import com.clarauso.demo.service.UserService;
import com.clarauso.demo.model.business.User;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public User create(@RequestBody User user) {
    return userService.add(user);
  }

  @GetMapping("/{id}")
  public User read(@PathVariable String id) {
    return Optional.ofNullable(userService.get(id)).orElseThrow(NotFoundException::new);
  }
}
