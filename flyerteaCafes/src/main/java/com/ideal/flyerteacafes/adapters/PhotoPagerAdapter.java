package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.xutils.XutilsHelp;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.util.List;


/**
 * Created by donglua on 15/6/21.
 */
public class PhotoPagerAdapter extends PagerAdapter {

    private List<String> paths;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private boolean isShowPage = false;//是否显示页码

    public interface imageClick {
        void isHideTopAndBottom(boolean bol);

        void longClick();
    }

    private imageClick IimageClick;

    public void setIimageClick(imageClick IimageClick) {
        this.IimageClick = IimageClick;
    }

    public PhotoPagerAdapter(Context mContext, List<String> paths) {
        this.mContext = mContext;
        this.paths = paths;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
            .setLoadingDrawableId(R.drawable.post_def)//加载中默认显示图片
            .setFailureDrawableId(R.drawable.post_def)//加载失败后默认显示图片
            .setUseMemCache(true)
            .build();

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = mLayoutInflater.inflate(R.layout.picker_item_pager, container, false);

        final ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_pager);
        imageView.setTag(false);

        final String path = paths.get(position);
        final Uri uri;
        if (path.startsWith("http")) {
            uri = Uri.parse(path);
        } else {
            uri = Uri.fromFile(new File(path));
        }

        x.image().bind(imageView, path, imageOptions);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//        if (mContext instanceof YulanTupianActivity) {
//          if (!((Activity) mContext).isFinishing()) {
//            ((Activity) mContext).onBackPressed();
//          }
                if (IimageClick != null) {
                    if ((boolean) imageView.getTag()) {//当前是隐藏状态，设置为显示状态
                        imageView.setTag(false);
                        IimageClick.isHideTopAndBottom(false);
                    } else {
                        imageView.setTag(true);
                        IimageClick.isHideTopAndBottom(true);
                    }
                }
//        }


            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (IimageClick != null) {
                    IimageClick.longClick();
                }
                return false;
            }
        });

        if (isShowPage) {
            TextView tv_page = (TextView) itemView.findViewById(R.id.picker_item_pager_page);
            tv_page.setText((position + 1) + "/" + paths.size());
        }

        container.addView(itemView);

        return itemView;
    }


    @Override
    public int getCount() {
        return paths.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    /**
     * 设置是否显示页码
     *
     * @param bol
     */
    public void isShowPage(boolean bol) {
        this.isShowPage = bol;
    }

}
