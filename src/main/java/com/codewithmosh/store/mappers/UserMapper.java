package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.user.RegisterUserRequest;
import com.codewithmosh.store.dtos.user.UpdateUserRequset;
import com.codewithmosh.store.dtos.user.UserDto;
import com.codewithmosh.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(RegisterUserRequest request);

    void update(UpdateUserRequset request, @MappingTarget User user);

}
