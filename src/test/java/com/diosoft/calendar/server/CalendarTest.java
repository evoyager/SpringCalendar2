package com.diosoft.calendar.server;

import com.diosoft.calendar.server.pojo.Event;
import com.diosoft.calendar.server.pojo.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertEquals;

/**
 * Created by EVGENIY on 25.03.14.
 */
public class CalendarTest {

    com.diosoft.calendar.server.data.Calendar dataStore = new com.diosoft.calendar.server.data.Calendar();
    Set<Event> eventSet;
    Event myBirthday;
    ArrayList<Person> listOfAttenders2;

    @Before
    public void setUp(){
        Person vasiliy = new Person.Builder()
                .setAge(30)
                .setEmail("vtegza@dio-soft.com")
                .setFirstName("Vasiliy")
                .setSecondName("Tegza")
                .build();

        Person evgeniy = new Person.Builder()
                .setAge(27)
                .setEmail("evgeniygusar@gmail.com")
                .setFirstName("Evgeniy")
                .setSecondName("Gusar")
                .build();

        Person ivan = new Person.Builder()
                .setAge(22)
                .setEmail("shapoval.ivan.ivanovich@gmail.com")
                .setFirstName("Ivan")
                .setSecondName("Shapoval")
                .build();

        Person igor = new Person.Builder()
                .setAge(27)
                .setEmail("sytniuk@gmail.com\"")
                .setFirstName("Igor")
                .setSecondName("Sytnuk")
                .build();

        Person nikolay = new Person.Builder()
                .setAge(40)
                .setEmail("nchigir@dio-soft.com")
                .setFirstName("Nikolay")
                .setSecondName("Chigir")
                .build();

        Person kostia = new Person.Builder()
                .setAge(30)
                .setEmail("kshraiber@dio-soft.com")
                .setFirstName("Kostia")
                .setSecondName("Shraiber")
                .build();

        ArrayList<Person> listOfAttenders =  new ArrayList<Person>();

        listOfAttenders.add(vasiliy);
        listOfAttenders.add(evgeniy);
        listOfAttenders.add(ivan);
        listOfAttenders.add(igor);
        listOfAttenders.add(nikolay);
        listOfAttenders.add(kostia);

        Event finishOfCourses = new Event.Builder()
                .setName("End of DIO-soft courses")
                .setDescription("Otprazdnovat okonchanie!")
                .setStartDate(new GregorianCalendar(2014, 2, 27, 11, 00, 00))
                .setAllDay(true)
                .setAttenders(listOfAttenders)
                .build();

        Map<UUID, Event> storage = new HashMap<UUID, Event>();

        storage.put(UUID.fromString("d849f178-b062-4fd6-b79c-0b824a7b0c"), finishOfCourses);

        Person i = new Person.Builder()
                .setAge(27)
                .setEmail("evgeniygusar@gmail.com")
                .setFirstName("Evgeniy")
                .setSecondName("Gusar")
                .build();

        Person girlfriend = new Person.Builder()
                .setAge(22)
                .setEmail("mashamasha@gmail.com")
                .setFirstName("Masha")
                .setSecondName("Kotnikova")
                .build();

        Person stepBrother = new Person.Builder()
                .setAge(30)
                .setEmail("dima.smirnov@gmail.com")
                .setFirstName("Dima")
                .setSecondName("Smirnov")
                .build();

        Person friend = new Person.Builder()
                .setAge(20)
                .setEmail("shalimovmisha@gmail.com")
                .setFirstName("Misha")
                .setSecondName("Shalimov")
                .build();

        Person brother = new Person.Builder()
                .setAge(32)
                .setEmail("maximgusar@gmail.com")
                .setFirstName("Maxim")
                .setSecondName("Gusar")
                .build();

        Person mother = new Person.Builder()
                .setAge(54)
                .setEmail("nataliagusar@gmail.com")
                .setFirstName("Natalia")
                .setSecondName("Gusar")
                .build();

        Person father = new Person.Builder()
                .setAge(55)
                .setEmail("valeriygusar@gmail.com")
                .setFirstName("Valeriy")
                .setSecondName("Gusar")
                .build();

        Person grandfather = new Person.Builder()
                .setAge(85)
                .setEmail("Mayakovskogo 10")
                .setFirstName("Konstantin")
                .setSecondName("Gusar")
                .build();

        Person grandmother = new Person.Builder()
                .setAge(82)
                .setEmail("Mayakovskogo 10")
                .setFirstName("Galina")
                .setSecondName("Gusar")
                .build();

        listOfAttenders2 =  new ArrayList<Person>();

        listOfAttenders2.add(i);
        listOfAttenders2.add(girlfriend);
        listOfAttenders2.add(stepBrother);
        listOfAttenders2.add(brother);
        listOfAttenders2.add(mother);
        listOfAttenders2.add(father);
        listOfAttenders2.add(grandmother);
        listOfAttenders2.add(grandfather);

        myBirthday = new Event.Builder()
                .setName("My Birthday")
                .setDescription("Celebrate with friends and family")
                .setStartDate(new GregorianCalendar(2014, 4, 15, 17, 30, 00))
                .setAllDay(true)
                .setAttenders(listOfAttenders2)
                .build();

        storage.put(UUID.fromString("477491d5-fe71-45e8-a1c9-986ec1905249"), myBirthday);

        dataStore.setStorage(storage);

        eventSet = new HashSet<Event>(storage.values());
    }

    @Test
    public void testPublish() throws Exception {
        Event NewYear = new Event.Builder()
                .setName("New Year")
                .setDescription("Celebrate New Year with friends and family")
                .setStartDate(new GregorianCalendar(2014, 11, 31, 23, 59, 59))
                .setAllDay(true)
                .setAttenders(listOfAttenders2)
                .build();
        UUID uuid = UUID.fromString("9736f89d-e9f8-4c14-9e60-354b2320a40b");
        dataStore.publish(uuid, NewYear);
        Event event = dataStore.getEvent(uuid);
        assertEquals(event.equals(NewYear), true);
    }

    @Test
    public void testRemove() throws Exception {
        UUID uuid = UUID.fromString("477491d5-fe71-45e8-a1c9-986ec1905249");
        dataStore.remove(uuid);
        Event event = dataStore.getEvent(uuid);
        assertEquals(event, null);
    }

    @Test
    public void testGetEvent() throws Exception {
        UUID uuid = UUID.fromString("477491d5-fe71-45e8-a1c9-986ec1905249");
        Event event = dataStore.getEvent(uuid);
        assertEquals(event.equals(myBirthday), true);
    }


//todo (+)mock tests
}
