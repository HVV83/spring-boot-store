package com.codewithmosh.store.users;

import lombok.Value;

@Value
public class ChangePasswordRequest {

    String oldPassword;
    String newPassword;

}
