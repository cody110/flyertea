package com.ideal.flyerteacafes.ui.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.LocationListBean;
import com.ideal.flyerteacafes.model.entity.TagBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.controls.StarBar;
import com.ideal.flyerteacafes.ui.dialog.AddTagDialog;
import com.ideal.flyerteacafes.ui.view.ToolBar;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/10/12.
 */

@ContentView(R.layout.activity_rating)
public class RatingActivity extends BaseActivity {


    @ViewInject(R.id.toolbar)
    ToolBar toolBar;
    @ViewInject(R.id.flowlayout)
    GridView gridView;
    @ViewInject(R.id.ratingbar)
    StarBar ratingbar;
    @ViewInject(R.id.ratingbar_tv)
    TextView ratingbar_tv;
    @ViewInject(R.id.description_tv)
    TextView description_tv;

    LocationListBean.LocationBean data;


    List<TagBean> allTags = new ArrayList<>();
    List<TagBean> tags = new ArrayList<>();
    int needTagSize = 10;
    List<TextView> tagsTv = new ArrayList<>();
    String add_tag_str = "换一批";
    CommonAdapter<TagBean> adapter;
    private String remindText;
    private String flight, flightid;
    TagBean chooseTagbean = new TagBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        EventBus.getDefault().register(this);

        data = (LocationListBean.LocationBean) getIntent().getSerializableExtra("data");
        allTags = (List<TagBean>) getIntent().getSerializableExtra("tags");
        flight = getIntent().getStringExtra("flight");
        flightid = getIntent().getStringExtra("flightid");
        float star = getIntent().getFloatExtra("star", 0);

        if (TextUtils.equals(data.getType(), "airport")) {
            remindText = "为航班打标签，为更多飞客出行提供参考";
        } else if (TextUtils.equals(data.getType(), "hotel")) {
            remindText = "为酒店打标签，为更多飞客入住提供参考";
        } else if (TextUtils.equals(data.getType(), "lounge")) {
            remindText = "为候机室打标签，为更多飞客出行提供参考";
        }
        WidgetUtils.setText(description_tv, remindText);
        toolBar.setTitle(data.getName());
        if (!TextUtils.isEmpty(flight)) {
            toolBar.setTitle(flight.toUpperCase());
        }


//        chooseTagbean.setTagname(add_tag_str);
//        initTags();
//        initChildViews();


        setStar(star);
        ratingbar.setStarMark(star);
        ratingbar.setIntegerMark(true);
        ratingbar.setOnStarChangeListener(new StarBar.OnStarChangeListener() {
            @Override
            public void onStarChange(float v) {
                setStar(v);
            }
        });


    }


    private void setStar(float v) {
        if (v == 5) {
            ratingbar_tv.setText("力荐");
        } else if (v == 4) {
            ratingbar_tv.setText("推荐");
        } else if (v == 3) {
            ratingbar_tv.setText("还行");
        } else if (v == 2) {
            ratingbar_tv.setText("较差");
        } else if (v == 1) {
            ratingbar_tv.setText("很差");
        }
    }

    private void initTags() {


        if (allTags.size() < needTagSize) {
            tags.addAll(allTags);
        } else {
            int startIndex = 0;
            if (tags.size() != 0) {
                startIndex = allTags.indexOf(tags.get(tags.size() - 2));
                startIndex += 1;
            }
            tags.clear();
            while (tags.size() < needTagSize) {
                if (startIndex == allTags.size()) {
                    startIndex = 0;
                }
                tags.add(allTags.get(startIndex));
                startIndex++;
            }
            tags.add(chooseTagbean);

        }

    }


    public void onEventMainThread(TagEvent event) {
        if (event.getTag() == TagEvent.TAG_TAB_ADD_TAG) {
            String tag = event.getBundle().getString("data");
            TagBean tagbean = new TagBean();
            tagbean.setTagname(tag);
            tags.add(tags.size() - 1, tagbean);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Event({R.id.commit_btn, R.id.skip_btn, R.id.toolbar_left})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.commit_btn:


                if (ratingbar.getStarMark() == 0) {
                    ToastUtils.showToast("请先点击星星评分");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", data);
                    bundle.putFloat("star", ratingbar.getStarMark());
                    bundle.putSerializable("tags", (Serializable) allTags);
                    bundle.putString("flight", flight);
                    bundle.putString("flightid", flightid);
                    bundle.putString("airport", data.getId());
                    jumpActivitySetResult(bundle);
                }
                break;
            case R.id.toolbar_left:
                finish();
                break;
            case R.id.skip_btn:
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", data);
                bundle.putFloat("star", 0);
                jumpActivitySetResult(bundle);
                break;
        }
    }

    static String TAG_SHRE_DIALOG = "AddTagDialog";

    public void showShareDialog() {
        removeDialogFragment(TAG_SHRE_DIALOG);
        AddTagDialog threadShareDialog = new AddTagDialog();
        threadShareDialog.show(getSupportFragmentManager(), TAG_SHRE_DIALOG);
    }

    protected void removeDialogFragment(String tag) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        if (null != fragment) {
            ft.remove(fragment);
        }
    }

    private void sendComment() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_COMMENT_LOCATION);
        if (TextUtils.equals(data.getType(), "hotel")) {
            params.addBodyParameter("cotype", "0");
        } else if (TextUtils.equals(data.getType(), "airport")) {
            params.addBodyParameter("cotype", "1");
        } else if (TextUtils.equals(data.getType(), "lounge")) {
            params.addBodyParameter("cotype", "3");
        } else {
            params.addBodyParameter("cotype", "4");
        }

        params.addBodyParameter("itemid", data.getId());
        params.addBodyParameter("itemname", data.getName());
        params.addBodyParameter("star", String.valueOf(ratingbar.getStarMark()));
        params.addQueryStringParameter("transcode", "yes");

        StringBuffer tagName = new StringBuffer();
        StringBuffer utagName = new StringBuffer();
        for (TagBean tag : tags) {
            if (tag.isSelect()) {
                tagName.append(tag.getTagname());
                tagName.append(";");
                if (TextUtils.isEmpty(tag.getTagid()) && TextUtils.isEmpty(tag.getType())) {
                    utagName.append(tag.getTagname());
                    utagName.append(";");
                }
            }
        }
        if (tagName.toString().endsWith(";")) {
            tagName.deleteCharAt(tagName.toString().length() - 1);
        }
        if (utagName.toString().endsWith(";")) {
            utagName.deleteCharAt(utagName.toString().length() - 1);
        }

        params.addBodyParameter("tags", tagName.toString());
        params.addBodyParameter("utags", utagName.toString());

        showDialog();
        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                if (JsonAnalysis.isSuccessEquals1(result)) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", data);
                    bundle.putFloat("star", ratingbar.getStarMark());
                    bundle.putSerializable("tags", (Serializable) tags);
                    jumpActivitySetResult(bundle);
                }
                ToastUtils.showToast(JsonAnalysis.getMessage(result));
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialogDismiss();
            }
        });
    }


    /**
     * 流标签设置数据
     */
    private void initChildViews() {
        adapter = new CommonAdapter<TagBean>(this, tags, R.layout.item_tag) {
            @Override
            public void convert(final ViewHolder holder, final TagBean tagBean) {
                holder.setText(R.id.item_tag_tv, tagBean.getTagname());
                if (tagBean.isSelect()) {
                    holder.setTextColorRes(R.id.item_tag_tv, R.color.tag_select);
                    holder.setBackgroundRes(R.id.item_tag_tv, R.drawable.tag_select);
                } else {
                    holder.setTextColorRes(R.id.item_tag_tv, R.color.tag_unselect);
                    holder.setBackgroundRes(R.id.item_tag_tv, R.drawable.tag_unselect);
                }

                holder.setOnClickListener(R.id.item_tag_tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (TextUtils.equals(tagBean.getTagname(), add_tag_str)) {
                            initTags();
                            adapter.notifyDataSetChanged();
                        } else {
                            tagBean.setSelect(!tagBean.isSelect());
                            notifyDataSetChanged();
                        }
                    }
                });
            }
        };
        gridView.setAdapter(adapter);
    }


}
