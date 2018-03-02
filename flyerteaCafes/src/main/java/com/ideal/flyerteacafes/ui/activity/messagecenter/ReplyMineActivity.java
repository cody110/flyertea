package com.ideal.flyerteacafes.ui.activity.messagecenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.ReplyAdapter;
import com.ideal.flyerteacafes.caff.BaseDataManger;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.model.entity.MessageReadsBean;
import com.ideal.flyerteacafes.model.entity.NotificationBean;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IReplyMine;
import com.ideal.flyerteacafes.ui.activity.presenter.ReplyMinePresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.MajorCommentActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.popupwindow.MessageCenterPopupWindow;
import com.ideal.flyerteacafes.ui.view.ToolBar;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V6.8.0
 * @description: 回复我的
 * @author: Cindy
 * @date: 2017/3/14 16:24
 */
@ContentView(R.layout.activity_reply_mine)
public class ReplyMineActivity extends MVPBaseActivity<IReplyMine, ReplyMinePresenter> implements IReplyMine, AdapterView.OnItemClickListener {

    @ViewInject(R.id.reply_mine_toolbar)
    private ToolBar reply_mine_toolbar;
    @ViewInject(R.id.fragment_listview)
    private ListView mListview;
    private ReplyAdapter replyAdapter;
    private MessageCenterPopupWindow messageCenterPopupWindow;
    @ViewInject(R.id.mll_no_message)
    private View mll_no_message;
    public static final int POP_MARK_ALL_READS = 0, POP_CLEAR_ALL_MESSAGE = 1;
    private List<NotificationBean> replyMineList = new ArrayList<>();
    boolean isClick = false;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case POP_MARK_ALL_READS:
                    mPresenter.requestMarkMessageReads();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mPresenter.init(this);
        initViews();
    }

    @Override
    public void initViews() {
        mListview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        isClick = true;
        NotificationBean notificationBean = replyMineList.get(pos);

        if (DataTools.getInteger(notificationBean.getPostpage()) == 1 && DataTools.getInteger(notificationBean.getPostindex()) == 0) {
            Bundle bundle = new Bundle();
            bundle.putString(ThreadPresenter.BUNDLE_TID, String.valueOf(notificationBean.getTid()));
            jumpActivity(ThreadActivity.class, bundle);
        } else {
            int postPage = DataTools.getInteger(notificationBean.getPostpage());
            int scroll_pos = DataTools.getInteger(notificationBean.getPostindex());
            Bundle bundle = new Bundle();
            bundle.putString(ThreadPresenter.BUNDLE_TID, String.valueOf(notificationBean.getTid()));
            bundle.putInt(ThreadPresenter.BUNDLE_STARTPAGE, postPage);
            bundle.putInt(ThreadPresenter.BUNDLE_SCROLL_POS, scroll_pos);
            jumpActivity(MajorCommentActivity.class, bundle);
        }
        //判断是否已读
        if (TextUtils.equals(replyMineList.get(pos).getIsnew(), Marks.MESSAGE_UNREAD)) {
            replyMineList.get(pos).setIsnew(Marks.MESSAGE_READ);
            replyAdapter.notifyDataSetChanged();
            BaseDataManger.getInstance().requestMarkPositionReads(replyMineList.get(pos).getId());
        }
    }

    @Event({R.id.toolbar_left, R.id.toolbar_right})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left://返回
                onBackPressed();
                break;
            case R.id.toolbar_right:
                if (!DataUtils.isEmpty(replyMineList)) {
                    if (messageCenterPopupWindow == null)
                        messageCenterPopupWindow = new MessageCenterPopupWindow(ReplyMineActivity.this, handler);
                    messageCenterPopupWindow.showAtLocation(reply_mine_toolbar, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        jumpActivitySetResult(null);
        super.onBackPressed();
    }

    @Override
    protected ReplyMinePresenter createPresenter() {
        return new ReplyMinePresenter();
    }


    /**
     * 回复我的列表
     *
     * @param replyMineList
     */
    @Override
    public void callbackReplyMine(List<NotificationBean> replyMineList) {
        this.replyMineList = replyMineList;
        showBlankPage(DataUtils.isEmpty(replyMineList));
        if (!DataUtils.isEmpty(replyMineList)) {
            if (replyAdapter == null) {
                replyAdapter = new ReplyAdapter(this, replyMineList, R.layout.item_reply_mine);
                replyAdapter = new ReplyAdapter(this, replyMineList, R.layout.item_message_center_other_message);
                mListview.setAdapter(replyAdapter);
            } else {
                replyAdapter.notifyDataSetChanged();
            }
        }
    }

    private void showBlankPage(boolean bol) {
        WidgetUtils.setVisible(mListview, !bol);
        WidgetUtils.setVisible(mll_no_message, bol);
    }

    /**
     * 标记全部消息为已读
     */
    @Override
    public void callbackMarkMessageReads(String result) {
        //隐藏小红点
        MessageReadsBean msg = JsonAnalysis.jsonToMessageReads(result);
        ToastUtils.showToast(msg.getMsg());
    }


    /**
     * 标记某条消息为已读
     *
     * @param result
     */
    @Override
    public void callbackMarkPositionReads(String result) {
        MessageReadsBean msg = JsonAnalysis.jsonToMessageReads(result);
        ToastUtils.showToast(msg.getMsg());
    }
}
