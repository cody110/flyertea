package com.ideal.flyerteacafes.ui.fragment.page.messagecenter;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.BaseDataManger;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.NotificationBean;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.MajorCommentActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.List;

/**
 * Created by Cindy on 2017/3/20.
 */
public class SendFlowersFragment extends NewPullRefreshFragment<NotificationBean> {

    @Override
    public void initViews() {
        super.initViews();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NotificationBean notificationBean = datas.get(position);
                Bundle bundle = new Bundle();
                bundle.putString(ThreadPresenter.BUNDLE_TID, datas.get(position).getTid());
                bundle.putInt(ThreadPresenter.BUNDLE_POS, position);
                bundle.putInt(ThreadPresenter.BUNDLE_TYPE_KEY, ThreadPresenter.BUNDLE_TYPE_MAJOR);
                if (DataTools.getInteger(notificationBean.getPostpage()) == 0 && DataTools.getInteger(notificationBean.getPostindex()) == 0) {
                    jumpActivityForResult(ThreadActivity.class, bundle, 0);
                } else {
                    int postPage = DataTools.getInteger(notificationBean.getPostpage());
                    int scroll_pos = DataTools.getInteger(notificationBean.getPostindex());
                    bundle.putString(ThreadPresenter.BUNDLE_TID, String.valueOf(notificationBean.getTid()));
                    bundle.putInt(ThreadPresenter.BUNDLE_STARTPAGE, postPage);
                    bundle.putInt(ThreadPresenter.BUNDLE_SCROLL_POS, scroll_pos);
                    jumpActivityForResult(MajorCommentActivity.class, bundle, 0);
                }
            }
        });
        addNormalLayout(R.layout.include_no_message);
    }

    @Override
    protected CommonAdapter<NotificationBean> createAdapter(List<NotificationBean> datas) {
        WidgetUtils.setVisible(normalLayout, DataUtils.isEmpty(datas));
        return new CommonAdapter<NotificationBean>(getActivity(), datas, R.layout.item_message_center_other_message) {
            @Override
            public void convert(ViewHolder holder, NotificationBean notificationBean) {
                holder.setText(R.id.item_my_reply_title, notificationBean.getContent());
                String dbdateline = notificationBean.getDateline();
                if (!TextUtils.isEmpty(dbdateline)) {
                    holder.setText(R.id.item_my_reply_time, DataUtils.conversionTime(dbdateline));
                } else {
                    holder.setText(R.id.item_my_reply_time, "");
                }
                holder.setVisible(R.id.icon_remind, TextUtils.equals(notificationBean.getIsnew(), "1"));
            }
        };
    }

    @Override
    protected PullRefreshPresenter<NotificationBean> createPresenter() {


        return new PullRefreshPresenter<NotificationBean>() {

            @Override
            public void init(Activity activity) {
                super.init(activity);
                BaseDataManger.getInstance().requestMarkTypeReads(Marks.MarkType.FLOWER);
            }

            @Override
            public void requestDatas() {
                getDialog().proDialogShow();
                FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SEND_FLOWERS);
                params.addQueryStringParameter("page", String.valueOf(page));
                params.addQueryStringParameter("perpage", String.valueOf(perpage));
                XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.LIST_KEY_NOTIFICATION, NotificationBean.class));
            }
        };
    }
}
