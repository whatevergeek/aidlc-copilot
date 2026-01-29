package com.eventplanning.service;

import com.eventplanning.model.RSVP;
import com.eventplanning.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GuestService {
    
    @Autowired
    private GuestRepository guestRepository;

    public RSVP submitRSVP(String eventId, String guestEmail, String guestName, RSVP.RSVPResponse response) {
        Optional<RSVP> existingRSVP = guestRepository.findByEventIdAndGuestEmail(eventId, guestEmail);
        
        if (existingRSVP.isPresent()) {
            RSVP rsvp = existingRSVP.get();
            rsvp.setResponse(response);
            rsvp.setGuestName(guestName);
            return guestRepository.save(rsvp);
        } else {
            RSVP rsvp = new RSVP(eventId, guestEmail, guestName, response);
            return guestRepository.save(rsvp);
        }
    }

    public List<RSVP> getGuestList(String eventId) {
        return guestRepository.findByEventId(eventId);
    }

    public Optional<RSVP> getRSVPByEventAndGuest(String eventId, String guestEmail) {
        return guestRepository.findByEventIdAndGuestEmail(eventId, guestEmail);
    }

    public Optional<RSVP> getRSVPByEventAndEmail(String eventId, String guestEmail) {
        return guestRepository.findByEventIdAndGuestEmail(eventId, guestEmail);
    }

    public RSVPSummary getRSVPSummary(String eventId) {
        long yesCount = guestRepository.countByEventIdAndResponse(eventId, RSVP.RSVPResponse.YES);
        long noCount = guestRepository.countByEventIdAndResponse(eventId, RSVP.RSVPResponse.NO);
        return new RSVPSummary(yesCount, noCount);
    }

    public static class RSVPSummary {
        private long yesCount;
        private long noCount;

        public RSVPSummary(long yesCount, long noCount) {
            this.yesCount = yesCount;
            this.noCount = noCount;
        }

        public long getYesCount() { return yesCount; }
        public long getNoCount() { return noCount; }
        public long getTotalCount() { return yesCount + noCount; }
    }
}