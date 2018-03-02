package com.ideal.flyerteacafes.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.CacheFileManger;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.SetCommonManger;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.model.entity.SmileyBean;
import com.ideal.flyerteacafes.model.loca.SegmentedStringMode;
import com.ideal.flyerteacafes.ui.activity.CommunitySubActivity;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.ui.activity.TopicDetailsActivity;
import com.ideal.flyerteacafes.ui.activity.UserDatumActvity;
import com.ideal.flyerteacafes.ui.activity.XunzhangActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.ArticlePresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleContentActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.utils.tools.ApplicationTools;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;
import com.ideal.flyerteacafes.utils.tools.GsonTools;
import com.ideal.flyerteacafes.utils.tools.StringTools;
import com.ideal.flyerteacafes.xmlparser.TypeModeHandler;
import com.ideal.flyerteacafes.xmlparser.XmlParserUtils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtils {

    /**
     * 根据ulr 获取头部参数
     *
     * @param url
     * @param key
     * @return
     */
    public static String getUrlParam(String url, String key) {
        if (url == null) return "";
        if (url.contains("?") && url.contains("&")) {
            int index = url.indexOf("?");
            url = url.substring(index + 1, url.length());
            LogFly.e("url:" + url);
            String[] array = url.split("&");
            for (int i = 0; i < array.length; i++) {
                String[] keyValue = array[i].split("=");
                if (keyValue.length == 2) {
                    if (TextUtils.equals(keyValue[0], key)) {
                        return keyValue[1];
                    }
                }
            }
        }
        return "";
    }

    /**
     * 前3位后四位不变，中间位变为*号
     *
     * @param text
     * @return
     */
    public static String star7(String text) {
        if (text == null)
            return "";
        int length = text.length();
        if (length > 7) {
            StringBuffer star = new StringBuffer();
            star.append(text.substring(0, 3));
            for (int i = 0; i < length - 7; i++) {
                star.append("*");
            }
            star.append(text.substring(length - 4, length));
            return star.toString();
        } else {
            return text;
        }
    }

    public static final String getFeimiUrl(String url) {
        if (UserManger.isLogin()) {
            StringBuffer sign = new StringBuffer();
            sign.append(UserManger.getUserInfo().getMember_uid());
//                    sign.append(databean.getDataBean().getTruename());
            sign.append(UserManger.getUserInfo().getExtcredits6());
            sign.append("f308e130a29cc4acf83a905ff6c3c51b");
            long time = System.currentTimeMillis();
//                    String time = "1491443653943";
            sign.append(time);
            sign.append("9b293f40d962024f105a4f87544c0e4f");


            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("uid", UserManger.getUserInfo().getMember_uid());
            params.put("userName", UserManger.getUserInfo().getMember_username());
            params.put("userLevel", UserManger.getUserInfo().getGroupid());//用户等级
            params.put("points", UserManger.getUserInfo().getExtcredits6());
            params.put("appKey", "f308e130a29cc4acf83a905ff6c3c51b");
            params.put("timestamp", time);
            params.put("sign", DataTools.getMD5(sign.toString()));

            String jsonString = GsonTools.objectToJsonString(params);
            String needStr = Base64.encodeToString(jsonString.getBytes(), 0, jsonString.getBytes().length, Base64.DEFAULT);
            url = url + "?loginBack=" + needStr;
        }
        return url;
    }

    public static final String getShowNumber(float number) {
        return getShowNumber(String.valueOf(number));
    }

    public static final String getShowNumber(String value) {
        if (TextUtils.isEmpty(value)) return "";
        if (value.endsWith(".0") || value.endsWith(".00")) {
            value = value.substring(0, value.indexOf("."));
        }
        return value;
    }

    /**
     * 是否是正常帖
     *
     * @return 是否专业模式（0 否 1 是）
     */
    public static boolean isNormal(String professional) {
        if (TextUtils.equals(professional, "1")) {
            return false;
        }
        return true;
    }


    /**
     * 压缩图片
     *
     * @param path 原路径
     * @return 新路径 失败返回null
     */
    public static final String compressPictures(String path) {
        String name = path.substring(path.lastIndexOf("/"), path.length());
        String flyPath = CacheFileManger.getCacheImagePath() + name;
        File file = new File(flyPath);
        Bitmap bmp = BitmapTools.compressImage(BitmapTools.getimage(path));
        boolean bol = BitmapTools.saveJPGE_After(bmp, file);
        if (bmp != null)
            bmp.recycle();
        return bol ? flyPath : null;
    }


    /**
     * 字符串拼接标识
     */
    public static final String SPLIT_MARK = ";";

    /**
     * 拼接标识
     *
     * @param strArray
     * @return
     */
    public static String splicMark(String... strArray) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strArray.length; i++) {
            sb.append(strArray[i]);
            if (i != strArray.length - 1)
                sb.append(SPLIT_MARK);
        }
        return sb.toString();
    }

    public static String splicMark(List<String> strArray) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strArray.size(); i++) {
            sb.append(strArray.get(i));
            if (i != strArray.size() - 1)
                sb.append(SPLIT_MARK);
        }
        return sb.toString();
    }

    /**
     * 拆分标识
     *
     * @param str
     * @return
     */
    public static String[] splitMark(String str) {
        if (TextUtils.isEmpty(str)) return null;
        return str.split(SPLIT_MARK);
    }


    /**
     * url请求拼接 HmacSha256 加密token
     *
     * @param url
     * @return
     */
    public static String urlAppedToken(String url) {
        String token;
        if (UserManger.isLogin()) {
            token = UserManger.getUserInfo().getMember_uid() + "|" + DateUtil.getDateline();
        } else {
            token = 0 + "|" + DateUtil.getDateline();
        }
        String newToken = HmacSha256.getSignature(token, "feb0e9a398bf6a79892a114825316a93");
        newToken = StringTools.encodeBase64((token + "|" + newToken).getBytes());
        if (url.indexOf("?") == -1) {
            url = url + "?appkey=98bf6a79892a1148a1&token=" + newToken;
        } else {
            url = url + "&appkey=98bf6a79892a1148a1&token=" + newToken;
        }

        return url;
    }


    public static String urlAppedParams(String url, String key, String value) {
        if (TextUtils.isEmpty(url)) return null;
        if (url.contains("?")) {
            url = url + "&" + key + "=" + value;
        } else {
            url = url + "?" + key + "=" + value;
        }
        return url;
    }

    static Context context = FlyerApplication.getContext();


    /**
     * 分段展示的数据
     *
     * @return
     */
    public static SpannableString getSegmentedDisplaySs(List<SegmentedStringMode> modeList) {

        StringBuffer sb = new StringBuffer();
        int[] location = new int[modeList.size()];

        for (int i = 0; i < modeList.size(); i++) {
            SegmentedStringMode mode = modeList.get(i);
            sb.append(mode.getShowText());
            location[i] = sb.length();
            mode.setSize(context.getResources().getDimensionPixelSize(mode.getSize()));
        }

        SpannableString ss = new SpannableString(sb.toString());

        for (int i = 0; i < modeList.size(); i++) {
            SegmentedStringMode mode = modeList.get(i);

            int starLocation;
            int endLocation;

            if (i == 0) {
                starLocation = 0;
            } else {
                starLocation = location[i - 1];
            }

            endLocation = location[i];

            ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(mode.getColor())), starLocation, endLocation, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(mode.getSize()), starLocation, endLocation, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (mode.getClickableSpan() != null) {
                ss.setSpan(mode.getClickableSpan(), starLocation, endLocation, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }

        return ss;
    }

    public static SpannableString getSegmentedDisplaySs(SegmentedStringMode user, SegmentedStringMode body, List<SmileyBean> smileyBeanList) {

        String bodyText = body.getShowText();

        for (SmileyBean bean : smileyBeanList) {
            if (!TextUtils.isEmpty(bean.getCode()) && bodyText.contains(bean.getCode())) {


                String code = bean.getCode(), code2 = bean.getCode();
                //TODO [] 为特殊字符 替换为<> 再处理
                code = "<" + code.substring(1, code.length() - 1) + ">";
                bodyText = bodyText.replace(code2, code);

            }
        }


        SpannableString ss = new SpannableString(user.getShowText() + bodyText);

        ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(user.getColor())), 0, user.getShowText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new AbsoluteSizeSpan(context.getResources().getDimensionPixelSize(user.getSize())), user.getShowText().length(), ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        for (SmileyBean bean : smileyBeanList) {

            //TODO 当表情符号在末尾时，split拆分不出来，自己再末尾添加一字符
            String content = ss.toString() + "?";
            String code = bean.getCode();
            //TODO [] 为特殊字符 替换为<> 再处理
            code = "<" + code.substring(1, code.length() - 1) + ">";
            if (!TextUtils.isEmpty(bean.getCode()) && ss.toString().contains(code)) {
                String[] datas = content.split(code);

                StringBuffer sbr = new StringBuffer();
                if (datas.length == 0) {
                    Bitmap b = BitmapFactory.decodeResource(FlyerApplication.getContext().getResources(), bean.getIid());
                    ImageSpan imgSpan = new ImageSpan(FlyerApplication.getContext(), b);
                    ss.setSpan(imgSpan, 0, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    for (int i = 0; i < datas.length; i++) {
                        sbr.append(datas[i]);
                        int startNum = sbr.length();
                        int endNum = sbr.length() + bean.getCode().length();
                        if (datas.length - 1 != i || (datas.length - 1 == i && content.endsWith(code))) {
                            Bitmap b = BitmapFactory.decodeResource(FlyerApplication.getContext().getResources(), bean.getIid());
                            b = BitmapTools.zoomImage(b, DensityUtil.dip2px(20), DensityUtil.dip2px(20));
                            ImageSpan imgSpan = new ImageSpan(FlyerApplication.getContext(), b);
                            sbr.append(code);
                            ss.setSpan(imgSpan, startNum, endNum, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                }

            }


        }


        return ss;

    }

    /**
     * 获取xmltypemode格式数据
     *
     * @param fileNamme
     * @return
     */
    public static List<TypeMode> getTypeModeList(String fileNamme) {
        TypeModeHandler starLevelHandler = new TypeModeHandler();
        XmlParserUtils.doMyMission(starLevelHandler, fileNamme);
        return starLevelHandler.getDataList();
    }


    /**
     * webview 里面超链接，点击跳转
     *
     * @param url
     * @param context
     */
    public static void webViewClickUrlDispose(String url, Context context) {
        if (TextUtils.isEmpty(url)) return;
        if (!url.contains("http://") && !url.contains("https://")) {
            url = "http://www.flyertea.com/" + url;
        }
        //TODO 帖子
        String def_url1 = ".flyertea.com/thread";
        String def_url2 = "http://www.flyertea.com/forum.php?mod=viewthread";
        String def_url3 = "http://www.flyertea.com/forum.php?mod=redirect";
        String def_url4 = "http://www.flyertea.com/home.php?mod=spacecp&ac=friend&op=add&";
        //版块 &fid=73
        String def_url6 = ".flyertea.com/forum.php?mod=forumdisplay";

        //文章http://www.flyertea.com/article  http://m.flyertea.com/article
        String def_url5 = ".flyertea.com/article";
        //话题详情页
        String def_url7 = ".flyertea.com/forum.php?mod=collection&action=view&ctid=";
        //勋章详情  参数 &action={勋章类型}&showmedalid={showmedalid}
        String def_url8 = ".flyertea.com/home.php?mod=medal";

        //判断是否是用户
        String user = "http://www.flyertea.com/home.php?mod=space&uid=";


        String tid = "";

        if (url.contains(def_url1)) {
            String[] array = url.split("-");
            try {
                tid = array[1];
                Intent intent = new Intent(context,
                        ThreadActivity.class);
                intent.putExtra("tid", tid);
                intent.putExtra("webviewClickUrl", "webviewClickUrl");
                LogFly.e("tid==>:" + tid);
                context.startActivity(intent);
            } catch (Exception e) {
                // TODO: handle exception
            }
        } else if (url.contains(def_url2)) {
            String[] array = url.split("&");
            String[] array2 = array[1].split("=");
            try {
                tid = array2[1];
                Intent intent = new Intent(context,
                        ThreadActivity.class);
                intent.putExtra("tid", tid);
                intent.putExtra("webviewClickUrl", "webviewClickUrl");
                context.startActivity(intent);
            } catch (Exception e) {
                // TODO: handle exception
            }

        } else if (url.contains(def_url3)) {
            String[] array = url.split("&");
            String[] array2 = array[2].split("=");
            try {
                tid = array2[1];
                Intent intent = new Intent(context,
                        ThreadActivity.class);
                intent.putExtra("tid", tid);
                intent.putExtra("webviewClickUrl", "webviewClickUrl");
                context.startActivity(intent);
            } catch (Exception e) {
                // TODO: handle exception
            }
        } else if (url.contains(def_url4)) {
            String[] array = url.split("&");
            String uid = array[3];
            uid = uid.replaceFirst("uid=", "");
            Intent intent = new Intent(context, UserDatumActvity.class);
            intent.putExtra("uid", uid);
            intent.putExtra(UserDatumActvity.STATUS_KEY, UserDatumActvity.STATUS_STRANGER);
            context.startActivity(intent);
        } else if (url.contains(def_url6)) {
            String fid = getUrlParam(url, "fid");
            if (!TextUtils.isEmpty(fid)) {
                Intent intent = new Intent(context, CommunitySubActivity.class);
                intent.putExtra(WriteThreadPresenter.BUNDLE_FID_2, fid);
                context.startActivity(intent);
            }
        } else if (url.contains(def_url5)) {
            String[] array = url.split("-");
            try {
                tid = array[1];
                Intent intent = new Intent(context,
                        ArticleContentActivity.class);
                intent.putExtra(ArticlePresenter.BUNDLE_AID, tid);
                intent.putExtra("webviewClickUrl", "webviewClickUrl");
                context.startActivity(intent);
            } catch (Exception e) {
                // TODO: handle exception
            }
        } else if (url.contains(def_url7)) {
            try {
                String ctid = getUrlParam(url, "ctid");
                Intent intent = new Intent(context,
                        TopicDetailsActivity.class);
                intent.putExtra("ctid", ctid);
                context.startActivity(intent);
            } catch (Exception e) {
                // TODO: handle exception
            }
        } else if (url.contains(def_url8) && url.contains("showmedalid")) {
            String showmedalid = getUrlParam(url, "showmedalid");
            if (!TextUtils.isEmpty(showmedalid)) {
                Intent intent = new Intent(context, XunzhangActivity.class);
                intent.putExtra("showmedalid", showmedalid);
                context.startActivity(intent);
            }
        } else if (url.contains(user)) {
            String uid = StringTools.replace(url, user, "");
            Intent intent = new Intent(context, UserDatumActvity.class);
            intent.putExtra("uid", uid);
            intent.putExtra("activity", "SearchMemberActivity");
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(context, TbsWebActivity.class);
            intent.putExtra("url", url);
            context.startActivity(intent);
        }
    }

    /**
     * 无图模式下，过去缓存图片 downloadPicture
     */
    public static void downloadPicture(ImageView imgv, String url, int def_img) {
        if (imgv == null || url == null)
            return;
        ImageOptions imageOptions = new ImageOptions.Builder()
//                .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))//图片大小
//                .setRadius(DensityUtil.dip2px(5))//ImageView圆角半径
//                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(def_img)//加载中默认显示图片
                .setFailureDrawableId(def_img)//加载失败后默认显示图片
                .setUseMemCache(true)
                .build();
        x.image().bind(imgv, url, imageOptions);

    }

    /**
     * 获取图片的本地存储路径。
     *
     * @param imageUrl 图片的URL地址。
     * @return 图片的本地存储路径。
     */
    public static String getImagePath(Context context, String imageUrl) {
        int lastSlashIndex = imageUrl.lastIndexOf("/");
        String imageName = imageUrl.substring(lastSlashIndex + 1);

        int index = imageName.indexOf(".");
        if (index != -1) {
//            imageName = imageName.substring(0, index);
        } else {
            imageUrl = imageUrl.substring(0, lastSlashIndex);
            lastSlashIndex = imageUrl.lastIndexOf("/");
            imageName = imageUrl.substring(lastSlashIndex + 1, imageUrl.length()) + ".jpg";
        }

        String imageDir = CacheFileManger.getCacheImagePath();
        File file = new File(imageDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String imagePath = imageDir + "/" + imageName;
        return imagePath;
    }

    /**
     * 根据url生成id
     *
     * @param imageUrl
     * @return
     */
    public static String getImageId(String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) return "-1";
        int lastSlashIndex = imageUrl.lastIndexOf("/");
        String imageName = imageUrl.substring(lastSlashIndex + 1);
        int index = imageName.indexOf(".");
        if (index != -1) {
            imageName = imageName.substring(0, index);
        } else {
            imageUrl = imageUrl.substring(0, lastSlashIndex);
            lastSlashIndex = imageUrl.lastIndexOf("/");
            imageName = imageUrl.substring(lastSlashIndex + 1, imageUrl.length());
        }
        return imageName;
    }

    /**
     * 截取掉表情image，前面的默认
     *
     * @param context
     * @param name
     * @return
     */
    public static int SubStringDef(Context context, String name) {
        int index = name.indexOf("/") + 1;
        name = "_" + name.substring(index);
        index = name.indexOf(".");
        name = name.substring(0, index);
        return ApplicationTools.getIdByName(context, name);
    }


    public static boolean isEmpty(String str) {
        return DataTools.isEmpty(str);
    }

    public static boolean isEmpty(List list) {
        return DataTools.isEmpty(list);
    }


    public static boolean loginIsOK(String str) {
        boolean flag = false;

        if (!isEmpty(str)) {
            if (str.equals(Utils.ret_suc_code)
                    || str.equals("location_login_succeed_mobile")
                    || str.equals("login_succeed")
                    || str.equals("post_reply_succeed")
                    || str.equals("do_success")
                    || str.equals("register_succeed")) {
                flag = true;
            }
        }

        return flag;
    }

    /**
     * 格式化时间字符串
     *
     * @param time
     * @return
     */

    @SuppressLint("SimpleDateFormat")
    public static String getTimeFormat(String time) {
        if (!isEmpty(time)) {
            int timeInt;
            try {
                timeInt = Integer.parseInt(time);
            } catch (Exception e) {
                return "";
            }
            Date date = new Date(timeInt * 1000L);
            SimpleDateFormat format = new SimpleDateFormat("MM/dd");
            time = format.format(date);
            return time;
        } else {
            return "";
        }

    }

    public static String getTimeFormatToBiaozhun(String time) {
        if (!isEmpty(time)) {
            int timeInt;
            try {
                timeInt = Integer.parseInt(time);
            } catch (Exception e) {
                return "";
            }
            Date date = new Date(timeInt * 1000L);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            time = format.format(date);
            return time;
        } else {
            return "";
        }
    }

    public static String getTimeFormat(String time, String formatStr) {
        if (!isEmpty(time)) {
            int timeInt;
            try {
                timeInt = Integer.parseInt(time);
            } catch (Exception e) {
                return "";
            }

            Date date = new Date(timeInt * 1000L);
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            time = format.format(date);
            return time;
        } else {
            return "";
        }
    }

    public static String conversionTime(long dataTime) {
        if (dataTime / 1000000000 > 1000) {
            dataTime /= 1000;
        }
        /**计算秒数*/
        String timeConversion = null;
        long presenTime = (System.currentTimeMillis() / 1000);
        long surplusTime = presenTime - dataTime;
        int temp = 0;
        if (surplusTime < 60) {
            timeConversion = "1分钟前";
        } else if ((temp = (int) (surplusTime / 60)) < 60) {
            timeConversion = temp + "分钟前";
        } else if ((temp = (int) (surplusTime / (60 * 60))) < 24) {
            timeConversion = temp + "小时前";
        } else if ((temp = (int) (surplusTime / (60 * 60))) < 48) {
            timeConversion = "昨天";
        } else if (DateTimeUtil.isSameYear(dataTime * 1000, System.currentTimeMillis())) {
            timeConversion = DataUtils.getTimeFormat(Long.toString(dataTime),
                    "MM-dd");
        } else {
            timeConversion = DataUtils.getTimeFormat(Long.toString(dataTime),
                    "yyyy-MM-dd");
        }
        return timeConversion;
    }

    public static String conversionTime(String time) {
        if (TextUtils.isEmpty(time)) return "";
        long dataTime = Long.parseLong(time);
        return conversionTime(dataTime);
    }

    public static String getTimePreferential(String time) {

        // timeInt
        int timeInt = Integer.parseInt(time);

        int hour = timeInt / 60 / 60;

        int day = hour / 24;

        if (day < 1) {
            time = hour + "小时";
        } else {
            hour = hour % 24;
            time = day + "天" + hour + "时";
        }

        return time;
    }

    public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;

        java.util.regex.Pattern p_html1;
        java.util.regex.Matcher m_html1;

        try {
            String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
            String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); // 过滤html标签

            htmlStr = htmlStr.replaceAll("&nbsp;", " ");
            htmlStr = htmlStr.replaceAll("&rsaquo;", " ");

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符串
    }

    /**
     * 替换html代码 style
     *
     * @param htmlStr
     * @return
     */
    public static String replyImgNull(String htmlStr) {
        // 生成Pattern对象并且编译一个简单的正则表达式"Kelvin"
        Pattern p = Pattern.compile("<a.*>[viewimg]</a>");
        // 用Pattern类的matcher()方法生成一个Matcher对象
        Matcher m = p.matcher(htmlStr);
        StringBuffer sb = new StringBuffer();
        int i = 0;
        // 使用find()方法查找第一个匹配的对象
        boolean result = m.find();

        // 使用循环将句子里所有的kelvin找出并替换再将内容加到sb里
        while (result) {
            String group = m.group();
            group = "";

            i++;
            m.appendReplacement(sb, group);
            System.out.println("第" + i + "次匹配后sb的内容是：" + sb);
            // 继续查找下一个匹配对象
            result = m.find();
        }
        // 最后调用appendTail()方法将最后一次匹配后的剩余字符串加到sb里；
        m.appendTail(sb);
        System.out.println("调用m.appendTail(sb)后sb的最终内容是:" + sb.toString());
        return sb.toString();
    }

    /**
     * 替换html代码 style
     *
     * @param htmlStr
     * @return
     */
    public static String replyImgHtml(String htmlStr) {
        // 生成Pattern对象并且编译一个简单的正则表达式"Kelvin"
        Pattern p = Pattern
                .compile("<\\s*?img\\s+[^>]*?\\s*src\\s*=\\s*([\"\'])((\\\\?+.)*?)\\1[^>]*?>");
        // 用Pattern类的matcher()方法生成一个Matcher对象
        Matcher m = p.matcher(htmlStr);
        StringBuffer sb = new StringBuffer();
        int i = 0;
        // 使用find()方法查找第一个匹配的对象
        boolean result = m.find();

        // 使用循环将句子里所有的kelvin找出并替换再将内容加到sb里
        while (result) {
            String group = m.group();
            // <img class='img-rounded img-responsive'
            // src='http://ptf.flyert.com/forum/201507/06/211633iy6me36voyw46wxz.jpg!k'
            // />
            int index = group.indexOf("smiley");

            if (index == -1) {
                if (group.indexOf("style") == -1)
                    group = group.replaceFirst("img",
                            "img style = 'width:100%'");
            } else {
                // 表情
                if (group.indexOf("style") == -1)
                    group = group.replaceFirst("img",
                            "img style = 'width:18;height:18'");
            }

            i++;
            m.appendReplacement(sb, group);
            System.out.println("第" + i + "次匹配后sb的内容是：" + sb);
            // 继续查找下一个匹配对象
            result = m.find();
        }
        // 最后调用appendTail()方法将最后一次匹配后的剩余字符串加到sb里；
        m.appendTail(sb);
        System.out.println("调用m.appendTail(sb)后sb的最终内容是:" + sb.toString());
        return sb.toString();
    }

    /**
     * html代码中图片替换为本地图片
     *
     * @param htmlStr
     */
    public static final String replyImgByLocal(String htmlStr) {
        // 生成Pattern对象并且编译一个简单的正则表达式"Kelvin"
        Pattern p = Pattern
                .compile("<\\s*?img\\s+[^>]*?\\s*src\\s*=\\s*([\"\'])((\\\\?+.)*?)\\1[^>]*?>");
        // 用Pattern类的matcher()方法生成一个Matcher对象
        Matcher m = p.matcher(htmlStr);
        StringBuffer sb = new StringBuffer();
        int i = 0;
        // 使用find()方法查找第一个匹配的对象
        boolean result = m.find();

        // 使用循环将句子里所有的kelvin找出并替换再将内容加到sb里
        while (result) {
            // style = 'width:100%' ./images/icon_def.png
            String group = "";

            int index = group.indexOf("smiley");

            if (index == -1) {
                group = "<img style = 'width:100%;height:100' src='file:///android_asset/post_not_image_def.png' />";
            } else {
                // 表情
                if (group.indexOf("style") == -1)
                    group = group.replaceFirst("img",
                            "img style = 'width:18;height:18'");
            }

            i++;
            m.appendReplacement(sb, group);
            System.out.println("第" + i + "次匹配后sb的内容是：" + sb);
            // 继续查找下一个匹配对象
            result = m.find();
        }
        // 最后调用appendTail()方法将最后一次匹配后的剩余字符串加到sb里；
        m.appendTail(sb);
        System.out.println("调用m.appendTail(sb)后sb的最终内容是:" + sb.toString());
        return sb.toString();
    }

    public static String replyImgSmileyByLocal(String htmlStr) {
        if (htmlStr == null)
            return "";

        // 生成Pattern对象并且编译一个简单的正则表达式"Kelvin"
        Pattern p = Pattern
                .compile("<\\s*?img\\s+[^>]*?\\s*src\\s*=\\s*([\"\'])((\\\\?+.)*?)\\1[^>]*?>");
        // 用Pattern类的matcher()方法生成一个Matcher对象
        Matcher m = p.matcher(htmlStr);
        StringBuffer sb = new StringBuffer();
        int i = 0;
        // 使用find()方法查找第一个匹配的对象
        boolean result = m.find();

        // 使用循环将句子里所有的kelvin找出并替换再将内容加到sb里
        while (result) {
            // style = 'width:100%' ./images/icon_def.png
            String group = m.group();

            int index = group.indexOf("smiley");

            if (index != -1) {
                group = getSubStr(group, 4);
                int num = group.indexOf("\"");
                if (num != -1)
                    group = group.substring(0, num);
                if (group.startsWith("image/"))
                    group = group.replace("image/", "");
                group = "<img class='smiley' src='file:///android_asset/template/"
                        + group + "' />";
            } else {
                group = "";
            }

            i++;
            m.appendReplacement(sb, group);
            // 继续查找下一个匹配对象
            result = m.find();
        }
        // 最后调用appendTail()方法将最后一次匹配后的剩余字符串加到sb里；
        m.appendTail(sb);
        return sb.toString();

    }

    public static String getSubStr(String str, int num) {
        String result = "";
        int i = 0;
        while (i < num) {
            int lastFirst = str.lastIndexOf('/');
            result = str.substring(lastFirst) + result;
            str = str.substring(0, lastFirst);
            i++;
        }
        return result.substring(1);
    }

    /**
     * 是否是图片模式
     *
     * @param context
     * @return true 为图片模式
     */
    public static final boolean isPictureMode(Context context) {
        if (FlyerApplication.wifiIsConnected)// wifi模式下，直接返回true，图片模式无效
            return true;
        return !SetCommonManger.isNoGraphMode();
    }


    public static void setGroupName(ImageView image, String groupname) {
        if (groupname == null)
            groupname = "";
        if (groupname.equals("新手会员")) {
            image.setImageResource(R.drawable.xinka);
        } else if (groupname.equals("蓝卡会员")) {
            image.setImageResource(R.drawable.lanka);
        } else if (groupname.equals("银卡会员")) {
            image.setImageResource(R.drawable.yinka);
        } else if (groupname.equals("金卡会员")) {
            image.setImageResource(R.drawable.jinka);
        } else if (groupname.equals("白金会员")) {
            image.setImageResource(R.drawable.baijin);
        } else if (groupname.equals("钻石会员")) {
            image.setImageResource(R.drawable.zuanshi);
        } else if (groupname.equals("蓝钻会员")) {
            image.setImageResource(R.drawable.lanzuan);
        } else if (groupname.equals("银钻会员")) {
            image.setImageResource(R.drawable.yinzuan);
        } else if (groupname.equals("金钻会员")) {
            image.setImageResource(R.drawable.jinzuan);
        } else if (groupname.equals("黑钻会员")) {
            image.setImageResource(R.drawable.heizuan);
        } else if (groupname.equals("终钻会员")) {
            image.setImageResource(R.drawable.zhongzuan);
        } else if (groupname.equals("元老会员")) {
            image.setImageResource(R.drawable.yuanlao);
        } else if (groupname.equals("羽客职员")) {
            image.setImageResource(R.drawable.zhiyuan);
        } else if (groupname.equals("编辑部")) {
            image.setImageResource(R.mipmap.bianjibu);
        } else if (groupname.equals("乘务长")) {
            image.setImageResource(R.mipmap.chengwuzhang);
        } else if (groupname.equals("飞客Plus")) {
            image.setImageResource(R.mipmap.feikeplus);
        } else if (groupname.equals("官方客服入驻")) {
            image.setImageResource(R.mipmap.guanfangkefu);
        } else if (groupname.equals("空乘")) {
            image.setImageResource(R.mipmap.kongcheng);
        } else if (groupname.equals("羽客会")) {
            image.setImageResource(R.mipmap.yukehui);
        } else {
            image.setImageResource(R.drawable.wuka);
        }

    }


    /**
     * 是不是有赞链接
     *
     * @param url
     * @return
     */
    public static final boolean isYouZanUrl(String url) {
        if (url == null) return false;
        if (url.indexOf("youzan") != -1 || url.indexOf("koudaitong") != -1) {
            return true;
        }
        return false;
    }

    public static void addDelSmileyListener(final EditText write_thread_content, final List<SmileyBean> smileyBeanList) {
        addDelSmileyListener(write_thread_content, smileyBeanList, null);
    }

    public static void addDelSmileyListener(final EditText write_thread_content, final List<SmileyBean> smileyBeanList, final TextWatcher textWatcher) {
        write_thread_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, final int start, final int delCount, final int addCount) {

                String addEndIndexMark = "<cody>";

                LogFly.e(charSequence.toString() + "delcount:" + delCount + "start:" + start);
                if (delCount > 0 && delCount != charSequence.length()) {//删除
                    for (SmileyBean bean : smileyBeanList) {
                        if (!TextUtils.isEmpty(bean.getCode()) && charSequence.toString().contains(bean.getCode())) {
                            String content = charSequence.toString();
                            String code = bean.getCode(), code2 = bean.getCode();
                            //TODO [] 为特殊字符 替换为<> 再处理
                            code = "<" + code.substring(1, code.length() - 1) + ">";
                            content += addEndIndexMark;
                            content = content.replace(code2, code);

                            int selection = -1;
                            String[] datas = content.split(code);


                            LogFly.e(content + "   " + datas.length + "  code:" + code);

                            StringBuffer sbr = new StringBuffer();
                            for (int i = 0; i < datas.length; i++) {
                                sbr.append(datas[i]);
                                int startNum = sbr.length();
                                int endNum = sbr.length() + bean.getCode().length();
                                if (start >= startNum && start < endNum) {
                                    selection = startNum;
                                } else {
                                    if (datas.length - 1 != i || (datas.length - 1 == i && content.endsWith(code))) {
                                        sbr.append(code2);
                                    }
                                }
                            }

                            sbr.delete(sbr.length() - addEndIndexMark.length(), sbr.length());

                            if (datas.length == 0 && TextUtils.equals(content, code)) {
                                LogFly.e(bean.toString());
                                smileyBeanList.remove(bean);
                                write_thread_content.setText(sbr.toString());
                                break;
                            }

                            if (selection != -1) {
                                LogFly.e(bean.toString());
                                smileyBeanList.remove(bean);
                                write_thread_content.setText(sbr.toString());
                                write_thread_content.setSelection(selection);
                                break;
                            }

                        }
                    }
                }
                if (textWatcher != null)
                    textWatcher.beforeTextChanged(charSequence, start, delCount, addCount);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, final int start, final int delCount, final int addCount) {
                if (textWatcher != null)
                    textWatcher.beforeTextChanged(charSequence, start, delCount, addCount);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (textWatcher != null) textWatcher.afterTextChanged(editable);
            }
        });
    }


    public static void noInputEmojiAddListener(final EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, final int start, final int before, final int count) {
                noInputEmoji(et, s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static void noInputEmoji(EditText et, CharSequence s, int start, int before, int count) {
        //输入的类容
        CharSequence input = s.subSequence(start, start + count);
        //e("输入信息:" + input);
        // 退格
        if (count == 0) return;

        //如果 输入的类容包含有Emoji
        if (isEmojiCharacter(input)) {
            //那么就去掉
            et.setText(removeEmoji(s));
        }

        //最后光标移动到最后 TODO 这里可能会有更好的解决方案
        et.setSelection(et.getText().toString().length());
    }

    /**
     * 去除字符串中的Emoji表情
     *
     * @param source
     * @return
     */
    private static String removeEmoji(CharSequence source) {
        String result = "";
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (isEmojiCharacter(c)) {
                continue;
            }
            result += c;
        }
        return result;
    }

    /**
     * 判断一个字符串中是否包含有Emoji表情
     *
     * @param input
     * @return true 有Emoji
     */
    private static boolean isEmojiCharacter(CharSequence input) {
        for (int i = 0; i < input.length(); i++) {
            if (isEmojiCharacter(input.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是Emoji 表情,抄的那哥们的代码
     *
     * @param codePoint
     * @return true 是Emoji表情
     */
    private static boolean isEmojiCharacter(char codePoint) {
        // Emoji 范围
        boolean isScopeOf = (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF) && (codePoint != 0x263a))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));

        return !isScopeOf;
    }

}
