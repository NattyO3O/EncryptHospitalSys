# 加密的预约挂号系统

**react+springboot+mysql**

管理员和医生采用用户名+密码和数字证书进行登录，患者使用用户名+密码登录。采用HTTPS協議，`src/main/resources/rsa.encryptHospital.pfx`為服務端證書。

运行前端
```
cd /frontend
npm install
npm start
```

运行后端`src/main/java/com/encrypt/hospital/HospitalApplication.java`
