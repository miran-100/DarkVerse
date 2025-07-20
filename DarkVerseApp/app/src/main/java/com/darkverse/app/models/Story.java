package com.darkverse.app.models;

public class Story {
    private String id;
    private String userId;
    private String userName;
    private String userProfileImage;
    private String mediaUrl;
    private String mediaType; // "image", "video"
    private long timestamp;
    private long expiryTime; // 24 hours from creation

    public Story() {
        // Default constructor required for calls to DataSnapshot.getValue(Story.class)
    }

    public Story(String userId, String userName, String userProfileImage, String mediaUrl, String mediaType) {
        this.userId = userId;
        this.userName = userName;
        this.userProfileImage = userProfileImage;
        this.mediaUrl = mediaUrl;
        this.mediaType = mediaType;
        this.timestamp = System.currentTimeMillis();
        this.expiryTime = timestamp + (24 * 60 * 60 * 1000); // 24 hours
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserProfileImage() { return userProfileImage; }
    public void setUserProfileImage(String userProfileImage) { this.userProfileImage = userProfileImage; }

    public String getMediaUrl() { return mediaUrl; }
    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }

    public String getMediaType() { return mediaType; }
    public void setMediaType(String mediaType) { this.mediaType = mediaType; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public long getExpiryTime() { return expiryTime; }
    public void setExpiryTime(long expiryTime) { this.expiryTime = expiryTime; }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

