package kura.controller;

import kura.mapper.UserMapper;
import kura.pojo.Result;
import kura.pojo.User;
import kura.service.UserService;
import kura.utils.AliyunOSSUtils;
import kura.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<Result> upload(@RequestHeader("Authorization") String token,MultipartFile file){
        Integer id = jwtUtils.getIdFromToken(token);
        String url =  aliyunOSSUtils.upload(file);
        User user = new User();
        user.setId(id);
        user.setAvatar(url);
        userService.updateByUser(user);
        return  ResponseEntity.ok(Result.success(url));
    }
}
