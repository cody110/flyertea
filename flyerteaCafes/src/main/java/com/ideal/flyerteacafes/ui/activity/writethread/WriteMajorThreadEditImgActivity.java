package com.ideal.flyerteacafes.ui.activity.writethread;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.AlbumActivity;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.AlbumPresenter;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by fly on 2017/3/13.
 */
@ContentView(R.layout.activity_write_majorthread_editimg)
public class WriteMajorThreadEditImgActivity extends BaseActivity {


    private static final int REQUESTCODE_CHANGE_IMG = 1;
    @ViewInject(R.id.image)
    ImageView imageView;


    ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
            .setLoadingDrawableId(R.drawable.post_def)//加载中默认显示图片
            .setFailureDrawableId(R.drawable.post_def)//加载失败后默认显示图片
            .setUseMemCache(true)
            .build();

    public static final String BUNDLE_IMG_PATH = "img_path";
    public static final String BUNDLE_LIST_POS = "list_pos";
    private int pos;
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        pos=getIntent().getIntExtra(BUNDLE_LIST_POS,-1);
        imgPath = getIntent().getStringExtra(BUNDLE_IMG_PATH);
        x.image().bind(imageView, imgPath, imageOptions);
    }

    @Override
    public boolean isSetSystemBar() {
        return false;
    }

    @Event({R.id.toolbar_left,R.id.toolbar_right,R.id.change_img,R.id.delete_img})
    private void click(View v){
        Bundle bundle=new Bundle();
        switch (v.getId()){
            case R.id.toolbar_left:
                finish();
                break;

            case R.id.toolbar_right:
                bundle.putString(BUNDLE_IMG_PATH,imgPath);
                bundle.putInt(BUNDLE_LIST_POS,pos);
                jumpActivitySetResult(bundle);
                break;

            case R.id.change_img:
                bundle.putString(AlbumPresenter.BUNDLE_FROM_TYPE,AlbumPresenter.BUNDLE_FROM_MAJOR_THREAD);
                bundle.putInt(AlbumPresenter.BUNDLE_NEED_SIZE, 1);
                jumpActivityForResult(AlbumActivity.class,bundle,REQUESTCODE_CHANGE_IMG);
                break;

            case R.id.delete_img:
                bundle.putString(BUNDLE_IMG_PATH,"");
                bundle.putInt(BUNDLE_LIST_POS,pos);
                jumpActivitySetResult(bundle);
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==REQUESTCODE_CHANGE_IMG){
                List<String> mSelectImage = (List<String>) data.getSerializableExtra("mSelectedImage");
                imgPath=mSelectImage.get(0);
                x.image().bind(imageView, imgPath, imageOptions);
            }
        }
    }
}
