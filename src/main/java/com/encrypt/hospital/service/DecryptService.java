package com.encrypt.hospital.service;

import com.encrypt.hospital.model.EncryptFile;
import com.encrypt.hospital.repository.DecryptFileRepository;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class DecryptService {

    @Autowired
    private DecryptFileRepository decryptFileRepository;

    public List<EncryptFile> searchFilesByName(String fileName) {
        return decryptFileRepository.findByFileNameContaining(fileName);
    }

    public EncryptFile getFileById(Long fileId) {
        return decryptFileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with ID: " + fileId));
    }


    public String decryptData(EncryptFile file) {
        try {
            String encryptedData = file.getEncryptedData();
            if (encryptedData == null) {
                System.out.println("No encrypted data available for decryption.");
                return null;
            }

            // 假设数据以Base64格式存储，先进行解码
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);

            System.out.println("Decrypted data length: " + decodedData.length);
            System.out.println("Sample data (first 10 bytes): " + decodedData);

            byte[] decryptedBytes;
            if ("AES".equals(file.getAlgorithm())) {
                decryptedBytes = decryptAES(decodedData);
            } else if ("SM4".equals(file.getAlgorithm())) {
                decryptedBytes = decryptSM4(decodedData);
            } else {
                System.out.println("Unsupported encryption algorithm: " + file.getAlgorithm());
                return null;
            }

            // 将解密后的字节数组转换为UTF-8编码的字符串
            return new String(decryptedBytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] decryptAES(byte[] data) throws Exception {
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
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, new javax.crypto.spec.IvParameterSpec(new byte[16]));
        return cipher.doFinal(data);
    }

    private static final byte[] keyBytes = {
            (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78,
            (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF,
            (byte) 0xFE, (byte) 0xDC, (byte) 0xBA, (byte) 0x98,
            (byte) 0x76, (byte) 0x54, (byte) 0x32, (byte) 0x10
    };

    private static byte[] unpad(byte[] input) {
        int padLen = input[input.length - 1];
        if (padLen < 1 || padLen > 16) {
            throw new IllegalArgumentException("Invalid padding");
        }
        byte[] original = new byte[input.length - padLen];
        System.arraycopy(input, 0, original, 0, original.length);
        return original;
    }

    public static byte[] decryptSM4(byte[] ciphertext) {
        try {
            byte[] iv = new byte[16];
            byte[] actualCiphertext = new byte[ciphertext.length - 16];

            System.arraycopy(ciphertext, 0, iv, 0, iv.length);
            System.arraycopy(ciphertext, iv.length, actualCiphertext, 0, actualCiphertext.length);

            SM4Engine sm4Engine = new SM4Engine();
            CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(keyBytes), iv);
            CBCBlockCipher cipher = new CBCBlockCipher(sm4Engine);
            cipher.init(false, ivAndKey);

            byte[] decryptedText = new byte[actualCiphertext.length];
            int offset = 0;
            while (offset < actualCiphertext.length) {
                cipher.processBlock(actualCiphertext, offset, decryptedText, offset);
                offset += cipher.getBlockSize();
            }

            return unpad(decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveDecryptedDataToFile(EncryptFile file) {
        String decryptedData = decryptData(file); // 假设这是你的解密方法

        if (decryptedData == null) {
            throw new RuntimeException("Decryption failed or no data available.");
        }

        File outputFile = new File("D:\\" + file.getFileName()); // 构建目标文件路径
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(decryptedData.getBytes(StandardCharsets.UTF_8)); // 写入解密后的数据到文件
            System.out.println("解密后的数据已保存到文件: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to write decrypted data to file.", e);
        }
    }

}