package com.quang.Identity_service.dto.request;

import com.quang.Identity_service.Validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 4,message = "USERNAME_INVALID")

     String username;
    @Size(min = 6,message = "INVALID_PASSWORD")

     String password;
     String firstName;
     String lastName;
    @DobConstraint(min = 16, message = "INVALID_DOB")
     LocalDate dob;
}