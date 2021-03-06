package com.example.demo.Dao;

import com.example.demo.Modal.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PickUpDao {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*** addNewUser
     *   Check if username or email is already in the DBj
     *
     *   RETURN: <Integer>
     *      0: New username and email is inserted into the DB
     *      1: Username already exist in the DB
     *      2: Email already exist in the DB
     *      3: Username and Email exist in the DB
     * ***/
    public Integer addNewUser(UserInfoModel userModel){
        String sqlSelect = "SELECT 1 FROM usermaster WHERE username = '"+ userModel.getUsername()+ "'";
        String checkEmail = "SELECT 2 FROM usermaster WHERE email = '"+ userModel.getEmail()+"'";

        List<CheckUserModel> m = jdbcTemplate.query(sqlSelect, new BeanPropertyRowMapper<>(CheckUserModel.class));
        List<CheckUserModel> n = jdbcTemplate.query(checkEmail, new BeanPropertyRowMapper<>(CheckUserModel.class));

        // Check if username and/or email already Exist in the database
        if(m.size() > 0 && n.size() > 0) {
            return 3;
        }else if(m.size() > 0) {
            return 1;
        } else if(n.size() > 0) {
            return 2;
        }

        String SQL = "INSERT into usermaster values(?,?,?,?,?,?)";
        jdbcTemplate.update(SQL,userModel.getId(), userModel.getUsername(),  passwordAuth(userModel.getPassword()), userModel.getUserdescription(), userModel.getPhotolink(), userModel.getEmail());

        return 0;
    }

    public String passwordAuth(String password){
        return passwordEncoder.encode(password);
    }

    /*** checkLoginDao
     *  Check if user exist and if password is authenticated
     *
     *  RETURN <UserInfoModel>
     *      -> UserInfoModel obj if user is authenticated
     *      -> NULL if user is not authenticated
     * ***/
    public UserInfoModel checkLoginDao(LoginModel loginModel){

        String sql = "SELECT * FROM usermaster WHERE username = '"+ loginModel.getUsername()+ "'";
        List<UserInfoModel> m = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserInfoModel.class));

        if(m.size() == 1 && passwordEncoder.matches(loginModel.getPassword(), m.get(0).getPassword())) {
            return m.get(0);
        }

        return null;
    }



    public Integer getUserId(String username){
        String sql = "SELECT id FROM usermaster WHERE username = '" +username+ "'";
        List<CheckUserModel> m = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CheckUserModel.class));
        if (m.size() > 0) return m.get(0).getId();
        return -1;
    }


    /** addEvent
     * User Adds a new event
     *
     * RETURNS <MyEvent>
     *     An obj with a list of all the events created by the particular user
     **/
    public void addEvent(EventModal eventList){
        String sql = "INSERT INTO events values(?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, eventList.getId(), eventList.getTitle(), eventList.getTimedate(), eventList.getDate(), eventList.getLocation(), eventList.getLat(), eventList.getLng(), eventList.getDescription(), eventList.getAuthor(), eventList.getCount());
//        return myEventsOwn(eventList.getAuthor());
    }

    /** myEventsOwn
     * Select all events created by given author
     *
     * @param author
     * @return: MyEvents obj which contains the list of events created by the user
     */
    public List<EventModal> myEventsOwn(String author){
//        MyEvents userEvents = new MyEvents();

        String sql = "SELECT * FROM events WHERE author='"+author+"'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(EventModal.class));


//        userEvents.setOwnEvents(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(EventModal.class)));

/*        int userId = getUserId(author);
        userEvents.setAttendingEvents(myEventsAttending(userId));

        return userEvents;*/
    }

    public ArrayList<EventModal> myEventsAttending(Integer userId){
        ArrayList<EventModal> whatUserAttending = new ArrayList<>();

        String sql2 = "SELECT * FROM attending WHERE user_id = '" + userId +"'";
        List<AttendingModal> n = jdbcTemplate.query(sql2, new BeanPropertyRowMapper<>(AttendingModal.class));
        for(int i=0; i < n.size(); i++){
            String sql3 = "SELECT * FROM events WHERE id = '" + n.get(i).getEvent_id()+ "'";
            List<EventModal> userAttendingEvents = jdbcTemplate.query(sql3, new BeanPropertyRowMapper<>(EventModal.class));
            whatUserAttending.add(userAttendingEvents.get(0));
        }

        return whatUserAttending;
    }

    public AllEventsModel allEvents(){
        AllEventsModel events = new AllEventsModel();
        String sql = "SELECT * FROM events";
        events.setAllEvents(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(EventModal.class)));

        return events;
    }

    public ArrayList<EventModal> addUserToEvent(String username, Integer event_id){
        // Search for userID
        int userId = getUserId(username);
        // Check Attending
        if(userId != -1 && !checkAlreadyAttending(userId, event_id)) {
            // Place in Attending if not in there/
            AttendingModal userCanAttend = new AttendingModal(userId, event_id);
            addAttending(userCanAttend);
            return myEventsAttending(userId);
        }

        return null;
    }

    // Return True User is already Attending an event
    public boolean checkAlreadyAttending(Integer user_id, Integer event_id){
        String sql = "SELECT * FROM attending WHERE user_id = '" +user_id+ "' AND event_id= '" +event_id+ "'";
        List<AttendingModal> m = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AttendingModal.class));
        if(m.size() > 0) return true;
        return false;
    }

    public void addAttending(AttendingModal attendingModal){
        String sql = "INSERT INTO attending values(?,?,?)";
        jdbcTemplate.update(sql, attendingModal.getUser_id(), attendingModal.getEvent_id(), attendingModal.getAuthor());
    }

    public ArrayList<EventModal> cancelUserFromEvent(UserAddAttending userAddAttending){
        int userID = getUserId(userAddAttending.getUsername());
        String sql = "DELETE from attending WHERE user_id= '" + userID +"' AND event_id = '" +userAddAttending.getId()+ "'";
        jdbcTemplate.update(sql);
        return myEventsAttending(userID);
    }

    public void deleteEvent(UserEventIdModal userEventIdModal){
        // Delete users from attending
        String sql = "DELETE from attending where event_id = ?";
        jdbcTemplate.update(sql, userEventIdModal.getId());
        // Delete event from event table
        String sql1 = "DELETE from events where id = ?";
        jdbcTemplate.update(sql1, userEventIdModal.getId());

//        return myEventsOwn(userEventIdModal.getUsername());
    }
}

