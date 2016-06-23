package com.rdxer.xxlibrary.OtherUtils.Matcher;

import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LXF on 16/6/23.
 * 参考资料 http://t.cn/R5Oh4XK
 *      使用:

 MatcherUtils m = new MatcherUtils() {
 //            如果输入的有 button
 //            @Override
 //            protected String getString(Object value) {
 //                if (value instanceof Button) {
 //                    return ((Button) value).getText().toString().trim();
 //                }
 //                return super.getString(value);
 //            }
 };
 try {
 m.matcherPhoneNumber(edtAccount);
 m.matcherPassword(edtPassword);
 } catch (MatcherError matcherError) {
 Toast.makeText(this, "请输入合适的账号名密码", Toast.LENGTH_SHORT).show();
 if (matcherError.getErrorCode() != 0) {
 if (matcherError.getValue() instanceof EditText) {
 KeyBoardUtils.openKeybord((EditText) matcherError.getValue(), LoginActivity.this);
 ((EditText) matcherError.getValue()).requestFocus();
 }
 }
 return;
 }
// 匹配通过..
 */
public class MatcherUtils {


    /**
     * 数字
     */
    public static final String reg_Number = "^\\d{11}$";


    /**
     * 手机号
     */
    public static final String reg_PhoneNumber = "^\\d{11}$";

    /**
     * 6 到 15 位
     */
    public static final String reg_password = "^\\w{6,15}$";

    /**
     * 邮箱
     */
    public static final String reg_Email = "^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?$";
                                        //= "^[\\w!#$%&'+/=?^_`{|}~-]+(?:.[\\w!#$%&'+/=?^_`{|}~-]+)@(?:[\\w](?:[\\w-][\\w])?.)+\\w?$";
    /**
     * 中日韩非符号字符
     */
    public static final String reg_symbolCharacter = "^[\\u2E80-\\u9FFF]{1,}$";

    /**
     * 中文字符包括繁体
     */
    public static final String reg_ChineseCharacter = "^[\\u4E00-\\u9FFF]{1,}+$";

    /**
     * 1 . 校验密码强度
     密码的强度必须是包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间。
     ^(?=.\d)(?=.[a-z])(?=.*[A-Z]).{8,10}$
     */

    /**
     * 匹配手机号
     * @param values
     * @throws MatcherError
     */
    public void matcherPhoneNumber(Object... values) throws MatcherError {
        matcher(new String[]{reg_PhoneNumber},values);
    }
    /**
     * 匹配合法的邮箱地址
     * @param values
     * @throws MatcherError
     */
    public void matcherEmil(Object... values) throws MatcherError {
        matcher(new String[]{reg_Email},values);
    }

    public void matcherEmilOrPhoneNumber(Object... values) throws MatcherError {
        matcher(new String[]{reg_Email,reg_PhoneNumber},values);
    }

    /**
     * 匹配合法的密码
     * @param values
     * @throws MatcherError
     */
    public void matcherPassword(Object... values) throws MatcherError {
        matcher(new String[]{reg_password},values);
    }

    /**
     * 匹配金额
     * {n}	重复n次
     * {n,}	重复n次或更多次
     * {n,m}	重复n到m次
     * @param countReg 保留的小数位 "1" 为一位 "1,3" 一位到三位
     * @param values
     * @throws MatcherError
     */
    public void matcherAmount(String countReg,Object... values) throws MatcherError {
        String reg = "^[0-9]+(.[0-9]{"+countReg+"})?$";
        matcher(new String[]{reg_PhoneNumber},values);
    }

    /**
     * 匹配
     *
     * @param patterns 正则表达式集合
     * @param values   待匹配项集合
     * @throws MatcherError 匹配错误抛出的异常
     */
    public void matcher(String[] patterns, Object... values) throws MatcherError {
        Map<String, Pattern> map = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            if (value == null) {
                throw new MatcherError("待匹配项不能是空值.", 0, patterns, value);
            }
            int count = 0;
            for (String pattern : patterns) {
                Pattern r = map.get(pattern);
                // 创建 Pattern 对象
                if (r == null) {
                    r = Pattern.compile(pattern);
                    map.put(pattern, r);
                }
                // 现在创建 matcher 对象
                Matcher m = r.matcher(getString(value));
                if (m.find()) {
                    System.out.println("Found value: " + m.group(0));
                    break;
                } else {
                    count++;
                    continue;
                }
            }
            if (count == patterns.length) {
                throw new MatcherError("不匹配", 1, patterns, value);
            }
        }
    }

    /**
     * 默认是 toString
     * @param value
     * @return
     */
    protected String getString(Object value) {
        if(value instanceof EditText){
            return ((EditText) value).getText().toString().trim();
        }
        return value.toString();
    }

}
