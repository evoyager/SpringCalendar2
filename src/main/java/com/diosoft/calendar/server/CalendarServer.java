package com.diosoft.calendar.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.logging.Logger;

/**
 * Created by EVGENIY on 24.03.14.
 */
public class CalendarServer {

    public static final Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("server.xml");
        logger.info("Server started");
    }
}
