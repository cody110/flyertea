package com.ideal.flyerteacafes.ui.fragment.page.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.view.AppTitleBar;
import com.ideal.flyerteacafes.utils.tools.V;


/**
 * Created by fly on 2015/11/17.
 */
public abstract class BasicFragment extends BaseFragment implements View.OnClickListener {

    protected static final int fBodyViewId = R.id.basic_content_layout;
    protected AppTitleBar mAppTitleBar;
    protected FrameLayout mContentContainer;

    protected abstract void setTitleBar();

    protected abstract View createBodyView(Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_basic, container, false);
        mAppTitleBar = V.f(view, R.id.basic_titlebar_layout);
        mContentContainer = V.f(view, R.id.basic_content_layout);
        mAppTitleBar.setLeftOnClickListener(this);
        setTitleBar();
        View contentView = createBodyView(savedInstanceState);
        if (contentView != null)
            mContentContainer.addView(contentView);
        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.include_title_menu_btn) {
            mActivity.finish();
        }
    }

    protected View getView(int layoutId) {
        View view = LayoutInflater.from(getActivity()).inflate(layoutId, null);
        return view;
    }

//    protected void addView(<T extends View> contentView ,View addView) {
//
//        if (contentView.getChildCount() > 0) {
//            contentView.removeAllViews();
//        }
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.MATCH_PARENT);
//        view.setLayoutParams(params);
//        mContent.addView(view);
//    }
}
