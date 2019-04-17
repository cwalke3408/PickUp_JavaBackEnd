package com.example.demo.Service;

import com.example.demo.Dao.PickUpDao;
import com.example.demo.Modal.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    private PickUpDao pickUpDao;

    @Override
    public AllEventsModel getCurrentEventService() {
        return this.pickUpDao.allEvents();
    }

    @Override
    public MyEvents addEventService(EventModal eventModal) {

        this.pickUpDao.addEvent(eventModal);
        return userCreatedEventsService(eventModal.getAuthor());

//        userEvents.setOwnEvents(this.pickUpDao.myEventsOwn(eventModal.getAuthor()));
//        Integer userId = this.pickUpDao.getUserId(eventModal.getAuthor());
//        userEvents.setAttendingEvents(this.pickUpDao.myEventsAttending(userId));
//
//        return userEvents;
    }

    @Override
    public MyEvents userCreatedEventsService(String userName) {

        MyEvents userEvents = new MyEvents();

        userEvents.setOwnEvents(this.pickUpDao.myEventsOwn(userName));
        Integer userId = this.pickUpDao.getUserId(userName);
        userEvents.setAttendingEvents(this.pickUpDao.myEventsAttending(userId));

        return userEvents;
//        return this.pickUpDao.myEventsOwn(usernameModel.getUsername());
    }

    @Override
    public ArrayList<EventModal> userIsAttendingEventService(UserAddAttending userAddAttending) {
        return this.pickUpDao.addUserToEvent(userAddAttending.getUsername(), userAddAttending.getId());
    }

    @Override
    public ArrayList<EventModal> cancelUserAttendingEventService(UserAddAttending userAddAttending) {
        return this.pickUpDao.cancelUserFromEvent(userAddAttending);
    }

    @Override
    public MyEvents deleteEntireEventService(UserEventIdModal userEventIdModal) {
        this.pickUpDao.deleteEvent(userEventIdModal);
        return userCreatedEventsService(userEventIdModal.getUsername());
    }
}
