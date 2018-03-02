package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.ChatAdapter;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.ChatBean;
import com.ideal.flyerteacafes.model.entity.ListObjectBean;
import com.ideal.flyerteacafes.model.entity.SmileyBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.controls.XListView;
import com.ideal.flyerteacafes.ui.controls.XListView.IXListViewListener;
import com.ideal.flyerteacafes.ui.view.ToolBar;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.ViewTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 聊天页面
 *
 * @author fly
 */
@ContentView(R.layout.activity_chat)
public class ChatActivity extends BaseActivity implements IXListViewListener {

    @ViewInject(R.id.toolbar)
    private ToolBar toolBar;
    @ViewInject(R.id.chat_listview)
    private XListView mListView;

    @ViewInject(R.id.chat_edittext)
    private EditText mEditText;
    @ViewInject(R.id.chat_biaoqing_layout)
    private View biaoqingView;

    private int pageSize = 5;
    private int pmnum;
    private String touid;
    private int page;

    private List<ChatBean> chatList = new ArrayList<ChatBean>();

    private ChatAdapter adapter;

    private String msg;
    private String activity = "";//是从帖子详情页过来就返回就直接关闭

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        EventBus.getDefault().register(this);
        biaoqingView.setVisibility(View.GONE);
        activity = getIntent().getStringExtra("activity");
        touid = getIntent().getStringExtra("uid");
        pmnum = getIntent().getIntExtra("pmnum", 0);
        page = pmnum / pageSize;
        if (pmnum % pageSize > 0) {
            page++;
        }
        String name = getIntent().getStringExtra("name");
        toolBar.setTitle("和" + name + "对话");
        requestMypm();

        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Event(R.id.toolbar_left)
    private void backClick(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        jumpActivitySetResult(null);
        super.onBackPressed();
    }

    @Event(R.id.chat_send_img)
    private void sendPmClick(View v) {
        if (mEditText.getText() != null)
            msg = mEditText.getText().toString();
        if (!DataUtils.isEmpty(msg))
            requestSendPm(msg);
    }

    @Event(R.id.chat_choose_look_img)
    private void chooseLook(View v) {
        if (biaoqingView.getVisibility() != View.VISIBLE)
            biaoqingView.setVisibility(View.VISIBLE);
    }

    @Event(value = {R.id.chat_listview, R.id.chat_edittext}, type = Touch.class)
    private boolean listOnTouch(View v, MotionEvent even) {
        if (biaoqingView.getVisibility() != View.GONE)
            biaoqingView.setVisibility(View.GONE);
        if (v.getId() == R.id.chat_edittext)
            mListView.setSelection(chatList.size() - 1);
        return false;
    }

    /**
     * 选择的表情
     *
     * @param bean
     */
    List<SmileyBean> sendSmileyList = new ArrayList<SmileyBean>();

    public void onEventMainThread(SmileyBean bean) {
        if (TextUtils.equals(bean.getImage(), "close")) {
            ViewTools.editTextDelete(mEditText);
        } else {
            //<img src="http://www.flyertea.com/static/image/smiley/default/handshake.gif" border="0" alt="" />
            //http://atf.flyert.com/static/image/smiley/
            String img = "<img src='http://www.flyertea.com/static/image/smiley/" + bean.getImage() + "' border='0' alt='' />";
            bean.setPath(img);
            sendSmileyList.add(bean);
            mEditText.getText().insert(mEditText.getSelectionStart(), bean.getCode());
        }
    }

    private void hintFaceView() {
        biaoqingView.setVisibility(View.GONE);
    }

    /**
     * 请求对话列表
     */
    private void requestMypm() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MYPM);
        params.addQueryStringParameter("subop", "view");
        params.addQueryStringParameter("touid", touid);
        params.addQueryStringParameter("page", page + "");
        XutilsHttp.Get(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                ListObjectBean beanList = JsonAnalysis.jsonToChatList(result);
                List<ChatBean> list = beanList.getDataList();
                if (list != null)
                    chatList.addAll(0, list);

                if (adapter == null) {
                    adapter = new ChatAdapter(ChatActivity.this, chatList, touid, mListView);
                    mListView.setAdapter(adapter);
                    adapter.setFaceClickListener(new ChatAdapter.IFaceListener() {
                        @Override
                        public void faceClick(int pos) {
                            if (UserManger.isLogin()) {
                                Bundle bundle = new Bundle();
                                bundle.putString("uid", chatList.get(pos).getAuthorid());
                                bundle.putString("activity", "SearchMemberActivity");
                                jumpActivity(UserDatumActvity.class, bundle);
                            } else {
                                DialogUtils.showDialog(ChatActivity.this);
                            }
                        }
                    });
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    /**
     * 发送私信
     */
    private void requestSendPm(final String msg) {
        String formhash = SharedPreferencesString.getInstances(this)
                .getStringToKey("formhash");
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SENDPM);
        params.addQueryStringParameter("touid", touid);
        params.addQueryStringParameter("formhash", formhash);
        params.addBodyParameter("message", URLEncoder.encode(msg));

        proDialogShow();
        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                BaseBean bean = JsonAnalysis.jsonToSendPm(result);
                if (bean.getCode().equals("do_success")) {
                    String face = SharedPreferencesString.getInstances(
                            ChatActivity.this).getStringToKey("user_icon");
                    ChatBean chatBean = new ChatBean();
                    chatBean.setSubject(replaceImgCodeToPath(msg));
                    chatBean.setFace(face);
                    chatBean.setTouid(touid);
                    chatBean.setAuthorid(UserManger.getUserInfo().getMember_uid());
                    chatList.add(chatBean);
                    adapter.notifyDataSetChanged();
                    mListView.setSelection(chatList.size() - 1);
                    mEditText.setText("");
                    sendSmileyList.clear();
                } else {
                    BToast(bean.getMessage());
                }
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialogDismiss();
            }
        });
    }


    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }


    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        if (page > 1) {
            page--;
            requestMypm();
        }
        onLoad();
    }

    @Override
    public void onLoadMore() {
        // TODO Auto-generated method stub

    }

    /**
     * 正则表达式，替换正文图片Uri为指定标识
     *
     * @param str
     * @param attachimgFlag true为替换，false为不替换(因为有在文本框中删除了图片的情况，false，表示删除无用集合)
     * @return
     */
    private String replaceImgCodeToPath(String str) {
        for (int i = 0; i < sendSmileyList.size(); i++) {
            SmileyBean info = sendSmileyList.get(i);
            //转意括号
            int start = info.getCode().indexOf("{");
            int end = info.getCode().indexOf("}");
            String code = "";
            if (start != -1)
                code = "\\" + info.getCode();
            if (end != -1) {
                code = code.replaceFirst("\\}", "");
                code = code + "\\}";
            }
            if (code.equals(""))
                code = info.getCode();
            str = str.replaceAll(code, info.getPath());
        }
        return str;
    }

}
