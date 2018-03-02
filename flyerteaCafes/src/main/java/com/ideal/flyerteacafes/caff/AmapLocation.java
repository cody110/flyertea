package com.ideal.flyerteacafes.caff;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.HashMap;

/****
 * 高德定位工具类
 *
 * @author young
 ***/
public class AmapLocation implements AMapLocationListener {

    //mLatitude 纬度  mLongitude经度
    public static double mLatitude = 0, mLongitude = 0;
    private AMapLocation amapLocation;

    private static LocationManagerProxy mLocationManagerProxy;
    static Context mcontext = FlyerApplication.getContext();

    private static AmapLocation aMapLocation;

    private HashMap<Object, MyBDLocation> bdLocationHashMap = new HashMap<>();

    public interface MyBDLocation {
        void myReceiveLocation(AMapLocation amapLocation);
    }

    public static AmapLocation getInstance() {
        if (aMapLocation == null)
            aMapLocation = new AmapLocation();
        return aMapLocation;
    }

    /**
     * 初始化定位
     */
    public AmapLocation init() {
        // 初始化定位，只采用网络定位
        mLocationManagerProxy = LocationManagerProxy.getInstance(mcontext);
        mLocationManagerProxy.setGpsEnable(false);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用destroy()方法
        // 其中如果间隔时间为-1，则定位只定一次,
        // 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
        mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);

        return this;

    }

    public void register(Object bdLocation) {
        bdLocationHashMap.put(bdLocation, (MyBDLocation) bdLocation);
        if (amapLocation != null)
            ((MyBDLocation) bdLocation).myReceiveLocation(amapLocation);
    }

    public void unregister(Object bdLocation) {
        bdLocationHashMap.remove(bdLocation);
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null)
            if (amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0) {
                this.amapLocation = amapLocation;
                mLatitude = amapLocation.getLatitude();
                mLongitude = amapLocation.getLongitude();
                for (MyBDLocation bdlocation : bdLocationHashMap.values()) {
                    bdlocation.myReceiveLocation(amapLocation);
                }
                requestUploadLocation(mLatitude, mLongitude);
            } else {
                LogFly.e("AmapErr", "Location ERR:" + amapLocation.getAMapException().getErrorCode());
            }
    }

    /****
     * 移除定位请求
     ***/
    public static void onPause() {
        // 移除定位请求
//		mLocationManagerProxy.removeUpdates(this);
        // 销毁定位
        mLocationManagerProxy.destroy();
    }


    private void requestUploadLocation(double mLatitude2, double mLongitude2) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_LOCATION);
        params.addBodyParameter("mapx", mLongitude2 + "");
        params.addBodyParameter("mapy", mLatitude2 + "");
        XutilsHttp.Post(params, null);
    }

}
