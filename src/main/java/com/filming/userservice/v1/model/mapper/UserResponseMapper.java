package com.filming.userservice.v1.model.mapper;

import com.filming.userservice.v1.model.dto.UserRequest;
import com.filming.userservice.v1.model.dto.UserResponse;
import com.filming.userservice.v1.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper extends GenericMapper<UserResponse, UserEntity> {
}
