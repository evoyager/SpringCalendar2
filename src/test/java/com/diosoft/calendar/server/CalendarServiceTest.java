package com.diosoft.calendar.server;

import com.diosoft.calendar.server.data.ICalendar;
import com.diosoft.calendar.server.pojo.Event;
import com.diosoft.calendar.server.pojo.Person;
import com.diosoft.calendar.server.service.CalendarService;
import com.diosoft.calendar.server.service.ICalendarService;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by EVGENIY on 26.03.14.
 */
public class CalendarServiceTest {
    Person I;
    Event event;
    UUID uuid;
    ICalendar calendar;
    ICalendarService service;

    @Before
    public void setUp(){
        I = new Person.Builder()
                .setFirstName("Evgeniy")
                .setSecondName("Gusar")
                .setAge(28)
                .setEmail("evgeniygusar@rambler.ru")
                .build();
        List<Person> attenders = new ArrayList<Person>();
        attenders.add(I);
        event = new Event.Builder()
                .setName("My Birthday")
                .setDescription("Celebrate my bithday")
                .setStartDate(new GregorianCalendar(2014, 4, 15, 17, 30, 00))
                .setAllDay(true)
                .setAttenders(attenders)
                .build();

        uuid = UUID.fromString("477491d5-fe71-45e8-a1c9-986ec1905249");

        calendar = mock(ICalendar.class);
        service = new CalendarService(calendar);
    }

    @Test
    public void testAddEvent() throws Exception {

        when(calendar.getEvent(uuid)).thenReturn(event);

        service.addEvent(uuid, event);

        Event returnedEvent = service.readEvent(uuid);
        assertEquals(event, returnedEvent);

        verify(calendar).publish(uuid, event);
        verify(calendar).getEvent(uuid);
    }

    @Test
    public void testDeleteEvent() throws Exception {
        when(calendar.remove(uuid)).thenReturn(event);

        service.addEvent(uuid, event);

        Event returnedEvent = service.deleteEvent(uuid);
        assertEquals(returnedEvent, event);

        verify(calendar).remove(uuid);
    }

    @Test
    public void testReadEvent() throws Exception {
        when(calendar.getEvent(uuid)).thenReturn(event);

        Event returnedEvent = service.readEvent(uuid);
        assertEquals(returnedEvent, event);

        verify(calendar).getEvent(uuid);
    }

    @Test
    public void testEditEvent() throws Exception {
        String name = "MYBIRTHDAY";
        Event newEvent = new Event.Builder(event)
                .setName(name)
                .build();
        when(calendar.getEvent(uuid)).thenReturn(event);

        Event returnedEvent = service.editEvent(uuid, "name", name);
        assertEquals(returnedEvent, newEvent);

        verify(calendar).getEvent(uuid);
        verify(calendar).remove(uuid);
        verify(calendar).publish(uuid, newEvent);
    }

    @Test
    public void testIsPersonAvailable() throws Exception {
        Map<UUID, Event> storage = new HashMap<UUID, Event>();
        storage.put(uuid, event);
        when(calendar.getStorage()).thenReturn(storage);

        GregorianCalendar desireDate = new GregorianCalendar(2014, 4, 15, 17, 30, 00);
        boolean availability = service.isPersonAvailable(I, desireDate);
        assertEquals(availability, false);

        verify(calendar).getStorage();
    }

    @Test
    public void testCheckAvailability() throws Exception {
        Map<UUID, Event> storage = new HashMap<UUID, Event>();
        storage.put(uuid, event);
        when(calendar.getStorage()).thenReturn(storage);

        List<GregorianCalendar> checkAvailabilityList;
        checkAvailabilityList = service.checkAvailability(I);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
        for(GregorianCalendar date : checkAvailabilityList)
            System.out.println(sdf.format(date.getTime()));
    }
}
