package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.BaseBeanCallback;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.GroupmembershipsBean;
import com.ideal.flyerteacafes.model.entity.UserCardBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IRegularCardInfo;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.UploadTask;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fly on 2017/6/5.
 */

public class RegularCardInfoPresenter extends BasePresenter<IRegularCardInfo> {

    private UserCardBean userCardBean;
    private GroupmembershipsBean groupmembershipsBean;
    private GroupmembershipsBean.Type groupmemberType;
    private GroupmembershipsBean.Memberships memberships;
    private String imgPath;
    private int selectVpIndex = 0;

    public void setSelectVpIndex(int selectVpIndex) {
        this.selectVpIndex = selectVpIndex;
    }

    public UserCardBean.CardInfo getCardInfo() {
        return userCardBean.getVips().get(selectVpIndex);
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public GroupmembershipsBean.Type getGroupmemberType() {
        return groupmemberType;
    }

    public UserCardBean getUserCardBean() {
        return userCardBean;
    }

    public GroupmembershipsBean getGroupmembershipsBean() {
        return groupmembershipsBean;
    }

    public void setGroupmemberType(GroupmembershipsBean.Type groupmemberType) {
        this.groupmemberType = groupmemberType;
    }

    public GroupmembershipsBean.Memberships getMemberships() {
        return memberships;
    }

    public void setMemberships(GroupmembershipsBean.Memberships memberships) {
        this.memberships = memberships;
    }

    @Override
    public void init(Activity activity) {
        super.init(activity);
        userCardBean = (UserCardBean) activity.getIntent().getSerializableExtra("data");
        requestGroupMemberShipsData();
        if (TextUtils.equals(userCardBean.getStatus(), "1")) {
            getView().disableButton();
        }
    }

    //TODO 该接口没有验证
    public void requestDelete(int index) {
        UserCardBean.CardInfo cardInfo = userCardBean.getVips().get(index);
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_DELETE_CARD);
        HashMap<String, String> map = new HashMap<>();
        map.put("id", cardInfo.getId());
        params.setBodyJson(map);
        XutilsHttp.Post(params, new BaseBeanCallback() {
            @Override
            public void flySuccess(BaseBean result) {
                if (!isViewAttached()) return;
                userCardBean.getVips().remove(selectVpIndex);
                getView().initViewPager(userCardBean, 0);
            }
        });
    }


    /**
     * 常客卡信息
     */
    private void requestGroupMemberShipsData() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_GROUPMEMBERSHIPS);
        XutilsHttp.Get(params, new DataCallback<GroupmembershipsBean>() {
            @Override
            public void flySuccess(DataBean<GroupmembershipsBean> result) {
                if (!isViewAttached()) return;
                groupmembershipsBean = result.getDataBean();
            }
        });
    }

    public void setTypeByGroupId(String id) {
        for (int i = 0; i < groupmembershipsBean.getHotelMembership().size(); i++) {
            if (TextUtils.equals(groupmembershipsBean.getHotelMembership().get(i).getGroupid(), id)) {
                groupmemberType = groupmembershipsBean.getHotelMembership().get(i);
            }
        }
    }

    /**
     * 上传图片
     */
    private void sendPic(final String id) {
        final String webPath = "/flyertea-app/" + UserManger.getUserInfo().getMember_uid() + "/" + DateUtil.getDateline() + ".jpg";

        UploadTask task = new UploadTask();
        task.setIUploadStatus(new UploadTask.IUploadStatus() {
            @Override
            public void uploadStatus(boolean result, Object data) {
                if (result) {
                    requestCommit("http://app.flyert.com" + webPath, id);
                } else {
                    getDialog().proDialogDissmiss();
                }
            }
        });
        task.uploadFeed(0, webPath);
        task.execute(imgPath);
    }


    /**
     * 保存
     *
     * @param img_url
     */
    private void requestCommit(String img_url, String id) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_CARD_RENGZHENG);
        Map<String, String> value = new HashMap<>();
        value.put("id", getCardInfo().getId());
        value.put("code", id);
        if (groupmemberType == null) {
            value.put("groupid", getCardInfo().getGroupid());
        } else {
            value.put("groupid", groupmemberType.getGroupid());
        }
        value.put("img_url", img_url);
        if (memberships == null) {
            value.put("mid", getCardInfo().getMid());
        } else {
            value.put("mid", memberships.getMid());
        }
        value.put("type", "hotel");
        params.setBodyJson(value);
        XutilsHttp.Post(params, new PDataCallback<UserCardBean.CardInfo>() {

            @Override
            public void flySuccess(DataBean<UserCardBean.CardInfo> result) {
                if (!isViewAttached()) return;
                if (JsonAnalysis.isSuccessEquals1By2(result.getData())) {
                    ToastUtils.showToast("保存成功");
                    userCardBean.getVips().remove(selectVpIndex);
                    userCardBean.getVips().add(selectVpIndex, result.getDataBean());
                    getView().initViewPager(userCardBean, selectVpIndex);
                } else {
                    ToastUtils.showToast(result.getMessage());
                }
            }

        });
    }


    /**
     * 修改
     *
     * @param id
     */
    public void requestUpdate(String id) {
        if (TextUtils.isEmpty(id)) {
            ToastUtils.showToast("请输入常客卡卡号");
            return;
        }
        if (imgPath != null) {
            getDialog().proDialogShow();
            sendPic(id);
        } else {
            requestCommit(getCardInfo().getImg_url(), id);
        }
    }

}
