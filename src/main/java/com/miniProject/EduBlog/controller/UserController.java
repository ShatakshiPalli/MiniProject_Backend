package com.miniProject.EduBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniProject.EduBlog.entity.Post;
import com.miniProject.EduBlog.entity.User;
import com.miniProject.EduBlog.repository.PostRepository;
import com.miniProject.EduBlog.repository.UserRepository;
import com.miniProject.EduBlog.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final PostRepository postRepository;	
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserService userService, PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
		this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    
    @GetMapping("/liked-list/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }
}
