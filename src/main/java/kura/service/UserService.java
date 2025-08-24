package kura.service;

import kura.pojo.User;

public interface UserService {
    User login(User user);

    User getByUsername(String username);

    User getById(Integer id);

    void insertNewUser(User user);

    void updateByUser(User user);

    User getByUser(User user);

    void saveRefreshToken(Integer id, String refreshToken);

}
