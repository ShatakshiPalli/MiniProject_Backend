package com.miniProject.EduBlog.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
public class Post {
    @Id
    private String id;

    private String title;

    private String category;

    private String description;

    private String content;
    
    private int likes = 0;

    public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	@DBRef
    private User author;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.description = extractDescription(content);
    }
    public String extractDescription(String markdown) {
        // Clean the markdown by removing various elements
        String cleanedMarkdown = markdown
            .replaceAll("!\\[.*?\\]\\(data:image/[^)]+\\)", "") // Remove image links
            .replaceAll("\\*\\*.*?\\*\\*", "") // Remove bold text
            .replaceAll("\\*.*?\\*", "") // Remove italic text
            .replaceAll("~{1,2}.*?~{1,2}", "") // Remove strikethrough text
            .replaceAll("(?m)^#{1,6}.*?(\\n|$)", "") // Remove headers
            .replaceAll("_{1,2}.*?_{1,2}", "") // Remove underlined text
            .replaceAll("\\n{2,}", "\n\n"); // Normalize multiple newlines

        // Split into paragraphs
        String[] paragraphs = cleanedMarkdown.split("\\n\\s*\\n");

        // Check for non-empty paragraphs
        for (String paragraph : paragraphs) {
            if (!paragraph.trim().isEmpty()) {
                String[] words = paragraph.trim().split("\\s+");
                StringBuilder result = new StringBuilder();
                int currentLength = 0;

                for (String word : words) {
                    if (currentLength + word.length() + 1 > 80) { // +1 for space
                        break; // Stop if adding this word exceeds 80 characters
                    }
                    result.append(word).append(" ");
                    currentLength += word.length() + 1; // Update current length
                }
                return result.toString().trim();
            }
        }

        // If no paragraphs found, check for list items
        String[] listItems = cleanedMarkdown.split("(?m)^\\s*[-*+]\\s+");
        StringBuilder listResult = new StringBuilder();
        int currentLength = 0;

        for (int i = 1; i < listItems.length; i++) { // Start from 1 to skip the split part
            String item = listItems[i].trim();
            if (!item.isEmpty()) {
                String[] words = item.split("\\s+");
                for (String word : words) {
                    if (currentLength + word.length() + 1 > 80) { // +1 for space
                        break; // Stop if adding this word exceeds 80 characters
                    }
                    listResult.append(word).append(" ");
                    currentLength += word.length() + 1; // Update current length
                }
                listResult.append("\n"); 
            }
        }

        // If we found list items, return them
        if (listResult.length() > 0) {
            return listResult.toString().trim();
        }

        // If no valid paragraphs or list items were found, return the default message
        return "No description is available.";
    }
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public User getUser () {
        return author;
    }

	public void setDescription(String description) {
		
	}

} 