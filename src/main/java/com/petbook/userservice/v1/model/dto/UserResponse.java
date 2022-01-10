package com.petbook.userservice.v1.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
	private String userId;
	private String username;
	private String password;
}
