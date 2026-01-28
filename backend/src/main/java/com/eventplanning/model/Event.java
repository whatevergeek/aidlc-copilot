package com.eventplanning.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "events")
public class Event {
    @Id
    private String id;
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private String location;
    private String organizerId;
    private String shareableLink;

    public Event() {}

    public Event(String name, String description, LocalDateTime dateTime, String location, String organizerId) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
        this.organizerId = organizerId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getOrganizerId() { return organizerId; }
    public void setOrganizerId(String organizerId) { this.organizerId = organizerId; }

    public String getShareableLink() { return shareableLink; }
    public void setShareableLink(String shareableLink) { this.shareableLink = shareableLink; }
}