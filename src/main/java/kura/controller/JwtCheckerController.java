package kura.controller;

import kura.pojo.Result;
import kura.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/check")
public class JwtCheckerController {

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/{jwt}")
    public ResponseEntity<Result> check(@PathVariable String jwt){
        try {
            jwtUtils.parseJWT(jwt);
            return ResponseEntity.ok(Result.success());
        } catch (Exception e) {
            return ResponseEntity.ok(Result.error("INVALID_TOKEN"));
        }
    }
}
