package com.cloudpos.util;

import android.util.Log;

import java.security.Key;

import javax.crypto.Cipher;

public class MessageUtil {
    private static final String TAG = MessageUtil.class.getSimpleName();

    /**
     * 消息加密
     */
    public static byte[] encryptByKey(byte[] data, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/NONE/PKCS1Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "encryptByKey failed");
            return null;
        }
    }

    /**
     * 消息解密
     */
    private static byte[] decryptByKey(byte[] data, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/NONE/PKCS1Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            Log.e(TAG, "decryptByKey failed");
            return null;
        }
    }


}
