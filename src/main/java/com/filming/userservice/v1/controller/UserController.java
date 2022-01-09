package com.filming.userservice.v1.controller;

import com.filming.userservice.v1.model.dto.UserRequest;
import com.filming.userservice.v1.model.dto.UserResponse;
import com.filming.userservice.v1.model.entity.UserEntity;
import com.filming.userservice.v1.model.mapper.UserRequestMapper;
import com.filming.userservice.v1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
// @CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	private final UserService userService;

	@GetMapping("/health")
	public ResponseEntity<String> health() {
		System.out.println("user-service is alive");
		return new ResponseEntity<String>("ok", HttpStatus.ACCEPTED );
	}

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequest userRequest) {
        System.out.println("userRequest = " + userRequest);
        UserResponse userResponse = userService.saveUser(userRequest);
		System.out.println("userResponse = " + userResponse);
        return new ResponseEntity<String>("ok", HttpStatus.ACCEPTED );
    }
}
