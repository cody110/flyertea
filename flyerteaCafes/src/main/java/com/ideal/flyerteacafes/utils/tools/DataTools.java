package com.ideal.flyerteacafes.utils.tools;

import android.util.TypedValue;

import com.ideal.flyerteacafes.caff.FlyerApplication;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;
import java.util.regex.Pattern;


public class DataTools {

    public static boolean isEmpty(String str) {
        if (str == null || str.equals("") || str.equals("null")) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(List list) {
        if (list != null && (list.size() > 0)) {
            return false;
        }
        return true;
    }

    /**
     * desc:将数组转为16进制
     *
     * @param bArray
     * @return modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * desc:将16进制的数据转为数组
     * <p>创建人：聂旭阳 , 2014-5-25 上午11:08:33</p>
     *
     * @param data
     * @return modified:
     */
    public static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }


    public static <T> T getBeanByListPos(List<T> list, int pos) {
        if (list == null || pos > list.size() || pos < 0)
            return null;
        if (pos >= list.size())
            return null;

        return list.get(pos);
    }


    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                FlyerApplication.getContext().getResources().getDisplayMetrics());
    }

//    public static Double getDecimal(double d, int length) {
//        BigDecimal b = new BigDecimal(d);
//        double f1 = b.setScale(length, BigDecimal.ROUND_HALF_UP).doubleValue();
//        return f1;
//    }

    /*
  * 判断是否为整数
  * @param str 传入的字符串
  * @return 是整数返回true,否则返回false
*/


    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static Double getTwoDecimal(double d) {
        BigDecimal b = new BigDecimal(d);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    public static String getString(Object obj) {
        if (obj == null) {
            return "";
        }
//		else if(obj.getClass() != String.class)
//		{
//			return "";
//		}
        else {
            return String.valueOf(obj);
//			return (String)obj;
        }
    }

    public static Double getDouble(Object obj) {
        return getNumber(obj).getDouble();
    }

    public static GNumber getNumber(Object src) {
        return new GNumber(src);
    }

//    public static float getFloat(String src) {
//        float res = 0f;
//        if (src != null) {
//            try {
//                res = Float.parseFloat(src);
//            } catch (NumberFormatException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return res;
//    }

    public static long getLong(Object src) {
        if (src.getClass() == Long.class) {
            return (Long) src;
        } else if (src.getClass() == Integer.class) {
            return (Integer) src;
        } else if (src.getClass() == String.class) {
            return Long.parseLong((String) src);
        } else {
            return 0;
        }
    }

    public static int getInteger(String src) {
        int res = 0;
        if (src != null) {
            try {
                res = Integer.parseInt(src);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }

    public static int getInteger(Object obj) {
        String src = getString(obj);

        int res = 0;
        if (src != null) {
            try {
                res = Integer.parseInt(src);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 获取list 的size
     *
     * @param list
     * @return
     */
    public static int getListSize(List list) {
        return (list == null) ? 0 : list.size();
    }


    private static int getRandom(int count) {
        return (int) Math.round(Math.random() * (count));
    }

    private static String string = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 获取随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        StringBuffer sb = new StringBuffer();
        int len = string.length();
        for (int i = 0; i < length; i++) {
            sb.append(string.charAt(getRandom(len - 1)));
        }
        return sb.toString();
    }

    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     */
    public static final String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            return "";
        }
    }

    public static String ToCH(int intInput) {
        String si = String.valueOf(intInput);
        String sd = "";
        if (si.length() == 1) // 個
        {
            sd += GetCH(intInput);
            return sd;
        } else if (si.length() == 2)// 十
        {
            if (si.substring(0, 1).equals("1"))
                sd += "十";
            else
                sd += (GetCH(intInput / 10) + "十");
            sd += ToCH(intInput % 10);
        } else if (si.length() == 3)// 百
        {
            sd += (GetCH(intInput / 100) + "百");
            if (String.valueOf(intInput % 100).length() < 2)
                sd += "零";
            sd += ToCH(intInput % 100);
        } else if (si.length() == 4)// 千
        {
            sd += (GetCH(intInput / 1000) + "千");
            if (String.valueOf(intInput % 1000).length() < 3)
                sd += "零";
            sd += ToCH(intInput % 1000);
        } else if (si.length() == 5)// 萬
        {
            sd += (GetCH(intInput / 10000) + "萬");
            if (String.valueOf(intInput % 10000).length() < 4)
                sd += "零";
            sd += ToCH(intInput % 10000);
        }

        return sd;
    }

    private static String GetCH(int input) {
        String sd = "";
        switch (input) {
            case 1:
                sd = "一";
                break;
            case 2:
                sd = "二";
                break;
            case 3:
                sd = "三";
                break;
            case 4:
                sd = "四";
                break;
            case 5:
                sd = "五";
                break;
            case 6:
                sd = "六";
                break;
            case 7:
                sd = "七";
                break;
            case 8:
                sd = "八";
                break;
            case 9:
                sd = "九";
                break;
            default:
                break;
        }
        return sd;
    }


}
