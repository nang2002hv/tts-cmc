package com.quang.Identity_service.controller;

import com.quang.Identity_service.dto.request.UserCreationRequest;
import com.quang.Identity_service.dto.request.UserUpdateRequest;
import com.quang.Identity_service.entity.User;
import com.quang.Identity_service.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/users")
public class UserController {

    @Autowired
    private UserSevice userSevice;
    @PostMapping
    User createUser(@RequestBody UserCreationRequest request){
     return userSevice.createUser(request);
    }
    @GetMapping
    List<User> getUsers(){
        return userSevice.getUsers();
    }
    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") String userId){
        return userSevice.getUser(userId);
    }
    @PutMapping("/{userId}")
    User updateUser(@PathVariable String userId ,@RequestBody UserUpdateRequest request){
        return userSevice.updateUser(userId, request);
    }
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){
         userSevice.deleteUser(userId);
         return "user da xoa";
    }
}
