package kura.controller;

import kura.pojo.User;
import kura.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginController loginController;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody User user){
        User res = userService.getByUsername(user.getUsername());

        // 有同名，驳回
        if(res != null){
            return ResponseEntity.badRequest().build();
        }

        // 没有同名，开始创建新用户
        userService.insertNewUser(user);

        // 创建完毕，登录返回JWT
        return loginController.login(user);
    }
}
