package com.alespotify.main.util;

import com.alespotify.main.models.dto.UserAndroid;
import com.alespotify.main.models.entities.User;

public class UserMapper {

    public static UserAndroid toUserAndroid(User user) {
        UserAndroid dto = new UserAndroid();
        dto.setId(user.getId());
        dto.setName(user.getName());
        return dto;
    }
}
