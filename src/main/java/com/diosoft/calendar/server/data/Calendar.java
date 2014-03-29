package com.diosoft.calendar.server.data;

import com.diosoft.calendar.server.adapter.EventAdapter;
import com.diosoft.calendar.server.pojo.Event;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Created by EVGENIY on 24.03.14.
 */
public class Calendar implements ICalendar {
//    private static final String EVENT_XML =
    private Map<UUID, Event> storage = new HashMap<UUID, Event>();

    public Map<UUID, Event> getStorage() {
        return storage;
    }

    public void setStorage(Map<UUID, Event> storage) {
        this.storage = storage;
    }

    @Override
    public void publish(UUID uuid, Event event) {
        storage.put(uuid, event);
        persistEvent(event);
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
        try {
            JAXBContext context = JAXBContext.newInstance(EventAdapter.class);
            Unmarshaller um = context.createUnmarshaller();
            String EVENT_XML = "./"+event.getName() + ".xml";
            EventAdapter ea = (EventAdapter) um.unmarshal(new FileReader(EVENT_XML));
            System.out.println(ea);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    private void persistEvent(Event event) {
        JAXBContext context = null;

        EventAdapter eventAdapter = new EventAdapter(event);
        try {
            context = JAXBContext.newInstance(EventAdapter.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            File file = new File("./"+event.getName() +".xml");

//            if (file.exists())
//                file.delete();
            m.marshal(eventAdapter, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
