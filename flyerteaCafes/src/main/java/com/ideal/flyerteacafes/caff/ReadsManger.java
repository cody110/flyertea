package com.ideal.flyerteacafes.caff;

import android.text.TextUtils;

import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.dal.BaseHelper;
import com.ideal.flyerteacafes.model.entity.ArticleContentBean;
import com.ideal.flyerteacafes.model.entity.ThreadDetailsBean;
import com.ideal.flyerteacafes.model.entity.ReadsBean;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fly on 2016/12/29.
 */

public class ReadsManger {


    /**
     * 满多少条保存
     */
    private static final int KEY_SAVE_SIZE = 20;

    private static ReadsManger instance;

    private ReadsManger() {
    }

    public static ReadsManger getInstance() {
        if (instance == null) {
            synchronized (ReadsManger.class) {
                instance = new ReadsManger();
            }
        }
        return instance;
    }


    /**
     * 保存文章
     *
     * @param articleBean
     */
    public void save(ArticleContentBean articleBean) {
        ReadsBean bean = new ReadsBean();
        bean.setFid(String.valueOf(articleBean.getFid()));
        bean.setFname(ForumDataManger.getInstance().getForumName(String.valueOf(articleBean.getFid())));
        bean.setIdtype(ReadsBean.IDTYPE_AID);
        bean.setDbdateline(String.valueOf(DateUtil.getDateline()));
        bean.setSubject(articleBean.getSubject());
        bean.setTid(String.valueOf(articleBean.getTid()));
        bean.setUid(UserManger.isLogin() ? UserManger.getUserInfo().getMember_uid() : "0");
        bean.setProfessional("1");
        save(bean);
    }

    /**
     * 保存帖子
     *
     * @param threadBean
     */
    public void save(ThreadDetailsBean threadBean) {
        ReadsBean bean = new ReadsBean();
        bean.setFid(String.valueOf(threadBean.getFid()));
        bean.setFname(threadBean.getForumname());
        bean.setIdtype(ReadsBean.IDTYPE_TID);
        bean.setDbdateline(String.valueOf(DateUtil.getDateline()));
        bean.setSubject(threadBean.getSubject());
        bean.setTid(String.valueOf(threadBean.getTid()));
        bean.setUid(UserManger.isLogin() ? UserManger.getUserInfo().getMember_uid() : "0");
        bean.setProfessional(threadBean.isProfessional() ? "1" : "0");
        save(bean);
    }

    /***
     * 保存阅读记录
     *
     * @param readsBean
     */
    public void save(ReadsBean readsBean) {
        try {
            BaseHelper.getInstance().getDbUtils().delete(ReadsBean.class, WhereBuilder.b("tid", "=", readsBean.getTid()).
                    and("uid", "=", UserManger.isLogin() ? UserManger.getUserInfo().getMember_uid() : "0"));
        } catch (DbException e) {
            e.printStackTrace();
        }
        BaseHelper.getInstance().saveBean(readsBean);


        List<ReadsBean> list = getUserListAll();
        if (list != null && list.size() >= KEY_SAVE_SIZE) {
            reqeust(list);
        }
    }


    /**
     * 请求上报接口
     *
     * @param list
     */
    private void reqeust(List<ReadsBean> list) {
        final String uid = UserManger.getUserInfo().getMember_uid();
        Map<String, Object> map = new HashMap<>();
        map.put("reads", list);
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_USER_READS_POST);
        params.setBodyJson(map);
        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                boolean isSuccess = false;
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("Variables");
                    String success = jsonObject.getString("sucess");
                    isSuccess = TextUtils.equals(success, "1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (isSuccess) {
                    BaseHelper.getInstance().delete(ReadsBean.class, "uid", "=", uid);
                }
            }
        });
    }

    /**
     * 重启应用或者退出账号时上报
     */
    public void upload() {
        List<ReadsBean> list = getUserListAll();
        if (list != null && !list.isEmpty()) {
            reqeust(list);
        }
    }

    /**
     * 获取当前用户所有阅读记录
     *
     * @return
     */
    private List<ReadsBean> getUserListAll() {
        List<ReadsBean> list = null;
        if (UserManger.isLogin()) {
            list = BaseHelper.getInstance().getList(ReadsBean.class, "uid", "=", UserManger.getUserInfo().getMember_uid());
        }
        return list;
    }

    /**
     * 获取当前用户所有阅读记录
     *
     * @return
     */
    public List<ReadsBean> getLocaListAll() {
        List<ReadsBean> list = BaseHelper.getInstance().getList(ReadsBean.class, "uid", "=", "0");
        return list;
    }


}
