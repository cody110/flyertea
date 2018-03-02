package com.ideal.flyerteacafes.ui.activity.myinfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.GroupmembershipsBean;
import com.ideal.flyerteacafes.model.entity.UserCardBean;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IRegularCardInfo;
import com.ideal.flyerteacafes.ui.activity.presenter.RegularCardInfoPresenter;
import com.ideal.flyerteacafes.ui.activity.viewholder.RegularCardInfoVH;
import com.ideal.flyerteacafes.ui.popupwindow.CommonBottomPopupwindow;
import com.ideal.flyerteacafes.ui.popupwindow.GroupTypePopupWindow;
import com.ideal.flyerteacafes.ui.view.MyAlertDialog;
import com.ideal.flyerteacafes.ui.wheelview.adapters.base.ListWheelAdapter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;
import com.ideal.flyerteacafes.utils.tools.UriTools;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.List;

/**
 * Created by fly on 2017/5/22.
 */
@ContentView(R.layout.activity_regularcard_info)
public class RegularCardInfoActivity extends MVPBaseActivity<IRegularCardInfo, RegularCardInfoPresenter> {


    private static final int REQUEST_ADD = 1, REQUEST_EDIT = 2, REQUESTIMAGECODE = 3;

    View mView;
    RegularCardInfoVH vh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachView(iRegularCardInfo);
        x.view().inject(this);
        vh = new RegularCardInfoVH(mView = getRootView(), iActionListener);
        mPresenter.init(this);
        vh.initViewPager(mPresenter.getUserCardBean(), 0);
    }

    @Override
    protected RegularCardInfoPresenter createPresenter() {
        return new RegularCardInfoPresenter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {


            switch (requestCode) {
                case REQUEST_ADD:
                    UserCardBean.CardInfo cardInfo = (UserCardBean.CardInfo) data.getSerializableExtra("data");
                    mPresenter.getUserCardBean().getVips().add(cardInfo);
                    vh.initViewPager(mPresenter.getUserCardBean(), vh.mViewPager.getCurrentItem());
                    break;


                case REQUESTIMAGECODE:
                    Bitmap bitmap = null;
                    Uri originalUri = data.getData();
                    if (originalUri == null)
                        return;
                    bitmap = BitmapTools.revitionImageSize(originalUri, DensityUtil.dip2px(40), this);
                    if (bitmap != null) {
                        vh.rengzhen_img.setImageBitmap(bitmap);

                        String path;// 图片路径
                        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
                        if (!isKitKat) {
                            path = UriTools.getFilePathByUri(this, originalUri);
                        } else {
                            path = UriTools.getFilePathByUriTWO(RegularCardInfoActivity.this, originalUri);
                        }

                        if (path == null || path.length() == 0)
                            return;
                        mPresenter.setImgPath(path);
                    } else {
                        ToastUtils.showToast("获取图片失败");
                    }

                    break;
            }
        }

    }


    private void showRemindDialog() {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(this);
        builder.setTitle(null);
        builder.setMessage("您确定删除当前常客卡?");
        builder.setNegativeButton("取消");
        builder.setPositiveButton("确定", new MyAlertDialog.OnAlertViewClickListener() {
            @Override
            public void OnAlertViewClick() {
                mPresenter.requestDelete(vh.mViewPager.getCurrentItem());
            }
        });
        builder.create().show();
    }


    @Override
    public void onBackPressed() {
        jumpActivitySetResult(null);
    }

    IRegularCardInfo iRegularCardInfo = new IRegularCardInfo() {
        @Override
        public void initViewPager(UserCardBean userCardBean, int index) {
            if (DataUtils.isEmpty(userCardBean.getVips())) {
                onBackPressed();
            } else {
                vh.initViewPager(userCardBean, index);
            }
        }

        @Override
        public void disableButton() {
            vh.disableButton();
        }
    };

    RegularCardInfoVH.IActionListener iActionListener = new RegularCardInfoVH.IActionListener() {
        @Override
        public void actionClick(View v) {
            switch (v.getId()) {
                case R.id.toolbar_left:
                    onBackPressed();
                    break;
                case R.id.toolbar_right:
                    jumpActivityForResult(AddRegularCardActivity.class, null, REQUEST_ADD);
                    break;
                case R.id.change_btn:
                    mPresenter.requestUpdate(vh.id_layout.getTextByRe());
                    break;
                case R.id.delete_btn:
                    showRemindDialog();
                    break;
                case R.id.type_layout:
                    if (mPresenter.getGroupmembershipsBean() == null) return;
                    GroupTypePopupWindow popupWindow = new GroupTypePopupWindow(RegularCardInfoActivity.this, mPresenter.getGroupmembershipsBean().getHotelMembership(), new CommonBottomPopupwindow.ISureOK<GroupmembershipsBean.Type>() {
                        @Override
                        public void selectItem(GroupmembershipsBean.Type type, int pos) {
                            mPresenter.setGroupmemberType(type);
                            vh.type_layout.setTextByCt(type.getName());
                        }
                    });
                    popupWindow.showAtLocation(mView, Gravity.BOTTOM, 0, 0);
                    break;

                case R.id.grade_layout:
                    if (mPresenter.getGroupmembershipsBean() == null) return;
                    if (TextUtils.isEmpty(vh.type_layout.getTextByCt())) {
                        ToastUtils.showToast("请先选择常客卡类型");
                    } else {
                        if (mPresenter.getGroupmemberType() == null) {
                            mPresenter.setTypeByGroupId(mPresenter.getCardInfo().getGroupid());
                        }
                        if (mPresenter.getGroupmemberType() == null) return;
                        if (DataUtils.isEmpty(mPresenter.getGroupmemberType().getMemberships()))
                            return;
                        CommonBottomPopupwindow popupwindow = new CommonBottomPopupwindow<GroupmembershipsBean.Memberships>(RegularCardInfoActivity.this, mPresenter.getGroupmemberType().getMemberships(), new CommonBottomPopupwindow.ISureOK<GroupmembershipsBean.Memberships>() {
                            @Override
                            public void selectItem(GroupmembershipsBean.Memberships memberships, int pos) {
                                vh.grade_layout.setTextByCt(memberships.getName());
                                mPresenter.setMemberships(memberships);
                            }
                        }) {
                            @Override
                            protected void bindDatas(List<GroupmembershipsBean.Memberships> datas) {
                                ListWheelAdapter adapter = new ListWheelAdapter<GroupmembershipsBean.Memberships>(RegularCardInfoActivity.this, datas) {
                                    @Override
                                    public CharSequence getItemText(int index) {
                                        return items.get(index).getName();
                                    }
                                };
                                wheelView.setViewAdapter(adapter);
                            }
                        };
                        popupwindow.showAtLocation(mView, Gravity.BOTTOM, 0, 0);
                    }
                    break;
                case R.id.img_layout:
                    Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
                    getImage.addCategory(Intent.CATEGORY_OPENABLE);
                    getImage.setType("image/*");
                    startActivityForResult(getImage, REQUESTIMAGECODE);
                    break;
            }
        }

        @Override
        public void onPageSelected(int position) {
            mPresenter.setSelectVpIndex(position);
            vh.bindData(mPresenter.getUserCardBean(), mPresenter.getCardInfo());
        }
    };
}
