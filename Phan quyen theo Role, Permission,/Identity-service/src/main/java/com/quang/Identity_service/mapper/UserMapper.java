package com.quang.Identity_service.mapper;

import com.quang.Identity_service.dto.request.UserCreationRequest;
import com.quang.Identity_service.dto.request.UserUpdateRequest;
import com.quang.Identity_service.dto.response.UserReponse;
import com.quang.Identity_service.entity.Role;
import com.quang.Identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserReponse toUserReponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    default String map(Role role) {
        return role.getName();
    }
}
