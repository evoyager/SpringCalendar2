package com.diosoft.calendar.client;

import com.diosoft.calendar.server.ICalendar;

/**
 * Created by EVGENIY on 24.03.14.
 */
public class ClientCalendar {
    private ICalendar calendar;

    public ICalendar getCalendar() {
        return calendar;
    }

    public void setCalendar(ICalendar calendar) {
        this.calendar = calendar;
    }
}
