package com.petbook.userservice.v1.model.mapper;

import com.petbook.userservice.v1.model.dto.UserRequest;
import com.petbook.userservice.v1.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRequestMapper extends GenericMapper<UserRequest, UserEntity> {
}
