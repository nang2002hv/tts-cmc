package com.quang.Identity_service.mapper;

import org.mapstruct.Mapper;

import com.quang.Identity_service.dto.request.PermissionRequest;
import com.quang.Identity_service.dto.response.PermissionResponse;
import com.quang.Identity_service.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
