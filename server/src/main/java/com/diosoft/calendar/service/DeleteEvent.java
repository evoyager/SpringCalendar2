package com.diosoft.calendar.service;

import com.diosoft.calendar.data.ICalendar;
import com.diosoft.calendar.pojo.Event;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.UUID;

/**
 * Created by EVGENIY on 03.04.14.
 */
public class DeleteEvent implements Runnable {

    Map<UUID, Event> storage;
    UUID uuid;
    public DeleteEvent(ICalendar calendar, UUID uuid) throws RemoteException {
        this.storage = calendar.getStorage();
        this.uuid = uuid;
    }

    @Override
    public void run() {
        Event event = storage.get(uuid);
        storage.remove(uuid);
        System.out.println("Removed event: " + event);
    }
}
