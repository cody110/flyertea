package com.ideal.flyerteacafes.ui.activity.writethread;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.AmapLocation;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.LocationListBean;
import com.ideal.flyerteacafes.model.entity.SmileyBean;
import com.ideal.flyerteacafes.model.entity.TagBean;
import com.ideal.flyerteacafes.model.loca.SegmentedStringMode;
import com.ideal.flyerteacafes.ui.activity.AlbumActivity;
import com.ideal.flyerteacafes.ui.activity.LocationListActivity;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.activity.MyLocationActivity;
import com.ideal.flyerteacafes.ui.activity.RatingActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IWriteThread;
import com.ideal.flyerteacafes.ui.activity.presenter.AlbumPresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteThreadPresenter;
import com.ideal.flyerteacafes.ui.controls.StarBar;
import com.ideal.flyerteacafes.ui.view.MyAlertDialog;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.ViewTools;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/11/14.
 */
@ContentView(R.layout.activity_write_thread)
public class WriteThreadActivity extends MVPBaseActivity<IWriteThread, WriteThreadPresenter> implements IWriteThread {

    @ViewInject(R.id.write_thread_title)
    EditText write_thread_title;
    @ViewInject(R.id.write_thread_content)
    EditText write_thread_content;
    @ViewInject(R.id.write_thread_content_size)
    TextView write_thread_content_size;
    @ViewInject(R.id.write_thread_location_name)
    TextView write_thread_location_name;
    @ViewInject(R.id.chat_biaoqing_layout)
    View biaoqingView;
    @ViewInject(R.id.biaoqing)
    ImageView choose_look_img;
    @ViewInject(R.id.rating_layout)
    View rating_layout;
    @ViewInject(R.id.rating_user_face)
    ImageView rating_user_face;
    @ViewInject(R.id.rating_use_info)
    TextView rating_use_info;
    @ViewInject(R.id.ratingbar)
    StarBar ratingbar;
    @ViewInject(R.id.ratingbar_tv)
    TextView ratingbar_tv;
    @ViewInject(R.id.del_location_btn)
    ImageView del_location_btn;
    @ViewInject(R.id.write_thread_topic_name)
    TextView write_thread_topic_name;

    public static final int requestLocation = 2, requestForumChoose = 5, requestFlyLocation = 6;

    public static final String BUNDLE_TITLE = "title", BUNDLE_CONTENT = "BUNDLE_CONTENT", BUNDLE_LOCATION = "content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        EventBus.getDefault().register(this);
        biaoqingView.setVisibility(View.GONE);
        ratingbar.setIsClick(false);
        mPresenter.init(this);
        initListener();
        AmapLocation.getInstance().register(bdLocation);

    }


    AmapLocation.MyBDLocation bdLocation = new AmapLocation.MyBDLocation() {
        @Override
        public void myReceiveLocation(AMapLocation amapLocation) {
            mPresenter.requestLocation();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AmapLocation.getInstance().unregister(bdLocation);
    }

    /**
     * 正文内容不操过1000字，过950提醒
     *
     * @param surplusSize
     */
    private void setContentSizeRemind(int surplusSize) {
        List<SegmentedStringMode> modeList = new ArrayList<>();
        SegmentedStringMode modeBlack = new SegmentedStringMode(surplusSize + "/", R.dimen.app_body_size_2, R.color.app_black);
        SegmentedStringMode modeGrey = new SegmentedStringMode("1000", R.dimen.app_body_size_2, R.color.grey);
        modeList.add(modeBlack);
        modeList.add(modeGrey);
        write_thread_content_size.setText(DataUtils.getSegmentedDisplaySs(modeList));
    }

    /**
     * 帖子内容改变监听事件
     */
    private void initListener() {

        DataUtils.addDelSmileyListener(write_thread_content, smileyBeanList, new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int surplusSize = start + count;
                if (surplusSize < 950) {
                    write_thread_content_size.setVisibility(View.GONE);
                } else {
                    write_thread_content_size.setVisibility(View.VISIBLE);
                    setContentSizeRemind(1000 - surplusSize);
                }

                DataUtils.noInputEmoji(write_thread_content, s, start, before, count);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        DataUtils.noInputEmojiAddListener(write_thread_title);


        write_thread_title.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hintFaceView();
                return false;
            }
        });

        write_thread_content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hintFaceView();
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case requestLocation:
                    String cityname = data.getStringExtra("cityname");
                    String location = data.getStringExtra("location");
                    if (TextUtils.equals(location, getString(R.string.not_display_position))) {
                        write_thread_location_name.setText("");
                        del_location_btn.setVisibility(View.GONE);
                    } else {
                        WidgetUtils.setText(write_thread_location_name, cityname + location);
                        del_location_btn.setVisibility(View.VISIBLE);
                    }
                    break;

                case requestForumChoose:
                    jumpActivitySetResult(null);
                    break;

                case requestFlyLocation:
                    mPresenter.locationBean = (LocationListBean.LocationBean) data.getSerializableExtra("data");
                    mPresenter.tagsList = (List<TagBean>) data.getSerializableExtra("tags");
                    mPresenter.star = data.getFloatExtra("star", 0);
                    mPresenter.flight = data.getStringExtra("flight");
                    mPresenter.flightid = data.getStringExtra("flightid");
                    mPresenter.airportid = data.getStringExtra("airport");


                    if (mPresenter.locationBean != null) {
                        mPresenter.airportid = mPresenter.locationBean.getId();
                        WidgetUtils.setText(write_thread_location_name, mPresenter.locationBean.getName());
                        del_location_btn.setVisibility(View.VISIBLE);
                    } else {
                        cityname = data.getStringExtra("cityname");
                        location = data.getStringExtra("location");
                        if (TextUtils.equals(location, getString(R.string.not_display_position))) {
                            write_thread_location_name.setText("");
                            del_location_btn.setVisibility(View.GONE);
                        } else {
                            WidgetUtils.setText(write_thread_location_name, cityname + location);
                            del_location_btn.setVisibility(View.VISIBLE);
                        }
                    }

                    bindRatingLayout(mPresenter.locationBean, mPresenter.star);

                    break;
            }
        }
    }

    @Override
    protected WriteThreadPresenter createPresenter() {
        return new WriteThreadPresenter();
    }

    @Event({R.id.toolbar_left, R.id.toolbar_right, R.id.write_thread_location_name, R.id.biaoqing, R.id.tupian, R.id.write_thread_topic_name, R.id.to_rating_icon, R.id.del_location_btn})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left:
                onBackPressed();
                break;

            case R.id.toolbar_right:
//                mPresenter.actionSendThread(write_thread_title.getText().toString(), write_thread_content.getText().toString(), write_thread_location_name.getText().toString());
                if (TextUtils.isEmpty(write_thread_title.getText().toString())) {
                    ToastUtils.showToast("您的帖子还没有标题哦");
                    return;
                }
                if (TextUtils.isEmpty(write_thread_content.getText().toString())) {
                    ToastUtils.showToast("您的帖子还没有内容哦");
                    return;
                }
                Bundle b = new Bundle();
                b.putString(WriteThreadPresenter.BUNDLE_FROM_TYPE, mPresenter.from_type);
                b.putString("subject", write_thread_title.getText().toString());
                b.putString("message", write_thread_content.getText().toString());
                b.putString("location", write_thread_location_name.getText().toString());
                b.putString(WriteThreadPresenter.BUNDLE_FID_1, mPresenter.fid1);
                b.putString(WriteThreadPresenter.BUNDLE_FID_2, mPresenter.fid2);
                b.putString(WriteThreadPresenter.BUNDLE_FID_3, mPresenter.fid3);
                b.putString(WriteThreadPresenter.BUNDLE_TYPEID, mPresenter.typeid);

                if (mPresenter.locationBean != null) {
                    b.putSerializable("locationBean", mPresenter.locationBean);
                    b.putFloat("star", mPresenter.star);
                    if (mPresenter.tagsList != null) {
                        b.putSerializable("tags", (Serializable) mPresenter.tagsList);
                        b.putString("flight", mPresenter.flight);
                        b.putString("flightid", mPresenter.flightid);
                        b.putString("airportid", mPresenter.airportid);
                    }
                }
                b.putString("collectionid", mPresenter.collectionid);


                jumpActivityForResult(ForumChooseActivity.class, b, requestForumChoose);
                break;

            case R.id.write_thread_location_name:

                if (mPresenter.locationListBean == null || (DataUtils.isEmpty(mPresenter.locationListBean.getAirports()) && DataUtils.isEmpty(mPresenter.locationListBean.getHotels()) && DataUtils.isEmpty(mPresenter.locationListBean.getLounges()))) {
                    jumpActivityForResult(LocationListActivity.class, null, requestLocation);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", mPresenter.locationListBean);
                    jumpActivityForResult(MyLocationActivity.class, bundle, requestFlyLocation);
                }
                break;

            case R.id.biaoqing:
                if (biaoqingView.getVisibility() == View.VISIBLE) {
                    hintFaceView();
                } else {
                    choose_look_img.setImageResource(R.drawable.jianpan);
                    biaoqingView.setTag(true);
                    hideSoftInput(getCurrentFocus().getWindowToken());
                    biaoqingView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tupian:
                Bundle bundle = new Bundle();
                bundle.putString(AlbumPresenter.BUNDLE_FROM_TYPE, AlbumPresenter.BUNDLE_FROM_MAJOR_THREAD_FIRST);
                bundle.putInt(AlbumPresenter.BUNDLE_NEED_SIZE, 30);
                bundle.putString(BUNDLE_TITLE, write_thread_title.getText().toString());
                bundle.putString(BUNDLE_CONTENT, write_thread_content.getText().toString());
                bundle.putString(BUNDLE_LOCATION, write_thread_location_name.getText().toString());

                if (mPresenter.locationBean != null) {
                    bundle.putSerializable("locationBean", mPresenter.locationBean);
                    bundle.putFloat("star", mPresenter.star);
                    if (mPresenter.tagsList != null) {
                        StringBuffer tagName = new StringBuffer();
                        StringBuffer utagName = new StringBuffer();
                        for (TagBean tag : mPresenter.tagsList) {
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
                        bundle.putSerializable("tagsList", (Serializable) mPresenter.tagsList);
                        bundle.putString("tags", tagName.toString());
                        bundle.putString("utags", utagName.toString());
                        bundle.putString("flight", mPresenter.flight);
                        bundle.putString("flightid", mPresenter.flightid);
                    }
                }
                bundle.putString("collectionid", mPresenter.collectionid);
                bundle.putString("topicName", mPresenter.topicName);

                jumpActivityForResult(AlbumActivity.class, bundle, 1);
                break;

            case R.id.write_thread_topic_name:

                break;

            case R.id.to_rating_icon:
                bundle = new Bundle();
                bundle.putSerializable("data", mPresenter.locationBean);
                bundle.putFloat("star", ratingbar.getStarMark());
                bundle.putSerializable("tags", (Serializable) mPresenter.tagsList);
                bundle.putString("flight", mPresenter.flight);
                jumpActivityForResult(RatingActivity.class, bundle, requestFlyLocation);
                break;

            case R.id.del_location_btn:
                write_thread_location_name.setText("");
                del_location_btn.setVisibility(View.GONE);
                mPresenter.locationBean = null;
                rating_layout.setVisibility(View.GONE);
                break;

        }
    }

    private void hintFaceView() {
        if (biaoqingView.getVisibility() == View.VISIBLE) {
            choose_look_img.setImageResource(R.drawable.reply_face);
            biaoqingView.setVisibility(View.GONE);
            InputMethodManager m = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    public void onBackPressed() {
        if (TextUtils.isEmpty(write_thread_title.getText().toString()) && TextUtils.isEmpty(write_thread_content.getText().toString())) {
            super.onBackPressed();
        } else {
            showDialogIsSaveDraft();
        }
    }


    /**
     * 是否保存草稿
     */
    @Override
    public void showDialogIsSaveDraft() {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(this);
        builder.setTitle(null);
        builder.setMessage("是否保存草稿");
        builder.setNegativeButton("删除草稿", new MyAlertDialog.OnAlertViewClickListener() {
            @Override
            public void OnAlertViewClick() {
                mPresenter.deleteDraft();
                jumpActivitySetResult(null);
            }
        });

        builder.setPositiveButton("保存草稿", new MyAlertDialog.OnAlertViewClickListener() {
            @Override
            public void OnAlertViewClick() {
                mPresenter.saveDraft(write_thread_title.getText().toString(), write_thread_content.getText().toString(), write_thread_location_name.getText().toString());
            }
        });
        builder.create().show();
    }

    @Override
    public void setTvTitleContent(String title, String content, String location, String topicName) {
        WidgetUtils.setText(write_thread_title, title);
        WidgetUtils.setText(write_thread_content, content);
        WidgetUtils.setText(write_thread_location_name, location);
        WidgetUtils.setText(write_thread_topic_name, topicName);
        WidgetUtils.setVisible(write_thread_topic_name, !TextUtils.isEmpty(topicName));

    }

    @Override
    public void bindRatingLayout(LocationListBean.LocationBean locationBean, float star) {

        if (locationBean == null || star == 0) {
            rating_layout.setVisibility(View.GONE);
        } else {
            rating_layout.setVisibility(View.VISIBLE);

            DataUtils.downloadPicture(rating_user_face, UserManger.getUserInfo().getFace(), R.drawable.def_face_2);

            if (TextUtils.equals(locationBean.getType(), "hotel")) {
                rating_use_info.setText("我住过");
            } else if (TextUtils.equals(locationBean.getType(), "airport")) {
                rating_use_info.setText("我乘坐过");
            } else if (TextUtils.equals(locationBean.getType(), "lounge")) {
                rating_use_info.setText("我体验过");
            }

            if (star == 5) {
                ratingbar_tv.setText("力荐");
            } else if (star == 4) {
                ratingbar_tv.setText("推荐");
            } else if (star == 3) {
                ratingbar_tv.setText("还行");
            } else if (star == 2) {
                ratingbar_tv.setText("较差");
            } else if (star == 1) {
                ratingbar_tv.setText("很差");
            }
            ratingbar.setStarMark(star);
        }

    }

    List<SmileyBean> smileyBeanList = new ArrayList<>();

    public void onEventMainThread(SmileyBean bean) {
        if (TextUtils.equals(bean.getImage(), "close")) {
            ViewTools.editTextDelete(write_thread_content);
        } else {
            smileyBeanList.add(bean);
            write_thread_content.getText().insert(write_thread_content.getSelectionStart(), bean.getCode());
        }
    }

    public void onEventMainThread(TagEvent tagEvent) {
        if (tagEvent.getTag() == TagEvent.TAG_FINISH_WRITETHREADACTIVITY) {
            mPresenter.deleteDraft();
            finish();
        }
    }

}
