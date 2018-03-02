package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.TypeBaseBean;
import com.ideal.flyerteacafes.model.loca.TuwenInfo;
import com.ideal.flyerteacafes.model.loca.UploadImgInfo;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;
import com.upyun.upplayer.widget.UpVideoView2;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by fly on 2017/3/13.
 */

public class WriteThreadAdapter extends CommonAdapter<TuwenInfo> {

    IClick iClick;

    public interface IClick {
        void deleteClick(int pos);

        void imgClick(int pos);

        void videoClick(int pos);

        void textClick(int pos);

        void itemUp(int pos);

        void itemDown(int pos);

        void reStartUpload(int pos);
    }

    public void setIClick(IClick iClick) {
        this.iClick = iClick;
    }


    ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.FIT_XY)
            .setLoadingDrawableId(R.drawable.icon_def)//加载中默认显示图片
            .setFailureDrawableId(R.drawable.icon_def)//加载失败后默认显示图片
            .setUseMemCache(true)
            .build();

    public WriteThreadAdapter(Context context, List<TuwenInfo> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(final ViewHolder holder, final TuwenInfo tuwenInfo) {

        FrameLayout content_layout = holder.getView(R.id.content_layout);
        if (tuwenInfo.getType() == TuwenInfo.TYPE_WEN) {
            WidgetUtils.setHeight(content_layout, DensityUtil.dip2px(160));
            holder.setBackgroundRes(R.id.content_tuwen_layout, 0);
        } else {
            WidgetUtils.setHeight(content_layout, DensityUtil.dip2px(110));
            holder.setBackgroundRes(R.id.content_tuwen_layout, R.drawable.light_grey_frame);
        }

        holder.setVisible(R.id.thread_img_layout, (tuwenInfo.getType() == TuwenInfo.TYPE_TU || tuwenInfo.getType() == TuwenInfo.TYPE_VIDEO));
        holder.setVisible(R.id.thread_img, tuwenInfo.getType() == TuwenInfo.TYPE_TU || tuwenInfo.getType() == TuwenInfo.TYPE_VIDEO);
        holder.setVisible(R.id.video_play_btn, tuwenInfo.getType() == TuwenInfo.TYPE_VIDEO);

        holder.setVisible(R.id.upload_ing_tv, tuwenInfo.getType() == TuwenInfo.TYPE_TU);
        holder.setVisible(R.id.upload_success_tv, tuwenInfo.getType() == TuwenInfo.TYPE_TU);
        holder.setVisible(R.id.upload_fail_tv, tuwenInfo.getType() == TuwenInfo.TYPE_TU);

        if (tuwenInfo.getType() == TuwenInfo.TYPE_TU) {
            x.image().bind((ImageView) holder.getView(R.id.thread_img), tuwenInfo.getImgPath(), imageOptions);
            holder.setVisible(R.id.upload_ing_tv, tuwenInfo.getImgStatus() == UploadImgInfo.STATUS_UPLOAD_ING || tuwenInfo.getImgStatus() == UploadImgInfo.STATUS_NOT_BEGIN);
            holder.setVisible(R.id.upload_success_tv, tuwenInfo.getImgStatus() == UploadImgInfo.STATUS_SUCCESS);
            holder.setVisible(R.id.upload_fail_tv, tuwenInfo.getImgStatus() == UploadImgInfo.STATUS_FAIL);
            holder.setHintText(R.id.thread_text, "给图片配点文案~");
        } else if (tuwenInfo.getType() == TuwenInfo.TYPE_VIDEO) {
            x.image().bind((ImageView) holder.getView(R.id.thread_img), tuwenInfo.getImgPath(), imageOptions);
            holder.setHintText(R.id.thread_text, "给视频配点文案~");
        }


        holder.setText(R.id.thread_text, tuwenInfo.getText());
        holder.setVisible(R.id.typesetting_layout, !showContent);


        holder.setOnClickListener(R.id.thread_img, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iClick != null) {
                    iClick.imgClick(holder.getPosition());
                }
            }
        });
        holder.setOnClickListener(R.id.thread_text, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iClick != null) {
                    iClick.textClick(holder.getPosition());
                }
            }
        });
        holder.setOnClickListener(R.id.thread_delete_btn, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iClick != null) {
                    iClick.deleteClick(holder.getPosition());
                }
            }
        });
        holder.setOnClickListener(R.id.thread_item_up, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iClick != null) {
                    iClick.itemUp(holder.getPosition());
                }
            }
        });
        holder.setOnClickListener(R.id.thread_item_down, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iClick != null) {
                    iClick.itemDown(holder.getPosition());
                }
            }
        });

        holder.setOnClickListener(R.id.upload_fail_tv, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iClick != null) iClick.reStartUpload(holder.getPosition());
            }
        });

        holder.setOnClickListener(R.id.video_play_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClick != null) iClick.videoClick(holder.getPosition());
            }
        });


    }

    boolean showContent = true;


    public void showMenu() {
        showContent = false;
        notifyDataSetChanged();
    }

    public void showContent() {
        showContent = true;
        notifyDataSetChanged();
    }


}
