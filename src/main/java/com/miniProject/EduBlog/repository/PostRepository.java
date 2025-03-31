package com.miniProject.EduBlog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
//    Post findByTitleAnd
    
    @Query("{ $or: [ " +
           "{ 'title': { $regex: ?0, $options: 'i' } }, " +
           "{ 'content': { $regex: ?0, $options: 'i' } }, " +
           "{ 'description': { $regex: ?0, $options: 'i' } }, " +
           "{ 'category': { $regex: ?0, $options: 'i' } } " +
           "] }")
    List<Post> searchPosts(String searchTerm);
    
	Page<Post> findByTitleContainingIgnoreCase(String title, Pageable pageable);
	//In PostRepository
	@Query("{ 'id': ?0 }")
	Optional<Post> findPostById(String id);
    
    // Alternatively, you can use a custom query
    @Query("{ 'title': { $regex: ?0, $options: 'i' } }")
    Page<Post> searchByTitle(String title, Pageable pageable);
} 