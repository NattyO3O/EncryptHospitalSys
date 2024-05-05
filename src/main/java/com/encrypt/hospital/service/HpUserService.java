package com.encrypt.hospital.service;

import com.encrypt.hospital.model.HpUser;
import com.encrypt.hospital.model.Patient;
import com.encrypt.hospital.repository.HpUserRepository;
import com.encrypt.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        user.setType("Patient");
        HpUser savedUser = userRepository.save(user);
        Patient patient = new Patient();
        patient.setUserID(savedUser.getUserID());
        patient.setPatientName(savedUser.getUserName());
        patient.setSex("Male");
        patientRepository.save(patient);

        return savedUser;
    }

    public HpUser loginUser(String username, String password) {
        HpUser user = userRepository.findByUserName(username);
        if (user == null) {
            throw new IllegalStateException("用户不存在，请注册。");
        } else if (!user.getPassWord().equals(password)) {
            throw new IllegalStateException("登录失败，密码错误");
        } else if (user.getType().equals("Admin") || user.getType().equals("Doctor")){
            throw new IllegalStateException("请从管理员或医生入口登录");
        }
        return user;
    }

    public HpUser loginAdmin(String username, String password, MultipartFile certificateData, MultipartFile p7bData) throws Exception {
        HpUser user = userRepository.findByUserName(username);
        if (user == null) {
            throw new IllegalStateException("用户不存在，请注册。");
        } else if (!user.getPassWord().equals(password)) {
            throw new IllegalStateException("登录失败，密码错误");
        }else if (user.getType().equals("Patient")){
            throw new IllegalStateException("请从用户入口登录");
        }
        if (certificateData != null && ("Admin".equals(user.getType()) || "Doctor".equals(user.getType()))) {
            System.out.println("接收到用户数字证书："+user.getUserName());
            if (!verifyCertificate(certificateData, p7bData)) {
                throw new IllegalStateException("数字证书验证失败");
            }
        }
        return user;
    }
}