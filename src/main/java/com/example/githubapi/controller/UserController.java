package com.example.githubapi.controller;

import com.example.githubapi.exception.UserNotFoundException;
import com.example.githubapi.model.Repository;
import com.example.githubapi.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}/repos")
    public List<Repository> getRepos(@PathVariable String username) throws UserNotFoundException {
        return userService.getRepositories(username);
    }
}