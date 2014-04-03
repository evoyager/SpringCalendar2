package com.diosoft.calendar.service;

import com.diosoft.calendar.adapter.EventAdapter;
import com.diosoft.calendar.pojo.Event;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Created by EVGENIY on 03.04.14.
 */
public class SerializeEvent implements Runnable {
    Event event;
    public SerializeEvent(Event event){
        this.event = event;
    }

    @Override
    public void run() {
        JAXBContext context = null;

        EventAdapter eventAdapter = new EventAdapter(event);
        try {
            context = JAXBContext.newInstance(EventAdapter.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            File file = new File("./"+event.getName() +".xml");

            m.marshal(eventAdapter, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
