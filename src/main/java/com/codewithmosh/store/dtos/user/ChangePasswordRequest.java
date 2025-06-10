package com.codewithmosh.store.dtos.user;

import lombok.Value;

@Value
public class ChangePasswordRequest {

    String oldPassword;
    String newPassword;

}
