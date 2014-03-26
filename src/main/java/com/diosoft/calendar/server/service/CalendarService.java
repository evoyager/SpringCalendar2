package com.diosoft.calendar.server.service;

import com.diosoft.calendar.server.ICalendar;
import com.diosoft.calendar.server.adapter.EventAdapter;
import com.diosoft.calendar.server.pojo.Event;
import com.diosoft.calendar.server.pojo.Person;
import com.diosoft.calendar.server.Calendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by EVGENIY on 26.03.14.
 */
public class CalendarService implements ICalendarService {

    private final ICalendar calendar;

    public CalendarService(ICalendar calendar){
        this.calendar = calendar;
    }

    @Override
    public void addEvent(UUID uuid, Event event) throws RemoteException {
        calendar.publish(uuid, event);
    }

    @Override
    public Event deleteEvent(UUID uuid) throws RemoteException {
        Event event = calendar.remove(uuid);
        return event;
    }

    @Override
    public Event readEvent(UUID uuid) throws RemoteException {
        return calendar.getEvent(uuid);
    }

    public Event editEvent(UUID uuid, String key, Object value) throws RemoteException {
        Event event = readEvent(uuid);
        if(key.equals("startTime"))
            event = new Event.Builder()
                    .setName(event.getName())
                    .setDescription(event.getDescription())
                    .setStartDate((GregorianCalendar) value)
                    .setEndDate(event.getEndDate())
                    .setAllDay(event.getAllDay())
                    .setAttenders(event.getAttenders())
                    .build();
        else if(key.equals("endTime"))
            event = new Event.Builder()
                    .setName(event.getName())
                    .setDescription(event.getDescription())
                    .setStartDate(event.getStartDate())
                    .setEndDate((GregorianCalendar) value)
                    .setAllDay(event.getAllDay())
                    .setAttenders(event.getAttenders())
                    .build();
        else if(key.equals("name")){
            event = new Event.Builder()
                    .setName((String)value)
                    .setDescription(event.getDescription())
                    .setStartDate(event.getStartDate())
                    .setEndDate(event.getEndDate())
                    .setAllDay(event.getAllDay())
                    .setAttenders(event.getAttenders())
                    .build();
        }
        else if(key.equals("description"))
            event = new Event.Builder()
                    .setName(event.getName())
                    .setDescription((String) value)
                    .setStartDate(event.getStartDate())
                    .setEndDate(event.getEndDate())
                    .setAllDay(event.getAllDay())
                    .setAttenders(event.getAttenders())
                    .build();
        deleteEvent(uuid);
        addEvent(uuid, event);
        return event;
    }

    public boolean isPersonAvailable(Person person, GregorianCalendar desireDate) throws RemoteException {
        Set<Event> eventSet = new HashSet<Event>(calendar.getStorage().values());
        for(Event event : eventSet){
            if((event.getStartDate().getTimeInMillis() <= desireDate.getTimeInMillis()) &&
                    (event.getEndDate().getTimeInMillis() > desireDate.getTimeInMillis()) &&
                    (event.getAttenders().contains(person)))
                return false;
        }
        return true;
    }

    public List<GregorianCalendar> checkAvailability(Person... persons) throws RemoteException {
        Set<Event> eventSet = new HashSet<Event>(calendar.getStorage().values());
        List <GregorianCalendar> checkAvailabilityList = new ArrayList<GregorianCalendar>();
        for(Person p: persons){
            for(Event event : eventSet){
                if (event.getAttenders().contains(p))
                    checkAvailabilityList.add(event.getStartDate());
            }
        }
        return checkAvailabilityList;
    }

    public void printEventSet(Set<Event> eventSet){
        int eventCounter = 1;
        for(Event event : eventSet){
            System.out.println("[" + eventCounter + "] " + event);
            eventCounter++;
        }
    }

    public void persistEvent(Event event) {
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
}
