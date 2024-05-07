package com.encrypt.hospital.service;

import com.encrypt.hospital.model.EncryptFile;
import com.encrypt.hospital.repository.EncryptedFileRepository;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class EncryptionService {

    @Autowired
    private EncryptedFileRepository encryptedFileRepository;

    // AES 加密
    public static byte[] encryptAES(byte[] data) throws Exception {
        byte[] keyBytes = new byte[] {
                (byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03,
                (byte)0x04, (byte)0x05, (byte)0x06, (byte)0x07,
                (byte)0x08, (byte)0x09, (byte)0x0A, (byte)0x0B,
                (byte)0x0C, (byte)0x0D, (byte)0x0E, (byte)0x0F,
                (byte)0x10, (byte)0x11, (byte)0x12, (byte)0x13,
                (byte)0x14, (byte)0x15, (byte)0x16, (byte)0x17,
                (byte)0x18, (byte)0x19, (byte)0x1A, (byte)0x1B,
                (byte)0x1C, (byte)0x1D, (byte)0x1E, (byte)0x1F
        };
        // 使用固定密钥初始化
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 使用密钥和固定的初始化向量
        byte[] iv = new byte[16]; // 通常，IV 也应该是随机的，这里仅为示例使用全零数组
        IvParameterSpec ivParams = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParams); //
        return cipher.doFinal(data);
    }

    public static byte[] encryptTextAES(String text) throws Exception {
        return encryptAES(text.getBytes("UTF-8"));
    }

    private static final byte[] keyBytes = {
            (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78,
            (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF,
            (byte) 0xFE, (byte) 0xDC, (byte) 0xBA, (byte) 0x98,
            (byte) 0x76, (byte) 0x54, (byte) 0x32, (byte) 0x10
    };

    private static byte[] pad(byte[] input, int blockSize) {
        int padLen = blockSize - (input.length % blockSize);
        byte[] padded = new byte[input.length + padLen];
        System.arraycopy(input, 0, padded, 0, input.length);
        for (int i = input.length; i < padded.length; ++i) {
            padded[i] = (byte) padLen;
        }
        return padded;
    }

    public static byte[] encryptSM4(String plaintext) {
        try {
            byte[] iv = new byte[16];
            new SecureRandom().nextBytes(iv);
            SM4Engine sm4Engine = new SM4Engine();
            CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(keyBytes), iv);
            CBCBlockCipher cipher = new CBCBlockCipher(sm4Engine);
            cipher.init(true, ivAndKey);

            byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);
            byte[] paddedPlaintext = pad(plaintextBytes, cipher.getBlockSize());
            byte[] ciphertext = new byte[iv.length + paddedPlaintext.length];

            // Encrypting the plaintext
            int offset = 0;
            while (offset < paddedPlaintext.length) {
                cipher.processBlock(paddedPlaintext, offset, ciphertext, iv.length + offset);
                offset += cipher.getBlockSize();
            }

            // Prefixing the IV to the ciphertext
            System.arraycopy(iv, 0, ciphertext, 0, iv.length);
            return ciphertext;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String encryptFile(MultipartFile file, String algorithm) throws Exception {

        // 模拟文件加密，实际中应使用真正的加密库和算法，以下为伪代码
        byte[] content = file.getBytes(); // 获取文件内容
        String fileContent = new String(content);
        byte[] encryptedData; // 这里将是加密后的数据

        switch (algorithm) {
            case "AES":
                encryptedData = encryptTextAES(fileContent);
                System.out.println("加密结果为"+encryptedData);
                break;
            case "SM4":
                encryptedData = encryptSM4(fileContent);
                System.out.println("加密结果为"+encryptedData);
                break;
            default:
                throw new IllegalArgumentException("Unsupported encryption algorithm");
        }

        // 对加密数据进行Base64编码，以便安全地在网络上传输
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public EncryptFile saveEncryptedFile(String fileName, String algorithm, String base64EncodedEncryptedData) {
        //String base64EncodedEncryptedData = Base64.getEncoder().encodeToString(encryptedData);
        EncryptFile encryptedFile = new EncryptFile();
        encryptedFile.setFileName(fileName);
        encryptedFile.setAlgorithm(algorithm);
        encryptedFile.setEncryptedData(base64EncodedEncryptedData);
        return encryptedFileRepository.save(encryptedFile);
    }
}