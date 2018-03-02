package com.ideal.flyerteacafes.ui.fragment.page.tab;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.TypeBaseBean;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.reportlist.ReportListFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.ReportPresenter;
import com.ideal.flyerteacafes.ui.interfaces.ITypeChoose;
import com.ideal.flyerteacafes.ui.popupwindow.FlyHuiOneListPopupwindow;
import com.ideal.flyerteacafes.ui.popupwindow.FlyHuiTwoListPopupwindow;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by fly on 2017/6/30.
 */

public abstract class ScreenTypeFragment extends BaseFragment implements ReportPresenter.IGetSortid {


    private FlyHuiTwoListPopupwindow popWindow;
    private FlyHuiOneListPopupwindow huiOneListPop;


    @ViewInject(R.id.btn_navigation_one)
    TextView btnOne;
    @ViewInject(R.id.btn_navigation_four)
    TextView btnFour;
    @ViewInject(R.id.choose_navigation_bar_line)
    View topHead;


    private List<TypeBaseBean.TypeInfo> typeAllList;

    private String key;


    //创建fragment
    public abstract ReportListFragment createFragment();

    //设置帅选数据
    public abstract TypeBaseBean.TypeInfoBean2 setScreenData();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_screen_type, container, false);
        x.view().inject(this, view);
        addFragment();
        btnFour.setTag(0);
        btnOne.setTag(0);
        return view;
    }

    @Event({R.id.ll_navigation_bar_frist, R.id.ll_navigation_bar_four})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.ll_navigation_bar_frist:
                if (typeAllList == null) {
                    TypeBaseBean.TypeInfoBean2 typeInfoBean2 = setScreenData();
                    if (typeInfoBean2 != null) {
                        key = typeInfoBean2.getIdentifier();
                        TypeBaseBean.TypeInfo typeInfo = new TypeBaseBean.TypeInfo();
                        typeInfo.setName("全部");
                        typeAllList = typeInfoBean2.getSubchoices();
                        if (typeAllList != null) {
                            typeAllList.add(0, typeInfo);
                        }
                    }
                }
                if (popWindow == null && typeAllList != null) {
                    popWindow = new FlyHuiTwoListPopupwindow(mActivity, typeAllList, new ITypeChoose() {
                        @Override
                        public void typeChoose(TypeBaseBean.TypeInfo oneInfo, TypeBaseBean.TypeInfo twoInfo) {
                            if (oneInfo == null) return;
                            if (TextUtils.equals(oneInfo.getName(), "全部")) {
                                fragment.setType("", "");
                            } else {
                                String value;
                                if (twoInfo == null) {
                                    value = oneInfo.getCid();
                                } else {
                                    value = twoInfo.getCid();
                                }
                                fragment.setType(key, value);
                            }
                        }

                        @Override
                        public void popDismiss() {
                            changeDrawable(R.drawable.up_arrow, btnOne);
                            changeDrawable(R.drawable.up_arrow, btnFour);
                        }
                    });
                }
                if (popWindow != null) {
                    changeDrawable(R.drawable.down_arrow, btnOne);
                    popWindow.showAsDropDown(topHead);
                    popWindow.setInAnim();
                }
                break;
            case R.id.ll_navigation_bar_four:
                changeDrawable(R.drawable.down_arrow, btnFour);
                if (huiOneListPop == null) {
                    huiOneListPop = new FlyHuiOneListPopupwindow(mActivity);
                    huiOneListPop.setISort(new FlyHuiOneListPopupwindow.ISort() {
                        @Override
                        public void sortType(String sortType) {
                            fragment.setSort(sortType);
                        }
                    });
                }
                huiOneListPop.showAsDropDown(topHead);
                break;
        }
    }


    private void changeDrawable(int draw, View textView) {
        Drawable drawable = getResources().getDrawable(draw);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        ((TextView) textView).setCompoundDrawables(null, null, drawable, null);//画在右边
        if (draw == R.drawable.up_arrow) {
            ((TextView) textView).setTextColor(getResources().getColor(R.color.black));
        } else {
            ((TextView) textView).setTextColor(getResources().getColor(R.color.app_bg_title));
        }

    }


    ReportListFragment fragment;
    String fmTagName;

    private void addFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (fmTagName != null)
            fragment = (ReportListFragment) getChildFragmentManager().findFragmentByTag(fmTagName);
        if (fragment == null) {
            fragment = createFragment();
            fmTagName = fragment.getFmTagName();
            transaction.add(R.id.fragment_layout, fragment, fmTagName);
            fragment.setIGetSortid(this);
        } else {
            transaction.show(fragment);
        }
        transaction.commit();
    }

}
