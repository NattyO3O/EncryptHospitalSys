# 加密的预约挂号系统

**react+springboot+mysql**

管理员和医生采用用户名+密码和数字证书进行登录，患者使用用户名+密码登录。采用HTTPS协议，`src/main/resources/rsa.localhost.pfx`为服务端证书。

1.运行前端：
```
cd /frontend
npm install
npm start
```
2.运行后端`src/main/java/com/encrypt/hospital/HospitalApplication.java`。

3.`/target/Hospital-0.0.1-SNAPSHOT.jar`是生成的jar包，可以在控制台中输入：
```
java -jar Hospital-0.0.1-SNAPSHOT.jar
```
并在浏览器中输入`https://localhost:8443`使用系统。
