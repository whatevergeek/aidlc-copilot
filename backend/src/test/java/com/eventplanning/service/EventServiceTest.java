package com.eventplanning.service;

import com.eventplanning.model.Event;
import com.eventplanning.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void createEvent_Success() {
        // Given
        Event savedEvent = new Event();
        savedEvent.setName("Test Event");
        savedEvent.setOrganizerId("user123");
        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        // When
        Event result = eventService.createEvent("Test Event", "Test Description", 
            LocalDateTime.now().plusDays(1), "Test Location", "user123");

        // Then
        assertNotNull(result);
        assertEquals("Test Event", result.getName());
        assertEquals("user123", result.getOrganizerId());
    }

    @Test
    void getAllEvents_Success() {
        // Given
        Event event1 = new Event();
        event1.setName("Event 1");
        Event event2 = new Event();
        event2.setName("Event 2");
        List<Event> events = Arrays.asList(event1, event2);
        when(eventRepository.findAll()).thenReturn(events);

        // When
        List<Event> result = eventService.getAllEvents();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Event 1", result.get(0).getName());
        assertEquals("Event 2", result.get(1).getName());
    }

    @Test
    void getEventById_Success() {
        // Given
        Event event = new Event();
        event.setId("event123");
        event.setName("Test Event");
        when(eventRepository.findById("event123")).thenReturn(Optional.of(event));

        // When
        Optional<Event> result = eventService.getEventById("event123");

        // Then
        assertTrue(result.isPresent());
        assertEquals("event123", result.get().getId());
        assertEquals("Test Event", result.get().getName());
    }

    @Test
    void getEventById_NotFound_ReturnsEmpty() {
        // Given
        when(eventRepository.findById("nonexistent")).thenReturn(Optional.empty());

        // When
        Optional<Event> result = eventService.getEventById("nonexistent");

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void deleteEvent_Success() {
        // Given
        doNothing().when(eventRepository).deleteById("event123");

        // When
        eventService.deleteEvent("event123");

        // Then
        verify(eventRepository).deleteById("event123");
    }
}