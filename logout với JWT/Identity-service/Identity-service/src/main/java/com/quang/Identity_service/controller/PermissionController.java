package com.quang.Identity_service.controller;

import com.quang.Identity_service.dto.request.ApiReponse;
import com.quang.Identity_service.dto.request.PermissionRequest;
import com.quang.Identity_service.dto.response.PermissionResponse;
import com.quang.Identity_service.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiReponse<PermissionResponse> create(@RequestBody PermissionRequest request){
        return ApiReponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }
    @GetMapping
    ApiReponse<List<PermissionResponse>> getAll(){
        return ApiReponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }
    @DeleteMapping("/{permission}")
    ApiReponse<Void> delete(@PathVariable String permission){
        permissionService.delete(permission);
        return ApiReponse.<Void>builder().build();
    }
}
