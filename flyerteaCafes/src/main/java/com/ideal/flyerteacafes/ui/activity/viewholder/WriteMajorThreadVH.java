package com.ideal.flyerteacafes.ui.activity.viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.WriteThreadAdapter;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.entity.LocationListBean;
import com.ideal.flyerteacafes.model.entity.SmileyBean;
import com.ideal.flyerteacafes.model.loca.SegmentedStringMode;
import com.ideal.flyerteacafes.ui.controls.StarBar;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.TvDrawbleUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;
import com.ideal.flyerteacafes.utils.tools.ViewTools;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2018/1/12.
 */

public class WriteMajorThreadVH extends BaseViewHolder {


    public WriteMajorThreadVH.IActionListener iActionListener;


    public interface IActionListener {
        /**
         * 点击
         */
        void click(View v);

        /**
         * 继续上传
         */
        void continueUploading();

    }


    @ViewInject(R.id.thread_content_listview)
    ListView thread_content_listview;
    @ViewInject(R.id.write_major_menu)
    View write_major_menu;
    @ViewInject(R.id.write_major_menu_layout)
    View write_major_menu_layout;
    @ViewInject(R.id.write_major_preview)
    View write_major_preview;
    @ViewInject(R.id.chat_biaoqing_layout)
    View biaoqingView;
    @ViewInject(R.id.choose_look_img)
    ImageView choose_look_img;
    @ViewInject(R.id.write_thread_content)
    EditText write_thread_content;
    @ViewInject(R.id.write_thread_title)
    EditText write_thread_title;
    @ViewInject(R.id.upload_img_layout)
    View upload_img_layout;
    @ViewInject(R.id.upload_img_text)
    TextView upload_img_text;
    @ViewInject(R.id.write_thread_location_name)
    TextView write_thread_location_name;

    @ViewInject(R.id.del_location_btn)
    ImageView del_location_btn;
    @ViewInject(R.id.write_thread_topic_name)
    TextView write_thread_topic_name;
    @ViewInject(R.id.video_img)
    View video_img;
    @ViewInject(R.id.write_major_send)
    TextView write_major_send;
    @ViewInject(R.id.del_location_btn_line)
    View del_location_btn_line;


    @Event({R.id.toolbar_left, R.id.write_major_send, R.id.write_major_menu, R.id.write_major_preview, R.id.add_text, R.id.add_img, R.id.choose_look_img, R.id.write_thread_location_name, R.id.del_location_btn, R.id.to_rating_icon, R.id.video_img})
    private void click(final View v) {
        if (iActionListener != null) iActionListener.click(v);
    }


    //标题
    public String getThreadTitle() {
        return write_thread_title.getText().toString();
    }

    //正文
    public String getThreadContent() {
        return write_thread_content.getText().toString();
    }

    //位置
    public String getLocationName() {
        return write_thread_location_name.getText().toString();
    }


    public WriteMajorThreadVH(View view, IActionListener iActionListener) {
        x.view().inject(this, view);
        this.iActionListener = iActionListener;
    }


    /**
     * 初始化
     *
     * @param smileyBeanList
     */
    public void init(List<SmileyBean> smileyBeanList) {
        biaoqingView.setVisibility(View.GONE);

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

        DataUtils.addDelSmileyListener(write_thread_content, smileyBeanList);

        WidgetUtils.setVisible(video_img, UserManger.isWriteVideoThread());

        if (UserManger.isWriteVideoThread()) {
            if (SharedPreferencesString.getInstances().isFirstVideo()) {
                showFirstVideo(video_img);
            }
        }

        write_major_send.setEnabled(false);
        write_major_menu.setEnabled(false);
        write_thread_title.addTextChangedListener(textWatcher);
        write_thread_content.addTextChangedListener(textWatcher);

    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(write_thread_title.getText()) || TextUtils.isEmpty(write_thread_content.getText())) {
                write_major_send.setEnabled(false);
                write_major_menu.setEnabled(false);
                write_major_send.setBackground(mContext.getResources().getDrawable(R.drawable.light_grey_frame));
                write_major_send.setTextColor(mContext.getColor(R.color.text_light_grey));
            } else {
                write_major_send.setEnabled(true);
                write_major_menu.setEnabled(true);
                write_major_send.setBackground(mContext.getResources().getDrawable(R.drawable.blue_frame));
                write_major_send.setTextColor(mContext.getColor(R.color.text_blue));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /**
     * 绑定adapter
     *
     * @param adapter
     */
    public void bindAdapter(WriteThreadAdapter adapter) {
        thread_content_listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ViewTools.setListViewHeightBasedOnChildren(thread_content_listview);
    }


    /**
     * 绑定数据
     *
     * @param title
     * @param subject
     * @param location
     * @param topicName
     */
    public void bindThreadTitleSubjectLocation(String title, String subject, String location, String topicName) {
        WidgetUtils.setText(write_thread_title, title);
        WidgetUtils.setText(write_thread_content, subject);
        WidgetUtils.setText(write_thread_location_name, location);
        WidgetUtils.setText(write_thread_topic_name, topicName);
        WidgetUtils.setVisible(del_location_btn, !TextUtils.isEmpty(location));
        WidgetUtils.setVisible(write_thread_topic_name, !TextUtils.isEmpty(topicName));
    }


    /**
     * 绑定表情
     *
     * @param bean
     */
    public void bindSmiley(SmileyBean bean) {
        if (TextUtils.equals(bean.getImage(), "close")) {
            ViewTools.editTextDelete(write_thread_content);
        } else {
            write_thread_content.getText().insert(write_thread_content.getSelectionStart(), bean.getCode());
        }
    }

    /**
     * 上传进度绑定
     *
     * @param allCount
     * @param successCount
     * @param errorCount
     */
    public void bindUploadProgress(final int allCount, final int successCount, final int errorCount) {
        if (allCount == 0) {
            upload_img_layout.setVisibility(View.GONE);
        } else {
            upload_img_layout.setVisibility(View.VISIBLE);


            if (successCount == allCount) {
                upload_img_text.setText("图片上传成功!" + successCount + "/" + allCount);
                upload_img_text.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        upload_img_layout.setVisibility(View.GONE);
                    }
                }, 2000);
            } else if (successCount + errorCount < allCount) {
                upload_img_text.setText("图片上传中..." + successCount + "/" + allCount);
            } else {
                SegmentedStringMode mode1 = new SegmentedStringMode(errorCount + "张图片上传失败，", R.dimen.zb_list_comment_size, R.color.content, null);
                SegmentedStringMode mode2 = new SegmentedStringMode("继续上传", R.dimen.zb_list_comment_size, R.color.red, new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        if (iActionListener != null) iActionListener.continueUploading();
                        if (successCount == allCount) {
                            upload_img_text.setText("图片上传成功!" + successCount + "/" + allCount);
                        } else {
                            upload_img_text.setText("图片上传中..." + successCount + "/" + allCount);
                        }
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        ds.setColor(mContext.getResources().getColor(R.color.red));
                    }
                });
                List<SegmentedStringMode> list = new ArrayList<>();
                list.add(mode1);
                list.add(mode2);
                upload_img_text.setText(DataUtils.getSegmentedDisplaySs(list));
                upload_img_text.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }

    /**
     *
     */
    public void hintFaceView() {
        if (biaoqingView.getVisibility() == View.VISIBLE) {
            choose_look_img.setImageResource(R.drawable.reply_face);
            biaoqingView.setVisibility(View.GONE);
            InputMethodManager m = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void bindMapLocation(Intent data) {
        String cityname = data.getStringExtra("cityname");
        String location = data.getStringExtra("location");
        if (TextUtils.equals(location, mContext.getString(R.string.not_display_position))) {
            WidgetUtils.setText(write_thread_location_name, "");
            TvDrawbleUtils.setTextDrawble(write_thread_location_name, R.mipmap.location_grey, TvDrawbleUtils.LEFT);
        } else {
            WidgetUtils.setText(write_thread_location_name, cityname + location);
            TvDrawbleUtils.setTextDrawble(write_thread_location_name, R.mipmap.location_blue, TvDrawbleUtils.LEFT);
        }
    }

    /**
     * 绑定关联位置
     *
     * @param locationBean
     * @param data
     */
    public void bindFlyLocation(LocationListBean.LocationBean locationBean, Intent data) {
        if (locationBean != null) {

            float star = data.getFloatExtra("star", 0);
            String ratingText = "";
            if (star == 5) {
                ratingText = "[力荐]";
            } else if (star == 4) {
                ratingText = "[推荐]";
            } else if (star == 3) {
                ratingText = "[还行]";
            } else if (star == 2) {
                ratingText = "[较差]";
            } else if (star == 1) {
                ratingText = "[很差]";
            }

            TvDrawbleUtils.setTextDrawble(write_thread_location_name, R.mipmap.location_blue, TvDrawbleUtils.LEFT);
            WidgetUtils.setText(write_thread_location_name, ratingText + locationBean.getName());
            del_location_btn.setVisibility(View.VISIBLE);
            del_location_btn_line.setVisibility(View.VISIBLE);
        } else {
            String cityname = data.getStringExtra("cityname");
            String location = data.getStringExtra("location");
            if (TextUtils.equals(location, mContext.getString(R.string.not_display_position))) {
                write_thread_location_name.setText("");
                del_location_btn.setVisibility(View.GONE);
                del_location_btn_line.setVisibility(View.GONE);
                TvDrawbleUtils.setTextDrawble(write_thread_location_name, R.mipmap.location_grey, TvDrawbleUtils.LEFT);
            } else {

                WidgetUtils.setText(write_thread_location_name, cityname + location);
                TvDrawbleUtils.setTextDrawble(write_thread_location_name, R.mipmap.location_blue, TvDrawbleUtils.LEFT);
                del_location_btn.setVisibility(View.VISIBLE);
                del_location_btn_line.setVisibility(View.VISIBLE);
            }
        }
    }


    /**
     * 排序
     */
    public void bindMajorSort() {
        write_major_preview.setVisibility(View.VISIBLE);
        write_major_menu_layout.setVisibility(View.GONE);
    }

    /**
     *
     */
    public void bindMajorPreview() {
        write_major_preview.setVisibility(View.GONE);
        write_major_menu_layout.setVisibility(View.VISIBLE);
    }


    /**
     * 删除位置
     */
    public void bindDeleteLocation() {
        write_thread_location_name.setText("");
        del_location_btn.setVisibility(View.GONE);
        del_location_btn_line.setVisibility(View.GONE);
        TvDrawbleUtils.setTextDrawble(write_thread_location_name, R.mipmap.location_grey, TvDrawbleUtils.LEFT);
    }


    public boolean bindChooseLookImg() {
        if (biaoqingView.getVisibility() == View.VISIBLE) {
            hintFaceView();
            return true;
        } else {
            choose_look_img.setImageResource(R.drawable.jianpan);
            biaoqingView.setTag(true);
            biaoqingView.setVisibility(View.VISIBLE);
            return false;
        }


    }


    public void showFirstVideo(final View v) {
        final ImageView view = (ImageView) View.inflate(mContext, R.layout.pop_first_remind_video, null);
        v.post(new Runnable() {
            @Override
            public void run() {
                view.setImageResource(R.drawable.first_remind_video);
                //获取PopupWindow中View的宽高
                view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                int measuredWidth = view.getMeasuredWidth();
                int measuredHeight = view.getMeasuredHeight();
                PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);//popupwindow设置焦点
//                popupWindow.setBackgroundDrawable(new ColorDrawable(0x000000));//设置背景
                popupWindow.setOutsideTouchable(true);//点击外面窗口消失
                // popupWindow.showAsDropDown(v,0,0);
                //获取点击View的坐标
                int[] location = new int[2];
                v.getLocationOnScreen(location);
//        popupWindow.showAsDropDown(v);//在v的下面
                //显示在上方
                popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0] + v.getWidth() / 2 - DensityUtil.dip2px(123) / 2, location[1] - measuredHeight);
                //显示在正上方
//        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - measuredWidth / 2, location[1]-measuredHeight);
//        //显示在左方
//        popupWindow.showAtLocation(v,Gravity.NO_GRAVITY,location[0]-popupWindow.getWidth(),location[1]);
//        //显示在下方
//        popupWindow.showAtLocation(v,Gravity.NO_GRAVITY,location[0]+v.getWidth(),location[1]);
            }
        });

    }

}
