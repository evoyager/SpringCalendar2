package com.diosoft.calendar.server.data;

/**
 * Created by EVGENIY on 24.03.14.
 */
import com.diosoft.calendar.server.pojo.Event;
import com.diosoft.calendar.server.pojo.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface ICalendar extends Remote {

    void publish(UUID uuid, Event event) throws RemoteException;

    Event remove(UUID uuid) throws RemoteException;

    Event getEvent(UUID uuid) throws RemoteException;

    public Map<UUID, Event> getStorage() throws RemoteException;
}
