package com.babbangona.usermanagement.controllers;


import com.babbangona.commons.library.dto.UserDto;
import com.babbangona.commons.library.dto.response.BaseResponse;
import com.babbangona.commons.library.exceptions.InvalidPayloadException;
import com.babbangona.commons.library.exceptions.UserIdAlreadyExistException;
import com.babbangona.usermanagement.services.UserService;
import lombok.AllArgsConstructor;
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
    public BaseResponse<UserDto> saveUser(@RequestBody UserDto userDto) {
        if (Objects.isNull(userDto)) {
            throw new InvalidPayloadException("Payload cannot be Null");
        }
        if(userService.findByUsername(userDto.getUsername())){
            throw new UserIdAlreadyExistException("Username is already taken");
        }
        return userService.saveUser(userDto);
    }


}
