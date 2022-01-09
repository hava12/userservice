package com.filming.userservice.v1.service;

import com.filming.userservice.v1.model.dto.UserRequest;
import com.filming.userservice.v1.model.dto.UserResponse;
import com.filming.userservice.v1.model.entity.UserEntity;
import com.filming.userservice.v1.model.mapper.UserRequestMapper;
import com.filming.userservice.v1.model.mapper.UserResponseMapper;
import com.filming.userservice.v1.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRequestMapper userRequestMapper;
	private final UserResponseMapper userResponseMapper;
	private final UserRepository userRepository;

	public UserResponse saveUser(UserRequest userRequest) {
		UserEntity userEntity = userRequestMapper.toEntity(userRequest);
		System.out.println("userEntity = " + userEntity);

		UserEntity resEntity = userRepository.save(userEntity);
		System.out.println("resEntity = " + resEntity);
		return userResponseMapper.toDto(resEntity);
	}
}
