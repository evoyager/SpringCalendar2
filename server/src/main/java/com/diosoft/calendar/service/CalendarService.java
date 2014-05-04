package com.diosoft.calendar.service;

import com.diosoft.calendar.data.ICalendar;
import com.diosoft.calendar.data.LoadEvent;
import com.diosoft.calendar.pojo.Event;
import com.diosoft.calendar.pojo.Person;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
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
    public void deleteEvent(UUID uuid) throws RemoteException {
//        Event event = calendar.remove(uuid);
//        calendar.remove(uuid);
//        return event;

        Runnable runnable  = new DeleteEvent(calendar, uuid);
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void toClearStorage() throws RemoteException {
        calendar.clearStorage();
    }

    @Override
    public Event readEvent(UUID uuid) throws RemoteException, JAXBException, FileNotFoundException {
        return calendar.getEvent(uuid);
    }

    public Event editEvent(UUID uuid, String key, Object value) throws RemoteException, JAXBException, FileNotFoundException {
        Event event = readEvent(uuid);
        //local code review (vtegza): use switch on strings here @ 04.05.14
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

    @Override
    public void loadEventsFromXml() throws RemoteException, JAXBException, FileNotFoundException, InterruptedException {

        toClearStorage();
        //local code review (vtegza): inline @ 04.05.14
        File f = null;
        File[] paths;
        f = new File("./");
        paths = f.listFiles();
        //local code review (vtegza): try to use nio2 instead of old IO @ 04.05.14
        for(File file : paths){
            if (file.getName().contains(".xml")){
                Runnable runnable  = new LoadEvent(file, calendar);
                //local code review (vtegza): take a look at java Executors @ 04.05.14
                Thread thread = new Thread(runnable);
                thread.start();
            }
        }

    }

    @Override
    public List<Event> getAllEvents() throws RemoteException {
        List<Event> eventList = new ArrayList<Event>();
        for(Event event : calendar.getStorage().values())
            eventList.add(event);
        return eventList;
    }

    public void printEventSet(Set<Event> eventSet){
        int eventCounter = 1;
        for(Event event : eventSet){
            System.out.println("[" + eventCounter + "] " + event);
            eventCounter++;
        }
    }
}
