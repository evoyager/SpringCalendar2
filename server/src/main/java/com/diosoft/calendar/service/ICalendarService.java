package com.diosoft.calendar.service;

import com.diosoft.calendar.pojo.Event;
import com.diosoft.calendar.pojo.Person;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by EVGENIY on 26.03.14.
 */
public interface ICalendarService extends Remote {
   void addEvent(UUID uuid, Event event) throws RemoteException;

   Event deleteEvent(UUID uuid) throws RemoteException;

   Event readEvent(UUID uuid) throws RemoteException, JAXBException, FileNotFoundException;

   Event editEvent(UUID uuid, String key, Object value) throws RemoteException, JAXBException, FileNotFoundException;

   boolean isPersonAvailable(Person person, GregorianCalendar desireDate) throws RemoteException;

   List<GregorianCalendar> checkAvailability(Person... persons) throws RemoteException;

   void loadEventsFromXml() throws RemoteException, JAXBException, FileNotFoundException;

   List<Event> getAllEvents() throws RemoteException;


}
