package com.example.githubapi.controller;

import com.example.githubapi.model.Repository;
import com.example.githubapi.service.UserService;
import com.example.githubapi.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{username}/repos")
    public ResponseEntity<List<Repository>> getRepos(@PathVariable String username) {
        try {
            List<Repository> repositories = userService.getRepositories(username);
            return ResponseEntity.ok(repositories);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}