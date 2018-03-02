package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.view.View;
import android.widget.GridView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.SelectItemAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;
import com.ideal.flyerteacafes.utils.tools.ViewTools;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2017/3/31.
 */

public class ChoosePagePopupwindow extends BottomPopupwindow {

    @ViewInject(R.id.page_gv)
    GridView page_gv;

    public ChoosePagePopupwindow(Context context) {
        super(context);
    }

    @Override
    protected int setBottomViewLayoutId() {
        return R.layout.pop_choose_page_bottom_layout;
    }


    public void initPage(int page, int totalpage) {

        //TODO 自适应高度，最高显示4行高
        int hang = totalpage / 5;
        if (totalpage % 5 > 0) {
            hang += 1;
        }
        hang = hang > 4 ? 4 : hang;
        ViewTools.setViewSize(DensityUtil.dip2px(60) * hang, page_gv);

        List<String> pageNumList = new ArrayList<>();
        for (int i = 0; i < totalpage; i++) {
            pageNumList.add(String.valueOf(i + 1));
        }
        SelectItemAdapter selectItemAdapter;
        page_gv.setAdapter(selectItemAdapter = new SelectItemAdapter<String>(mContext, pageNumList, R.layout.item_page) {
            @Override
            public void convert(final ViewHolder holder, String s) {
                holder.setText(R.id.page_text, s);
                if (selectIndex == holder.getPosition()) {
                    holder.setTextColorRes(R.id.page_text, R.color.white);
                    holder.setBackgroundRes(R.id.page_text, R.drawable.blue_circle);
                } else {
                    holder.setTextColorRes(R.id.page_text, R.color.app_body_black);
                    holder.setBackgroundRes(R.id.page_text, 0);
                }

                holder.setOnClickListener(R.id.page_root, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (iPageChoose != null) iPageChoose.selectPagePos(holder.getPosition());
                        selectIndex = holder.getPosition();
                        notifyDataSetChanged();
                        dismiss();
                    }
                });

            }
        });
        selectItemAdapter.setSelectIndex(page - 1);
    }

    public interface IPageChoose {
        void selectPagePos(int pos);
    }


    public IPageChoose iPageChoose;

    public void setiPageChoose(IPageChoose iPageChoose) {
        this.iPageChoose = iPageChoose;
    }


}
