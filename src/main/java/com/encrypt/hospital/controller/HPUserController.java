package com.encrypt.hospital.controller;

import com.encrypt.hospital.model.HpUser;
import com.encrypt.hospital.service.HpUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HPUserController {

    @Autowired
    private HpUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody HpUser user) {
        try {
            HpUser registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    public String generateToken(HpUser user) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(user.getUserName())
                .claim("userId", user.getUserID())
                .claim("userName", user.getUserName())
                .claim("userType", user.getType())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 1800000)) // 设置30分钟过期
                .signWith(SignatureAlgorithm.HS256, "hospital") // 使用密钥
                .compact();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody HpUser loginRequest) {
        System.out.println("接收到来自用户的登录请求: " + loginRequest.getUserName());
        try {
            HpUser user = userService.loginUser(loginRequest.getUserName(), loginRequest.getPassWord());
            return getResponseEntity(user);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/adminLogin")
    public ResponseEntity<?> adminLogin(@RequestParam("userName") String username,
                                        @RequestParam("passWord") String password,
                                        @RequestParam("certificate") MultipartFile certificate,
                                        @RequestParam("p7b") MultipartFile p7b) {
        try {
            // 确保文件不为空
            if (certificate.isEmpty() || p7b.isEmpty()) {
                return ResponseEntity.badRequest().body("证书文件不能为空");
            }
            HpUser user = userService.loginAdmin(username, password, certificate, p7b);
            return getResponseEntity(user);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("文件读取失败: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private ResponseEntity<?> getResponseEntity(HpUser user) {
        String token = generateToken(user);
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("userName", user.getUserName());
        tokenMap.put("userId", user.getUserID());
        tokenMap.put("userType", user.getType());
        tokenMap.put("token", token);
        return ResponseEntity.ok(tokenMap);
    }

    @GetMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String tokenHeader) {
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            System.out.println("token格式错误");
            return ResponseEntity.badRequest().body("Invalid token format.");
        }
        System.out.println("获取到token");
        String token = tokenHeader.substring(7); // Remove Bearer prefix
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("hospital")
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println("Parsed userName: " + claims.get("userName"));
            System.out.println("Parsed userId: " + claims.get("userId"));
            System.out.println("Parsed userType: " + claims.get("userType"));
            HashMap<String, Object> userInfo = new HashMap<>();
            userInfo.put("userName", claims.get("userName"));
            userInfo.put("userId", claims.get("userId"));
            userInfo.put("userType", claims.get("userType"));
            userInfo.put("token", token);
            System.out.println("token验证成功");
            return ResponseEntity.ok(userInfo);
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("token验证失败");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token validation failed.");
        }
    }
}
