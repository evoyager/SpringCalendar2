package com.diosoft.calendar.server.pojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by EVGENIY on 24.03.14.
 */
public class Event implements Serializable {
    private final String name;
    private final String description;
    private final GregorianCalendar startDate;
    private final GregorianCalendar endDate;
    private final boolean allDay;
    private final List<Person> attenders;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public GregorianCalendar getStartDate() {
        return startDate;
    }

    public GregorianCalendar getEndDate() {
        return endDate;
    }

    public boolean getAllDay() {
        return allDay;
    }

    public List<Person> getAttenders() {
        return attenders;
    }

    private Event(Builder builder){
        this.name = builder.name;
        this.description = builder.description;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.allDay = builder.allDay;
        this.attenders = builder.attenders;
    }

    public boolean equals(Object obj){
        if (!(obj instanceof Event))
            return false;
        Event event = (Event)obj;
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

    public static class Builder {
        private String name;
        private String description;
        private GregorianCalendar startDate;
        private GregorianCalendar endDate;
        private boolean allDay;
        private List<Person> attenders;

        public Builder(){}

        public Builder(Event original){
            this.name = original.name;
            this.description = original.description;
            this.startDate = original.startDate;
            this.endDate = original.endDate;
            this.allDay = original.allDay;
            this.attenders = original.attenders;
        }

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Builder setDescription(String description){
            this.description = description;
            return this;
        }

        public Builder setStartDate(GregorianCalendar startDate){
            this.startDate = startDate;
            return this;
        }

        public Builder setEndDate(GregorianCalendar endDate){
            this.endDate = endDate;
            return this;
        }

        public Builder setAllDay(boolean allDay){
            if (allDay){
                int year = startDate.get(Calendar.YEAR);
                int month = startDate.get(Calendar.MONTH);
                int dayOfMonth = startDate.get(Calendar.DAY_OF_MONTH);
                endDate = new GregorianCalendar(year, month, dayOfMonth+1, 00, 00, 00);
            }
            this.allDay = allDay;
            return this;
        }

        public Builder setAttenders(List<Person> attenders){
            this.attenders = attenders;
            return this;
        }

        public Event build(){
            return new Event(this);
        }
    }
}
