package com.miniProject.EduBlog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniProject.EduBlog.dto.PostRequest;
import com.miniProject.EduBlog.entity.Post;
import com.miniProject.EduBlog.entity.User;
import com.miniProject.EduBlog.repository.PostRepository;
import com.miniProject.EduBlog.repository.UserRepository;
import com.miniProject.EduBlog.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
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

    @PostMapping("/liked-list/")
    public HashMap<String,Boolean> likedPost(@RequestBody HashMap<String,String> postRequest) {
    	User user = userRepository.getUserByUsername(postRequest.get("username"));
    	List<String> likes = user.getLikedPostIds();
    	likes.add(postRequest.get("post_id"));
    	user.setLikedPostIds(likes);
    	HashMap<String,Boolean> res = new HashMap<>();
    	res.put("isLiked", true);
    	res.put(postRequest.get("username"), true);
    	System.out.println("ok");
    	return res;
    }
    
    @PostMapping("/liked-list/dislike")
    public HashMap<String,Boolean> disLikedPost(@RequestBody HashMap<String,String> postRequest) {
    	User user = userRepository.getUserByUsername(postRequest.get("username"));
    	List<String> likes = user.getLikedPostIds();
    	int v = likes.indexOf(postRequest.get("post_id"));
    	if(v > -1) {    		
    		likes.remove(v);
    	}
    	user.setLikedPostIds(likes);
    	HashMap<String,Boolean> res = new HashMap<>();
    	res.put("isDisliked", true);
    	res.put(postRequest.get("username"), true);
    	return res;
    }
    
    @GetMapping("/liked-list/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }
}
