package com.eventplanning.repository;

import com.eventplanning.model.RSVP;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends MongoRepository<RSVP, String> {
    List<RSVP> findByEventId(String eventId);
    Optional<RSVP> findByEventIdAndGuestEmail(String eventId, String guestEmail);
    long countByEventIdAndResponse(String eventId, RSVP.RSVPResponse response);
}