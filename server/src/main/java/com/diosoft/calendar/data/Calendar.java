package com.diosoft.calendar.data;

import com.diosoft.calendar.adapter.EventAdapter;
import com.diosoft.calendar.adapter.PersonAdapter;
import com.diosoft.calendar.pojo.Event;
import com.diosoft.calendar.pojo.Person;
import com.diosoft.calendar.service.DeleteEvent;
import com.diosoft.calendar.service.SerializeEvent;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by EVGENIY on 24.03.14.
 */
public class Calendar implements ICalendar {
    public static final Logger logger = Logger.getAnonymousLogger();

    private Map<UUID, Event> storage = new HashMap<UUID, Event>();

    public Map<UUID, Event> getStorage() {
        return storage;
    }

    public void setStorage(Map<UUID, Event> storage) {
        this.storage = storage;
    }

    @Override
    public void clearStorage() throws RemoteException {
        storage = new HashMap<UUID, Event>();
    }

    @Override
    public void publish(UUID uuid, Event event) {
        storage.put(uuid, event);
        persistEvent(event);
    }

    @Override
    public Event getEvent(UUID uuid){
        Event event = storage.get(uuid);
        return event;
    }

    private void persistEvent(Event event) {

        Runnable runnable  = new SerializeEvent(event);
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
