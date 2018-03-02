package com.ideal.flyerteacafes.utils.xutils;

import android.widget.ImageView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.model.entity.ArticleBean;
import com.ideal.flyerteacafes.model.entity.CitysBean;
import com.ideal.flyerteacafes.model.entity.FriendsInfo;
import com.ideal.flyerteacafes.model.entity.ReadsBean;
import com.ideal.flyerteacafes.model.entity.SearchHistoryBean;
import com.ideal.flyerteacafes.model.entity.StartAdvertBean;
import com.ideal.flyerteacafes.model.loca.DraftInfo;
import com.ideal.flyerteacafes.utils.Utils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.net.HttpCookie;
import java.util.List;

public class XutilsHelp {


    /**
     * 保存cookies
     */
    public static void saveCookie() {
        DbCookieStore instance = DbCookieStore.INSTANCE;
        List<HttpCookie> cookies = instance.getCookies();
        StringBuffer sb = new StringBuffer();
        for (HttpCookie cookie : cookies) {
            sb.append(cookie.getName());
            sb.append("=");
            sb.append(cookie.getValue());
            sb.append(";");
        }
        SharedPreferencesString.getInstances().savaToString("cookie", sb.toString());
        SharedPreferencesString.getInstances().commit();
    }


    /**
     * 数据库
     */
    private static DbManager.DaoConfig daoConfig;

    public static DbManager getDbUtils() {
        daoConfig = new DbManager.DaoConfig()
                .setDbName(Utils.Database_Name)//创建数据库的名称
//                .setDbVersion(12)//数据库版本号
                .setDbVersion(13)//数据库版本号
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        try {
                            db.dropTable(ArticleBean.class);
                            db.dropTable(DraftInfo.class);
                            db.dropTable(ReadsBean.class);
                            db.dropTable(FriendsInfo.class);
                            db.dropTable(CitysBean.class);
                            db.dropTable(StartAdvertBean.class);
                            db.dropTable(SearchHistoryBean.class);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                    }
                });//数据库更新操作


        DbManager db = x.getDb(daoConfig);
        return db;
    }


    public static ImageOptions image_FIT_XY = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.FIT_XY)
            .setLoadingDrawableId(R.drawable.post_def)//加载中默认显示图片
            .setFailureDrawableId(R.drawable.post_def)//加载失败后默认显示图片
            .setUseMemCache(true)
            .build();

}
