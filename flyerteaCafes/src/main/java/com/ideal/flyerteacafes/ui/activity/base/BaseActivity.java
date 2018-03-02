package com.ideal.flyerteacafes.ui.activity.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.BaseDataManger;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.ui.activity.SplashActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IBase;
import com.ideal.flyerteacafes.ui.activity.interfaces.IDialog;
import com.ideal.flyerteacafes.ui.popupwindow.LoadingPopupwindow;
import com.ideal.flyerteacafes.utils.DateTimeUtil;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.SmartUtil;
import com.ideal.flyerteacafes.utils.TaskUtil;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.systembartint.SystemBarUtils;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.tools.IntentTools;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.umeng.analytics.MobclickAgent;

import java.util.Calendar;
import java.util.List;

import de.greenrobot.event.EventBus;

public class BaseActivity extends FragmentActivity implements IBase, IDialog {

    /**
     * 响应第二次点击的最小时间差
     */
    private static final int MIN_CLICK_DELAY_TIME = 500;
    public SharedPreferencesString preferences;

    private View content;
    private LoadingPopupwindow popupwindow;
    private boolean isRender = false;


    public void showDialog() {
        showDialog(null);
    }

    public void showDialog(String tip) {
        if (isEnd()) return;
        if (popupwindow == null) {
            popupwindow = new LoadingPopupwindow(this);
        }

        popupwindow.setTipTextView(tip);

        if (isRender) {
            popupwindow.showAtLocation(content, Gravity.CENTER, 0, 0);
        } else {
            content.post(new Runnable() {
                @Override
                public void run() {
                    if (!isEnd())
                        popupwindow.showAtLocation(content, Gravity.CENTER, 0, 0);
                }
            });
        }
    }

    public void dialogDismiss() {
        if (popupwindow != null) {
            popupwindow.dismiss();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = findViewById(android.R.id.content);
        //ui渲染完成会回调此方法
        content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                isRender = true;
            }
        });
        preferences = SharedPreferencesString.getInstances();


        if (isSystemBarTransparent()) {
            SystemBarUtils.transparencyBar(this);
        }

        if (isSetSystemBar()) {
            SystemBarUtils.setStatusBarColor(this, R.color.bg_toolbar);
        }

        SystemBarUtils.StatusBarLightMode(this);

        if (FlyerApplication.isNeedPgy) {
            //蒲公英
            PgyCrashManager.register(this);
        }

    }


    /**
     * 获取根布局
     *
     * @return
     */
    protected View getRootView() {
        View view = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        return view;
    }

    /**
     * activity  是否结束
     *
     * @return
     */
    public boolean isEnd() {
        if (isDestroyed() || isFinishing())
            return true;
        return false;
    }


    /**
     * 是否透明系统状态栏
     *
     * @return
     */
    public boolean isSystemBarTransparent() {
        return true;
    }

    /**
     * 是否设置状态栏
     *
     * @return
     */
    public boolean isSetSystemBar() {
        return true;
    }


    @Override
    public void toLogin() {
        DialogUtils.showDialog(this);
    }

    @Override
    public void endActivity() {
        finish();
    }

    @Override
    public void jumpActivity(Intent intent) {
        startActivity(intent);
    }


    public void jumpActivity(Class<? extends Activity> activity) {
        jumpActivity(activity, null);
    }

    public void jumpActivity(Class<? extends Activity> activity, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void jumpActivityForResult(Class<? extends Activity> activity, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void jumpActivitySetResult(Class<? extends Activity> activity, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    public void jumpActivitySetResult(Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isShouldHideInput(getCurrentFocus(), ev)) {
                hideSoftInput(getCurrentFocus().getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 默认的toast弹出方法，为了简化
     */
    public void BToast(String message) {
        SmartUtil.BToast(this, message);
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
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;// 点击EditText的事件，忽略它。
            } else {
                return true;
            }
        } // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长


        if (FlyerApplication.isNeedPgy) {
            // 自定义摇一摇的灵敏度，默认为950，数值越小灵敏度越高。
            PgyFeedbackShakeManager.setShakingThreshold(1000);

            // 以对话框的形式弹出，对话框只支持竖屏
            PgyFeedbackShakeManager.register(this);

            // 以Activity的形式打开，这种情况下必须在AndroidManifest.xml配置FeedbackActivity
            // 打开沉浸式,默认为false
            // FeedbackActivity.setBarImmersive(true);
            //PgyFeedbackShakeManager.register(MainActivity.this, true); 相当于使用Dialog的方式；
            PgyFeedbackShakeManager.register(this, false);
        }


        if (!BaseDataManger.getInstance().isActive()) {
            LogFly.i("从后台返回到前台");
            LogFly.e("从后台返回到前台：" + getClass().getName());
            long nowTime = DateUtil.getDateline();
            long disTime = nowTime - BaseDataManger.getInstance().getToForegroundTime();
            if (disTime > 30 * 60 * 1000) {//后台返回到前台 30分钟后才重新显示广告
                if (!getClass().getName().endsWith("SplashActivity")) {
                    BaseDataManger.getInstance().setIsActive(true);
                    //从后台返回到前台 显示广告
                    Bundle bundle = new Bundle();
                    bundle.putString("type", IntentKey.TYPE_ISACTIVE_SHOW_ADV);
                    jumpActivity(SplashActivity.class, bundle);
                }
            }
        }
    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPause(this);

        if (FlyerApplication.isNeedPgy) {
            PgyFeedbackShakeManager.unregister();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) {
            //app 进入后台
            LogFly.i("从前台返回到后台");
            //全局变量isActive = false 记录当前已经进入后台
            BaseDataManger.getInstance().setIsActive(false);
            LogFly.e("进入后台：" + getClass().getName());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (FlyerApplication.isNeedPgy) {
            PgyCrashManager.unregister();
        }
    }


    /**
     * 初始化变量 intent的数据 activity内部变量
     */
    public void initVariables() {

    }


    /**
     * 加载布局文件，初始化控件，为控件挂上事件方法
     */
    public void initViews() {
    }


    /**
     * 加载mobileAPI获取数据
     */
    public void loadData() {
    }


    /**
     * 是否是单次点击
     *
     * @param view
     * @return
     */
    public static boolean isSingleClick(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (view.getTag() == null) {
            view.setTag(currentTime);
            return true;
        }
        if (view.getTag() instanceof Long) {
            long lastTime = (long) view.getTag();
            if (currentTime - lastTime < MIN_CLICK_DELAY_TIME) {
                return false;
            } else {
                view.setTag(currentTime);
                return true;
            }
        } else {
            return true;
        }

    }

    @Override
    public void proDialogShow() {
        if (!TaskUtil.isOnUiThread()) {
            TaskUtil.postOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showDialog();
                }
            });
        } else {
            showDialog();
        }
    }

    @Override
    public void proDialogDissmiss() {
        dialogDismiss();
    }


    /**
     * 重写 onActivityResult 调用fragment的onActivityResult方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> fragmentList = fm.getFragments();
        if (fragmentList != null) {
            for (Fragment f : fragmentList) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }

    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode,
                              Intent data) {
        frag.onActivityResult(requestCode, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }


    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

}

