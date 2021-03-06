package com.diosoft.calendar.data;

/**
 * Created by EVGENIY on 24.03.14.
 */
import com.diosoft.calendar.pojo.Event;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface ICalendar extends Remote {

    void setStorage(Map<UUID, Event> storage) throws RemoteException;

    void clearStorage() throws RemoteException;

    void publish(UUID uuid, Event event) throws RemoteException;

    Event getEvent(UUID uuid) throws RemoteException, JAXBException, FileNotFoundException;

    public Map<UUID, Event> getStorage() throws RemoteException;
}
