package com.quang.Identity_service.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quang.Identity_service.dto.request.UserCreationRequest;
import com.quang.Identity_service.dto.request.UserUpdateRequest;
import com.quang.Identity_service.dto.response.UserReponse;
import com.quang.Identity_service.entity.User;
import com.quang.Identity_service.enums.Role;
import com.quang.Identity_service.exception.AppException;
import com.quang.Identity_service.exception.ErorCode;
import com.quang.Identity_service.mapper.UserMapper;
import com.quang.Identity_service.repository.RoleRepository;
import com.quang.Identity_service.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserSevice {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserReponse createUser(UserCreationRequest request) {
        log.info("Service : Create user");
        //        if (userRepository.existsByUsername(request.getUsername())) throw new
        // AppException(ErorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>(); // them phan quyen
        roles.add(Role.USER.name());

        //         user.setRoles(roles);
        try {

            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErorCode.USER_EXISTED);
        }

        return userMapper.toUserReponse(user);
    }
    // @PreAuthorize("hasAuthority('Approve_Post')")
    //    @PreAuthorize("hasRole('ADMIN')") // tao 1 cai kiem tra trc goi ham chi admin moi thay return
    public List<UserReponse> getUsers() {
        log.info("In method get Users");
        return userRepository.findAll().stream().map(userMapper::toUserReponse).toList();
    }

    @PostAuthorize(
            "returnObject.username == authentication.name") // ktra sau in khi cai post thuc hien xong, chi lay ttin cua
    // minh
    public UserReponse getUser(String id) {
        log.info("In method get user by Id");
        return userMapper.toUserReponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErorCode.USER_NOT_EXISTED)));
    }

    public UserReponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErorCode.USER_NOT_EXISTED));

        return userMapper.toUserReponse(user);
    }

    public UserReponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserReponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
