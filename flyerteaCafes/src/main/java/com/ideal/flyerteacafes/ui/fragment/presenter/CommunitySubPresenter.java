package com.ideal.flyerteacafes.ui.fragment.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.ideal.flyerteacafes.caff.ForumDataManger;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubDetailsBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubTypeBean;
import com.ideal.flyerteacafes.model.entity.ThreadSubjectBean;
import com.ideal.flyerteacafes.model.entity.MyFavoriteBean;
import com.ideal.flyerteacafes.model.entity.TopThreadBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.AlbumActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.AutoPlayListFmPresenter;
import com.ideal.flyerteacafes.ui.activity.search.ThreadSearchActivity;
import com.ideal.flyerteacafes.ui.activity.ThreadTypeActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.AlbumPresenter;
import com.ideal.flyerteacafes.ui.activity.search.ThreadSearchActivity2;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteMajorThreadContentActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteThreadPresenter;
import com.ideal.flyerteacafes.ui.fragment.interfaces.ICommunitySubFm;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.Tools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/11/29.
 */

public class CommunitySubPresenter extends AutoPlayListFmPresenter {


    /**
     * 跳转type分类页面code
     */
    public static final int REQUEST_THREAD_TYPE_ACTIVITY = 1;

    /**
     * 版块分类
     */
    private List<CommunitySubTypeBean> communitySubTypeBeanList = new ArrayList<>();


    /**
     * 所有置顶帖
     *
     * @param intent
     */

    private List<TopThreadBean> allTopThreadList;

    /**
     * 适配器需要的置顶帖
     */
    private List<TopThreadBean> needTopThreadList = new ArrayList<>();

    /**
     * 选中type的下标
     */
    private int selectTypeIndex = 0;

    /**
     * 关注id >0 已关注
     */
    private String favid = "0";

    /**
     * 排序规则 默认按回复
     */
    private String orderby = "lastpost";

    /**
     * 页面回掉接口
     */
    ICommunitySubFm iCommunitySubFm;

    public String fid1, fname1, fid2, fname2;

    public void setOrderby(String sort) {
        orderby = sort;
    }

    public List<TopThreadBean> getNeedTopThreadList() {
        return needTopThreadList;
    }


    public void setiCommunitySubFm(ICommunitySubFm i) {
        this.iCommunitySubFm = i;
    }

    /**
     * 是否关注默认false
     */
    private boolean isFav() {
        if (DataTools.getInteger(favid) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public CommunitySubDetailsBean communitySubDetailsBean;

    @Override
    public void init(Activity activity) {
        super.init(activity);
        requestForumInfo();
        requestTopThreads();
        requestFavorite();
        getView().callbackData(datas);
    }


    @Override
    public boolean isFirstLoadNeedHeader() {
        return false;
    }

    @Override
    protected void getIntentDatas(Intent intent) {
        super.getIntentDatas(intent);
        fid1 = intent.getStringExtra(WriteThreadPresenter.BUNDLE_FID_1);
        fname1 = intent.getStringExtra(WriteThreadPresenter.BUNDLE_NAME_1);
        fid2 = intent.getStringExtra(WriteThreadPresenter.BUNDLE_FID_2);
        String sub_sort = SharedPreferencesString.getInstances().getStringToKey("sub_sort");
        if (!TextUtils.isEmpty(sub_sort))
            orderby = sub_sort;
        iCommunitySubFm.actionSetTvSort(orderby);

    }

    @Override
    public void requestDatas() {
        getDialog().proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FORUMDISPLAY);
        params.addQueryStringParameter("appversion", Tools.getVersion(context));
        params.addQueryStringParameter("orderby", orderby);
        params.addQueryStringParameter("page", String.valueOf(page));
        params.addQueryStringParameter("fid", fid2);
        if (page > 1 && !TextUtils.isEmpty(ver))
            params.addQueryStringParameter("ver", ver);

        if (selectTypeIndex == 0) {
            params.addQueryStringParameter("filter", "typeid");
        } else if (selectTypeIndex == 1) {
            params.addQueryStringParameter("filter", "heat");
            params.addQueryStringParameter("orderby", "heats");
        } else if (selectTypeIndex == 2) {
            params.addQueryStringParameter("filter", "digest");
            params.addQueryStringParameter("digest", "1");
        } else {
            params.addQueryStringParameter("filter", "typeid");

            String typeid = communitySubTypeBeanList.get(selectTypeIndex).getFid();


            boolean isFind = false;

            if (!DataTools.isEmpty(communitySubDetailsBean.getRelatedgroupList())) {
                for (CommunitySubTypeBean bean : communitySubDetailsBean.getRelatedgroupList()) {
                    if (TextUtils.equals(bean.getFid(), typeid)) {
                        params.addQueryStringParameter("relatedgroupid", typeid);
                        params.addQueryStringParameter("relatedgroup", "1");
                        isFind = true;
                    }
                }
            }

            if (!isFind) {
                if (!DataTools.isEmpty(communitySubDetailsBean.getSubtypesList())) {
                    for (CommunitySubTypeBean bean : communitySubDetailsBean.getSubtypesList()) {
                        if (TextUtils.equals(bean.getFid(), typeid)) {
                            params.addQueryStringParameter("subtypeid", typeid);
                            isFind = true;
                        }
                    }
                }
            }

            if (!isFind) {
                params.addQueryStringParameter("typeid", typeid);
            }
        }

        if (selectTypeIndex > 2) {
            XutilsHttp.Get(params, new VideoListDataHandlerCallback(ListDataCallback.LIST_KEY_FORUM_THREADLIST, ThreadSubjectBean.class) {
                @Override
                public void flySuccess(ListDataBean response) {
                    if (!isViewAttached()) return;
                    super.flySuccess(response);
                    iCommunitySubFm.actionShowAdapterType(false);
                }
            });
        } else {
            XutilsHttp.Get(params, new VideoListDataHandlerCallback(ListDataCallback.LIST_KEY_FORUM_THREADLIST, ThreadSubjectBean.class) {
                @Override
                public void flySuccess(ListDataBean response) {
                    if (!isViewAttached()) return;
                    super.flySuccess(response);
                    iCommunitySubFm.actionShowAdapterType(true);
                }
            });
        }

    }


    /**
     * 请求具体版块信息
     */
    private void requestForumInfo() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FORUMDETAIL);
        params.addQueryStringParameter("fid", fid2);
        XutilsHttp.Get(params, new PDataCallback<CommunitySubDetailsBean>() {
            @Override
            public void flySuccess(DataBean<CommunitySubDetailsBean> result) {
                if (!isViewAttached()) return;
                if (result.getDataBean() == null) return;
                communitySubDetailsBean = result.getDataBean();
                fname2 = result.getDataBean().getName();

                List<CommunitySubTypeBean> typeBeanList = new ArrayList<>();
                if (!DataUtils.isEmpty(communitySubDetailsBean.getRelatedgroupList()))
                    typeBeanList.addAll(communitySubDetailsBean.getRelatedgroupList());
                if (!DataUtils.isEmpty(communitySubDetailsBean.getSubtypesList()))
                    typeBeanList.addAll(communitySubDetailsBean.getSubtypesList());

                if (DataUtils.isEmpty(typeBeanList)) {
                    if (!DataUtils.isEmpty(communitySubDetailsBean.getSubforumList()))
                        typeBeanList.addAll(communitySubDetailsBean.getSubforumList());
                }


                initCommunitySubTypeList(typeBeanList);
                iCommunitySubFm.actionForumType(communitySubTypeBeanList, fname2);

                iCommunitySubFm.actionForumInfo(result.getDataBean());
            }
        });
    }


    /**
     * 置顶帖
     */
    private void requestTopThreads() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_TOPTHREADS);
        params.addQueryStringParameter("fid", fid2);
        params.addQueryStringParameter("appversion", Tools.getVersion(context));
        XutilsHttp.Get(params, new PListDataCallback(ListDataCallback.DATA, TopThreadBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (!isViewAttached()) return;
                allTopThreadList = result.getDataList();
                if (allTopThreadList == null || allTopThreadList.size() <= 4)
                    iCommunitySubFm.actionIsShowTopMoreBtn(false);
                else
                    iCommunitySubFm.actionIsShowTopMoreBtn(true);
                setNeedTopThreadList(false);
            }
        });
    }


    /**
     * 初始化分类数据
     *
     * @param typeBeanList
     */
    private void initCommunitySubTypeList(List<CommunitySubTypeBean> typeBeanList) {
        CommunitySubTypeBean bean1 = new CommunitySubTypeBean();
        bean1.setName("全部");
        communitySubTypeBeanList.add(bean1);
        CommunitySubTypeBean bean2 = new CommunitySubTypeBean();
        bean2.setName("热门");
        communitySubTypeBeanList.add(bean2);
        CommunitySubTypeBean bean3 = new CommunitySubTypeBean();
        bean3.setName("精华");
        communitySubTypeBeanList.add(bean3);
        if (typeBeanList != null && !typeBeanList.isEmpty())
            communitySubTypeBeanList.addAll(typeBeanList);
    }


    /**
     * 设置需要的置顶帖数据
     *
     * @param isAll
     */
    public void setNeedTopThreadList(boolean isAll) {
        needTopThreadList.clear();
        if (allTopThreadList != null && !allTopThreadList.isEmpty()) {
            if (isAll) {
                needTopThreadList.addAll(allTopThreadList);
            } else {
                if (allTopThreadList.size() > 0)
                    needTopThreadList.add(allTopThreadList.get(0));
                if (allTopThreadList.size() > 1)
                    needTopThreadList.add(allTopThreadList.get(1));
                if (allTopThreadList.size() > 2)
                    needTopThreadList.add(allTopThreadList.get(2));
                if (allTopThreadList.size() > 3)
                    needTopThreadList.add(allTopThreadList.get(3));
            }
        }
        iCommunitySubFm.actionTopThread(needTopThreadList);
    }


    public void communitySubTypeLayoutItemClick(int pos) {
        if (selectTypeIndex != pos) {
            selectTypeIndex = pos;
            page = 1;
            requestDatas();
        }
    }

    /**
     * 点击关注
     */
    public void actionFollow() {
        if (isFav()) {
            requestCancleFollow();
        } else {
            requestFollow();

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", fname2);
            MobclickAgent.onEvent(context, FinalUtils.EventId.forum_subscribe, map);
        }
    }


    /**
     * 关注
     */
    public void requestFollow() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FORUMACTION);
        params.addQueryStringParameter("action", "follow");
        params.addQueryStringParameter("id", fid2);
        params.addQueryStringParameter("version", "5");

        XutilsHttp.Get(params, new PListDataCallback(ListDataCallback.DATA, MyFavoriteBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (!isViewAttached()) return;
                if (TextUtils.equals(result.getCode(), "favorite_do_success")) {
                    List<MyFavoriteBean> list = result.getDataList();
                    if (list != null && !list.isEmpty()) {
                        MyFavoriteBean myFavoriteBean = (MyFavoriteBean) result.getDataList().get(0);
                        favid = myFavoriteBean.getFavid();
                        iCommunitySubFm.actionIsFavorite(isFav());
                        TagEvent tagEvent = new TagEvent(TagEvent.TAG_FAV_FOLLOW);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", myFavoriteBean);
                        tagEvent.setBundle(bundle);
                        EventBus.getDefault().post(tagEvent);
                    }
                }
                ToastUtils.showToast(result.getMessage());
            }
        });
    }

    /**
     * 取消关注
     */
    public void requestCancleFollow() {
        if (TextUtils.equals(favid, "0")) return;//因为id是从我的关注里遍历出来
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FORUMACTION);
        params.addQueryStringParameter("action", "unfollow");
        params.addQueryStringParameter("favid", favid);
        XutilsHttp.Get(params, new PBaseCallback() {
            @Override
            public void flySuccess(BaseBean result) {
                if (!isViewAttached()) return;
                if (TextUtils.equals(result.getCode(), "do_success")) {
                    MyFavoriteBean favBean = new MyFavoriteBean();
                    favBean.setFavid(favid);
                    favBean.setId(fid2);
                    favBean.setTitle(fname2);
                    TagEvent tagEvent = new TagEvent(TagEvent.TAG_FAV_CANCLE);
                    Bundle bundle = new Bundle();
                    tagEvent.setBundle(bundle);
                    bundle.putSerializable("data", favBean);
                    EventBus.getDefault().post(tagEvent);
                    favid = "0";
                    iCommunitySubFm.actionIsFavorite(isFav());
                    ToastUtils.showToast("取消收藏成功");
                } else {
                    ToastUtils.showToast(result.getMessage());
                }
            }
        });
    }


    /**
     * 我的关注
     */
    public void requestFavorite() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FAVORITE);
        XutilsHttp.Get(params, new PListDataCallback(ListDataCallback.LIST_KEY_MY_FAOORITE, MyFavoriteBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (!isViewAttached()) return;
                if (!DataUtils.isEmpty(result.getDataList())) {
                    List<MyFavoriteBean> listFav = result.getDataList();
                    for (MyFavoriteBean bean : listFav) {
                        if (TextUtils.equals(bean.getId(), fid2)) {
                            favid = bean.getFavid();
                            break;
                        }
                    }
                }
                iCommunitySubFm.actionIsFavorite(isFav());
            }
        });
    }



    /**
     * 存在只传二级fid的情况
     */
    public void toWriteThread() {
        Bundle bundle = toWriteThreadBundle();
        if (bundle != null)
            getBaseView().jumpActivity(WriteMajorThreadContentActivity.class, bundle);
    }


    private Bundle toWriteThreadBundle() {
        if (TextUtils.isEmpty(fid1)) {
            CommunityBean bean = ForumDataManger.getInstance().getCommunityBeanByFid2(fid2);
            if (bean == null) {
                return null;
            } else {
                fid1 = bean.getFid();
                fname1 = bean.getName();
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString(WriteThreadPresenter.BUNDLE_FID_1, fid1);
        bundle.putString(WriteThreadPresenter.BUNDLE_NAME_1, fname1);
        bundle.putString(WriteThreadPresenter.BUNDLE_FID_2, fid2);
        bundle.putString(WriteThreadPresenter.BUNDLE_NAME_2, fname2);

        LogFly.e("fid1:" + fid1 + "fid2:" + fid2);

        bundle.putString(AlbumPresenter.BUNDLE_FROM_TYPE, AlbumPresenter.BUNDLE_FROM_MAJOR_THREAD_FIRST);
        bundle.putInt(AlbumPresenter.BUNDLE_NEED_SIZE, 30);


        return bundle;
    }


    /**
     * 去搜索
     */
    public void toSearchThread() {
        Bundle bundle = new Bundle();
        bundle.putString(IntentKey.BUNDLE_FID_KEY, fid2);
        getBaseView().jumpActivity(ThreadSearchActivity2.class, bundle);
    }
}
