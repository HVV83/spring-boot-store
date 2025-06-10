package com.codewithmosh.store.dtos.user;

import lombok.Value;

@Value
public class RegisterUserRequest {

    String name;
    String email;
    String password;

}
