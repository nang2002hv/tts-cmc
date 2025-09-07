package com.quang.Identity_service.controller;

import com.quang.Identity_service.dto.request.ApiReponse;
import com.quang.Identity_service.dto.request.UserCreationRequest;
import com.quang.Identity_service.dto.request.UserUpdateRequest;
import com.quang.Identity_service.dto.response.UserReponse;
import com.quang.Identity_service.entity.User;
import com.quang.Identity_service.service.UserSevice;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

     UserSevice userSevice;
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
    UserReponse getUser(@PathVariable("userId") String userId){
        return userSevice.getUser(userId);
    }
    @PutMapping("/{userId}")
    UserReponse updateUser(@PathVariable String userId ,@RequestBody UserUpdateRequest request){
        return userSevice.updateUser(userId, request);
    }
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){
         userSevice.deleteUser(userId);
         return "user da xoa";
    }
}
