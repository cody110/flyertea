package com.ideal.flyerteacafes.ui.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.Touch;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.CacheFileManger;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.dal.RoughDraftHelper;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.CommentBean;
import com.ideal.flyerteacafes.model.entity.RoughDraftBean;
import com.ideal.flyerteacafes.model.entity.SmileyBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.web.SystemWebActivity;
import com.ideal.flyerteacafes.ui.controls.RPLinearLayout;
import com.ideal.flyerteacafes.ui.popupwindow.LoadingPopupwindow;
import com.ideal.flyerteacafes.ui.popupwindow.RoughDraftPopupWindow;
import com.ideal.flyerteacafes.ui.view.MyAlertDialog;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.ViewTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.SmartUtil;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.ApplicationTools;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.ideal.flyerteacafes.utils.tools.StringTools;
import com.ideal.flyerteacafes.utils.tools.UriTools;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;

/**
 * 回帖页面
 *
 * @author fly
 */
@SuppressLint("SimpleDateFormat")
@ContentView(R.layout.activity_replyposts)
public class ReplyPostsActivity extends FragmentActivity {

    @ViewInject(R.id.replyPost_adduce_text)
    private TextView adduceText;
    @ViewInject(R.id.replyPost_edit)
    private EditText editText;
    @ViewInject(R.id.reply_post_choose_button_layout)
    private LinearLayout chooseBtnLayout;
    @ViewInject(R.id.reply_post_biaoqing_layout)
    private View biaoqingLayout;
    @ViewInject(R.id.root)
    private RPLinearLayout rootView;
    @ViewInject(R.id.reply_post_title)
    private TextView titleView;
    @ViewInject(R.id.publish_post_choose_type_layout)
    private View chooseTypeView;
    @ViewInject(R.id.publish_post_edit_title)
    private EditText postTitleEdit;
    @ViewInject(R.id.replyPost_adduce_layout)
    private View adduceLayout;
    @ViewInject(R.id.replyPost_adduce_subject)
    private TextView subjectView;
    @ViewInject(R.id.replyPost_adduce_time)
    private TextView timeView;
    @ViewInject(R.id.reply_post_choose_button_biaoqing)
    ImageView faceBtn;
    @ViewInject(R.id.replyPost_choose_img)
    ImageView tupianBtn;
    @ViewInject(R.id.reply_post_right_img)
    ImageView titleRightImg;

    private ArrayList<RecordingPositon> imgPathList = new ArrayList<RecordingPositon>();

    private int tid;
    private int fid;
    private int pid;// 引用回复id
    private String quoteComment;
    private String uid;
    private String commintMsg;// 引用回复，提交给服务器的内容
    private String subject;// 作者
    private String time;// 时间
    private String authorid;

    private Uri photoUri;// 有时候返回拍照返回Uri为null,需要拍照之前手动指定Uri、

    private int postType;// 发帖的类型
    private String[] stringArray;
    private int typeid = 0;// 发帖所属的子模块分类id
    private String titleStr;// 发帖标题
    private int typeIndex = -1;
    private String commentContent;//评论上个页面带过来的内容

    public static final int SHOW_KEY = 1, HIDE_KEY = 0, POP_SEND_TYPE = 2;

    int num = 0;// 代表上传图片的次数
    boolean allFlag = false;// 图片传完为true
    boolean oneFlag = true;// 默认成功
    boolean titleEditFlag = false;// 标题edittext是否有焦点

    private String commitMessage;

    private String attachnew;// 专门拼接图片返回码
    private RoughDraftPopupWindow popupWindow;
    private RoughDraftHelper helper;

    private RoughDraftBean roughDraftBean;
    private String imgPath = "";
    private String forumName;//二级版块名称

    private int w_screen;
    private String from;

    private static final int requestImageCode = 1, requestChooseType = 3,
            requestCameraCode = 2;

    class RecordingPositon {
        String imgPath;
        String uriPath;
        String attachimg;
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == HIDE_KEY) {
                faceBtn.setImageResource(R.drawable.jianpan);
                faceBtn.setTag(true);
                if (biaoqingLayout.getTag() != null
                        && (Boolean) biaoqingLayout.getTag()) {
                    biaoqingLayout.setTag(false);
                    biaoqingLayout.setVisibility(View.VISIBLE);
                    chooseBtnLayout.setVisibility(View.VISIBLE);
                } else {
                    if (chooseBtnLayout.getVisibility() != View.GONE)
                        chooseBtnLayout.setVisibility(View.GONE);
                }
            } else if (msg.what == SHOW_KEY) {
                faceBtn.setImageResource(R.drawable.reply_face);
                faceBtn.setTag(false);
                if (chooseBtnLayout.getVisibility() != View.VISIBLE
                        && !titleEditFlag)
                    chooseBtnLayout.setVisibility(View.VISIBLE);
                biaoqingLayout.setVisibility(View.GONE);
            } else if (msg.what == 3) {
                typeIndex = msg.arg2;
//				typeid = listType.get(typeIndex).getTypeId();
            } else if (msg.what == FinalUtils.ROUGHDRAFT_SAVE) {
                popupWindow.dismiss();
                RoughDraftBean bean = new RoughDraftBean();
                titleStr = postTitleEdit.getText().toString();
                commitMessage = editText.getText().toString();
                bean.setFid(fid);
                if (!DataUtils.isEmpty(titleStr))
                    bean.setTitle(titleStr);
                if (!DataUtils.isEmpty(commitMessage)) {
                    for (int i = 0; i < imgPathList.size(); i++) {
                        imgPath += imgPathList.get(i).imgPath + ",";
                    }
                    bean.setMessage(commitMessage);
                    bean.setImgPath(imgPath);
                }
                bean.setForumName(forumName);
                bean.setTypeId(typeid);
                helper.updateRoughDraftByDB(bean);
                Intent intent = new Intent();
                setResult(RESULT_FIRST_USER, intent);
                finish();
            } else if (msg.what == FinalUtils.ROUGHDRAFT_NOT_SAVE) {
                if (popupWindow != null)
                    popupWindow.dismiss();
                helper.deteleRoughDraftByFid(fid);
                EventBus.getDefault().post(FinalUtils.EventId.forum_post_write_back);
                Intent intent = new Intent();
                setResult(RESULT_FIRST_USER, intent);
                finish();
            } else if (msg.what == FinalUtils.ROUGHDRAFT_CANCEL) {

            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        helper = new RoughDraftHelper();
        biaoqingLayout.setVisibility(View.GONE);
        EventBus.getDefault().register(this);
        rootView.setOnResizeListener(resizeListener);
        initData();
        initWeight();
        initLisenner();
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Event(R.id.replyPost_close_btn)
    private void closePage(View v) {
        hideSoftInput(getCurrentFocus().getWindowToken());
        titleStr = postTitleEdit.getText().toString();
        commitMessage = editText.getText().toString();
        if ((postType == FinalUtils.POST_ISSUE || postType == FinalUtils.FEED_BACK) && (!DataUtils.isEmpty(titleStr)
                || !DataUtils.isEmpty(commitMessage))) {
            if (popupWindow == null)
                popupWindow = new RoughDraftPopupWindow(this, handler);
            popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        } else {
            finish();
        }
    }

    @Event(R.id.reply_post_title_view)
    private void titleClick(View v) {
        if (typeid > 0) {
            Intent intent = new Intent(this, ChooseForumActivity.class);
            intent.putExtra(IntentKey.KEY_FROM, from);
            intent.putExtra(IntentKey.KEY_AGAIN, true);
            startActivityForResult(intent, requestChooseType);
        }
    }

    @Event(R.id.replyPost_commit)
    private void commit(View v) {
        if (SmartUtil.isFastClick()) {
            return;
        }

        if (!TextUtils.equals(UserManger.getUserInfo().getAuthed(), "1")) {
            showAuthodDialog();
            return;
        }

        commitMessage = editText.getText().toString();
        // 发帖
        if (postType == FinalUtils.POST_ISSUE
                || postType == FinalUtils.FEED_BACK) {
            titleStr = postTitleEdit.getText().toString();
            if (!!DataUtils.isEmpty(titleStr)) {
                BToast("请输入标题");
                return;
            }
            if (!DataUtils.isEmpty(commitMessage)) {
                showDialog();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        commitMessage = replaceImgUripathToStringMark(commitMessage,
                                false);
                        commitRequest();
                    }
                }).start();
            } else {
                BToast("请输入帖子正文");
            }
        } else {// 回复
            if (!DataUtils.isEmpty(commitMessage)) {
                commitMessage = replaceImgUripathToStringMark(commitMessage,
                        false);
                showDialog();
                commitRequest();
            } else {
                BToast(getString(R.string.replypost_not_message_pointout));
            }
        }

    }

    public void showAuthodDialog() {
        MyAlertDialog.Builder builder = new MyAlertDialog.Builder(ReplyPostsActivity.this);
        builder.setTitle("手机号绑定提示");
        builder.setMessage("依《网络安全法》相关要求回帖之前需先完成手机绑定");
        builder.setNegativeButton("放弃");
        builder.setPositiveButton("去绑定", new MyAlertDialog.OnAlertViewClickListener() {
            @Override
            public void OnAlertViewClick() {
                Bundle bundle = new Bundle();
                bundle.putString("url", Utils.HtmlUrl.HTML_BIND_PHONE + "?access_token=" + UserManger.getUserInfo().getPs_token().getAccess_token());
                Intent intent = new Intent(ReplyPostsActivity.this, SystemWebActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        builder.create().show();
    }

    @Event(R.id.replyPost_choose_img)
    private void chooseImg(View v) {
        Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
        getImage.addCategory(Intent.CATEGORY_OPENABLE);
        getImage.setType("image/*");
        startActivityForResult(getImage, requestImageCode);
    }

    /**
     * 这种请求拍照方式，为了预防拍照完成返回Uri为null的情况，手动指定拍照Uri、 网上找的解决方法
     * http://blog.sina.com.cn/s/blog_4c7d14600101jhaf.html
     *
     * @param v
     */
    @Event(R.id.reply_post_choose_button_phono)
    private void toPhono(View v) {
        // 没有储存卡要挂掉，暂未找到解决方法
        if (ApplicationTools.sdCardExist()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            ContentValues values = new ContentValues();
            values.put(Media.TITLE, filename);

            photoUri = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

            startActivityForResult(intent, requestCameraCode);
        }

    }

    @Event(R.id.reply_post_choose_button_biaoqing)
    private void showBiaoQingLayout(View v) {
        if ((boolean) faceBtn.getTag()) {
            biaoqingLayout.setVisibility(View.GONE);
            InputMethodManager m = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            biaoqingLayout.setTag(true);
            hideSoftInput(getCurrentFocus().getWindowToken());
        }
    }

    @Event(value = R.id.replyPost_edit, type = Touch.class)
    private boolean hideBiaoQingLayout(View v, MotionEvent event) {
        if (biaoqingLayout.getVisibility() != View.GONE) {
            biaoqingLayout.setVisibility(View.GONE);
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {
            if (requestCode == requestImageCode
                    || requestCode == requestCameraCode) {
                Bitmap bitmap = null;
                Uri originalUri = null;
                if (requestCode == requestImageCode) {// 选择相册照片
                    originalUri = intent.getData();

                } else if (requestCode == requestCameraCode) {// 拍照
                    if (intent != null && intent.getData() != null) {
                        originalUri = intent.getData();
                    }
                    if (originalUri == null)
                        originalUri = photoUri;
                }
                if (originalUri == null)
                    return;
                bitmap = BitmapTools.revitionImageSize(originalUri,
                        w_screen / 2, this);
                // bitmap=BitmapTools.getBitmapFromUri(this, originalUri);
                // bitmap=BitmapTools.zoomImage(bitmap, 400, 600);
                if (bitmap != null) {
                    String path;// 图片路径

                    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
                    if (!isKitKat) {
                        path = UriTools.getFilePathByUri(this, originalUri);
                    } else {

                        path = UriTools.getFilePathByUriTWO(
                                ReplyPostsActivity.this, originalUri);
                    }

                    if (path == null || path.length() == 0)
                        return;
                    RecordingPositon recording = new RecordingPositon();// 记录插入图片路径，位置
                    recording.imgPath = path;
                    recording.uriPath = originalUri.getPath();
                    imgPathList.add(recording);
                    insertIntoEditText(getBitmapMime(bitmap, path));
                } else {
                    Toast.makeText(ReplyPostsActivity.this, "获取图片失败",
                            Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == requestChooseType) {
                forumName = intent.getStringExtra("forumName");
                typeid = intent.getIntExtra("typeid", 0);
                titleView.setText(forumName);
                if (typeid == 0) {
                    titleRightImg.setVisibility(View.GONE);
                } else {
                    titleRightImg.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    private void initData() {
        w_screen = SharedPreferencesString.getInstances(this).getIntToKey(
                "w_screen");
        subject = getIntent().getStringExtra("subject");
        time = getIntent().getStringExtra("time");
        commintMsg = getIntent().getStringExtra("commit");
        postType = getIntent().getIntExtra("posttype", FinalUtils.POST_COMMENT);
        tid = getIntent().getIntExtra("tid", 0);
        fid = getIntent().getIntExtra("fid", 0);
        pid = getIntent().getIntExtra("pid", 0);
        uid = SharedPreferencesString.getInstances(this).getStringToKey("uid");
        forumName = getIntent().getStringExtra("forumName");
        typeid = getIntent().getIntExtra("typeid", 0);
        from = getIntent().getStringExtra(IntentKey.KEY_FROM);
        commentContent = getIntent().getStringExtra("commentContent");
        authorid = getIntent().getStringExtra("authorid");
    }

    private void initWeight() {

        if (postType == FinalUtils.POST_QUOTE) {//引用回复
            quoteComment = getIntent().getStringExtra("quoteComment");
            adduceText.setText(Html.fromHtml(quoteComment));
            titleView.setText(getString(R.string.reply));
            adduceLayout.setVisibility(View.VISIBLE);
            subjectView.setText(subject);
            timeView.setText(Html.fromHtml(time));
        } else if (postType == FinalUtils.POST_COMMENT) {//评论回复
            titleView.setText(getString(R.string.reply));
            WidgetUtils.setText(editText, commentContent);
            if (!TextUtils.isEmpty(commentContent))
                editText.setSelection(commentContent.length());
        } else if (postType == FinalUtils.POST_ISSUE || postType == FinalUtils.FEED_BACK) {// 发帖
            if (postType == FinalUtils.POST_ISSUE)
                titleView.setText(forumName);
            else
                titleView.setText(getString(R.string.reply_title_feedback));
            if (typeid == 0) {
                titleRightImg.setVisibility(View.GONE);
            } else {
                titleRightImg.setVisibility(View.VISIBLE);
            }
            chooseTypeView.setVisibility(View.VISIBLE);

            roughDraftBean = helper.getRoughDraftByFid(fid, typeid);
            if (roughDraftBean != null) {
                if (!DataUtils.isEmpty(roughDraftBean.getTitle()))
                    postTitleEdit.setText(roughDraftBean.getTitle());
                if (typeid != 0) {// 分类
                    typeIndex = roughDraftBean.getTypeIndex();
                    typeid = roughDraftBean.getTypeId();
                }
                if (!DataUtils.isEmpty(roughDraftBean.getMessage())) {// 正文
                    editText.setText(roughDraftBean.getMessage());
                    message = editText.getText().toString();
                    imgPath = roughDraftBean.getImgPath();
                    String[] pathArray = imgPath.split(",");
                    for (int i = 0; i < pathArray.length; i++) {
                        if (existsByString(roughDraftBean.getMessage(),
                                pathArray[i])) {
                            RecordingPositon recording = new RecordingPositon();
                            recording.imgPath = pathArray[i];
                            imgPathList.add(recording);
                            Bitmap bitmap = BitmapTools.revitionImageSize(
                                    pathArray[i], w_screen / 2);
                            replaceIntoEditText(
                                    getBitmapMime(bitmap, pathArray[i]),
                                    pathArray[i]);
                        } else {
                            imgPath = imgPath.replace(pathArray[i] + ",", "");
                        }
                    }
                }
            }

        }
    }

    private void initLisenner() {
        postTitleEdit
                .setOnFocusChangeListener(new View.OnFocusChangeListener() {

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {// 获得焦点
                            titleEditFlag = true;
                            if (chooseBtnLayout.getVisibility() != View.GONE)
                                chooseBtnLayout.setVisibility(View.GONE);
                        } else {// 失去焦点
                            titleEditFlag = false;
                        }
                    }
                });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {// 获得焦点
                    if (chooseBtnLayout.getVisibility() != View.VISIBLE)
                        chooseBtnLayout.setVisibility(View.VISIBLE);
                } else {// 失去焦点

                }
            }
        });
    }

    /**
     * 提交请求
     */
    private void commitRequest() {
        if (!!DataUtils.isEmpty(imgPathList))
            allFlag = true;

        if (allFlag) {
            if (postType == FinalUtils.POST_ISSUE)
                requestPublish();
            else if (postType == FinalUtils.FEED_BACK)
                requestCommentUs();
            else
                requestReply();
            return;
        }
        for (int i = 0; i < imgPathList.size(); i++) {
            requestUploadPictures(imgPathList.get(i).imgPath);
        }
    }

    String sdFile = CacheFileManger.getCacheImagePath();

    /**
     * 上传图片
     */
    private void requestUploadPictures(String path) {
        int startIndex = path.lastIndexOf("/");
        if (startIndex == -1)
            return;
        String name = path.substring(startIndex, path.length());
        String flyPath = sdFile + name;
        File file = new File(flyPath);
        Bitmap bmp = BitmapTools.compressImage(BitmapTools.getimage(path));
        boolean bol = BitmapTools.saveJPGE_After(bmp, file);
        if (bol) {
            FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_uploadPictures);
            params.addBodyParameter("Filedata", file);
            params.addQueryStringParameter("fid", fid + "");
            params.addQueryStringParameter("uid", uid + "");

            XutilsHttp.Post(params, new StringCallback() {
                @Override
                public void flySuccess(String result) {
                    if (!DataUtils.isEmpty(result)) {
                        if (result.equals("-2")) {
                            oneFlag = false;
                        } else {
                            if (num < imgPathList.size())
                                imgPathList.get(num).attachimg = result;
                            num++;
                            if (num == imgPathList.size())
                                allFlag = true;
                            if (allFlag)
                                commitRequest();
                        }
                    }
                }

                @Override
                public void flyError() {
                    super.flyError();
                    dialogDismiss();
                    BToast("发表失败！");
                    oneFlag = false;
                }

            });
        }

    }

    /**
     * 回复
     */
    @SuppressWarnings("unused")
    private void requestReply() {
        commitMessage = replaceImgUripathToStringMark(commitMessage, true);
        String formhash = SharedPreferencesString.getInstances(this)
                .getStringToKey("formhash");

        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_REPLYPOST);
        params.addQueryStringParameter("tid", tid + "");
        params.addQueryStringParameter("fid", fid + "");

        if (pid != 0) {
            params.addQueryStringParameter("pid", String.valueOf(pid));
            params.addQueryStringParameter("noticetrimstr", commintMsg);
        }
        LogFly.e(commitMessage);
        params.addQueryStringParameter("message", commitMessage);
        params.addQueryStringParameter("formhash", formhash);
        params.addQueryStringParameter("attachnew", attachnew);
        params.addQueryStringParameter("replytouid", authorid);
        params.addQueryStringParameter("version", "6");


        XutilsHttp.Get(params, new DataCallback<CommentBean>() {


            @Override
            public void flySuccess(DataBean<CommentBean> result) {
                ToastUtils.showToast("发表评论成功");
                CommentBean newCommentBean = result.getDataBean();
                Bundle bundle = new Bundle();
                bundle.putSerializable(ThreadPresenter.BUNDLE_COMMENT_IMG, newCommentBean);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void flyError() {
                super.flyError();
                ToastUtils.showToast("发表评论失败");
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialogDismiss();
            }
        });


    }

    /**
     * 发帖
     */
    private void requestPublish() {
        commitMessage = replaceImgUripathToStringMark(commitMessage, true);
        String formhash = SharedPreferencesString.getInstances(this)
                .getStringToKey("formhash");
//        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_PUBLISHPOST);
//        params.addBodyParameter("fid", fid + "");
//        params.addBodyParameter("typeid", typeid + "");
//        params.addBodyParameter("subject", titleStr);
//        params.addBodyParameter("message", commitMessage);
//        params.addBodyParameter("formhash", formhash);
//        params.addBodyParameter("attachnew", attachnew);
//        params.addQueryStringParameter("version", "4");
//        params.addBodyParameter("mapx", AmapLocation.mLongitude + "");
//        params.addBodyParameter("mapy", AmapLocation.mLatitude + "");

        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_PUBLISHPOST).setCharsetGBK();
        params.addBodyParameter("fid", fid + "");
        params.addBodyParameter("typeid", typeid + "");
        params.addBodyParameter("subject", titleStr);
        params.addBodyParameter("message", commitMessage);
        params.addBodyParameter("formhash", formhash);
        params.addBodyParameter("attachnew", attachnew);
        params.addQueryStringParameter("transcode", "yes");

        XutilsHttp.Post(params, publishPostCallback);


    }

    StringCallback publishPostCallback = new StringCallback() {
        @Override
        public void flySuccess(String result) {
            dialogDismiss();
            BaseBean bean = JsonAnalysis.jsonToReplyPost(result);
            if (!DataUtils.isEmpty(bean.getMessage()))
                BToast(bean.getMessage());
            if (bean.getCode().equals("post_reply_succeed")
                    || bean.getCode().equals("post_newthread_succeed")) {
                handler.sendEmptyMessage(FinalUtils.ROUGHDRAFT_NOT_SAVE);
            }
        }


        @Override
        public void flyError() {
            super.flyError();
            dialogDismiss();
            BToast("发表失败！");
        }
    };

    /**
     * 评论我们
     */
    private void requestCommentUs() {
        commitMessage = replaceImgUripathToStringMark(commitMessage, true);
        String formhash = SharedPreferencesString.getInstances(this)
                .getStringToKey("formhash");
//        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_PUBLISHPOST);
//        params.addQueryStringParameter("fid", fid + "");
//        params.addQueryStringParameter("typeid", "1006");
//        params.addQueryStringParameter("subject", titleStr);
//        params.addQueryStringParameter("message", commitMessage);
//        params.addQueryStringParameter("formhash", formhash);
//        params.addQueryStringParameter("attachnew", attachnew);
//        params.addBodyParameter("mapx", AmapLocation.mLongitude + "");
//        params.addBodyParameter("mapy", AmapLocation.mLatitude + "");


        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_PUBLISHPOST).setCharsetGBK();
        params.addBodyParameter("fid", fid + "");
        params.addBodyParameter("typeid", "1006");
        params.addBodyParameter("subject", titleStr);
        params.addBodyParameter("message", commitMessage);
        params.addBodyParameter("formhash", formhash);
        params.addBodyParameter("attachnew", attachnew);
        params.addQueryStringParameter("transcode", "yes");


        XutilsHttp.Post(params, publishPostCallback);
    }

    /**
     * 选择的表情
     *
     * @param bean
     */
    public void onEventMainThread(SmileyBean bean) {
        if (TextUtils.equals(bean.getImage(), "close")) {
            ViewTools.editTextDelete(editText);
        } else {
            editText.getText().insert(editText.getSelectionStart(), bean.getCode());
        }
    }

    private void hintFaceView() {
        biaoqingLayout.setVisibility(View.GONE);
    }


    public void BToast(String message) {
        ToastUtils.showToast(message);
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     */
    public void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 图片转edittext能显示的格式
     *
     * @param pic
     * @param uri
     * @return
     */
    private SpannableString getBitmapMime(Bitmap pic, Uri uri) {
        if (editText.getText().toString().trim().length() > 0)
            editText.append("\n");
        String path = uri.getPath();
        SpannableString ss = new SpannableString(path);
        ImageSpan span = new ImageSpan(this, pic);
        ss.setSpan(span, 0, path.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 图片转edittext能显示的格式
     *
     * @param pic
     * @return
     */
    private SpannableString getBitmapMime(Bitmap pic, String path) {
        SpannableString ss = new SpannableString(path);
        ImageSpan span = new ImageSpan(this, pic);
        ss.setSpan(span, 0, path.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 插入到edittext光标位置
     *
     * @param ss
     */
    private void insertIntoEditText(SpannableString ss) {
        Editable et = editText.getText();// 先获取Edittext中的内容
        int start = editText.getSelectionStart();
        if (start != 0)//光标前面有内容就换行插入图片
            et.insert(start, "\n");
        start = editText.getSelectionStart();
        et.insert(start, ss);// 设置ss要添加的位置
        editText.setText(et);// 把et添加到Edittext中
        String huanhang = "\n";
        editText.append(huanhang);
        editText.setSelection(start + ss.length() + huanhang.length());// 设置Edittext中光标在最后面显示
    }

    private String message;// 记录原始保存的帖子，为了解决相同图片只能替换第一张图片问题

    private void replaceIntoEditText(SpannableString ss, String path) {
        Editable et = editText.getText();// 先获取Edittext中的内容
        // String message = et.toString();
        int start = message.indexOf(path);
        if (start != -1) {
            et.replace(start, start + path.length(), ss);
            message = message.replaceFirst(path,
                    StringTools.getRandomString(path.length()));
        }
    }

    private String replaceImgUripathToStringMark(String str,
                                                 Boolean attachimgFlag) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < imgPathList.size(); i++) {
            RecordingPositon info = imgPathList.get(i);
            String regEx = info.imgPath; // 表示a或F
            if (str.contains(regEx)) {
                if (attachimgFlag) {
                    str = StringTools.replaceFirst(str, regEx, "[attachimg]" + info.attachimg
                            + "[/attachimg]");
                    sBuffer.append(info.attachimg);
                    sBuffer.append("_");
                }
            } else {
                imgPathList.remove(i);
            }
        }
        attachnew = sBuffer.toString();
        if (attachnew.length() > 2)
            attachnew = attachnew.substring(0, attachnew.length() - 1);
        return str;
    }

    /**
     * 判断字符串中是否存在该字符
     *
     * @param str
     * @param mark
     * @return
     */
    private boolean existsByString(String str, String mark) {
        Pattern pat = Pattern.compile(mark);
        Matcher mat = pat.matcher(str);
        boolean rs = mat.find();
        return rs;
    }

    /**
     * 在自定义的布局里，实现这个接口，通过布局高度变化，区分软键盘是否弹出
     */
    private RPLinearLayout.OnResizeListener resizeListener = new RPLinearLayout.OnResizeListener() {

        @SuppressWarnings("unused")
        @Override
        public void OnResize(int w, int h, int oldw, int oldh) {
            if (oldw != 0 && oldh != 0) {
                if (h < oldh) {// 软键盘弹出来了
                    int keyboardHeight = oldh - h;
                    handler.sendEmptyMessage(SHOW_KEY);
                } else {
                    handler.sendEmptyMessage(HIDE_KEY);
                }
            }

        }
    };

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onResume(this); // 统计时长
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPause(this);
    }


    private LoadingPopupwindow popupwindow;


    public void showDialog() {
        if (popupwindow == null)
            popupwindow = new LoadingPopupwindow(this);
        popupwindow.showAtLocation(editText, Gravity.CENTER, 0, 0);

    }

    public void dialogDismiss() {
        if (popupwindow != null) {
            popupwindow.dismiss();
        }
    }

}
