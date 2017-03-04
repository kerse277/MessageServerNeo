package com.kerse.message.controller;

import com.kerse.message.business.UserBusiness;
import com.kerse.message.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserContoller {

    @Autowired
    UserBusiness userBusiness;

    @PostMapping(value = "adduser")
    @ResponseBody
    public User addUser(@RequestBody RegisterUserModel registerUserModel){
        return userBusiness.addUser(registerUserModel);
    }

    @GetMapping(value = "getuser")
    @ResponseBody
    public CustomUser getUser(@RequestParam("token") String token,@RequestParam("userId")String userId){
        return userBusiness.getUser(token,userId);
    }

    @PostMapping(value = "login")
    @ResponseBody
    public Token login(@RequestBody LoginRequest loginRequest){
        return userBusiness.login(loginRequest);
    }

    @GetMapping(value = "userlist")
    @ResponseBody
    public List<CustomUser> userlist(@RequestParam("token") String token ){
        return userBusiness.userList(token);
    }


    @PostMapping(value = "userlist")
    @ResponseBody
    public List<CustomUser> reuserlist(@RequestBody UserListModel userListModel ){
        return userBusiness.reuserList(userListModel);
    }

    @GetMapping(value = "active")
    @ResponseBody
    public void active(@RequestParam("token") String token){
        userBusiness.active(token);
    }

    @GetMapping(value = "passive")
    @ResponseBody
    public void passive(@RequestParam("token") String token){
         userBusiness.passive(token);
    }

    @GetMapping(value = "getuserstatus")
    @ResponseBody
    public ServerResponse getuserstatus(@RequestParam("uniqueid") String uniqueID){
        return userBusiness.getuserstatus(uniqueID);
    }

    @PostMapping(value = "updateprofilephoto")
    @ResponseBody
    public User updateprofilephoto(@RequestBody Image image){
        return userBusiness.updateProfilePhoto(image);
    }

    @PostMapping(value = "updateprofile")
    @ResponseBody
    public User updateProfile(@RequestBody User user){
        return userBusiness.updateProfile(user);
    }

}
