package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteThreadPresenter;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by fly on 2016/11/14.
 */

public class AddImageAdapter extends CommonAdapter<String> {


    ImageOptions imageOptions = new ImageOptions.Builder()
            .setSize(DensityUtil.dip2px(60), DensityUtil.dip2px(60))//图片大小
//                .setRadius(DensityUtil.dip2px(5))//ImageView圆角半径
//                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .setLoadingDrawableId(R.drawable.post_def)//加载中默认显示图片
            .setFailureDrawableId(R.drawable.post_def)//加载失败后默认显示图片
            .setUseMemCache(true)
            .build();

    public AddImageAdapter(Context context, List<String> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, String s) {
        if (WriteThreadPresenter.addImgMark.equals(s)) {
            holder.setImageResource(R.id.zhibo_gridview_img, R.drawable.zhibo_add);
        } else {
            ImageView iv = holder.getView(R.id.zhibo_gridview_img);
            x.image().bind(iv, s, imageOptions);
        }
    }
}
