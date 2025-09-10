package com.quang.Identity_service.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.quang.Identity_service.dto.request.UserCreationRequest;
import com.quang.Identity_service.entity.User;
import com.quang.Identity_service.exception.AppException;
import com.quang.Identity_service.repository.UserRepository;
import com.quang.Identity_service.service.UserSevice;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserSeviceTest {

    @Autowired
    private UserSevice userSevice;

    @MockitoBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(1990, 1, 1);
        request = UserCreationRequest.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .password("12345678")
                .dob(dob)
                .build();

        user = User.builder()
                .id("07b4fc9dde25")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .password("12345678")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var response = userSevice.createUser(request);

        // THEN
        assertThat(response.getId()).isEqualTo("07b4fc9dde25");
        assertThat(response.getUsername()).isEqualTo("john");
    }

    @Test
    void createUser_userExisted_fail() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN
        var exception = Assertions.assertThrows(AppException.class, () -> userSevice.createUser(request));
        // when
        assertThat(exception.getErorCode().getCode()).isEqualTo(1002);
    }

    @Test
    @WithMockUser(username = "john")
    void getMyInfo_valid_success() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        var response = userSevice.getMyInfo();
        assertThat(response.getUsername()).isEqualTo("john");
        assertThat(response.getId()).isEqualTo("07b4fc9dde25");
    }

    @Test
    @WithMockUser(username = "john")
    void getMyInfo_userNotFound_error() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));
        var exception = Assertions.assertThrows(AppException.class, () -> userSevice.getMyInfo());
        assertThat(exception.getErorCode().getCode()).isEqualTo(1005);
    }
}
