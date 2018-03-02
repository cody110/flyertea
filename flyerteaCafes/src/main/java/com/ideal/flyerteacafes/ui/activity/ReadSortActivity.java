package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.GridViewSortAdapter;
import com.ideal.flyerteacafes.caff.CacheFileManger;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.controls.DragSortGridView;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.FileUtil;
import com.ideal.flyerteacafes.utils.tools.GsonTools;
import com.ideal.flyerteacafes.xmlparser.TypeModeHandler;
import com.ideal.flyerteacafes.xmlparser.XmlParserUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/10/11.
 */
@ContentView(R.layout.activity_read_sort)
public class ReadSortActivity extends BaseActivity {

    @ViewInject(R.id.drag_grid_view)
    DragSortGridView drag_grid_view;

    private List<TypeMode> typeModeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initData();
        initView();


    }

    public void initData() {
        final String cacheText = FileUtil.readSDFile(CacheFileManger.getSavePath() + "/" + Utils.read_tab);
        if (TextUtils.isEmpty(cacheText)) {
            TypeModeHandler starLevelHandler = new TypeModeHandler();
            XmlParserUtils.doMyMission(starLevelHandler, "xml/community_read_tab.xml");
            List<TypeMode> datas = starLevelHandler.getDataList();
            typeModeList.addAll(datas);
        } else {
            List<TypeMode> datas = GsonTools.jsonToList(cacheText, TypeMode.class);
            typeModeList.addAll(datas);
        }
    }

    private void initView() {
        drag_grid_view = (DragSortGridView) findViewById(R.id.drag_grid_view);
        GridViewSortAdapter adapter = new GridViewSortAdapter(drag_grid_view, this, typeModeList);
        drag_grid_view.setAdapter(adapter);
    }


    @Event(R.id.toolbar_right)
    private void click(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        String data = GsonTools.objectToJsonString(typeModeList);
        String txtPath = CacheFileManger.getSavePath() + "/" + Utils.read_tab;
        boolean bol = FileUtil.writeFileSdcard(txtPath, data);
        EventBus.getDefault().post(new TagEvent(TagEvent.TAG_TAB_SORT_CHANGE));
        super.onBackPressed();
    }
}
