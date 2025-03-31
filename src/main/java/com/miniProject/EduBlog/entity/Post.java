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
        String cleanedMarkdown = markdown.replaceAll("!\\[.*?\\]\\(data:image/[^)]+\\)", ""); 
        cleanedMarkdown = cleanedMarkdown.replaceAll("\\*\\*.*?\\*\\*", ""); 
        cleanedMarkdown = cleanedMarkdown.replaceAll("\\*.*?\\*", ""); 
        cleanedMarkdown = cleanedMarkdown.replaceAll("~{1,2}.*?~{1,2}", ""); 
        cleanedMarkdown = cleanedMarkdown.replaceAll("(?m)^#{1,6}.*?$\\n?", ""); 
        cleanedMarkdown = cleanedMarkdown.replaceAll("_{1,2}.*?_{1,2}", ""); 

        String[] paragraphs = cleanedMarkdown.split("\\n\\s*\\n");
        
        for (String paragraph : paragraphs) {
            if (!paragraph.trim().isEmpty()) {
                String[] words = paragraph.trim().split("\\s+");
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < Math.min(words.length, 15); i++) {
                    result.append(words[i]).append(" ");
                }
                return result.toString().trim();
            }
        }

        
        String[] listItems = cleanedMarkdown.split("(?m)^\\s*[-*+]\\s+");
        StringBuilder listResult = new StringBuilder();
        for (int i = 1; i < listItems.length; i++) { 
            String item = listItems[i].trim();
            if (!item.isEmpty()) {
                String[] words = item.split("\\s+");
                for (int j = 0; j < Math.min(words.length, 15); j++) {
                    listResult.append(words[j]).append(" ");
                }
                listResult.append("\n"); 
            }
        }

        if (listResult.length() > 0) {
            return listResult.toString().trim();
        }

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