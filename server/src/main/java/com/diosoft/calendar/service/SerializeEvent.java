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
    //local code review (vtegza): private/public ? @ 04.05.14
    Event event;
    public SerializeEvent(Event event){
        this.event = event;
    }

    @Override
    public void run() {
        //local code review (vtegza): move to try/catch block @ 04.05.14
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
