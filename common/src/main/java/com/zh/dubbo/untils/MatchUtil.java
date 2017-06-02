package com.zh.dubbo.untils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/6/2.
 */
public class MatchUtil {
    private static final Pattern URL = Pattern.compile(
            "^((https|http|ftp|rtsp|mms)?://)"
                    + "+(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"
                    + "(([0-9]{1,3}\\.){3}[0-9]{1,3}"
                    + "|"
                    + "([0-9a-z_!~*'()-]+\\.)*"
                    + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\."
                    + "[a-z]{2,6})"
                    + "(:[0-9]{1,4})?"
                    + "((/?)|"
                    + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$", Pattern.CASE_INSENSITIVE
    );
    private static final Pattern EMAIL = Pattern.compile("^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$");
    private static final Pattern NUMERIC = Pattern.compile("^[0-9]+$");
    private static final Pattern MONEY = Pattern.compile("^[0-9]+$|^[0-9]+\\.[0-9]{1,6}$");
    private static final Pattern CH_CHAR = Pattern.compile("^[\u4e00-\u9fa5]+$");
    private static final Pattern PHONE = Pattern.compile("^1[3|4|5|7|8]([0-9])\\d{8}$");
    private static final Pattern TELPHONE = Pattern.compile("^(0[0-9]{2,4}-?[0-9]{7,8})|(1[3|4|5|7|8][0-9]{9})$");

    public static boolean checkUrl(String url) {
        Matcher m = URL.matcher(url);
        return m.matches();
    }

    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static Boolean checkEmail(String email) {
        Matcher m = EMAIL.matcher(email);
        return m.matches();
    }

    /**
     * 验证手机号
     * @param phone
     * @return
     */
    public static Boolean checkPhone(String phone) {
        Matcher m = PHONE.matcher(phone);
        return m.matches();
    }

    /**
     * 验证固话和手机
     */
    public static Boolean checkTelPhone(String tel){
        Matcher m = TELPHONE.matcher(tel);
        return m.matches();
    }

    /**
     * 是否是中文字符
     * @param str
     * @return
     */
    public static boolean is_chinese(String str) {
        if (str == null) {
            return false;
        }
        Matcher result = CH_CHAR.matcher(str);
        return result.matches();
    }

    /**
     *
     * @Description: 判断字符串是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Matcher isNum = NUMERIC.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否为金额
     * @param str
     * @return
     */
    public static boolean isMoney(String str) {
        return MONEY.matcher(str).matches();
    }
    public static boolean isIdCard(String idcard){
        return IdCardUtil.validateCard(idcard);
    }
}
