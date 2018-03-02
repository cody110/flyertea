package com.ideal.flyerteacafes.ui.activity.swingcard;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 任务搜索
 * Created by Cindy on 2017/4/12.
 */
@ContentView(R.layout.activity_task_search)
public class TaskSearchActivity extends BaseActivity{

    @ViewInject(R.id.task_tab_line)
    View task_tab_line;

    @ViewInject(R.id.ll_brank_layout)
    View ll_brank_layout;

    /** 选择银行 **/
    @ViewInject(R.id.label_brank)
    TextView label_brank;
    /** 卡组织 **/
    @ViewInject(R.id.label_card)
    TextView label_card;

    @ViewInject(R.id.img_brank)
    ImageView img_brank;
    @ViewInject(R.id.img_card)
    ImageView img_card;

    private boolean brankFlag=false,cardFlag=false;
//    private BrankPopupWindow brankPopupWindow;
//    private CardChannelPopupWindow cardChannelPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    /**
     *
     * @description: 选择银行
     *
     * @author: Cindy
     *
     * @date: 2016/5/10 15:28
     */
    private void setChooseBrank(){
        //设置tab选中样式
        img_card.setImageResource(R.mipmap.mark_normal);
        cardFlag=false;
        label_brank.setTextColor(getResources().getColor(R.color.app_black));
        label_card.setTextColor(getResources().getColor(R.color.grey));
//        if(brankPopupWindow==null){
//            brankPopupWindow=new BrankPopupWindow(this);
//        }
//        //设置popwindow在控件下方显示
//        brankPopupWindow.showAsDropDown(task_tab_line);
//        brankFlag=true;
//        img_brank.setImageResource(R.mipmap.mark_up_choose);
//        brankPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                brankFlag = false;
//                img_brank.setImageResource(R.mipmap.mark_choose);
//            }
//        });
    }

    /**
     *
     * @description: 卡组织
     *
     * @author: Cindy
     *
     * @date: 2016/5/10 15:30
     */
    private void setChooseCardChannel(){
//        if(cardChannelPopupWindow==null){
//            cardChannelPopupWindow=new CardChannelPopupWindow(this);
//        }
//        //设置popwindow在控件下方显示
//        cardChannelPopupWindow.showAsDropDown(task_tab_line);
        //设置tab选中样式
        img_brank.setImageResource(R.mipmap.mark_normal);
        brankFlag=false;
        label_card.setTextColor(getResources().getColor(R.color.app_black));
        label_brank.setTextColor(getResources().getColor(R.color.grey));
        cardFlag=true;
        img_card.setImageResource(R.mipmap.mark_up_choose);
//        cardChannelPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                cardFlag=false;
//                img_card.setImageResource(R.mipmap.mark_choose);
//            }
//        });
    }

}
