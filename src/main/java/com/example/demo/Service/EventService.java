package com.example.demo.Service;

import com.example.demo.Modal.*;

import java.util.ArrayList;

public interface EventService {

    AllEventsModel getCurrentEventService();

    MyEvents addEventService(EventModal eventModal);

    MyEvents userCreatedEventsService(String userName);

    ArrayList<EventModal> userIsAttendingEventService(UserAddAttending userAddAttending);

    ArrayList<EventModal> cancelUserAttendingEventService(UserAddAttending userAddAttending);

    MyEvents deleteEntireEventService(UserEventIdModal userEventIdModal);
}
