package com.ideal.flyerteacafes.utils.tools;

import org.apache.commons.codec.binary.Base64;

import java.util.Random;

public class StringTools {

    public static String empty(String str) {
        if (str == null)
            return "";
        return str;
    }

    /**
     * 字符串替换，从头到尾查询一次，替换后的字符串不检查
     *
     * @param str    源字符串
     * @param oldStr 目标字符串
     * @param newStr 替换字符串
     * @return 替换后的字符串
     */
    static public String replaceAll(String str, String oldStr, String newStr) {
        if (str == null || oldStr == null || newStr == null)
            return str;
        int i = str.indexOf(oldStr);
        int n = 0;
        while (i != -1) {
            str = str.substring(0, i) + newStr
                    + str.substring(i + oldStr.length());
            i = str.indexOf(oldStr, i + newStr.length());
            n++;
        }
        return str;
    }

    /**
     * 字符串替换，从头到尾查询一次，替换后的字符串不检查
     *
     * @param str    源字符串
     * @param oldStr 目标字符串
     * @param newStr 替换字符串
     * @return 替换后的字符串
     */
    static public String replace(String str, String oldStr, String newStr) {
        if (str == null || oldStr == null || newStr == null)
            return str;
        int i = str.indexOf(oldStr);
        int n = 0;
        while (i != -1) {
            str = str.substring(0, i) + newStr
                    + str.substring(i + oldStr.length());
            i = str.indexOf(oldStr, i + newStr.length());
            n++;
        }
        return str;
    }

    /**
     * 字符串替换，左边第一个。
     *
     * @param str    源字符串
     * @param oldStr 目标字符串
     * @param newStr 替换字符串
     * @return 替换后的字符串
     */
    static public String replaceFirst(String str, String oldStr, String newStr) {
        if (str == null || oldStr == null || newStr == null)
            return str;
        int i = str.indexOf(oldStr);
        if (i == -1)
            return str;
        str = str.substring(0, i) + newStr + str.substring(i + oldStr.length());
        return str;
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encodeBase64(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

    public static String encodeBase64(final String str) {
        if(str==null)
            return "";
        return new String(Base64.encodeBase64(str.getBytes()));
    }

    /**
     * 生成随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) { // length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
