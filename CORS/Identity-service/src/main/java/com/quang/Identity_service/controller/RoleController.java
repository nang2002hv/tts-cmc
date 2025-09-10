package com.quang.Identity_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.quang.Identity_service.dto.request.ApiReponse;
import com.quang.Identity_service.dto.request.RoleRequest;
import com.quang.Identity_service.dto.response.RoleResponse;
import com.quang.Identity_service.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiReponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiReponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiReponse<List<RoleResponse>> getAll() {
        return ApiReponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiReponse<Void> delete(@PathVariable String role) {
        roleService.delete(role);
        return ApiReponse.<Void>builder().build();
    }
}
