package com.quang.Identity_service.controller;

import com.quang.Identity_service.dto.request.ApiReponse;
import com.quang.Identity_service.dto.request.AuthenticationRequest;
import com.quang.Identity_service.dto.response.AuthenticationResponse;
import com.quang.Identity_service.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
@PostMapping("/log-in")
    ApiReponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
     boolean result = authenticationService.authenticate(request);
     return ApiReponse.<AuthenticationResponse>builder()
             .result(AuthenticationResponse.builder()
                     .authenticated(result)
                     .build())
                     .build();
}

}
