package com.encrypt.hospital.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.security.Security;
import java.security.cert.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class cerUtil {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    public static boolean verifyCertificate(MultipartFile certificateData, MultipartFile p7bData) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");

            // 生成用户证书对象
            X509Certificate userCert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certificateData.getBytes()));

            // 生成CA证书链
            Collection<? extends Certificate> caCerts = cf.generateCertificates(new ByteArrayInputStream(p7bData.getBytes()));
            List<X509Certificate> caCertList = new ArrayList<>();
            for (Certificate cert : caCerts) {
                caCertList.add((X509Certificate) cert);  // 显示地进行类型转换
            }

            // 检查并验证证书
            // 确保用户证书的有效性
            userCert.checkValidity();
            System.out.println("用户证书在有效期内");

            // 验证用户证书的签名
            if (!caCertList.isEmpty()) {
                X509Certificate issuingCA = caCertList.get(1);
                userCert.verify(issuingCA.getPublicKey());
                System.out.println("用户证书签名有效");
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
