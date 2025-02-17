package com.babbangona.usermanagement.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/resource")
public class ResourceController {

    @GetMapping("/secure")
    public ResponseEntity<String> secureResource(){
        return ResponseEntity.ok(".");
    }
}
