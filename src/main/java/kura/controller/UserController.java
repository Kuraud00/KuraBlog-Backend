package kura.controller;

import kura.pojo.Result;
import kura.pojo.User;
import kura.service.UserService;
import kura.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/{username}")
    public ResponseEntity<Result> getByUsername(@PathVariable String username){
        User res = userService.getByUsername(username);
        if(res == null){
            return ResponseEntity.ok(Result.error("not found"));
        }
        return ResponseEntity.ok(Result.success(res.toVO()));
    }

    @GetMapping
    public ResponseEntity<Result> getByToken(@RequestHeader("Authorization") String token){
        Integer id = jwtUtils.getIdFromToken(token);
        User res = userService.getById(id);
        if(res == null){
            return ResponseEntity.ok(Result.error("NOT_FOUND"));
        }
        return ResponseEntity.ok(Result.success(res.toVO()));
    }

    @PutMapping
    public ResponseEntity<Result> update(@RequestHeader("Authorization") String token, @RequestBody User user){
        Integer id = jwtUtils.getIdFromToken(token);
        User res = userService.getByUsername(user.getUsername());
        if(res != null && !res.getId().equals(id)){
            log.info("找到相同名称但ID不同的用户，不可更改此名称因为名称是唯一的");
            return ResponseEntity.ok(Result.error("SAME_USERNAME"));
        }
        user.setId(id);
        userService.updateByUser(user);
        log.info("修改完成");
        return ResponseEntity.ok(Result.success());
    }
}
