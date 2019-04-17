package com.example.demo.Controller;

import com.example.demo.Modal.*;
import com.example.demo.Service.EventService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
public class PickUpController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String sayHello(){
        return "Hello This is World!!!";
    }


    @CrossOrigin
//    @RequestMapping(name="/newUser", method = RequestMethod.POST)
    @PostMapping("/newUser")
    public Integer addingNewUser(@RequestBody UserInfoModel userInfoModel){
        return userService.addNewUserService(userInfoModel);
    }

    @CrossOrigin
    @PostMapping("/login")
//    @RequestMapping(name="/login", method = RequestMethod.POST)
    public UserInfoModel loginCheck(@RequestBody LoginModel loginModel){
        return userService.userLoginCheckService(loginModel);
    }

    @CrossOrigin
    @PostMapping("/addEvent")
    public MyEvents addingEvent(@RequestBody EventModal eventModal){
        return eventService.addEventService(eventModal);
    }

    @CrossOrigin
    @PostMapping("/userEvents")
    public MyEvents myOwnEvents(@RequestBody UsernameModel usernameModel){
        return eventService.userCreatedEventsService(usernameModel);
    }

    @CrossOrigin
    @RequestMapping("/allEvents")
    public AllEventsModel allCurrentEvents(){
        return eventService.getCurrentEventService();
    }

    @CrossOrigin
    @PostMapping("/attendEvent")
    public ArrayList<EventModal> addUserToEvent(@RequestBody UserAddAttending userAddAttending){
        return eventService.userIsAttendingEventService(userAddAttending);
    }

    @CrossOrigin
    @PostMapping("/cancelAttend")
    public ArrayList<EventModal> cancelAttendedEvent(@RequestBody UserAddAttending userAddAttending){
        return eventService.cancelUserAttendingEventService(userAddAttending);
    }

    @CrossOrigin
    @PostMapping("/deleteEvent")
    public MyEvents deleteEvent(@RequestBody UserEventIdModal userEventIdModal){
        return eventService.deleteEntireEventService(userEventIdModal);
    }


}
