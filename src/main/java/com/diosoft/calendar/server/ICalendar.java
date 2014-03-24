package com.diosoft.calendar.server;

/**
 * Created by EVGENIY on 24.03.14.
 */
import com.diosoft.calendar.server.pojo.Event;
import java.util.UUID;

public interface ICalendar {

    void publish(UUID uuid, Event event);

    Event remove(UUID uuid);

    Event getEvent(UUID uuid);
}
