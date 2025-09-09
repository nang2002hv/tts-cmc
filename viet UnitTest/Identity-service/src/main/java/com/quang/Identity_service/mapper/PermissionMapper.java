package com.quang.Identity_service.mapper;

import com.quang.Identity_service.dto.request.PermissionRequest;
import com.quang.Identity_service.dto.request.UserCreationRequest;
import com.quang.Identity_service.dto.request.UserUpdateRequest;
import com.quang.Identity_service.dto.response.PermissionResponse;
import com.quang.Identity_service.dto.response.UserReponse;
import com.quang.Identity_service.entity.Permission;
import com.quang.Identity_service.entity.Role;
import com.quang.Identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

}
