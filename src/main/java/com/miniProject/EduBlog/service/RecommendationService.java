package com.miniProject.EduBlog.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.miniProject.EduBlog.entity.Post;
import com.miniProject.EduBlog.entity.User;
import com.miniProject.EduBlog.entity.UserHistory;
import com.miniProject.EduBlog.repository.PostRepository;
import com.miniProject.EduBlog.repository.UserHistoryRepository;

@Service
public class RecommendationService {
    private final UserHistoryRepository userHistoryRepository;
    private final PostRepository postRepository;
    private static final int RECOMMENDATION_LIMIT = 3;
    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    public RecommendationService(UserHistoryRepository userHistoryRepository, PostRepository postRepository) {
        this.userHistoryRepository = userHistoryRepository;
        this.postRepository = postRepository;
    }

    public List<Post> getRecommendedPosts(User user, List<Post> allPosts) {
        List<UserHistory> userHistory = userHistoryRepository.findByUser(user);

        // Log user ID
        logger.info("Fetching recommendations for user ID: {}", user.getId());

        if (userHistory.isEmpty()) {
            // If no history, return random posts excluding user's own posts
            List<Post> nonUserPosts = allPosts.stream()
                .filter(post -> !post.getAuthor().getId().equals(user.getId()))
                .collect(Collectors.toList());

            logger.info("No user history found. Returning random posts (excluding user's own posts).");
            Collections.shuffle(nonUserPosts);
            return nonUserPosts.stream().limit(RECOMMENDATION_LIMIT).collect(Collectors.toList());
        }

        // Get categories the user has viewed
        Set<String> viewedCategories = userHistory.stream()
            .map(history -> history.getPost().getCategory())
            .collect(Collectors.toSet());

        logger.info("User viewed categories: {}", viewedCategories);

        // Filter posts by viewed categories and exclude the user's own posts
        List<Post> recommendedPosts = allPosts.stream()
            .filter(post -> viewedCategories.contains(post.getCategory()) && !post.getAuthor().getId().equals(user.getId()))
            .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
            .limit(RECOMMENDATION_LIMIT)
            .collect(Collectors.toList());

        logger.info("Recommended posts after category filtering: {}", recommendedPosts.size());

        // If we don't have enough posts from viewed categories, add random posts excluding user's own posts
        if (recommendedPosts.size() < RECOMMENDATION_LIMIT) {
            List<Post> remainingPosts = allPosts.stream()
                .filter(post -> !recommendedPosts.contains(post) && !post.getAuthor().getId().equals(user.getId()))
                .collect(Collectors.toList());

            Collections.shuffle(remainingPosts);
            recommendedPosts.addAll(
                remainingPosts.stream()
                    .limit(RECOMMENDATION_LIMIT - recommendedPosts.size())
                    .collect(Collectors.toList())
            );
        }

        // Log the final recommended posts
        logger.info("Final recommended posts count: {}", recommendedPosts.size());
        recommendedPosts.forEach(post -> logger.info("Post ID: {}, Title: {}, Author ID: {}", 
            post.getId(), post.getTitle(), post.getAuthor().getId()));

        return recommendedPosts;
    }

    public void recordPostView(User user, Post post) {
        if (!userHistoryRepository.existsByUserAndPostId(user, post.getId())) {
            UserHistory history = new UserHistory(user, post);
            userHistoryRepository.save(history);
        }
    }
}
