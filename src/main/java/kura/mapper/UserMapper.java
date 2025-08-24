package kura.mapper;

import kura.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User getByUsernameAndPassword(User user);

    User getByUsername(String username);

    void insertNewUser(User user);

    User getById(Integer id);

    void updateByUser(User user);

    void saveRefreshToken(Integer id, String refreshToken);

    User findRefreshToken(String refreshToken);

    User getByUser(User user);
}
