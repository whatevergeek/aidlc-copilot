package com.eventplanning.service;

import com.eventplanning.model.Event;
import com.eventplanning.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(String name, String description, LocalDateTime dateTime, String location, String organizerId) {
        Event event = new Event(name, description, dateTime, location, organizerId);
        event.setShareableLink(generateShareableLink());
        return eventRepository.save(event);
    }

    public Optional<Event> getEventById(String eventId) {
        return eventRepository.findById(eventId);
    }

    public List<Event> getEventsByOrganizer(String organizerId) {
        return eventRepository.findByOrganizerId(organizerId);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event updateEvent(String eventId, String name, String description, LocalDateTime dateTime, String location) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isEmpty()) {
            throw new RuntimeException("Event not found");
        }
        
        Event event = eventOpt.get();
        event.setName(name);
        event.setDescription(description);
        event.setDateTime(dateTime);
        event.setLocation(location);
        return eventRepository.save(event);
    }

    public void deleteEvent(String eventId) {
        eventRepository.deleteById(eventId);
    }

    public String generateShareableLink() {
        return "event-" + UUID.randomUUID().toString();
    }

    public Optional<Event> getEventByShareableLink(String shareableLink) {
        return eventRepository.findAll().stream()
                .filter(event -> shareableLink.equals(event.getShareableLink()))
                .findFirst();
    }
}