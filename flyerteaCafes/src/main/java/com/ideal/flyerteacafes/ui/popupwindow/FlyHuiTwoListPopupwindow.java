package com.ideal.flyerteacafes.ui.popupwindow;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ideal.flyerteacafes.model.entity.TypeBaseBean;
import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.FlyHuiTypeAapter;
import com.ideal.flyerteacafes.ui.interfaces.ITypeChoose;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.V;

public class FlyHuiTwoListPopupwindow extends PopupWindow {

    protected Context context;
    protected View mMenuView;
    protected ListView oneLv, secondLv;
    protected LinearLayout typeAllView;
    protected FlyHuiTypeAapter typeAllOneAapter;
    protected FlyHuiTypeAapter typeAllTwoAapter;
    protected int pos = 0;//第一列表选中项

    public FlyHuiTwoListPopupwindow(final Context context, final List<TypeBaseBean.TypeInfo> listType, final ITypeChoose iTypeChoose) {

        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popupwindow_typeall_choose, null);
        oneLv = (ListView) mMenuView.findViewById(R.id.choose_typeall_one_listview);
        secondLv = (ListView) mMenuView.findViewById(R.id.choose_typeall_second_listview);
        typeAllView = V.f(mMenuView, R.id.typeall_pop_view);

        mMenuView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                setOutAnim();
            }
        });

        initPopView(context, listType, iTypeChoose);

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);  //true 可点击  false 不可点击
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimTop);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        this.setOutsideTouchable(false);
        this.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                iTypeChoose.popDismiss();
            }
        });
    }

    protected void initPopView(final Context context, final List<TypeBaseBean.TypeInfo> listType, final ITypeChoose iTypeChoose) {

        typeAllOneAapter = new FlyHuiTypeAapter(context, listType, R.layout.item_typeall_one_level_layout, FlyHuiTypeAapter.LEVEL_ONE);
        oneLv.setAdapter(typeAllOneAapter);

        oneLv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isAnimAtionRun) {
                    return;
                }
                typeAllOneAapter.selectItem(position);
                if (pos != position) {
                    if (typeAllTwoAapter != null) {
                        typeAllTwoAapter.refresh(listType.get(position).getSubchoices());
                        typeAllTwoAapter.selectItem(-1);
                    }
                }
                pos = position;
                if (listType.get(position).getSubchoices() == null || listType.get(position).getSubchoices().size() == 0) {
                    iTypeChoose.typeChoose(typeAllOneAapter.getItem(position), null);
                    setOutAnim();
                }
            }
        });


        boolean isShowTwoLevel = false;

        for (int i = 0; i < listType.size(); i++) {
            if (!DataUtils.isEmpty(listType.get(i).getSubchoices())) {
                isShowTwoLevel = true;
                break;
            }
        }

        if (!isShowTwoLevel) {
            secondLv.setVisibility(View.GONE);
        } else {
            //第二列表设置，第一列表第一项数据
            List<TypeBaseBean.TypeInfo> type2List = null;
            if (DataTools.getBeanByListPos(listType, 0) != null)
                type2List = DataTools.getBeanByListPos(listType, 0).getSubchoices();
            if (type2List == null)
                type2List = new ArrayList<>();
            typeAllTwoAapter = new FlyHuiTypeAapter(context, type2List, R.layout.item_typeall_one_level_layout, FlyHuiTypeAapter.LEVEL_TWO);
            secondLv.setAdapter(typeAllTwoAapter);
            secondLv.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    if (isAnimAtionRun) {
                        return;
                    }
                    typeAllTwoAapter.selectItem(arg2);
                    iTypeChoose.typeChoose(typeAllOneAapter.getItem(pos), typeAllTwoAapter.getItem(arg2));
                    setOutAnim();
                }
            });

        }

    }

    public void setInAnim() {
        AnimationSet inAnim = (AnimationSet) AnimationUtils.loadAnimation(
                context, R.anim.push_up_down_in);
        typeAllView.startAnimation(inAnim);
    }

    protected boolean isAnimAtionRun = false;

    public void setOutAnim() {
        AnimationSet outAnim = (AnimationSet) AnimationUtils.loadAnimation(
                context, R.anim.push_up_down_out);
        typeAllView.startAnimation(outAnim);
        outAnim.setAnimationListener(new AnimationListener() {

            public void onAnimationStart(Animation animation) {
                isAnimAtionRun = true;
            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                isAnimAtionRun = false;
                dismiss();
            }
        });
    }


}
