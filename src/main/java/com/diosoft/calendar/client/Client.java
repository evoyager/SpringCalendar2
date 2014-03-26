package com.diosoft.calendar.client;

import com.diosoft.calendar.server.pojo.Event;
import com.diosoft.calendar.server.pojo.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by EVGENIY on 24.03.14.
 */
public class Client {

    public static final Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) throws RemoteException {
        ApplicationContext factory = new ClassPathXmlApplicationContext("client.xml");
        ClientCalendar cal = (ClientCalendar) factory.getBean("clientCalendar");
        List<Person> womens = new ArrayList<Person>();

        Person mother = new Person.Builder()
                .setAge(54)
                .setEmail("natali.gusar@mail.ru")
                .setFirstName("Natalia")
                .setSecondName("Gusar")
                .build();

        womens.add(mother);

        Event WomensDay = new Event.Builder()
                .setName("WomensDay")
                .setDescription("Congratulate all the women I know the holiday of spring and beauty")
                .setStartDate(new GregorianCalendar(2014, 2, 8, 9, 00, 00))
                .setAllDay(true)
                .setAttenders(womens)
                .build();

        UUID uuid = UUID.fromString("9736f89d-e9f8-4c14-9e60-354b2320a40b");
        cal.getCalendar().publish(uuid, WomensDay);

        logger.info("Created event: " + cal.getCalendar().getEvent(uuid));
    }
}
