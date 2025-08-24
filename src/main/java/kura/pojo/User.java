package kura.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String description;
    private String avatar;
    private Date birthday;
    private String email;
    private Date registerTime;
    private String refreshToken;

    public UserVO toVO(){
        return new UserVO(id, username, description, avatar, birthday, email, registerTime);
    }
}
