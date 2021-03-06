package com.web.service;

import com.web.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> allUsers();

    User findById(Long id);

    void deleteUser(Long id);

    boolean saveUser(User user);

    void edit(User user);

    User getUserByNickname(String nickname);

}
