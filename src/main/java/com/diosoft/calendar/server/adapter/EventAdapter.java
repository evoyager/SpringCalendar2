package com.diosoft.calendar.server.adapter;

import com.diosoft.calendar.server.pojo.Event;
import com.diosoft.calendar.server.pojo.Person;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by EVGENIY on 24.03.14.
 */
@XmlRootElement
@XmlType(name = "event")
public class EventAdapter implements Serializable {
    private String name;
    private String description;
    private GregorianCalendar startDate;
    private GregorianCalendar endDate;
    private boolean allDay;

    private List<PersonAdapter> attenders;

    public EventAdapter(){
    }

    public EventAdapter(Event event){
        this.name = event.getName();
        this.description = event.getDescription();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.allDay = event.getAllDay();

        this.attenders = new ArrayList<PersonAdapter>();
        if (event.getAttenders() != null){
            for (Person person : event.getAttenders()){
                this.attenders.add(new PersonAdapter(person));
            }
        }
    }

    public boolean equals(Object obj){
        if (!(obj instanceof Event))
            return false;
        EventAdapter event = (EventAdapter)obj;
        return name.equals(event.name) &&
                description.equals(event.description) &&
                startDate==event.startDate && endDate.equals(event.endDate)
                && allDay==event.allDay && attenders.equals(event.attenders);
    }

    public int hashCode(){
        int hash = 37;
        hash = hash + name.hashCode();
        hash = hash + description.hashCode();
        hash = hash + startDate.hashCode();
        hash = hash + endDate.hashCode();
        hash = hash + attenders.hashCode();
        return hash;
    }

    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
        return "Start date: " + sdf.format(startDate.getTime()) + ", End date: " + sdf.format(endDate.getTime()) +
                ", name: " + name + ", description: " + description + "\nattenders: " + attenders;
    }

    @XmlElement(name = "name")

    public String getName() {
        return name;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GregorianCalendar getStartDate() {
        return startDate;
    }

    public void setStartDate(GregorianCalendar startDate) {
        this.startDate = startDate;
    }

    public GregorianCalendar getEndDate() {
        return endDate;
    }

    public void setEndDate(GregorianCalendar endDate) {
        this.endDate = endDate;
    }

    public List<PersonAdapter> getAttenders() {
        return attenders;
    }

    public void setAttenders(List<PersonAdapter> attenders) {
        this.attenders = attenders;
    }
}
