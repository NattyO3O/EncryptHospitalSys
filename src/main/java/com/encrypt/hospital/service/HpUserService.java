package com.encrypt.hospital.service;

import com.encrypt.hospital.model.HpUser;
import com.encrypt.hospital.model.Patient;
import com.encrypt.hospital.repository.HpUserRepository;
import com.encrypt.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static com.encrypt.hospital.util.cerUtil.verifyCertificate;

@Service
public class HpUserService {

    private final HpUserRepository userRepository;
    private final PatientRepository patientRepository;
    //private final PasswordEncoder passwordEncoder;

    @Autowired
    public HpUserService(HpUserRepository userRepository, PatientRepository patientRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    public HpUser registerUser(HpUser user) {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new IllegalStateException("用户名已存在");
        }
        try {
            byte[] encryptedPassword = EncryptionService.encryptSM4(user.getPassWord());
            user.setPassWord(Base64.getEncoder().encodeToString(encryptedPassword));  // 将加密后的二进制数据转换为Base64字符串
            user.setType("Patient");

            HpUser savedUser = userRepository.save(user);
            Patient patient = new Patient();
            patient.setUserID(savedUser.getUserID());
            patient.setPatientName(savedUser.getUserName());
            patient.setSex("Male");
            patientRepository.save(patient);

            return savedUser;
        } catch (Exception e) {
            throw new RuntimeException("加密时发生错误", e);
        }
    }

    public HpUser loginUser(String username, String password) {
        HpUser user = userRepository.findByUserName(username);
        if (user == null) {
            throw new IllegalStateException("用户不存在，请注册。");
        } else if (user.getType().equals("Admin") || user.getType().equals("Doctor")){
            throw new IllegalStateException("请从管理员或医生入口登录");
        }
        try {
            byte[] encryptedStoredPassword = Base64.getDecoder().decode(user.getPassWord());
            byte[] decryptedPassword = DecryptService.decryptSM4(encryptedStoredPassword);
            if (!Arrays.equals(decryptedPassword, password.getBytes(StandardCharsets.UTF_8))) {
                throw new IllegalStateException("登录失败，密码错误");
            }
        } catch (Exception e) {
            throw new RuntimeException("解密时发生错误", e);
        }
        return user;
    }

    public HpUser loginAdmin(String username, String password, MultipartFile certificateData, MultipartFile p7bData) throws Exception {
        HpUser user = userRepository.findByUserName(username);
        if (user == null) {
            throw new IllegalStateException("用户不存在，请注册。");
        } else if (user.getType().equals("Patient")){
            throw new IllegalStateException("请从用户入口登录");
        }
        try {
            byte[] encryptedStoredPassword = Base64.getDecoder().decode(user.getPassWord());
            byte[] decryptedPassword = DecryptService.decryptSM4(encryptedStoredPassword);
            if (!Arrays.equals(decryptedPassword, password.getBytes(StandardCharsets.UTF_8))) {
                throw new IllegalStateException("登录失败，密码错误");
            }
        } catch (Exception e) {
            throw new RuntimeException("解密时发生错误", e);
        }
        if (certificateData != null && ("Admin".equals(user.getType()) || "Doctor".equals(user.getType()))) {
            System.out.println("接收到用户数字证书："+user.getUserName());
            if (!verifyCertificate(certificateData, p7bData)) {
                throw new IllegalStateException("数字证书验证失败");
            }
        }
        return user;
    }

    public HpUser save(HpUser user) {
        return userRepository.save(user);
    }

    // Fetch all admin users
    public List<HpUser> getAllAdmins() {
        return userRepository.findByType("Admin");
    }

    // Add a new admin user
    public void addAdmin(String username, String password) {
        if (userRepository.findByUserName(username) != null) {
            throw new IllegalStateException("用户名已存在");
        }
        HpUser newAdmin = new HpUser();
        newAdmin.setUserName(username);
        byte[] encryptedPassword = EncryptionService.encryptSM4(password);  // 使用 SM4 加密密码
        newAdmin.setPassWord(Base64.getEncoder().encodeToString(encryptedPassword));  // 将加密后的二进制数据转换为Base64字符串存储
        newAdmin.setType("Admin");
        userRepository.save(newAdmin);
    }

    // Delete a user by ID
    public void deleteUserById(int userID) {
        userRepository.deleteById(userID);
    }
}