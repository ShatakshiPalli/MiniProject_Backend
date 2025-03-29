package com.miniProject.EduBlog.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.miniProject.EduBlog.entity.Post;
import com.miniProject.EduBlog.entity.User;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByAuthor(User author);
    List<Post> findByCategory(String category);
    List<Post> findByTitleContainingIgnoreCase(String title);
    
    @Query("{ $or: [ " +
           "{ 'title': { $regex: ?0, $options: 'i' } }, " +
           "{ 'content': { $regex: ?0, $options: 'i' } }, " +
           "{ 'description': { $regex: ?0, $options: 'i' } }, " +
           "{ 'category': { $regex: ?0, $options: 'i' } } " +
           "] }")
    List<Post> searchPosts(String searchTerm);
} 