package com.quang.Identity_service.dto.request;

import com.quang.Identity_service.Validator.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
     String password;
     String firstName;
     String lastName;
    @DobConstraint(min = 2, message = "INVALID_DOB")
     LocalDate dob;
     List<String> roles;


}