package com.diosoft.calendar.data;

import com.diosoft.calendar.adapter.EventAdapter;
import com.diosoft.calendar.adapter.PersonAdapter;
import com.diosoft.calendar.pojo.Event;
import com.diosoft.calendar.pojo.Person;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by EVGENIY on 02.04.14.
 */
public class LoadEvent implements Runnable {
    File file;
    ICalendar calendar;

    public LoadEvent(File file, ICalendar calendar){
        this.file = file;
        this.calendar = calendar;
    }
    @Override
    public void run() {

        JAXBContext context = null;

        try {
            context = JAXBContext.newInstance(EventAdapter.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        assert context != null;
        Unmarshaller um = null;
        try {
            um = context.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        String EVENT_XML = "./"+file.getName();
        assert um != null;
        EventAdapter ea = null;
        try {
            ea = (EventAdapter) um.unmarshal(new FileReader(EVENT_XML));
        } catch (JAXBException e) {
            e.printStackTrace();
          }
          catch (FileNotFoundException e) {
            e.printStackTrace();
          }
        Event event = eventAdapterToEvent(ea);
        UUID uuid = UUID.randomUUID();
        Calendar cal = new Calendar();
//        cal.storage.put(uuid, event);
        try {
            calendar.publish(uuid, event);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
        System.out.println(event);
    }

    public Event eventAdapterToEvent(EventAdapter ea){
        List<PersonAdapter> attendersPA = ea.getAttenders();
        List<Person> attendersP = new ArrayList<Person>();
        for(PersonAdapter pa : attendersPA)
            attendersP.add(new Person.Builder()
                    .setAge(pa.getAge())
                    .setEmail(pa.getEmail())
                    .setFirstName(pa.getFirstName())
                    .setSecondName(pa.getSecondName())
                    .build()
            );

        Event event = new Event.Builder()
                .setName(ea.getName())
                .setDescription(ea.getDescription())
                .setStartDate(ea.getStartDate())
                .setAllDay(ea.isAllDay())
                .setAttenders(attendersP)
                .build();
        return event;
    }

}
