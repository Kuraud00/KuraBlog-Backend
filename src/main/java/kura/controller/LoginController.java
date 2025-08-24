package kura.controller;

import kura.pojo.JwtResponse;
import kura.pojo.User;
import kura.service.UserService;
import kura.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody User user){
        User res = userService.login(user);

        if(res != null){
            // 分配JWT令牌
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",res.getId());
            claims.put("username",res.getUsername());

            String accessToken = jwtUtils.generateAccessToken(claims);
            String refreshToken = jwtUtils.generateRefreshToken(claims);

            userService.saveRefreshToken(res.getId(),refreshToken);

            return ResponseEntity.ok(new JwtResponse(accessToken,refreshToken));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
    }
}
