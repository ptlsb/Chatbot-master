package com.example.random;

import android.os.Build;

import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Base64;

public class Encrypt {
    private SecretKey secretKey ;
    private int Key_size = 128  ;
    private Cipher cipher;
    private int T_len = 128 ;

    // generating the key
    public void init() throws Exception{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(Key_size);
        secretKey = keyGenerator.generateKey();
    }
    // encryption
    public String encrypt(String message) throws Exception{
        byte[] messageInbytes = message.getBytes();
        cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        byte[] cipherbyte = cipher.doFinal(messageInbytes);
        return encode(cipherbyte);
    }

    // deccrpytion
    public String decrypt(String encrypted_message) throws Exception{
        byte[] decodedbyte = decode(encrypted_message) ;
        Cipher decrptydcipher = Cipher.getInstance("AES/GCM/NoPadding") ;
        GCMParameterSpec spec = new GCMParameterSpec(T_len,cipher.getIV());
        decrptydcipher.init(Cipher.DECRYPT_MODE,secretKey,spec);
        byte[] decrpyt = decrptydcipher.doFinal(decodedbyte) ;
        return new String(decrpyt);
    }
    // encoding the byte array to string
    private String encode(byte[] data) throws Exception{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(data);
        }
        return "" ;
    }
    private byte[] decode(String data) throws Exception{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getDecoder().decode(data);
        }
        byte[] temp = new byte[0]; 
        return temp ;
    }
}
