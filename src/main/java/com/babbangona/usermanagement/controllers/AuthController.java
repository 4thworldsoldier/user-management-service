package com.babbangona.usermanagement.controllers;

import com.babbangona.commons.library.dto.request.AuthenticationRequest;
import com.babbangona.commons.library.dto.response.AuthenticationResponse;
import com.babbangona.commons.library.dto.response.BaseResponse;
import com.babbangona.commons.library.security.JwtUtil;
import com.babbangona.commons.library.utils.ResponseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class.getName());

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws BadCredentialsException {
        logger.info("Login Request Received for: " + authenticationRequest.getUsername() + " and password " + authenticationRequest.getPassword() );

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            logger.error("Login failed Error occurred during login :" + e.getMessage(), e);
            throw e;

        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        BaseResponse resp = new BaseResponse<>(ResponseConstants.SUCCESS_CODE,ResponseConstants.SUCCESS_MESSAGE,new AuthenticationResponse(jwt));

        logger.info("Login Request Succeeded for: " + authenticationRequest.getUsername() );
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/post-login")
    public ResponseEntity<BaseResponse> postLogin(@RequestParam("token") String token) {
        logger.info("0auth2 Login successfully Received with token :" + token);
        BaseResponse resp = new BaseResponse<>(ResponseConstants.SUCCESS_CODE,ResponseConstants.SUCCESS_MESSAGE,token);
        return ResponseEntity.ok(resp);
    }
}

