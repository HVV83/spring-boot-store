package com.codewithmosh.store.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    @JsonIgnore
    private Long id;
    private String name;
    private String email;

}
