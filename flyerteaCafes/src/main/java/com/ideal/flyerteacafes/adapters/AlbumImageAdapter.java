package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.params.AlbumImgaeNumEvent;
import com.ideal.flyerteacafes.ui.activity.PictureBrowseActivity;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/11/15.
 */

public class AlbumImageAdapter extends CommonAdapter<String> {
    public List<String> mSelectedImage;
    private int maxPic = 9;

    public AlbumImageAdapter(Context context, List<String> mDatas, int itemLayoutId, List<String> mSelectedImage, int maxPic) {
        super(context, mDatas, itemLayoutId);
        this.mSelectedImage = mSelectedImage;
        this.maxPic = maxPic;
    }


    @Override
    protected boolean isNeedMyList() {
        return true;
    }

    static ImageOptions imageOptions = new ImageOptions.Builder()
            .setSize(DensityUtil.dip2px(40), DensityUtil.dip2px(40))//图片大小
//                .setRadius(DensityUtil.dip2px(5))//ImageView圆角半径
//                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
//                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .setLoadingDrawableId(R.drawable.icon_def)//加载中默认显示图片
            .setFailureDrawableId(R.drawable.icon_def)//加载失败后默认显示图片
            .setUseMemCache(true)
            .build();

    @Override
    public void convert(final ViewHolder helper, final String item) {
        helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no_white);
        helper.setImageResource(R.id.id_item_select,
                R.drawable.pictures_selected);

        x.image().bind((ImageView) helper.getView(R.id.id_item_image), item, imageOptions);

//        helper.setImageUrl(R.id.id_item_image,  item,R.drawable.icon_def);

        final ImageView mImageView = helper.getView(R.id.id_item_image);
        final ImageView mSelect = helper.getView(R.id.id_item_select);


        mSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedImage.contains(item)) {
                    mSelectedImage.remove(item);
                    mSelect.setImageResource(R.drawable.pictures_selected);
                } else {
                    if (mSelectedImage.size() < maxPic) {
                        mSelectedImage.add(item);
                        mSelect.setImageResource(R.drawable.select_xiangce_lv);
                    } else {
                        ToastUtils.showToast("每次最多添加30张，可添加多次");
                    }
                }
                EventBus.getDefault().post(new AlbumImgaeNumEvent(mSelectedImage.size()));
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> urlList = new ArrayList<>();
                urlList.add(item);
                Intent intent = new Intent(mContext, PictureBrowseActivity.class);
                intent.putStringArrayListExtra("urlList", urlList);
                intent.putExtra("pos", helper.getPosition());
                mContext.startActivity(intent);
            }
        });

        if (mSelectedImage.contains(item)) {
            mSelect.setImageResource(R.drawable.select_xiangce_lv);
        }

    }

//    @Override
//    public int getCount() {
//        return super.getCount()>8?8:super.getCount();
//    }

}
