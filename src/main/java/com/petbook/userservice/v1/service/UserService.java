package com.petbook.userservice.v1.service;

import com.petbook.userservice.v1.model.dto.UserRequest;
import com.petbook.userservice.v1.model.dto.UserResponse;
import com.petbook.userservice.v1.model.entity.UserEntity;
import com.petbook.userservice.v1.model.mapper.UserRequestMapper;
import com.petbook.userservice.v1.model.mapper.UserResponseMapper;
import com.petbook.userservice.v1.model.repository.UserRepository;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

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

	public UserResponse selectUserOne(UserRequest userRequest) {
		UserEntity userEntity = userRequestMapper.toEntity(userRequest);
		System.out.println("userEntity = " + userEntity);

		Optional<UserEntity> resEntityOptional = userRepository.findByUserIdAndPassword(userEntity.getUserId(), userEntity.getPassword());

		if(resEntityOptional.isEmpty()) {
			return null;
		}

		System.out.println("resEntity = " + resEntityOptional.get());
		return userResponseMapper.toDto(resEntityOptional.get());
	}

	public UserResponse selectUserById(String userId) {

		Optional<UserEntity> resEntityOptional = userRepository.findByUserId(userId);

		if(resEntityOptional.isEmpty()) {
			return null;
		}

		System.out.println("resEntity = " + resEntityOptional.get());
		return userResponseMapper.toDto(resEntityOptional.get());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> userEntity = userRepository.findByUserId(username);

		if(!userEntity.isPresent()) {
			throw new UsernameNotFoundException(username);
		}

		return new User(userEntity.get().getUserId(), userEntity.get().getPassword(), true, true, true, true, new ArrayList<>());

	}
}
