package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.AmapLocation;
import com.ideal.flyerteacafes.model.loca.LocationInfo;
import com.ideal.flyerteacafes.ui.activity.base.BaseToolbarActivity;
import com.ideal.flyerteacafes.ui.view.ToolBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2015/11/25.
 */
public class LocationListActivity extends BaseToolbarActivity implements GeocodeSearch.OnGeocodeSearchListener, AdapterView.OnItemClickListener {

    CommonAdapter adapter;
    private ListView mListView;

    List<LocationInfo> locaList = new ArrayList<>();

    @Override
    protected void setTitleBar(ToolBar mToolbar) {
        mToolbar.setTitle("位置");
    }

    @Override
    protected View createBodyView(Bundle savedInstanceState) {
        mListView = (ListView) getView(R.layout.layout_listview);
//        mListView= V.f(view, R.id.layout_listview);

        GeocodeSearch geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
//latLonPoint参数表示一个Latlng，第二参数表示范围多少米，GeocodeSearch.AMAP表示是国测局坐标系还是GPS原生坐标系
        LatLonPoint latLonPoint = new LatLonPoint(AmapLocation.mLatitude, AmapLocation.mLongitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
        mListView.setOnItemClickListener(this);


        LocationInfo info = new LocationInfo();
        info.setTitle(getString(R.string.not_display_position));
        info.setLocaName("");
        locaList.add(info);

        adapter = new CommonAdapter<LocationInfo>(this, locaList, R.layout.listview_selection_item) {

            @Override
            public void convert(ViewHolder holder, LocationInfo loca) {
                holder.setText(R.id.selection_text, loca.getTitle());
                holder.setText(R.id.location_jiedao, loca.getLocaName());
            }
        };
        mListView.setAdapter(adapter);

        return mListView;
    }

    //逆地理编码回调接口
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        List<PoiItem> poiList = regeocodeResult.getRegeocodeAddress().getPois();
        for (PoiItem item : poiList) {
            LocationInfo locationInfo = new LocationInfo();
            locationInfo.setTitle(item.getTitle());
            locationInfo.setLocaName(item.getSnippet());
            locationInfo.setCityName(regeocodeResult.getRegeocodeAddress().getProvince());
            locaList.add(locationInfo);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LocationInfo item = (LocationInfo) adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString("location", item.getTitle());
        bundle.putString("cityname", item.getCityName());
        jumpActivitySetResult(bundle);
    }
}
