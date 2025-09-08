package com.quang.Identity_service.service;

import com.quang.Identity_service.dto.request.UserCreationRequest;
import com.quang.Identity_service.dto.request.UserUpdateRequest;
import com.quang.Identity_service.dto.response.UserReponse;
import com.quang.Identity_service.entity.User;
import com.quang.Identity_service.exception.AppException;
import com.quang.Identity_service.exception.ErorCode;
import com.quang.Identity_service.mapper.UserMapper;
import com.quang.Identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSevice {
     UserRepository userRepository;
     UserMapper userMapper;

    public User createUser(UserCreationRequest request){

        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErorCode.USER_EXISTED);
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public UserReponse getUser(String id){
        return userMapper.toUserReponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found") ));
    }
    public UserReponse updateUser(String userId,UserUpdateRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found") );

        userMapper.updateUser(user, request);
        return    userMapper.toUserReponse(userRepository.save(user));
    }
    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
}
