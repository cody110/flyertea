package com.ideal.flyerteacafes.ui.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.UmengShare;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.entity.MyMedalsSubBean;
import com.ideal.flyerteacafes.model.loca.ThreadBottomInfo;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.ideal.flyerteacafes.utils.xutils.XutilsHelp;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2017/10/20.
 */

public class ShareMedalsPopupWindow extends PopupWindow {


    private Activity activity;
    private List<ThreadBottomInfo> dataList = new ArrayList<>();

    @ViewInject(R.id.share_gridview)
    GridView share_gridview;
    @ViewInject(R.id.share_layout)
    LinearLayout share_layout;
    @ViewInject(R.id.face_img)
    ImageView face_img;
    @ViewInject(R.id.username_tv)
    TextView username_tv;
    @ViewInject(R.id.user_group_tv)
    TextView user_group_tv;
    @ViewInject(R.id.medals_igv)
    ImageView medals_igv;
    @ViewInject(R.id.medals_name)
    TextView medals_name;

    public ShareMedalsPopupWindow(Activity context, MyMedalsSubBean medalsSubBean) {
        activity = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View mMenuView = inflater.inflate(R.layout.pop_share_medals, null);
        x.view().inject(this, mMenuView);


        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);


        if (UserManger.getUserInfo() != null) {
            x.image().bind(face_img, UserManger.getUserInfo().getFace(), XutilsHelp.image_FIT_XY);
            WidgetUtils.setText(username_tv, UserManger.getUserInfo().getMember_username());
            WidgetUtils.setText(user_group_tv, UserManger.getUserInfo().getGroupname());
        }
        if (medalsSubBean != null) {
            DataUtils.downloadPicture(medals_igv, medalsSubBean.getImage(), R.drawable.icon_def);
            WidgetUtils.setText(medals_name, medalsSubBean.getName());
        }

        initShareData();
        bindAdapter();
    }


    /**
     * 初始化分享数据
     */
    private void initShareData() {
        ThreadBottomInfo wechat_moment = new ThreadBottomInfo("朋友圈", R.drawable.share_wechat_moment, ThreadBottomInfo.TYPE_SHARE, SHARE_MEDIA.WEIXIN_CIRCLE);
        ThreadBottomInfo wechat_friend = new ThreadBottomInfo("微信好友", R.drawable.share_wechat, ThreadBottomInfo.TYPE_SHARE, SHARE_MEDIA.WEIXIN);
        ThreadBottomInfo qq_friend = new ThreadBottomInfo("QQ好友", R.drawable.share_qq, ThreadBottomInfo.TYPE_SHARE, SHARE_MEDIA.QQ);
        ThreadBottomInfo sine = new ThreadBottomInfo("新浪微博", R.drawable.share_sine, ThreadBottomInfo.TYPE_SHARE, SHARE_MEDIA.SINA);

        dataList.add(wechat_moment);
        dataList.add(wechat_friend);
        dataList.add(qq_friend);
        dataList.add(sine);

    }


    private void bindAdapter() {
        share_gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        share_gridview.setAdapter(new CommonAdapter<ThreadBottomInfo>(activity, dataList, R.layout.thread_share_item) {
            @Override
            public void convert(ViewHolder holder, ThreadBottomInfo threadBottomInfo) {
                holder.setText(R.id.share_item_title, threadBottomInfo.getTitle());
                holder.setImageResource(R.id.share_item_icon, threadBottomInfo.getResId());
            }
        });
    }

    @Event(R.id.btn_close)
    private void click(View v) {
        dismiss();
    }

    @Event(value = R.id.share_gridview, type = AdapterView.OnItemClickListener.class)
    private void gridViewItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bitmap bitmap = BitmapTools.viewToBitmap(share_layout);
        ThreadBottomInfo info = dataList.get(position);
        UmengShare.shareMedail(activity, bitmap, info.getPlatform());
    }

}
