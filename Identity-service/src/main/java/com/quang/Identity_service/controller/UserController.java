package com.quang.Identity_service.controller;

import com.quang.Identity_service.dto.request.ApiReponse;
import com.quang.Identity_service.dto.request.UserCreationRequest;
import com.quang.Identity_service.dto.request.UserUpdateRequest;
import com.quang.Identity_service.entity.User;
import com.quang.Identity_service.service.UserSevice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/users")
public class UserController {

    @Autowired
    private UserSevice userSevice;
    @PostMapping
    ApiReponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiReponse<User> apiReponse = new ApiReponse<>();
        apiReponse.setResult(userSevice.createUser(request));
     return apiReponse;
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
