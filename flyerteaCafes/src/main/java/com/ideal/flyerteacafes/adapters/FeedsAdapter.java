package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.model.entity.FeedsBean;
import com.ideal.flyerteacafes.model.entity.ReplysBean;
import com.ideal.flyerteacafes.model.loca.SegmentedStringMode;
import com.ideal.flyerteacafes.ui.controls.NoScrollGridView;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.UploadTask;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2015/11/19.
 */
public class FeedsAdapter extends CommonAdapter<FeedsBean> {




    public interface itemWidgetClick {

        /**
         * 评论用户名点击
         * @param uid
         */
        void toUserInfoActivity(String uid);

        /**
         * 头像点击
         * @param position
         */
        void faceClick(int position);

        /**
         * 点赞
         * @param position
         */
        void zanClick(int position);

        /**
         * gridview 里图片点击
         * @param
         * @param position 图片位置下标
         */
        void gridViewItemClick(int position,FeedsBean bean);

        /**
         * 点击话题标签
         * @param topicId
         * @param topicName
         */
        void toTopicList(String topicId, String topicName);

        /**
         * 响应整个item点击事件
         * @param posttion
         */
        void itemClick(int posttion);
    }

    private itemWidgetClick IClick;

    public void setItemWidgetClick(itemWidgetClick IClick) {
        this.IClick = IClick;
    }

    private Context context;
    private int img_width;
    private int gd_width;
    private int comment_li_distance;

    public FeedsAdapter(Context context, List<FeedsBean> datas, int layoutId) {
        super(context, datas, layoutId);
        this.context = context;
        int w_screen= SharedPreferencesString.getInstances().getIntToKey("w_screen");
        gd_width=w_screen-context.getResources().getDimensionPixelOffset(R.dimen.listview_distance)*2;
        img_width=gd_width/3*2;
        comment_li_distance=15;
    }

    @Override
    public void convert(final ViewHolder holder, final FeedsBean feedsBean) {

        holder.setOnClickListener(R.id.zb_list_item_root, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IClick.itemClick(holder.getPosition());
            }
        });

        holder.setOnClickListener(R.id.zhibo_list_body, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IClick.itemClick(holder.getPosition());
            }
        });

        holder.setOnClickListener(R.id.zhibo_list_zan_text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IClick.zanClick(holder.getPosition());
            }
        });

        holder.setOnClickListener(R.id.zhibo_list_face, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IClick.faceClick(holder.getPosition());
            }
        });

        String face = UploadTask.getSplit(feedsBean.getUser_id());
        holder.setImageUrl(R.id.zhibo_list_face, face, R.drawable.def_face);
        holder.setText(R.id.zhibo_list_username, feedsBean.getUser_name());
        String time = DataUtils.getTimeFormatToBiaozhun(String.valueOf(feedsBean.getCreated_at()));
        holder.setText(R.id.zhibo_list_time, time);
        TextView zanTv = holder.getView(R.id.zhibo_list_zan_text);

        if (feedsBean.isLiked()) {
            Drawable nav_up = context.getResources().getDrawable(R.drawable.zhibo_zan_lv);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            zanTv.setCompoundDrawables(nav_up, null, null, null);
            zanTv.setTextColor(context.getResources().getColor(R.color.app_bg_title));
        } else {
            Drawable nav_up = context.getResources().getDrawable(R.drawable.zhibo_zan_bai);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            zanTv.setCompoundDrawables(nav_up, null, null, null);
            zanTv.setTextColor(context.getResources().getColor(R.color.app_body_grey));
        }

        if (TextUtils.isEmpty(feedsBean.getFeed_topic_title())) {
            holder.setText(R.id.zhibo_list_body, feedsBean.getContent());
        } else {
            holder.setText(R.id.zhibo_list_body, setContent(feedsBean.getFeed_topic_title(), feedsBean.getFeed_topic_id(), feedsBean.getContent()));
        }

        TextView contentTv = holder.getView(R.id.zhibo_list_body);
        contentTv.setMovementMethod(LinkMovementMethod.getInstance());




        TextView bodyTv = holder.getView(R.id.zhibo_list_body);
        bodyTv.setMaxLines(4);
        bodyTv.setEllipsize(TextUtils.TruncateAt.END);

        if (!DataUtils.isEmpty(feedsBean.getAttachment_urls())) {
            if (feedsBean.getUrlList() == null) {
                List<String> urlList = new ArrayList<>();
                String urls = feedsBean.getAttachment_urls();
                if (!DataUtils.isEmpty(urls)) {
                    String[] urlArray = urls.split("\\$\\$");
                    for (int i = 0; i < urlArray.length; i++) {
                        if (i == 9)
                            break;
                        urlList.add(urlArray[i]);
                    }
                }
                feedsBean.setUrlList(urlList);
            }

            if (feedsBean.getUrlList() != null) {
                if (feedsBean.getUrlList().size() == 1) {
                    WidgetUtils.setWidth(holder.getView(R.id.zhibo_list_one_by_img_layout),img_width);
                    holder.setVisible(R.id.zhibo_list_one_by_img_layout, true);
                    holder.setVisible(R.id.zhibo_list_gridview, false);
                    if(feedsBean.getUrlList().get(0).indexOf("http://app.flyert.com/flyertea-app")!=-1)
                        holder.setImageUrl(R.id.zhibo_list_one_by_img, feedsBean.getUrlList().get(0) + "!autox300", R.drawable.post_def);
                    else
                        holder.setImageUrl(R.id.zhibo_list_one_by_img, feedsBean.getUrlList().get(0), R.drawable.post_def);
                    holder.setOnClickListener(R.id.zhibo_list_one_by_img, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (IClick != null) {
                                IClick.gridViewItemClick( 0,feedsBean);
                            }
                        }
                    });

                    if(feedsBean.getAttachment_type()== Marks.FEED_SHIPIN){
                        holder.setVisible(R.id.zhibo_list_video_play,true);
                    }else{
                        holder.setVisible(R.id.zhibo_list_video_play,false);
                    }

                } else {
                    holder.setVisible(R.id.zhibo_list_one_by_img_layout, false);
                    holder.setVisible(R.id.zhibo_list_gridview, true);
                    final NoScrollGridView gridView = holder.getView(R.id.zhibo_list_gridview);
                    if (feedsBean.getUrlList().size() == 4 ) {
                        WidgetUtils.setWidth(gridView,img_width);
                        gridView.setNumColumns(2);
                    } else {
                        WidgetUtils.setWidth(gridView,gd_width);
                        gridView.setNumColumns(3);
                    }
                    CommonAdapter<String> adapter = new CommonAdapter<String>(context, feedsBean.getUrlList(), R.layout.gridview_zhibo_tupian) {
                        @Override
                        public void convert(ViewHolder holder, String url) {
                            holder.setImageUrl(R.id.zhibo_gridview_img, url + "!250x250", R.drawable.post_def);
                        }
                    };
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (IClick != null) {
                                IClick.gridViewItemClick( position,feedsBean);
                            }
                        }
                    });
                }
            }
        }
        if (!DataUtils.isEmpty(feedsBean.getLocation())) {
            holder.setText(R.id.zhibo_list_location, feedsBean.getLocation());
            holder.setVisible(R.id.zhibo_list_location, true);
        } else {
            holder.setVisible(R.id.zhibo_list_location, false);
        }
        holder.setText(R.id.zhibo_list_zan_text, String.valueOf(feedsBean.getLike_num()));
        holder.setText(R.id.zhibo_list_comment_num_text, String.valueOf(feedsBean.getComment_num()));

        holder.setVisible(R.id.zhibo_list_tag, !TextUtils.isEmpty(feedsBean.getTags()));
        holder.setText(R.id.zhibo_list_tag, feedsBean.getTags());


        LinearLayout zhibo_list_comment_layout = holder.getView(R.id.zhibo_list_comment_layout);
        zhibo_list_comment_layout.removeAllViews();
        int cSize = (DataTools.getListSize(feedsBean.getCommentlist()) > 3) ? 3 : DataTools.getListSize(feedsBean.getCommentlist());

        if(cSize==0){
            holder.setVisible(R.id.zhibo_list_comment_layout_all,false);
        }else{
            holder.setVisible(R.id.zhibo_list_comment_layout_all,true);
        }
        for (int i = 0; i < cSize; i++) {
            TextView tv=new TextView(context);
            zhibo_list_comment_layout.addView(tv);
            if(i!=0){
                tv.setPadding(0,comment_li_distance,0,0);
            }
            ReplysBean replys = feedsBean.getCommentlist().get(i);
            List<SegmentedStringMode> modeList=new ArrayList<>();
            if(replys.getDirect_reply()==1){
                SegmentedStringMode modeAuthor=new SegmentedStringMode(replys.getFrom_user_name(),R.dimen.zb_list_comment_size,R.color.grey,new UnameClick(replys.getFrom_user_id()));
                SegmentedStringMode modeHui=new SegmentedStringMode("回复",R.dimen.zb_list_comment_size,R.color.app_body_black);
                SegmentedStringMode modeRen=new SegmentedStringMode(replys.getTo_user_name()+":",R.dimen.zb_list_comment_size,R.color.grey,new UnameClick(replys.getFrom_user_id()));
                modeList.add(modeAuthor);
                modeList.add(modeHui);
                modeList.add(modeRen);
            }else{
                SegmentedStringMode modeAuthor=new SegmentedStringMode(replys.getFrom_user_name()+":",R.dimen.zb_list_comment_size,R.color.grey,new UnameClick(replys.getFrom_user_id()));
                modeList.add(modeAuthor);
            }
            SegmentedStringMode modeContent=new SegmentedStringMode(replys.getContent(),R.dimen.zb_list_comment_size,R.color.app_body_black);
            modeList.add(modeContent);

            tv.setText(DataUtils.getSegmentedDisplaySs(modeList));
            tv.setMovementMethod(LinkMovementMethod.getInstance());

        }

        if (feedsBean.getComment_num() >= 3) {
            TextView tv = new TextView(context);
            tv.setTextColor(context.getResources().getColor(R.color.app_bg_title));
            tv.setText("查看更多评论>>");
            zhibo_list_comment_layout.addView(tv);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimensionPixelSize(R.dimen.app_body_size_3));
            tv.setPadding(0,comment_li_distance,0,0);
        }


    }

    /**
     * 评论用户名点击事件
     */
    class UnameClick extends ClickableSpan {

        String uid;

        public UnameClick(int uid){
            this.uid=uid+"";
        }

        @Override
        public void onClick(View widget) {
            if(IClick!=null)
                IClick.toUserInfoActivity(uid);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setColor(context.getResources().getColor(R.color.grey));
        }

    }

    /**
     * 话题内容设置
     * @param name
     * @param topicId
     * @param content
     * @return
     */
    public SpannableString setContent(final String name, final String topicId, String content) {
        String topicName = "#" + name + "#";

        List<SegmentedStringMode> modeList=new ArrayList<>();

        ClickableSpan clickableSpan = null;
        if (topicClick) {
            clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    IClick.toTopicList(topicId, name);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            };

        }

        SegmentedStringMode modeTopicName=new SegmentedStringMode(topicName,R.dimen.app_body_size_2,R.color.app_bg_title,clickableSpan);
        SegmentedStringMode modeContent=new SegmentedStringMode(content, R.dimen.app_body_size_2, R.color.app_body_black,null);

        modeList.add(modeTopicName);
        modeList.add(modeContent);

        return DataUtils.getSegmentedDisplaySs(modeList);
    }

    boolean topicClick = false;

    public void setTopicClick(boolean flag) {
        topicClick = flag;
        notifyDataSetChanged();
    }


}
