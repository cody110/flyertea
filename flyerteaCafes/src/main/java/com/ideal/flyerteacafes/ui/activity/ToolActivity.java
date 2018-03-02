package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.ToolAdapter;
import com.ideal.flyerteacafes.model.loca.ToolBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2016/6/6.
 */
@ContentView(R.layout.activity_tool)
public class ToolActivity extends BaseActivity {

    @ViewInject(R.id.tool_listview)
    ListView listView;


    List<ToolBean> toolBeanList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initVariables();
        initViews();
    }

    @Event(R.id.toolbar_left)
    private void click(View view){
        finish();
    }

    @Event(value = R.id.tool_listview,type = AdapterView.OnItemClickListener.class)
    private void itemClick(AdapterView<?> parent, View view, int position, long id){
        Bundle bundle=new Bundle();
        if(position==0){
            bundle.putString("url", Utils.HtmlUrl.HTML_CREDITCARD);
            bundle.putString("title",getString(R.string.html_title_creditcard));
            jumpActivity(TbsWebActivity.class, bundle);
        }else if(position==1){
            bundle.putString("url",Utils.HtmlUrl.HTML_PLUGIN);
            bundle.putString("title",getString(R.string.html_title_plugin));
            jumpActivity(TbsWebActivity.class, bundle);
        }else if(position==2){//MCC查询
            jumpActivity(CreditCardPointActivity.class,null);
        }else{//招行酒店查询
            bundle.putString("url",Utils.HtmlUrl.HTML_HOTEL_SEARCH);
            bundle.putString("title",getString(R.string.html_title_hotel_search));
            jumpActivity(TbsWebActivity.class, bundle);
        }
    }

    @Override
    public void initVariables() {
        super.initVariables();
        toolBeanList.add(new ToolBean(R.drawable.tool_item_bg, R.mipmap.tool_creditcard_icon, R.mipmap.tool_arrow, "信用卡", "积分攻略", "选择适合自己的"));
        toolBeanList.add(new ToolBean(R.drawable.tool_item_bg,R.mipmap.tool_phone_icon,R.mipmap.tool_arrow,"电话","都在我这里","欢迎查看"));
        toolBeanList.add(new ToolBean(R.drawable.tool_item_bg,R.mipmap.tool_mcc_search_icon,R.mipmap.tool_arrow,"MCC查询","商户类别码","全在这里"));
        toolBeanList.add(new ToolBean(R.drawable.tool_item_bg,R.mipmap.tool_hotel_search_icon,R.mipmap.tool_arrow,"招商银行300+100精品酒店查询","招行300+100酒店、房型、金额一览",""));
    }

    @Override
    public void initViews() {
        super.initViews();
        ToolAdapter adapter=new ToolAdapter(this,toolBeanList,R.layout.item_tool_layout);
        listView.setAdapter(adapter);
    }
}
