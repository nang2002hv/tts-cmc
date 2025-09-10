package com.quang.Identity_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.quang.Identity_service.dto.request.ApiReponse;
import com.quang.Identity_service.dto.request.UserCreationRequest;
import com.quang.Identity_service.dto.request.UserUpdateRequest;
import com.quang.Identity_service.dto.response.UserReponse;
import com.quang.Identity_service.service.UserSevice;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserSevice userSevice;

    @PostMapping
    ApiReponse<UserReponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        log.info("Controller : create user");
        return ApiReponse.<UserReponse>builder()
                .result(userSevice.createUser(request))
                .build();
    }

    @GetMapping
    ApiReponse<List<UserReponse>> getUsers() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());

        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiReponse.<List<UserReponse>>builder()
                .result(userSevice.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiReponse<UserReponse> getUser(@PathVariable("userId") String userId) {
        return ApiReponse.<UserReponse>builder()
                .result(userSevice.getUser(userId))
                .build();
    }

    @GetMapping("/my-info")
    ApiReponse<UserReponse> getMyInfo() {
        return ApiReponse.<UserReponse>builder().result(userSevice.getMyInfo()).build();
    }

    @PutMapping("/{userId}")
    ApiReponse<UserReponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return ApiReponse.<UserReponse>builder()
                .result(userSevice.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiReponse<String> deleteUser(@PathVariable String userId) {
        userSevice.deleteUser(userId);
        return ApiReponse.<String>builder().result(" user da xoa").build();
    }
}
