package com.example.demo.Service;

import com.example.demo.Dao.PickUpDao;
import com.example.demo.Modal.LoginModel;
import com.example.demo.Modal.UserInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    PickUpDao pickUpDao;

    public Integer addNewUserService(UserInfoModel userInfoModel) {
        return this.pickUpDao.addNewUser(userInfoModel);
    }

    public UserInfoModel userLoginCheckService(LoginModel loginModel) {
        return this.pickUpDao.checkLoginDao(loginModel);
    }
}
