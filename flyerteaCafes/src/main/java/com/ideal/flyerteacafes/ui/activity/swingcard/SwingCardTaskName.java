package com.ideal.flyerteacafes.ui.activity.swingcard;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.entity.BankBean;
import com.ideal.flyerteacafes.model.entity.CardGroupBean;
import com.ideal.flyerteacafes.model.entity.TaskNameBean;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.ISwingCardTaskName;
import com.ideal.flyerteacafes.ui.activity.presenter.SwingCardTaskNamePresenter;
import com.ideal.flyerteacafes.ui.popupwindow.BankPopupwindow;
import com.ideal.flyerteacafes.ui.popupwindow.CardGroupPopupwindow;
import com.ideal.flyerteacafes.ui.view.SerachEdittext;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by fly on 2017/4/13.
 */
@ContentView(R.layout.activity_taskname)
public class SwingCardTaskName extends MVPBaseActivity<ISwingCardTaskName, SwingCardTaskNamePresenter> implements ISwingCardTaskName {

    @ViewInject(R.id.taskname_listview)
    ListView taskname_listview;
    @ViewInject(R.id.search_edit)
    SerachEdittext serachEdittext;
    @ViewInject(R.id.line)
    View tab_line;
    @ViewInject(R.id.choose_bank_btn)
    TextView choose_bank_btn;
    @ViewInject(R.id.card_group_btn)
    TextView card_group_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        serachEdittext.setHint("输入搜索字词");
        serachEdittext.setISearchClick(new SerachEdittext.ISearchClick() {
            @Override
            public void onSearchClick(String text) {
                mPresenter.setKeyword(text);
            }
        });
        mPresenter.init(this);
    }

    @Override
    protected SwingCardTaskNamePresenter createPresenter() {
        return new SwingCardTaskNamePresenter();
    }


    @Event({R.id.toolbar_left, R.id.choose_bank_btn, R.id.card_group_btn})
    private void onclick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left:
                finish();
                break;

            case R.id.choose_bank_btn:
                mPresenter.showBank();
                break;

            case R.id.card_group_btn:
                mPresenter.showCardGroup();
                break;
        }
    }

    @Event(value = R.id.taskname_listview, type = AdapterView.OnItemClickListener.class)
    private void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", mPresenter.mDatas.get(position));
        jumpActivitySetResult(bundle);
    }

    @Override
    public void bindDataTaskNameList(List<TaskNameBean> datas) {
        if (taskname_listview.getAdapter() == null) {
            CommonAdapter<TaskNameBean> taskNameAdapter = new CommonAdapter<TaskNameBean>(this, datas, R.layout.item_taskname) {
                @Override
                public void convert(ViewHolder holder, TaskNameBean taskNameBean) {
                    holder.setText(R.id.taskname_tv, taskNameBean.getCardMissionTitle());
                }
            };
            taskname_listview.setAdapter(taskNameAdapter);
        } else {
            ((CommonAdapter) taskname_listview.getAdapter()).notifyDataSetChanged();
        }
    }

    @Override
    public void bindDataCardGroupList(List<CardGroupBean> datas) {
        CardGroupPopupwindow popupwindow = new CardGroupPopupwindow(SwingCardTaskName.this);
        popupwindow.bindData(datas);
        popupwindow.showAsDropDown(tab_line);
        popupwindow.setiItemClick(new CardGroupPopupwindow.IItemClick() {
            @Override
            public void itemClick(CardGroupBean bean) {
                if (TextUtils.equals(bean.getCardChannelName(), "全部")) {
                    WidgetUtils.setText(card_group_btn, "卡组织");
                } else {
                    WidgetUtils.setText(card_group_btn, bean.getCardChannelName());
                }
                mPresenter.setChannelid(String.valueOf(bean.getCardChannelId()));
            }
        });
    }

    @Override
    public void bindDataBankList(List<BankBean> datas) {
        BankPopupwindow popupwindow = new BankPopupwindow(SwingCardTaskName.this);
        popupwindow.bindData(datas);
        popupwindow.showAsDropDown(tab_line);
        popupwindow.setiItemClick(new BankPopupwindow.IItemClick() {
            @Override
            public void itemClick(BankBean bean) {
                if (TextUtils.equals(bean.getBankName(), "全部")) {
                    WidgetUtils.setText(choose_bank_btn, "选择银行");
                } else {
                    WidgetUtils.setText(choose_bank_btn, bean.getBankName());
                }
                mPresenter.setBankid(String.valueOf(bean.getBankId()));
            }

        });
    }
}
