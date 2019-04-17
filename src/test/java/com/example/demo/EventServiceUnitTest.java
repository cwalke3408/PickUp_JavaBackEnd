package com.example.demo;

import com.example.demo.Dao.PickUpDao;
import com.example.demo.Service.EventService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceUnitTest {


    @InjectMocks
    EventService eventService;

    @Mock
    PickUpDao pickUpDao;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    public void addCreatedEventTest() {
        // eventList.getId(), eventList.getTitle(),
        // eventList.getTimedate(), eventList.getDate(),
        // eventList.getLocation(), eventList.getLat(),
        // eventList.getLng(), eventList.getDescription(),
        // eventList.getAuthor(), eventList.getCount());



    }
}
