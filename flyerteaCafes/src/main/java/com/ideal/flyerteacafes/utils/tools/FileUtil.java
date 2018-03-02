package com.ideal.flyerteacafes.utils.tools;

import android.content.Context;

import com.ideal.flyerteacafes.utils.LogFly;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    // 读assets下的文件
    public static String readAssetsFile(Context context, String fileName) {
        String result = "";
        InputStream is;
        try {
            is = context.getAssets().open(fileName);
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            result = new String(buffer, "UTF-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    // 读文件
    public static String readSDFile(String fileName) {

        File file = new File(fileName);

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int length = fis.available();

            byte[] buffer = new byte[length];
            fis.read(buffer);

            String res = new String(buffer, "UTF-8");

            fis.close();
            return res;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    /****
     * 解决FileInputStream读取txt文件时，汉字乱码
     ***/
    public static String readFile(File filePath, String fileName) {
        FileInputStream fileInputStream = null;
        byte b[] = null;
        try {
            File cacheFile = new File(filePath + File.separator + fileName);
            b = new byte[(int) cacheFile.length()];
            int i = 0;
            fileInputStream = new FileInputStream(cacheFile);
            byte c = (byte) fileInputStream.read();
            while (c != -1) {
                b[i] = c;
                i++;
                c = (byte) fileInputStream.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new String(b);
    }

    /**
     * 写， 读sdcard目录上的文件，要用FileOutputStream， 不能用openFileOutput
     * 不同点：openFileOutput是在raw里编译过的，FileOutputStream是任何文件都可以
     *
     * @param fileName 完整路径
     * @param message
     */
    // 写在/mnt/sdcard/目录下面的文件 
    public static boolean writeFileSdcard(String fileName, String message) {

        try {

//            FileOutputStream fout = FlyerApplication.getContext().openFileOutput(fileName, Context.MODE_PRIVATE);

            FileOutputStream fout = new FileOutputStream(fileName);

            byte[] bytes = message.getBytes();

            fout.write(bytes);

            fout.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            LogFly.e("错误log:"+e.getMessage());

        }
        return false;
    }
//
//    public static void delFile(String filePath) {
//        File file = new File(filePath);
//        if (file.exists() && !file.isDirectory()) {
//            file.delete();
//        }
//    }
//
//    // 获取当前目录下文件目录
//    public static Vector<String> ObtainFileName(String fileAbsolutePath) {
//        Vector<String> vecFile = new Vector<String>();
//        File file = new File(fileAbsolutePath);
//        File[] subFile = file.listFiles();
//        if (file.exists() && subFile.length > 0) {
//            for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
//                String filename = subFile[iFileLength].getName();
//                vecFile.add(filename);
//            }
//        }
//        return vecFile;
//    }

    /**
     * 判断文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }
}
