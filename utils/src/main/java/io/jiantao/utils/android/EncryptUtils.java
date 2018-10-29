package io.jiantao.utils.android;

import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密工具类
 * Created by jiantaoyang on 2018/10/29.
 */
public class EncryptUtils {
    private static final String AL_3DES = "DESede";

    public static String encrypt3Des(String str, String key) throws UnsupportedEncodingException {
        if (!TextUtils.isEmpty(str)) {
            byte[] enBytes = encryptMode(key, str.getBytes(), AL_3DES);
            return new String(Base64.encodeToString(enBytes, Base64.NO_WRAP).getBytes(), "UTF-8");
        } else {
            return null;
        }
    }

    private static byte[] encryptMode(String key, byte[] src , String algorithm) {
        try {
            SecretKey deskey = new SecretKeySpec(build3DesKey(key), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes("UTF-8");

        if (key.length > temp.length) {
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }

    public static String decrypt3Des(String str, String key) throws UnsupportedEncodingException {
        if (!TextUtils.isEmpty(str)) {
            byte[] enBytes = Base64.decode(str, Base64.NO_WRAP);
            byte[] deBytes = decryptMode(key, enBytes, AL_3DES);
            return new String(deBytes, "UTF-8");
        } else {
            return null;
        }
    }

    /**
     * 解密
     *
     * @param key
     * @param src
     * @return
     */
    private static byte[] decryptMode(String key, byte[] src, String algorithm) {
        try {
            SecretKey deskey = new SecretKeySpec(build3DesKey(key), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
