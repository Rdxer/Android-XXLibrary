package com.firstcare.dylibrary.Encrypt;

/**
 * 加密工具
 *
 * @author LXF
 */
public class DYEncryptionTools {

    public static String salt = "5ED8854161283A2A3EE892A1B700DC65";

    /**
     * 第一次加密
     *
     * @param msg 明文
     * @return md5之后的密文1
     */
    public static String encryption1(String msg) {
        return EncryptUtils.toMD5(EncryptUtils.hmacSHA512(msg, salt));
    }

    /**
     * 第二次加密
     *
     * @param msg       密文1
     * @param dateStamp 时间戳 (需要和请求头的时间戳一致)
     * @return 密文2
     */
    public static String encryption2_0(String msg, long dateStamp) {
        return EncryptUtils.hmacSHA512(msg, EncryptUtils.hmacSHA512(dateStamp + "", salt));
    }

    /**
     * 第一次加密 + 第二次加密
     *
     * @param msg       明文
     * @param dateStamp 时间戳(需要和请求头的时间戳一致)
     * @return 密文2
     */
    public static String encryption2_1(String msg, long dateStamp) {
        return encryption2_0(encryption1(msg), dateStamp );
    }

    /**
     * 生成请求头 hmac 校对码
     *
     * @param deviceID        设备 id
     * @param deviceTimestamp 设备位置时间戳
     * @param currentUserID   当前用户id,如果未登录传 null
     * @param accesstoken     accesstoken
     * @return hmac
     */
    public static String generateHmacAuthCode(String deviceID, String deviceTimestamp, String currentUserID, String accesstoken) {
        if (currentUserID == null || "0".equals(currentUserID) || accesstoken == null){
            currentUserID = "0";
            accesstoken = "xfn100";
        }
        String msg = deviceID + deviceTimestamp + currentUserID + salt;
        return EncryptUtils.hmacSHA512(msg,accesstoken);
    }
}
