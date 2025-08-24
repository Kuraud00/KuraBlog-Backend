package kura.controller;

import io.jsonwebtoken.Claims;
import kura.pojo.JwtResponse;
import kura.pojo.User;
import kura.service.UserService;
import kura.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @GetMapping("/refresh/{refreshToken}")
    public ResponseEntity<?> refreshToken(@PathVariable String refreshToken){
        log.info("refresh called,refreshToken:{}",refreshToken);
        if(!jwtUtils.isJWTAvailable(refreshToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
        }

        Claims claims = jwtUtils.parseJWT(refreshToken);
        Integer id = (Integer) claims.get("id");
        String username = (String) claims.get("username");

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setRefreshToken(refreshToken);

        User res = userService.getByUser(user);
        if(res == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Expired Refresh Token");
        }

        // 确认RefreshToken仍然有效并且用户存在，则重新发配AccessToken
        String newAccessToken = jwtUtils.generateAccessToken(claims);

        // 旧的RefreshToken已经用过了，为了加强安全性再生成一个新的RefreshToken
        String newRefreshToken = jwtUtils.generateRefreshToken(claims);
        userService.saveRefreshToken(id,newRefreshToken);

        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(newAccessToken,newRefreshToken));
    }
}
