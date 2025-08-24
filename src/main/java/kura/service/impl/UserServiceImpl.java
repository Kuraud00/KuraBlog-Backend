package kura.service.impl;

import io.jsonwebtoken.Claims;
import kura.mapper.UserMapper;
import kura.pojo.User;
import kura.service.UserService;
import kura.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        return userMapper.getByUsernameAndPassword(user);
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public void insertNewUser(User user) {
        user.setRegisterTime(new Date());
        userMapper.insertNewUser(user);
    }

    @Override
    public void updateByUser(User user) {
        userMapper.updateByUser(user);
    }

    @Override
    public User getByUser(User user) {
        return userMapper.getByUser(user);
    }

    @Override
    public void saveRefreshToken(Integer id,String refreshToken) {
        userMapper.saveRefreshToken(id,refreshToken);
    }

}
