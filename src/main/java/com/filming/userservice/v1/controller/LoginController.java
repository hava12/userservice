package com.filming.userservice.v1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

	@GetMapping("/health")
	public ResponseEntity<String> health() {
		System.out.println("user-service is alive");
		return new ResponseEntity<String>("ok", HttpStatus.ACCEPTED );
	}

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> req) {
        System.out.println("req = " + req);
        return new ResponseEntity<String>("ok", HttpStatus.ACCEPTED );
    }
}
