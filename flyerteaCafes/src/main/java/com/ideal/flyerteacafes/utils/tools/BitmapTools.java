package com.ideal.flyerteacafes.utils.tools;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;

import com.ideal.flyerteacafes.utils.LogFly;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class BitmapTools {


    /**
     * 根据视频路径生成缩略图
     *
     * @param videoPath
     * @return
     */
    public static Bitmap getVideoThumbnail(String videoPath) {
//        Bitmap bitmap = null;
//        MediaMetadataRetriever mediaMetadataRetriever = null;
//        try {
//            mediaMetadataRetriever = new MediaMetadataRetriever();
//            mediaMetadataRetriever.setDataSource(videoPath);
//            bitmap = mediaMetadataRetriever.getFrameAtTime();
//        } catch (Exception e) {
//            e.printStackTrace();
//            LogFly.e("Exception in getVideoThumbnail(String videoPath)" + e.getMessage());
//        } finally {
//            if (mediaMetadataRetriever != null) {
//                mediaMetadataRetriever.release();
//            }
//        }
//        return bitmap;
        return ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MICRO_KIND);
    }

    public static Bitmap getVideoThumbnail(String filePath, int width, int height) {
        //定義一個Bitmap對象bitmap；
        Bitmap bitmap = null;

        //ThumbnailUtils類的截取的圖片是保持原始比例的，但是本人發現顯示在ImageView控件上有时候有部分沒顯示出來；
        //調用ThumbnailUtils類的靜態方法createVideoThumbnail獲取視頻的截圖；
        bitmap = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Images.Thumbnails.MICRO_KIND);

        //調用ThumbnailUtils類的靜態方法extractThumbnail將原圖片（即上方截取的圖片）轉化為指定大小；
        //最後一個參數的具體含義我也不太清楚，因為是閉源的；
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

        //放回bitmap对象；
        return bitmap;
    }


    public static Bitmap viewToBitmap(View view) {

        //打开图片的缓存
        view.setDrawingCacheEnabled(true);
        //图片的大小 固定的语句
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //将位置传给view
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        //转化为bitmap文件
        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }


    public static Bitmap compressImage(String srcPath) {

//        BitmapFactory.Options bfOptions=new BitmapFactory.Options();
//        bfOptions.inDither=false;
//       bfOptions.inPurgeable=true;
//         bfOptions.inTempStorage=new byte[12 * 1024];
//        // bfOptions.inJustDecodeBounds = true;
//        File file = new File(srcPath);
//        FileInputStream fs=null;
//         try {
//            fs = new FileInputStream(file);
//         } catch (FileNotFoundException e) {
//                e.printStackTrace();
//          }
//        Bitmap image = null;
//         if(fs != null) {
//             try {
//                 image = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
//             } catch (IOException e) {
//                 e.printStackTrace();
//             } finally {
//                 if (fs != null) {
//                     try {
//                         fs.close();
//                     } catch (IOException e) {
//                         e.printStackTrace();
//                     }
//                 }
//             }
//         }

        Bitmap image = BitmapFactory.decodeFile(srcPath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        if ( baos.toByteArray().length / 1024>1024) {    //循环判断如果压缩后图片是否大于1M,大于继续压缩
//            baos.reset();//重置baos即清空baos
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩options%，把压缩后的数据存放到baos中

//        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return image;
    }


    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 50;
        while (baos.toByteArray().length / 1024 > 500) {    //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static byte[] compressImageBybyte(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 50;
        while (baos.toByteArray().length / 1024 > 500) {    //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        return baos.toByteArray();
    }


    public static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 1920f;//这里设置高度为800f
        float ww = 1080f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;//压缩好比例大小后再进行质量压缩
    }

    /**
     * 获取图片宽度
     *
     * @param srcPath
     * @return
     */
    public static int getImageWidth(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        return newOpts.outWidth;
    }


    public static Bitmap revitionImageSize(String filePath, int size) {
        Bitmap bitmap = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);

        opts.inSampleSize = computeSampleSize(opts, -1, size * size);
        opts.inJustDecodeBounds = false;

        try {
            bitmap = BitmapFactory.decodeFile(filePath, opts);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }


    /**
     * 缩小bitmap
     *
     * @param uri
     * @param size
     * @param context
     * @return
     */
    @SuppressWarnings("unused")
    public static Bitmap revitionImageSize(Uri uri, int size, Context context) {
        ContentResolver resolver = context.getContentResolver();
        try {
            // 根据Uri取得图片流
            InputStream temp = resolver.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
            options.inJustDecodeBounds = true;
            // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
            BitmapFactory.decodeStream(temp, null, options);
            // 关闭流
            temp.close();
            // 生成压缩的图片
            Bitmap bitmap = null;
            // 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
            temp = resolver.openInputStream(uri);
            // 这个参数表示 新生成的图片为原始图片的几分之一。
            options.inSampleSize = computeSampleSize(options, -1, size * size);
            // 这里之前设置为了true，所以要改为false，否则就创建不出图片
            options.inJustDecodeBounds = false;

            bitmap = BitmapFactory.decodeStream(temp, null, options);
            return bitmap;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }


    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap resizeBitMapImage1(String filePath, int targetWidth,
                                            int targetHeight) {
        Bitmap bitMapImage = null;
        // First, get the dimensions of the image
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        double sampleSize = 0;
        // Only scale if we need to
        // (16384 buffer for img processing)
        Boolean scaleByHeight = Math.abs(options.outHeight - targetHeight) >= Math
                .abs(options.outWidth - targetWidth);
        if (options.outHeight * options.outWidth * 2 >= 1638) {
            // Load, scaling to smallest power of 2 that'll get it <= desired
            // dimensions
            sampleSize = scaleByHeight ? options.outHeight / targetHeight
                    : options.outWidth / targetWidth;
            sampleSize = (int) Math.pow(2d,
                    Math.floor(Math.log(sampleSize) / Math.log(2d)));
        }
        // Do the actual decoding
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[128];
        while (true) {
            try {
                options.inSampleSize = (int) sampleSize;
                bitMapImage = BitmapFactory.decodeFile(filePath, options);
                break;
            } catch (Exception ex) {
                try {
                    sampleSize = sampleSize * 2;
                } catch (Exception ex1) {
                }
            }
        }
        return bitMapImage;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage   ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 加载本地图片 http://bbs.3gstdy.com
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return bitmap;
    }

    public static Bitmap getDiskBitmap(File file) {
        Bitmap bitmap = null;
        try {
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(file.getPath());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return bitmap;
    }

    /**
     * 将byte[]转成bitmap,在这做了压缩处理
     *
     * @param bytes
     * @return
     */
    public static Bitmap getBitmapFromBytes(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inSampleSize = 2 * (int) (options.outWidth / 720 / 2);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                    options);
        } catch (OutOfMemoryError ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 下载图片
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static Bitmap loadImageFromUrl(String uri) {
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            // open connection
            URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            /* for Get request */
            urlConnection.setRequestMethod("GET");
            int fileLength = urlConnection.getContentLength();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                inputStream = urlConnection.getInputStream();
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    // publishing the progress....  (total * 100 / fileLength)) - 1
                    output.write(data, 0, count);
                }
                ByteArrayInputStream bufferInput = new ByteArrayInputStream(output.toByteArray());
                Bitmap bitmap = BitmapFactory.decodeStream(bufferInput);
                inputStream.close();
                bufferInput.close();
                output.close();
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    /**
     * 下载图片
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static Bitmap loadImageFromUrl(String uri, boolean bol)
            throws Exception {

        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            // open connection
            URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            /* for Get request */
            urlConnection.setRequestMethod("GET");
            int fileLength = urlConnection.getContentLength();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                inputStream = urlConnection.getInputStream();
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    // publishing the progress....  (total * 100 / fileLength)) - 1
                    output.write(data, 0, count);
                }
                ByteArrayInputStream bufferInput = new ByteArrayInputStream(output.toByteArray());
                Bitmap bitmap;
                if (bol)
                    bitmap = getBitmapFromBytes(output.toByteArray());
                else
                    bitmap = BitmapFactory.decodeStream(bufferInput);

                inputStream.close();
                bufferInput.close();
                output.close();
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }


        return null;
    }

    public static Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL
                    .openConnection();
            // 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            // 连接设置获得数据流
            conn.setDoInput(true);
            // 不使用缓存
            conn.setUseCaches(false);
            // 这句可有可无，没有影响
            // conn.connect();
            // 得到数据流
            InputStream is = conn.getInputStream();
            // 解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            // 关闭数据流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;

    }

    /**
     * 建立HTTP请求，并获取Bitmap对象。
     *
     * @param imageUrl 图片的URL地址
     * @return 解析后的Bitmap对象
     */
    public static Bitmap downloadBitmap(String imageUrl) {
        Bitmap bitmap = null;
        HttpURLConnection con = null;
        try {
            URL url = new URL(imageUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5 * 1000);
            con.setReadTimeout(10 * 1000);
            con.setDoInput(true);
            con.setDoOutput(true);
            bitmap = BitmapFactory.decodeStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return bitmap;
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        if (w == 0)
            return null;
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 储存bitmap到本地
     *
     * @param bitName
     * @param mBitmap
     */
    public static boolean saveMyBitmap(File f, Bitmap mBitmap) {
        try {
            f.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return false;
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 保存图片为JPEG
     *
     * @param bitmap
     * @param path
     */
    public static boolean saveJPGE_After(Bitmap bitmap, File file) {
        // File file = new File(path);
        if (bitmap == null || file == null)
            return false;
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
