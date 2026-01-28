package com.eventplanning.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rsvps")
public class RSVP {
    @Id
    private String id;
    private String eventId;
    private String guestEmail;
    private String guestName;
    private RSVPResponse response;

    public enum RSVPResponse {
        YES, NO
    }

    public RSVP() {}

    public RSVP(String eventId, String guestEmail, String guestName, RSVPResponse response) {
        this.eventId = eventId;
        this.guestEmail = guestEmail;
        this.guestName = guestName;
        this.response = response;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getGuestEmail() { return guestEmail; }
    public void setGuestEmail(String guestEmail) { this.guestEmail = guestEmail; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public RSVPResponse getResponse() { return response; }
    public void setResponse(RSVPResponse response) { this.response = response; }
}