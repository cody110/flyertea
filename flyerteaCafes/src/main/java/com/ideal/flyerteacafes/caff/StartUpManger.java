package com.ideal.flyerteacafes.caff;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.dal.BaseHelper;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.model.entity.CitysBean;
import com.ideal.flyerteacafes.model.entity.LoginBase;
import com.ideal.flyerteacafes.model.entity.MyFavoriteBean;
import com.ideal.flyerteacafes.model.entity.SmileyBean;
import com.ideal.flyerteacafes.model.entity.StartAdvertBean;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.tinker.TinkerService;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.ApplicationTools;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.FileUtil;
import com.ideal.flyerteacafes.utils.tools.GsonTools;
import com.ideal.flyerteacafes.utils.tools.Tools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.ideal.flyerteacafes.xmlparser.TypeModeHandler;
import com.ideal.flyerteacafes.xmlparser.XmlParserUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2017/3/23.
 * 应用启动管理类
 */

public class StartUpManger {


    private static StartUpManger instance;

    private StartUpManger() {
    }

    public static StartUpManger getInstance() {
        if (instance == null) {
            synchronized (StartUpManger.class) {
                instance = new StartUpManger();
            }
        }
        return instance;
    }


    /**
     * 自动登录
     */
    public interface IAutomaticLogin {
        void loginSuccess(UserBean userBean);
    }

    private List<IAutomaticLogin> iAutomaticLogins = new ArrayList<>();

    public void registerIAutomaticLogin(IAutomaticLogin iAutomaticLogin) {
        iAutomaticLogins.add(iAutomaticLogin);
    }

    public void unregisterIAutomaticLogin(IAutomaticLogin iAutomaticLogin) {
        iAutomaticLogins.remove(iAutomaticLogin);
    }

    /**
     * 获取我的关注列表
     */
    public interface IMyFavorite {
        void myFavoriteData(List<MyFavoriteBean> favoriteDatas);
    }

    private List<IMyFavorite> iMyFavorites = new ArrayList<>();

    public void registerIMyFavorite(IMyFavorite iMyFavorite) {
        iMyFavorites.add(iMyFavorite);
    }

    public void unRegisterIMyFavorite(IMyFavorite iMyFavorite) {
        iMyFavorites.remove(iMyFavorite);
    }


    /**
     * 应用启动初始化
     *
     * @param activity
     */
    public void init(final Activity activity) {
        UserManger.getInstance();
        ForumDataManger.getInstance().init();
        BaseDataManger.getInstance().requestTea();
        BaseDataManger.getInstance().requestType();
        SetCommonManger.defaultSetting();
        CacheFileManger.init();

        initData(activity);
        requestStartAdvert();
        if (!TextUtils.isEmpty(UserManger.getCookie())) {
            requestLogin();
        }

        requestGetPatch();


        FlyRequestParams params = new FlyRequestParams("http://115.159.197.42:8912/Api/Pricing");

        LogFly.e("http://115.159.197.42:8912/Api/Pricing");
        params.setBodyContent("{\"cid\":\"flyertrip\",\"routing\":{\"adultPrice\":1640,\"adultTax\":50,\"childPrice\":1000,\"childTax\":0,\"currency\":\"CNY\",\"data\":\"2MQGaIGsQKq4HEZMQEfQOjoyPWpGJctdhDIvBdZUhnnHOXvM4ujC5GnAYB7fYYiJT8bhx8JQtVg+uNljPr2T8+Lex0V2V+CzRkibVf1luD9Sd9JO9Gz4AhQ4WBKcFlQG4F9e8freCDZbOSPrE6JtxtKaI9K7+B+W0gp3ulTDYIGmm98LpuDVNuEbQfKK9EwLKXP51oWkDaZydFLuoU5aHQVZ/HlI1WiJh9cJSH+EiiWznsXFv8BW4lv48ghZB+N1yphjxGT1hGKh8jFko5ueiZA5Kxu5GbAFjZ+6OlGpyKM1Ob7UbQMS/xZJpA6mBymi5QU0/8/Hll5wV/YLHSHWkzzNIhmi4H3UkBj7E+En6/Q5v4dGve0VUZxQtuNHU/FUND3ZEMMBEHdLas/sAXTR+jKYcyOizwX6UdPyV1gU54aMc3MDxDmAFkwo24l271CboDmuv9xDTmTIa0ktEaFDrOpJCpG4UVQNcIz13Xukwj0RB46b3r3l+HWRzxKlmPSvKE1SnyQrA/pD+I5zSlD7QygTsn7Xjk1kkoCOcqjRl1cJVcO4tlXExEj51rPUD79K2WxmWjs+JSi8tnsQEm7dlbaiCpJGMbDWLHed6lSOEW8h5IDaYcXtuEIy+/hBMBe1\",\"fromSegments\":[{\"aircraftCode\":\"33G\",\"arrAirport\":\"CAN\",\"arrTime\":\"201801241445\",\"cabinClass\":1,\"cabinCode\":\"M\",\"carrier\":\"CZ\",\"codeShare\":false,\"depAirport\":\"PEK\",\"depTime\":\"201801241130\",\"flightNumber\":\"CZ3116\",\"seatCount\":10,\"shareFlightNumber\":\"\",\"stopCities\":\"\"}],\"retSegments\":[],\"rule\":{\"baggage\":\"具体行李额及是否直挂请致电航空公司核实\",\"endorse\":\"\",\"other\":\"退改签按航司规定，验价时会返回正确的退改签规则\",\"refund\":\"\"},\"ticketingCarrier\":\"\"},\"source\":2,\"tripType\":1}");
        XutilsHttp.Post(params, null);

    }


    /**
     * 初始化数据
     */
    public void initData(Activity context) {
        // 版本更新
        if (!Tools.getVersion(context).equals(
                SharedPreferencesString.getInstances().getStringToKey("versionName"))) {
            SharedPreferencesString.getInstances().savaToInt("count", 0);
            SharedPreferencesString.getInstances().savaToString("versionName", Tools.getVersion(context));
            SharedPreferencesString.getInstances().savaToString("defaultModul",
                    context.getString(R.string.defaultModul));
            SharedPreferencesString.getInstances().savaToString("uid", "");
            SharedPreferencesString.getInstances().commit();
            CacheDataManger.clearCacheDataByUpgrade();


            final String cacheText = FileUtil.readSDFile(CacheFileManger.getDataCachePath() + "/" + Utils.read_tab);
            if (TextUtils.isEmpty(cacheText)) {
                TypeModeHandler starLevelHandler = new TypeModeHandler();
                XmlParserUtils.doMyMission(starLevelHandler, "xml/community_read_tab.xml");
                List<TypeMode> typeModeList = starLevelHandler.getDataList();

                String data = GsonTools.objectToJsonString(typeModeList);
                String txtPath = CacheFileManger.getSavePath() + "/" + Utils.read_tab;
                FileUtil.writeFileSdcard(txtPath, data);
            }


//            if (BaseHelper.getInstance().getCount(SmileyBean.class) == 0) {
            String smileyjson = FileUtil
                    .readAssetsFile(context, "smiley_json.txt");
            List<SmileyBean> smileyList = JsonAnalysis.jsonToSmiley(
                    context, smileyjson);
            BaseHelper.getInstance().saveListAll(smileyList, SmileyBean.class);
//            }
        }

        int count = SharedPreferencesString.getInstances().getIntToKey("count");
        if (count == 0) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int w_screen = dm.widthPixels;
            int h_screen = dm.heightPixels;
            float density = dm.density;


            float scale_w = w_screen / 1080.f;
            float scale_h = h_screen / 1920.f;
            float scale = 0;
            if (scale_w > scale_h) {
                scale = scale_h;
            } else {
                scale = scale_w;
            }
            SharedPreferencesString.getInstances().savaToFloat("density", density);
            SharedPreferencesString.getInstances().savaToInt("w_screen", w_screen);
            SharedPreferencesString.getInstances().savaToInt("h_screen", h_screen);
            SharedPreferencesString.getInstances().savaToFloat("scale", scale);
            SharedPreferencesString.getInstances().savaToInt("pictureMode", 0);
            SharedPreferencesString.getInstances().savaToInt("pushMode", 1);
            SharedPreferencesString.getInstances().commit();

        }

        if (BaseHelper.getInstance().getCount(CitysBean.class) == 0) {
            String cityjson = FileUtil.readAssetsFile(context, "city_json.txt");
            List<CitysBean> cityList = JsonAnalysis.getCityList(cityjson);
            BaseHelper.getInstance().saveListAll(cityList, CitysBean.class);
        }


        String smileyjson = FileUtil
                .readAssetsFile(context, "smiley_json.txt");
        List<SmileyBean> smileyList = JsonAnalysis.jsonToSmiley(
                context, smileyjson);
        BaseHelper.getInstance().saveListAll(smileyList, SmileyBean.class);

        //获取useragent存储
        if (TextUtils.isEmpty(SharedPreferencesString.getInstances().getUserAgent())) {
            SharedPreferencesString.getInstances().savaUserAgent();
        }
    }


    /**
     * 启动页广告
     */
    public void requestStartAdvert() {
        final StartAdvertBean startAdvertBean = BaseHelper.getInstance().getBeanByFirst(StartAdvertBean.class);
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_START_ADVERT), new ListDataCallback(ListDataCallback.LIST_KEY_ADV, StartAdvertBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                List<StartAdvertBean> list = result.getDataList();
                StartAdvertBean bean = DataTools.getBeanByListPos(list, 0);

                BaseHelper.getInstance().deleteAll(StartAdvertBean.class);
                if (bean != null)
                    BaseHelper.getInstance().saveBean(bean);

//                if (bean != null && (startAdvertBean == null || !TextUtils.equals(startAdvertBean.getOrder_id(), bean.getOrder_id()))) {
//                    if (startAdvertBean != null && startAdvertBean.getImg_path() != null) {//order id 不同时，说明广告变更
////                        XutilsHelp.getBitmapUtils().clearCache(startAdvertBean.getImg_path());
////                        XutilsHelp.getBitmapUtils().clearDiskCache(startAdvertBean.getImg_path());
//                    }
//                    BaseHelper.getInstance().deleteAll(StartAdvertBean.class);
//                    BaseHelper.getInstance().saveBean(bean);
//                }
            }
        });
    }


    /**
     * 登录
     */
    @SuppressWarnings("deprecation")
    public void requestLogin() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_login);
        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                if (JsonAnalysis.isSuccessEquals1(result)) {
                    LoginBase loginBase = JsonAnalysis.getBean(result, LoginBase.class);
                    UserBean bean = loginBase.getVariables();
                    if (DataUtils.loginIsOK(loginBase.getMessage().getCode())) {
                        for (IAutomaticLogin iAutomaticLogin : iAutomaticLogins) {
                            iAutomaticLogin.loginSuccess(bean);
                        }
                        SharedPreferencesString.getInstances().saveUserinfo(bean);
                        YueManger.getInstance().initUserReadIds();
                        requestFavorite();
                    }
                }

            }

        });
    }


    /**
     * 预加载
     * 我的关注
     */
    public void requestFavorite() {
        final FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FAVORITE);
        params.setLoadCache(true);
        XutilsHttp.Get(params, new ListDataCallback(ListDataCallback.LIST_KEY_MY_FAOORITE, MyFavoriteBean.class) {

            @Override
            public FlyRequestParams getRequestParams() {
                return params;
            }

            @Override
            public void flySuccess(ListDataBean result) {
                List<MyFavoriteBean> listFav = result.getDataList();
                if (listFav != null && !listFav.isEmpty()) {
                    for (int i = listFav.size() - 1; i >= 0; i--) {
                        if (TextUtils.equals(listFav.get(i).getId(), "0")) {
                            listFav.remove(i);
                        }
                    }
                }
                UserManger.getInstance().setFavList(listFav);
                for (IMyFavorite iMyFavorite : iMyFavorites) {
                    iMyFavorite.myFavoriteData(listFav);
                }
            }

        });
    }

    /**
     * 加载热修复的补丁文件
     */
    public void requestGetPatch() {
        String path = ApplicationTools.getSDPath() + "/cody.apatch";
        AndFixPatchManager.getInstance().addPatch(path);


        //启动TinkerService
        Intent intent = new Intent(FlyerApplication.getContext(), TinkerService.class);
        FlyerApplication.getContext().startService(intent);
    }


}
