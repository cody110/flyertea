package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PhotoPagerAdapter;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.fragment.page.AcdseeFragment;
import com.ideal.flyerteacafes.utils.tools.ApplicationTools;
import com.ideal.flyerteacafes.utils.tools.V;
import com.ideal.flyerteacafes.utils.SmartUtil;
import com.ideal.flyerteacafes.ui.view.AppTitleBar;

import java.util.ArrayList;

/**
 * Created by fly on 2015/11/23.
 */
public class YulanTupianActivity extends BaseActivity implements AcdseeFragment.vpListener ,View.OnClickListener,PhotoPagerAdapter.imageClick{

    protected AppTitleBar titleBar;
    protected AcdseeFragment acdseeFragment;
    protected TextView tv_count, tv_ok;
    protected View bottomView;
    protected ArrayList<String> mSelectedImage;
    protected ArrayList<String> urlList;
    protected int index,selectLength,activity,h_screen;

    public static final int fBack=0,fOk=1,fFromPictureAll=2,fFromPictureYulan=3,fFromWrite=4;
    private final int animTime=500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        h_screen= SharedPreferencesString.getInstances(this).getIntToKey("h_screen");
        setContentView(R.layout.activity_xiangce_tupianliulan);
        titleBar = (AppTitleBar) findViewById(R.id.xiangce_tupianliulan_titlebar);
        acdseeFragment = (AcdseeFragment) getSupportFragmentManager().findFragmentById(R.id.xiangce_tupianliulan_fragment);
        tv_count = V.f(this, R.id.xiangce_bottom_count);
        tv_ok = V.f(this, R.id.xiangce_bottom_ok);
        bottomView= V.f(this, R.id.xiangce_bottom);
        int index = getIntent().getIntExtra("pos", 0);
        urlList = (ArrayList<String>) getIntent().getSerializableExtra("urlList");
        mSelectedImage= (ArrayList<String>) getIntent().getSerializableExtra("mSelectedImage");
        if(urlList==null){
            urlList=new ArrayList<>();
            urlList.addAll(mSelectedImage);
        }
        activity=getIntent().getIntExtra("activity",fFromPictureAll);
        if(activity==fFromPictureAll){
            selectLength=9;
        }else{
            selectLength=mSelectedImage.size();
        }
        acdseeFragment.setvpListener(this);
        acdseeFragment.setUrlDatas(urlList, index);
        acdseeFragment.setAdapterImageClickI(this);
        tv_count.setText(mSelectedImage.size() + "/" + selectLength);
        titleBar.setRightOnClickListener(this);
        titleBar.setLeftOnClickListener(this);
        titleBar.rightView.setVisibility(View.VISIBLE);
        titleBar.setTitleTxt("预览");
        tv_ok.setText(getString(R.string.accomplish));
        tv_ok.setOnClickListener(this);
        statusBarHeight= ApplicationTools.getStatusBarHeight(this);
    }

    @Override
    public void onPageSelected(int index) {
        this.index=index;
        if(mSelectedImage.contains(urlList.get(index))){
            titleBar.setRightImageResource(R.drawable.pictures_selected);
        }else{
            titleBar.setRightImageResource(R.drawable.picture_unselected);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==AppTitleBar.fLeftViewId){
//            if(activity==YulanTupianActivity.fFromWrite){
//                jumpActivitySetResult(null);
//                return;
//            }
//            Bundle bundle=new Bundle();
//            bundle.putSerializable("mSelectedImage",mSelectedImage);
//            bundle.putInt("mark", fBack);
//            jumpActivitySetResult(bundle);
            finish();
        }else if(v.getId()==AppTitleBar.fRightViewId) {
            if(mSelectedImage.contains(urlList.get(index))){
                mSelectedImage.remove(urlList.get(index));
                titleBar.setRightImageResource(R.drawable.picture_unselected);
            }else{
                if(mSelectedImage.size()<9){
                    mSelectedImage.add(urlList.get(index));
                    titleBar.setRightImageResource(R.drawable.pictures_selected);
                }else{
                    SmartUtil.BToast(YulanTupianActivity.this, getString(R.string.tupian_chaochu_remind));
                }
            }

            tv_count.setText(mSelectedImage.size()+"/"+selectLength);
        }else if(v.getId()== R.id.xiangce_bottom_ok){
            Bundle bundle=new Bundle();
            bundle.putSerializable("mSelectedImage",mSelectedImage);
            bundle.putInt("mark",fOk);
            jumpActivitySetResult(bundle);
        }
    }

    @Override
    public void isHideTopAndBottom(boolean bol) {
        if(bol){
//            titleBar.rootView.setVisibility(View.GONE);
//            bottomView.setVisibility(View.GONE);
            viewGoneAnim();
        }else{
//            titleBar.rootView.setVisibility(View.VISIBLE);
//            bottomView.setVisibility(View.VISIBLE);
            viewVisibleAnim();
        }
    }

    @Override
    public void longClick() {

    }


    private int topHeight,bottomHeight,statusBarHeight;

    public void viewGoneAnim()
    {
        if(topHeight==0) {
            topHeight = titleBar.rootView.getHeight();
            bottomHeight = bottomView.getHeight();
        }
        // need API12
        titleBar.rootView.animate()//
                .alpha(0.5f)//
                .y(-topHeight).setDuration(animTime).start();
        bottomView.animate()//
                .alpha(0.5f)//
                .y(bottomHeight+h_screen).setDuration(animTime).start();
    }

    public void viewVisibleAnim()
    {
        // need API12
        titleBar.rootView.animate()//
                .alpha(1.0f)//
                .y(0).setDuration(animTime).start();
        bottomView.animate()//
                .alpha(1.0f)//
                .y(h_screen-bottomHeight-statusBarHeight).setDuration(animTime).start();
    }

}
