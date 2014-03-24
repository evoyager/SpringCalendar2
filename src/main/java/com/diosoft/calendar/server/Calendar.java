package com.diosoft.calendar.server;

import com.diosoft.calendar.server.adapter.EventAdapter;
import com.diosoft.calendar.server.pojo.Event;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by EVGENIY on 24.03.14.
 */
public class Calendar implements ICalendar {
    private Map<UUID, Event> storage = new HashMap<UUID, Event>();

    @Override
    public void publish(UUID uuid, Event event) {
        storage.put(uuid, event);
        persistEvent(event);
    }

    private void persistEvent(Event event) {
        JAXBContext context = null;

        EventAdapter eventAdapter = new EventAdapter(event);
        try {
            context = JAXBContext.newInstance(EventAdapter.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(eventAdapter, new File("./"+event.getName() +". xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Event remove(UUID uuid) {
        Event event = storage.get(uuid);
        storage.remove(uuid);
        return event;
    }

    @Override
    public Event getEvent(UUID uuid) {
        Event event = storage.get(uuid);
        return event;
    }
}
