package com.diosoft.calendar.server.service;

import com.diosoft.calendar.server.pojo.Event;
import com.diosoft.calendar.server.pojo.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by EVGENIY on 26.03.14.
 */
public interface ICalendarService extends Remote {
   void addEvent(UUID uuid, Event event) throws RemoteException;

   Event deleteEvent(UUID uuid) throws RemoteException;

   Event readEvent(UUID uuid) throws RemoteException;

   Event editEvent(UUID uuid, String key, Object value) throws RemoteException;

   boolean isPersonAvailable(Person person, GregorianCalendar desireDate) throws RemoteException;

   List<GregorianCalendar> checkAvailability(Person... persons) throws RemoteException;

}
