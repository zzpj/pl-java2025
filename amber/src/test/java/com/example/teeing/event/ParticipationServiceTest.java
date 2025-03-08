package com.example.teeing.event;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParticipationServiceTest {

    @Test
    void processEventGuests() {
        List<Guest> guests = List.of(
                new Guest("name1", true, 10),
                new Guest("name2", false, 2),
                new Guest("name3", true, 5)
        );
        ParticipationService participationService = new ParticipationService();
        EventParticipation eventParticipation = participationService.processEventGuests(guests);

        assertEquals(eventParticipation.getGuestNameList(), Arrays.asList("name1", "name3"));
        assertEquals(17, eventParticipation.getTotalNumberOfParticipants());
    }
}
