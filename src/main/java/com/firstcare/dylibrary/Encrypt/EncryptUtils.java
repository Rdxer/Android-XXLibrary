package com.firstcare.dylibrary.Encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by LXF on 16/6/2.
 */

public class EncryptUtils {
    /**
     * hmacSHA512
     *
     * @param data
     *            待加密数据
     * @param key
     *            key
     * @return 加密字符串
     */
    public static String hmacSHA512(String data, String key) {
        return hmacDigest(data, key, "HmacSHA512");
    }

    /**
     * 统一加密验证处理
     */
    public static String hmacDigest(String msg, String keyString, String algo) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes("UTF-8"));

            StringBuilder hash = new StringBuilder();
            for (byte aByte : bytes) {
                String hex = Integer.toHexString(0xFF & aByte);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException ignored) {
        } catch (InvalidKeyException ignored) {
        } catch (NoSuchAlgorithmException ignored) {
        }
        return digest;
    }

    public static String toMD5(String str) {
        byte[] source;
        try {
            source = str.getBytes("ascii");
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            md.update(source);
            StringBuffer buf = new StringBuffer();
            for (byte b : md.digest())
                buf.append(String.format("%02x", b & 0xff));// %02x
            return buf.toString().toLowerCase(Locale.getDefault());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
