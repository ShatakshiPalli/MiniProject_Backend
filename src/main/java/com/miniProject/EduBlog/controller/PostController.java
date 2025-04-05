package com.miniProject.EduBlog.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniProject.EduBlog.dto.PostRequest;
import com.miniProject.EduBlog.entity.Post;
import com.miniProject.EduBlog.entity.User;
import com.miniProject.EduBlog.repository.PostRepository;
import com.miniProject.EduBlog.service.PostService;
import com.miniProject.EduBlog.service.RecommendationService;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private final PostRepository postRepository;
    private final RecommendationService recommendationService;
    @Autowired
    private PostService postService;

    public PostController(PostRepository postRepository, RecommendationService recommendationService) {
        this.postRepository = postRepository;
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest, @AuthenticationPrincipal User user) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setCategory(postRequest.getCategory());
        post.setContent(postRequest.getContent());
        post.setDescription(postRequest.getDescription());
        post.setAuthor(user);
        post.onCreate();
        
        Post savedPost = postRepository.save(post);
        return ResponseEntity.ok(savedPost);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPosts(
            @RequestParam(required = false) String category,
            @AuthenticationPrincipal User user) {
        try {
            List<Post> posts = postService.getAllPosts();
            Map<String, Object> response = new HashMap<>();
            response.put("blogs", posts);
        response.put("total", posts.size());
        response.put("showing", posts.size());
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error fetching posts: " + e.getMessage());
        errorResponse.put("blogs", new ArrayList<>());
        errorResponse.put("total", 0);
        errorResponse.put("showing", 0);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

@GetMapping("/{id}")
public ResponseEntity<Post> getPost(@PathVariable String id) {
    Optional<Post> post = postService.getPostById(id);
    return post.map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
}

@PutMapping("/{id}")
public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody PostRequest postRequest, @AuthenticationPrincipal User user) {
    Optional<Post> postOptional = postRepository.findById(id);
    if (postOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    Post post = postOptional.get();
    if (!post.getAuthor().getId().equals(user.getId())) {
        return ResponseEntity.badRequest().build();
    }

    post.setTitle(postRequest.getTitle());
    post.setCategory(postRequest.getCategory());
    post.setContent(postRequest.getContent());
    post.setDescription(postRequest.getDescription());
    post.onUpdate();
    
    Post updatedPost = postRepository.save(post);
    return ResponseEntity.ok(updatedPost);
}

@DeleteMapping("/{id}")
public ResponseEntity<?> deletePost(@PathVariable String id, @AuthenticationPrincipal User user) {
    Optional<Post> postOptional = postRepository.findById(id);
    if (postOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    Post post = postOptional.get();
    if (!post.getAuthor().getId().equals(user.getId())) {
        return ResponseEntity.badRequest().body("You can only delete your own posts");
    }

    postRepository.delete(post);
    return ResponseEntity.ok().body("Post deleted successfully");
}

@GetMapping("/my-posts")
public ResponseEntity<List<Post>> getMyPosts(@AuthenticationPrincipal User user) {
    List<Post> posts = postRepository.findByAuthor(user);
    return ResponseEntity.ok(posts);
}

@GetMapping("/recommended")
public ResponseEntity<List<Post>> getRecommendedPosts(@AuthenticationPrincipal User user) {
    if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    List<Post> allPosts = postRepository.findAll();
    List<Post> recommendedPosts = recommendationService.getRecommendedPosts(user, allPosts);
    return ResponseEntity.ok(recommendedPosts);
}

@GetMapping("/search")
public Map<String, Object> searchPostsWithPagination(
        @RequestParam(value = "query", required = false, defaultValue = "") String query,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {
    
    Pageable pageable = PageRequest.of(page, size);
    Page<Post> postPage = postRepository.findByTitleContainingIgnoreCase(query, pageable);
    
    Map<String, Object> response = new HashMap<>();
    response.put("blogs", postPage.getContent());
    response.put("total", postPage.getTotalElements());
    response.put("showing", postPage.getNumberOfElements());
    
    return response;
}


@PostMapping("/{id}/like")
public ResponseEntity<Post> likePost(@PathVariable String id, @AuthenticationPrincipal User user) {
    postService.likePost(id, user);
    return ResponseEntity.ok(postRepository.findById(id).orElse(null));
}

@DeleteMapping("/{id}/like")
    public ResponseEntity<Post> unlikePost(@PathVariable String id, @AuthenticationPrincipal User user) {
        postService.unlikePost(id, user);
        return ResponseEntity.ok(postRepository.findById(id).orElse(null));
    }
} 