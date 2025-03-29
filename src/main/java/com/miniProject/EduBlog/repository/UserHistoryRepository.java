package com.miniProject.EduBlog.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.miniProject.EduBlog.entity.User;
import com.miniProject.EduBlog.entity.UserHistory;

public interface UserHistoryRepository extends MongoRepository<UserHistory, String> {
    
    // Find last N viewed posts by user
    @Query(value = "{ 'user.$id' : ?0 }", sort = "{ 'viewedAt' : -1 }")
    List<UserHistory> findLastNByUser(String userId, int limit);

    // Check if user has already viewed this post
    boolean existsByUserAndPostId(User user, String postId);

    List<UserHistory> findByUser(User user);
} 