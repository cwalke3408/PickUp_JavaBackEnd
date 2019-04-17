package com.example.demo.Service;

import com.example.demo.Dao.PickUpDao;
import com.example.demo.Modal.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class EventService {

    @Autowired
    private PickUpDao pickUpDao;

    public AllEventsModel getCurrentEventService() {
        return this.pickUpDao.allEvents();
    }

    public MyEvents addEventService(EventModal eventModal) {
        return this.pickUpDao.addEvent(eventModal);
    }

    public MyEvents userCreatedEventsService(UsernameModel usernameModel) {
        return this.pickUpDao.myEventsOwn(usernameModel.getUsername());
    }

    public ArrayList<EventModal> userIsAttendingEventService(UserAddAttending userAddAttending) {
        return this.pickUpDao.addUserToEvent(userAddAttending.getUsername(), userAddAttending.getId());
    }

    public ArrayList<EventModal> cancelUserAttendingEventService(UserAddAttending userAddAttending) {
        return this.pickUpDao.cancelUserFromEvent(userAddAttending);
    }

    public MyEvents deleteEntireEventService(UserEventIdModal userEventIdModal) {
        return this.pickUpDao.deleteEvent(userEventIdModal);
    }
}
