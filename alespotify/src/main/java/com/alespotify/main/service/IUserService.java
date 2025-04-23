package com.alespotify.main.service;

import com.alespotify.main.models.entities.User;

public interface IUserService {
    User findByEmail(String email);
    User login(String credentials);
}
