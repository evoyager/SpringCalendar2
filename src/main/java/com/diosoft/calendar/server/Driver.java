package com.diosoft.calendar.server;

import com.diosoft.calendar.server.pojo.Event;
import com.diosoft.calendar.server.pojo.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by EVGENIY on 24.03.14.
 */
public class Driver {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("server.xml");
        ICalendar cal = (ICalendar) context.getBean("calendar");
        List<Person> womens = new ArrayList<Person>();

        Person mother = new Person.Builder()
                .setAge(54)
                .setEmail("natali.gusar@mail.ru")
                .setFirstName("Natalia")
                .setSecondName("Gusar")
                .build();

        womens.add(mother);

        Event WomensDay = new Event.Builder()
               .setName("WomansDay")
               .setDescription("Congratulate all the women I know the holiday of spring and beauty")
               .setStartDate(new GregorianCalendar(2014, 2, 8, 9, 00, 00))
               .setAllDay(true)
               .setAttenders(womens)
               .build();

        UUID uuid = UUID.fromString("9736f89d-e9f8-4c14-9e60-354b2320a40b");
        cal.publish(uuid, WomensDay);
        System.out.println(cal.getEvent(uuid));
    }
}
