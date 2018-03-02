package com.ideal.flyerteacafes.ui.activity.search;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.dal.FriendsInfoHelper;
import com.ideal.flyerteacafes.model.entity.FriendsInfo;
import com.ideal.flyerteacafes.model.entity.SearchMemberBean;
import com.ideal.flyerteacafes.model.entity.SearchMemberBean.SearchMemberInfo;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.ui.activity.UserDatumActvity;
import com.ideal.flyerteacafes.ui.activity.UserInfoActivity;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_search_member)
public class SearchMemberActivity extends BaseActivity {

    @ViewInject(R.id.search_member_root)
    View rootView;
    @ViewInject(R.id.search_member_et_search)
    EditText searchET;
    @ViewInject(R.id.search_member_list)
    ListView searchList;
    @ViewInject(R.id.search_member_et_search_remind)
    TextView remindText;

    CommonAdapter<SearchMemberInfo> adapter;
    CommonAdapter<FriendsInfo> friendAdapter;

    List<SearchMemberInfo> memberList;
    List<FriendsInfo> friendList;
    String mark;
    UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mark = getIntent().getStringExtra("mark");
        userBean = (UserBean) getIntent().getSerializableExtra("userBean");
        if (mark == null)
            mark = "";
        if (isAdd())
            memberList = new ArrayList<SearchMemberInfo>();
        else
            friendList = new ArrayList<FriendsInfo>();
    }

    @Event({R.id.searchmember_left_title_back_layout})
    private void onActionClick(View v) {
        if (v.getId() == R.id.searchmember_left_title_back_layout) {
            finish();
        }
    }

    private void requestSearchMember(String name) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SEARCH_MEMBER);
        params.addQueryStringParameter("kw", name);
        XutilsHttp.Get(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                memberList.clear();
                SearchMemberBean bean = JsonAnalysis.getSearchMember(result);
                if (bean != null && bean.getList() != null) {
                    memberList.addAll(bean.getList());
                }
                if (adapter == null) {
                    adapter = new CommonAdapter<SearchMemberBean.SearchMemberInfo>(
                            getApplicationContext(), memberList,
                            R.layout.listview_search_member) {

                        @Override
                        public void convert(ViewHolder holder, SearchMemberInfo t) {
                            holder.setImageUrl(R.id.listview_search_member_face, t.getFace(), R.drawable.def_face);
                            if (t.getUsername() != null)
                                holder.setText(R.id.listview_search_member_name,
                                        t.getUsername());
                            if (t.getSightml() == null || t.getSightml().equals("")) {
                                holder.setText(R.id.listview_search_member_sign,
                                        getString(R.string.not_sightml));
                            } else {
                                holder.setText(R.id.listview_search_member_sign,
                                        t.getSightml());
                            }

                        }
                    };
                    searchList.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

        });
    }

    private void searchFriends(String username) {
        friendList.clear();
        List<FriendsInfo> list = FriendsInfoHelper.getInstance()
                .getFriendsInfoByUserName(username);
        if (list == null) {
            if (friendAdapter != null)
                adapter.notifyDataSetChanged();
            return;
        } else {
            friendList.addAll(list);
        }
        if (friendAdapter == null) {
            friendAdapter = new CommonAdapter<FriendsInfo>(this, friendList,
                    R.layout.item_friendinfo) {

                @Override
                public void convert(ViewHolder holder, FriendsInfo f) {
                    // TODO Auto-generated method stub
                    holder.setText(R.id.friend_name, f.getUsername());
                    holder.setImageUrl(R.id.head_icon, f.getFace(), R.drawable.def_face);
                    holder.setVisible(R.id.catalog, false);
                }
            };
            searchList.setAdapter(friendAdapter);
        } else {
            friendAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            String username = searchET.getText().toString();
            if (!DataUtils.isEmpty(username)) {
                if (mark == null)
                    return super.dispatchKeyEvent(event);
                if (isAdd())
                    requestSearchMember(username);
                else
                    searchFriends(username);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Event(value = R.id.search_member_list, type = AdapterView.OnItemClickListener.class)
    private void toUserDatumActivity(AdapterView<?> parent, View view,
                                     int position, long id) {
        if (isAdd()) {
            SearchMemberInfo info = (SearchMemberInfo) adapter
                    .getItem(position);
            String uid = SharedPreferencesString.getInstances(SearchMemberActivity.this).getStringToKey("uid");

            if (uid.equals(info.getUid())) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("userBean", userBean);
                bundle.putString("type", "search");
                jumpActivity(UserInfoActivity.class, bundle);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("uid", info.getUid());
                bundle.putString("activity", "SearchMemberActivity");
                jumpActivity(UserDatumActvity.class, bundle);
            }
        } else {
            FriendsInfo info = (FriendsInfo) friendAdapter
                    .getItem(position);
            Bundle bundle = new Bundle();
            bundle.putString("uid", info.getUid());
            bundle.putString("activity", "FriendsFragment");
            jumpActivity(UserDatumActvity.class, bundle);
        }
    }

    private boolean isAdd() {
        return mark.equals("add");
    }

}
