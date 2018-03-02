package com.ideal.flyerteacafes.ui.activity.writethread;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.flyer.tusdk.Activity.PlayVideoActivity;
import com.flyer.tusdk.model.VideoInfo;
import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.WriteThreadAdapter;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.model.entity.LocationListBean;
import com.ideal.flyerteacafes.model.entity.SmileyBean;
import com.ideal.flyerteacafes.model.entity.TagBean;
import com.ideal.flyerteacafes.model.loca.TuwenInfo;
import com.ideal.flyerteacafes.ui.activity.AlbumActivity;
import com.ideal.flyerteacafes.ui.activity.LocationListActivity;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.activity.MyLocationActivity;
import com.ideal.flyerteacafes.ui.activity.RatingActivity;
import com.ideal.flyerteacafes.ui.activity.video.DeleteVideoActivity;
import com.ideal.flyerteacafes.ui.activity.video.VideoListActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IWriteMajorThreadContent;
import com.ideal.flyerteacafes.ui.activity.presenter.AlbumPresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteMajorThreadContentPresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.viewholder.WriteMajorThreadVH;
import com.ideal.flyerteacafes.ui.popupwindow.WriteMajorThreadMenuPopupWindow;
import com.ideal.flyerteacafes.ui.view.MyAlertDialog;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ContentView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/3/10.
 */
@ContentView(R.layout.activity_write_majorthreadcontent)
public class WriteMajorThreadContentActivity extends MVPBaseActivity<IWriteMajorThreadContent, WriteMajorThreadContentPresenter> {


    private WriteThreadAdapter writeThreadAdapter;
    WriteMajorThreadMenuPopupWindow writeMajorThreadMenuPopupWindow;

    public static final int REQUESTCODE_ADD_IMGTEXT = 1, REQUESTCODE_ADD_IMG = 2, REQUESTCODE_EDIT_TEXT = 3, REQUESTCODE_EDIT_IMG = 5, requestLocation = 6, requestForumChoose = 7, requestFlyLocation = 8, REQUEST_VIDEO = 9, REQUEST_DELETE_VIDEO = 10;

    private WriteMajorThreadVH vh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_majorthreadcontent);
        mPresenter.attachView(iWriteMajorThreadContent);
        vh = new WriteMajorThreadVH(getRootView(), iActionListener);
        vh.init(smileyBeanList);
        EventBus.getDefault().register(this);
        mPresenter.init(this);
        writeThreadAdapter.setIClick(itemClick);
    }


    /**
     * persenter 回调
     */
    IWriteMajorThreadContent iWriteMajorThreadContent = new IWriteMajorThreadContent() {

        @Override
        public void bindAdapter(List<TuwenInfo> tuwenInfoList) {
            if (writeThreadAdapter == null) {
                writeThreadAdapter = new WriteThreadAdapter(FlyerApplication.getContext(), tuwenInfoList, R.layout.item_write_major_post_list);
            }
            vh.bindAdapter(writeThreadAdapter);
        }

        /**
         * 删除确认提示框
         */
        @Override
        public void showDeleteRemindDialog(final int pos) {
            MyAlertDialog.Builder builder = new MyAlertDialog.Builder(WriteMajorThreadContentActivity.this);
            builder.setMessage("是否确认删除整个区域");
            builder.setNegativeButton("删除", new MyAlertDialog.OnAlertViewClickListener() {
                @Override
                public void OnAlertViewClick() {
                    mPresenter.deleteItem(pos);
                }
            });
            builder.setPositiveButton("取消");
            builder.create().show();
        }

        @Override
        public void uploadProgress(final int allCount, final int successCount, final int errorCount) {
            vh.bindUploadProgress(allCount, successCount, errorCount);
        }

        @Override
        public void threadCountText(String countText, String attachId, String vids) {
            Bundle bundle = new Bundle();
            bundle.putString(WriteThreadPresenter.BUNDLE_FROM_TYPE, mPresenter.from_type);
            bundle.putString("subject", vh.getThreadTitle());
            bundle.putString("message", countText);
            bundle.putString("attachId", attachId);
            bundle.putString("location", vh.getLocationName());
            bundle.putString("vids", vids);
            bundle.putString(WriteThreadPresenter.BUNDLE_FID_1, mPresenter.fid1);
            bundle.putString(WriteThreadPresenter.BUNDLE_FID_2, mPresenter.fid2);
            bundle.putString(WriteThreadPresenter.BUNDLE_FID_3, mPresenter.fid3);
            bundle.putString(WriteThreadPresenter.BUNDLE_TYPEID, mPresenter.typeid);

            if (mPresenter.locationBean != null) {
                bundle.putSerializable("locationBean", mPresenter.locationBean);
                bundle.putFloat("star", mPresenter.star);
                if (mPresenter.tagsList != null) {
                    StringBuffer tagName = new StringBuffer();
                    StringBuffer utagName = new StringBuffer();
                    for (TagBean tag : mPresenter.tagsList) {
                        if (tag.isSelect()) {
                            tagName.append(tag.getTagname());
                            tagName.append(";");
                            if (TextUtils.isEmpty(tag.getTagid()) && TextUtils.isEmpty(tag.getType())) {
                                utagName.append(tag.getTagname());
                                utagName.append(";");
                            }
                        }
                    }
                    if (tagName.toString().endsWith(";")) {
                        tagName.deleteCharAt(tagName.toString().length() - 1);
                    }
                    if (utagName.toString().endsWith(";")) {
                        utagName.deleteCharAt(utagName.toString().length() - 1);
                    }
                    bundle.putString("tags", tagName.toString());
                    bundle.putString("utags", utagName.toString());
                    bundle.putString("flight", mPresenter.flight);
                    bundle.putString("flightid", mPresenter.flightid);
                    bundle.putString("airportid", mPresenter.airportid);
                }
            }
            bundle.putString("collectionid", mPresenter.collectionid);

            jumpActivityForResult(ForumChooseActivity.class, bundle, requestForumChoose);
        }

        @Override
        public void threadTitleSubjectLocation(String title, String subject, String location, String topicName) {
            vh.bindThreadTitleSubjectLocation(title, subject, location, topicName);
        }
    };


    /**
     * list item 事件监听
     */
    WriteThreadAdapter.IClick itemClick = new WriteThreadAdapter.IClick() {
        @Override
        public void deleteClick(int pos) {
            iWriteMajorThreadContent.showDeleteRemindDialog(pos);
        }

        @Override
        public void imgClick(int pos) {
            if (mPresenter.isEditModle) return;
            if (TuwenInfo.TYPE_TU == mPresenter.getTuwenInfoList().get(pos).getType()) {
                Bundle bundle = new Bundle();
                bundle.putString(WriteMajorThreadEditImgActivity.BUNDLE_IMG_PATH, mPresenter.getTuwenInfoList().get(pos).getImgPath());
                bundle.putInt(WriteMajorThreadEditImgActivity.BUNDLE_LIST_POS, pos);
                jumpActivityForResult(WriteMajorThreadEditImgActivity.class, bundle, WriteMajorThreadContentActivity.REQUESTCODE_EDIT_IMG);
            }
        }

        @Override
        public void videoClick(int pos) {
            Bundle bundle = new Bundle();
            bundle.putInt("pos", pos);
            bundle.putString(IntentKey.VIDEO_URL, mPresenter.getTuwenInfoList().get(pos).getVideoPath());
            jumpActivityForResult(DeleteVideoActivity.class, bundle, REQUEST_DELETE_VIDEO);
        }

        @Override
        public void textClick(int pos) {
            if (mPresenter.isEditModle) return;
            Bundle bundle = new Bundle();
            bundle.putInt(EditMajorThreadImgTextActivity.BUNDLE_LIST_POS, pos);
            bundle.putString(WriteMajorThreadImgTextActivity.BUNDLE_IMG_TEXT, writeThreadAdapter.getItem(pos).getText());
            jumpActivityForResult(EditMajorThreadImgTextActivity.class, bundle, WriteMajorThreadContentActivity.REQUESTCODE_EDIT_TEXT);
            overridePendingTransition(R.anim.activity_push_bottom_in, R.anim.activity_stay);
        }

        @Override
        public void itemUp(int pos) {
            mPresenter.itemUp(pos);
        }

        @Override
        public void itemDown(int pos) {
            mPresenter.itemDown(pos);
        }

        @Override
        public void reStartUpload(int i) {
            mPresenter.reStartUpload(i);
        }
    };


    /**
     * 时件监听
     */
    WriteMajorThreadVH.IActionListener iActionListener = new WriteMajorThreadVH.IActionListener() {
        @Override
        public void click(final View v) {
            switch (v.getId()) {
                case R.id.toolbar_left:
                    onBackPressed();
                    break;

                case R.id.write_major_send:
                    if (TextUtils.isEmpty(vh.getThreadTitle())) {
                        ToastUtils.showToast("您的帖子还没有标题哦");
                        return;
                    }
                    if (writeThreadAdapter.getCount() == 0 && TextUtils.isEmpty(vh.getThreadContent())) {
                        ToastUtils.showToast("您的帖子还没有内容哦");
                        return;
                    }

                    if (mPresenter.successCount + mPresenter.errorCount < mPresenter.allCount) {
                        //还有图片处于“正在上传”状态时
                        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(WriteMajorThreadContentActivity.this);
                        builder.setTitle(null);
                        builder.setIsOneButton(true);
                        builder.setMessage("图片正在上传中...");
                        builder.create().show();
                        return;
                    }
                    if (mPresenter.errorCount > 0) {
                        //有图片处于“上传失败”状态时
                        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(WriteMajorThreadContentActivity.this);
                        builder.setTitle(null);
                        builder.setMessage("还有" + mPresenter.errorCount + "张图片未成功上传");
                        builder.setNegativeButton("直接发布", new MyAlertDialog.OnAlertViewClickListener() {
                            @Override
                            public void OnAlertViewClick() {
                                mPresenter.getThreadCountText(vh.getThreadContent());
                            }
                        });
                        builder.setPositiveButton("继续上传", new MyAlertDialog.OnAlertViewClickListener() {
                            @Override
                            public void OnAlertViewClick() {
                                mPresenter.uploadImage();
                            }
                        });
                        builder.create().show();
                        return;
                    }

                    mPresenter.getThreadCountText(vh.getThreadContent());
                    break;

                case R.id.write_major_menu:
                    if (writeMajorThreadMenuPopupWindow == null) {
                        writeMajorThreadMenuPopupWindow = new WriteMajorThreadMenuPopupWindow(WriteMajorThreadContentActivity.this);
                        writeMajorThreadMenuPopupWindow.setIClick(new WriteMajorThreadMenuPopupWindow.IClick() {

                            @Override
                            public void sort() {
                                if (writeThreadAdapter.getCount() == 0) {
                                    ToastUtils.showToast("先编辑文案图片");
                                    return;
                                }
                                writeThreadAdapter.showMenu();
                                mPresenter.isEditModle = true;
                                vh.bindMajorSort();
                            }

                            @Override
                            public void save() {
                                mPresenter.saveDraft(vh.getThreadTitle(), vh.getThreadContent(), vh.getLocationName());
                            }

                            @Override
                            public void view() {
                                if (TextUtils.isEmpty(vh.getThreadTitle())) {
                                    ToastUtils.showToast("您的帖子还没有标题哦");
                                    return;
                                }
                                if (writeThreadAdapter.getCount() == 0 && TextUtils.isEmpty(vh.getThreadContent())) {
                                    ToastUtils.showToast("您的帖子还没有内容哦");
                                    return;
                                }
                                mPresenter.toThreadPreviewActivity(vh.getThreadTitle(), vh.getThreadContent(), vh.getLocationName());
                            }
                        });
                    }
                    //获取点击View的坐标
                    int[] location = new int[2];
                    v.getLocationOnScreen(location);
                    //显示在下方
                    writeMajorThreadMenuPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, SharedPreferencesString.getInstances().getW_Screen() - DensityUtil.dip2px(170), location[1] + DensityUtil.dip2px(45));
                    break;

                case R.id.write_major_preview:
                    writeThreadAdapter.showContent();
                    mPresenter.isEditModle = false;
                    vh.bindMajorPreview();
                    break;

                case R.id.add_text:
                    if (mPresenter.isEditModle) return;
                    jumpActivityForResult(WriteMajorThreadImgTextActivity.class, null, REQUESTCODE_ADD_IMGTEXT);
                    vh.hintFaceView();
                    break;

                case R.id.add_img:
                    if (mPresenter.isEditModle) return;
                    MobclickAgent.onEvent(WriteMajorThreadContentActivity.this, FinalUtils.EventId.post_pic_upload);
                    Bundle bundle = new Bundle();
                    bundle.putString(AlbumPresenter.BUNDLE_FROM_TYPE, AlbumPresenter.BUNDLE_FROM_MAJOR_THREAD);
                    bundle.putInt(AlbumPresenter.BUNDLE_NEED_SIZE, 30);
                    jumpActivityForResult(AlbumActivity.class, bundle, REQUESTCODE_ADD_IMG);
                    vh.hintFaceView();
                    break;

                case R.id.choose_look_img:
                    if (!vh.bindChooseLookImg()) {
                        hideSoftInput(getCurrentFocus().getWindowToken());
                    }
                    break;

                case R.id.write_thread_location_name:

                    if (mPresenter.locationListBean == null || (DataUtils.isEmpty(mPresenter.locationListBean.getAirports()) && DataUtils.isEmpty(mPresenter.locationListBean.getHotels()) && DataUtils.isEmpty(mPresenter.locationListBean.getLounges()))) {
                        jumpActivityForResult(LocationListActivity.class, null, requestLocation);
                    } else {
                        bundle = new Bundle();
                        bundle.putSerializable("data", mPresenter.locationListBean);
                        jumpActivityForResult(MyLocationActivity.class, bundle, requestFlyLocation);
                    }
                    break;
                case R.id.del_location_btn:
                    vh.bindDeleteLocation();
                    mPresenter.locationBean = null;
                    break;

                case R.id.to_rating_icon:
                    bundle = new Bundle();
                    bundle.putSerializable("data", mPresenter.locationBean);
                    bundle.putFloat("star", mPresenter.getStarMark());
                    bundle.putSerializable("tags", (Serializable) mPresenter.tagsList);
                    bundle.putString("flight", mPresenter.flight);
                    jumpActivityForResult(RatingActivity.class, bundle, requestFlyLocation);
                    break;

                case R.id.video_img:
                    if (mPresenter.isEditModle) return;
                    MobclickAgent.onEvent(WriteMajorThreadContentActivity.this, FinalUtils.EventId.post_video_upload);
                    jumpActivityForResult(VideoListActivity.class, null, REQUEST_VIDEO);
                    break;

            }
        }

        @Override
        public void continueUploading() {
            mPresenter.uploadImage();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected WriteMajorThreadContentPresenter createPresenter() {
        return new WriteMajorThreadContentPresenter();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //添加文字返回
                case WriteMajorThreadContentActivity.REQUESTCODE_ADD_IMGTEXT:
                    mPresenter.activityResultAddImgText(data);
                    break;
                //编辑文字返回
                case WriteMajorThreadContentActivity.REQUESTCODE_EDIT_TEXT:
                    mPresenter.activityResultEditText(data);
                    break;
                //添加图片返回
                case WriteMajorThreadContentActivity.REQUESTCODE_ADD_IMG:
                    mPresenter.activityResultAddImg(data);
                    break;
                //编辑图片
                case WriteMajorThreadContentActivity.REQUESTCODE_EDIT_IMG:
                    mPresenter.activityResultEditImg(data);
                    break;
                //茶馆接口关联位置
                case WriteMajorThreadContentActivity.requestFlyLocation:
                    LogFly.e("茶馆接口关联位置");
                    mPresenter.activityResultFlyLocation(data);
                    vh.bindFlyLocation(mPresenter.locationBean, data);
                    break;
                //板块选择
                case WriteMajorThreadContentActivity.requestForumChoose:
                    jumpActivitySetResult(null);
                    break;
                //地图定位
                case requestLocation:
                    vh.bindMapLocation(data);
                    break;
                //删除视频
                case REQUEST_DELETE_VIDEO:
                    mPresenter.activityResultDeleteVideo(data);
                    break;
            }

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.setTuwenList(intent);
    }

    /**
     * 监听back键
     */
    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(vh.getThreadTitle()) || !TextUtils.isEmpty(vh.getThreadContent()) || !DataUtils.isEmpty(mPresenter.getTuwenInfoList())) {
            showDraftDialog();
        } else {
            jumpActivitySetResult(null);
        }
    }

    /**
     * 草稿提示框
     */
    private void showDraftDialog() {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(this);
        builder.setTitle(null);
        builder.setMessage("是否保存草稿");
        builder.setNegativeButton("删除草稿", new MyAlertDialog.OnAlertViewClickListener() {
            @Override
            public void OnAlertViewClick() {
                mPresenter.deleteDraft();
                jumpActivitySetResult(null);
            }
        });
        builder.setPositiveButton("保存草稿", new MyAlertDialog.OnAlertViewClickListener() {
            @Override
            public void OnAlertViewClick() {
                mPresenter.saveDraft(vh.getThreadTitle(), vh.getThreadContent(), vh.getLocationName());
            }
        });
        builder.create().show();
    }


    List<SmileyBean> smileyBeanList = new ArrayList<>();

    /**
     * 选择了表情
     *
     * @param bean
     */
    public void onEventMainThread(SmileyBean bean) {
        if (!TextUtils.equals(bean.getImage(), "close")) {
            smileyBeanList.add(bean);
        }
        vh.bindSmiley(bean);
    }

    /**
     * 选择了视频
     */
    public void onEventMainThread(VideoInfo videoInfo) {
        LogFly.e(videoInfo.toString());
        mPresenter.addVideoData(videoInfo);
    }

}
