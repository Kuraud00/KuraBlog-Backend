package kura.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements VO{
    private Integer id;
    private String username;
    private String description;
    private String avatar;
    private Date birthday;
    private String email;
    private Date registerTime;
}
