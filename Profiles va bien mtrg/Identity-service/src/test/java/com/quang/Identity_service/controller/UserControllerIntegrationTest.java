package com.quang.Identity_service.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.quang.Identity_service.dto.request.UserCreationRequest;
import com.quang.Identity_service.dto.response.UserReponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
public class UserControllerIntegrationTest {

    @Container
    static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:8.0.36");

    @DynamicPropertySource
    static void configureDataSource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driverClassName:", () -> "com.mysql.cj.jdbc.Driver");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

    @Autowired
    private MockMvc mockMvc;

    private UserCreationRequest request;
    private UserReponse userReponse;
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
        userReponse = UserReponse.builder()
                .id("07b4fc9dde25")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();
    }

    @Test
    // kiem tra laf dung
    void createUser_validRequest_success() throws Exception {
        // given du lieu dau vao
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        // when khi nao request api nay
        var response = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk()) // then o day
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("result.username").value("john"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.lastName").value("Doe"));

        log.info("Result : {}", response.andReturn().getResponse().getContentAsString());
        // then khi when xay ra thi co dieu gi

    }

    //    @Test
    // kiem tra laf dung
    //    void createUser_userInvalid_success() throws Exception {
    //
    //
    //        // given du lieu dau vao
    //        request.setUsername("joh");
    //        ObjectMapper objectMapper = new ObjectMapper();
    //        objectMapper.registerModule(new JavaTimeModule());
    //        String content = objectMapper.writeValueAsString(request);
    //
    //        //when khi nao request api nay
    //        mockMvc.perform(MockMvcRequestBuilders
    //                        .post("/users")
    //                        .contentType(MediaType.APPLICATION_JSON_VALUE)
    //                        .content(content))
    //                .andExpect(MockMvcResultMatchers.status().isBadRequest())// then o day
    //                .andExpect(MockMvcResultMatchers.jsonPath("code")
    //                        .value(1003))
    //                .andExpect(MockMvcResultMatchers.jsonPath("message")
    //                        .value("user name must be at least 4 character")
    //                );
    //
    //
    //        //then khi when xay ra thi co dieu gi
    //
    //
    //    }

}
