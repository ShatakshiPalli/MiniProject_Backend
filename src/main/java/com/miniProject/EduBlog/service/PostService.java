package com.miniProject.EduBlog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniProject.EduBlog.entity.Post;
import com.miniProject.EduBlog.entity.User;
import com.miniProject.EduBlog.repository.PostRepository;
import com.miniProject.EduBlog.repository.UserRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(Post post, String userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        post.setAuthor(user);
        post.onCreate();
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> searchPosts(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return postRepository.findAll();
        }
        return postRepository.searchPosts(searchTerm.trim());
    }

    public Map<String, Object> searchPostsWithPagination(String searchTerm, int page, int size) {
        List<Post> searchResults;
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            searchResults = postRepository.findAll();
        } else {
            searchResults = postRepository.searchPosts(searchTerm.trim());
        }

        int start = page * size;
        int end = Math.min((start + size), searchResults.size());
        
        List<Post> paginatedResults = start < searchResults.size() ? 
            searchResults.subList(start, end) : 
            new ArrayList<>();

        Map<String, Object> response = new HashMap<>();
        response.put("blogs", paginatedResults);
        response.put("total", searchResults.size());
        response.put("showing", paginatedResults.size());
        
        return response;
    }

    public Map<String, Object> getAllPostsPaginated(int page, int size) {
        List<Post> allPosts = postRepository.findAll();
        int start = page * size;
        int end = Math.min((start + size), allPosts.size());
        
        List<Post> paginatedPosts = start < allPosts.size() ? 
            allPosts.subList(start, end) : 
            new ArrayList<>();

        Map<String, Object> response = new HashMap<>();
        response.put("blogs", paginatedPosts);
        response.put("total", allPosts.size());
        response.put("showing", paginatedPosts.size());
        
        return response;
    }

    public Optional<Post> getPostById(String id) {
        return postRepository.findById(id);
    }

    public List<Post> getUserPosts(User user) {
        return postRepository.findByAuthor(user);
    }

    public Post updatePost(String id, Post postDetails) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post not found"));
        
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        post.setCategory(postDetails.getCategory());
        post.setDescription(postDetails.getDescription());
        post.onUpdate();
        
        return postRepository.save(post);
    }

    public void likePost(String postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        if (!user.getLikedPostIds().contains(postId)) {
            post.setLikes(post.getLikes() + 1);
            user.getLikedPostIds().add(postId); 
            postRepository.save(post);
            userRepository.save(user); 
        }
    }

    public void unlikePost(String postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        if (user.getLikedPostIds().contains(postId)) {
            post.setLikes(post.getLikes() - 1);
            user.getLikedPostIds().remove(postId);
            postRepository.save(post);
            userRepository.save(user); 
        }
    }
    
    public void deletePost(String id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);
    }
} 