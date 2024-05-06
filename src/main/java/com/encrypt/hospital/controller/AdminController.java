package com.encrypt.hospital.controller;

import com.encrypt.hospital.model.HpUser;
import com.encrypt.hospital.service.HpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private HpUserService hpUserService;

    // Fetch all admin users from the HpUser table
    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAllAdmins() {
        List<HpUser> admins = hpUserService.getAllAdmins();
        // Transform the list to only include `userID` and `userName`
        List<Map<String, Object>> result = admins.stream()
                .map(admin -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("userID", admin.getUserID());
                    map.put("userName", admin.getUserName());
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // Add a new admin
    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody Map<String, String> payload) {
        try {
            String username = payload.get("username");
            String password = payload.get("password");
            hpUserService.addAdmin(username, password);
            return ResponseEntity.ok("管理员添加成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("添加失败: " + e.getMessage());
        }
    }

    // Delete an admin by userID
    @DeleteMapping("/delete/{userID}")
    public ResponseEntity<?> deleteAdmin(@PathVariable int userID) {
        try {
            hpUserService.deleteUserById(userID);
            return ResponseEntity.ok("管理员删除成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败: " + e.getMessage());
        }
    }
}
