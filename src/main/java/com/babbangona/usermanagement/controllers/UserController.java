package com.babbangona.usermanagement.controllers;


import com.babbangona.commons.library.dto.UserDto;
import com.babbangona.commons.library.dto.response.BaseResponse;
import com.babbangona.commons.library.exceptions.InvalidPayloadException;
import com.babbangona.commons.library.exceptions.UserIdAlreadyExistException;
import com.babbangona.usermanagement.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/internal/user/register")
    public BaseResponse<UserDto> saveUser(@RequestBody@Valid UserDto userDto) {
        if (Objects.isNull(userDto)) {
            throw new InvalidPayloadException("Payload cannot be Null", HttpStatus.BAD_REQUEST);
        }
        if(userService.findByUsername(userDto.getUsername())){
            throw new UserIdAlreadyExistException("Username is already taken",HttpStatus.BAD_REQUEST);
        }
        return userService.saveUser(userDto);
    }


}
