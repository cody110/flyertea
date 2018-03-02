package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.content.Context;

import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.callback.BaseBeanCallback;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.callback.MapCallback;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.MapBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IBase;
import com.ideal.flyerteacafes.ui.activity.interfaces.IDialog;
import com.ideal.flyerteacafes.utils.LogFly;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by fly on 2016/1/13.
 */
public class BasePresenter<T> {

    protected Context context = FlyerApplication.getContext();

    protected Reference<IDialog> mDialogRef;

    protected Reference<IBase> mBaseRef;

    public void attachDialog(IDialog iDialog) {
        mDialogRef = new WeakReference<IDialog>(iDialog);
    }

    public void attachBase(IBase iBase) {
        mBaseRef = new WeakReference<IBase>(iBase);
    }

    public IDialog getDialog() {
        return mDialogRef.get();
    }

    public IBase getBaseView() {
        return mBaseRef.get();
    }

    public boolean isDialogAttached() {
        return mDialogRef != null && mDialogRef.get() != null;
    }


    protected Reference<T> mViewRef;

    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view);
    }

    protected T getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public void detachDialog() {
        if (mDialogRef != null) {
            mDialogRef.clear();
            mDialogRef = null;
        }
    }

    public void detachBase() {
        if (mBaseRef != null) {
            mBaseRef.clear();
            mBaseRef = null;
        }
    }


    public void init(Activity activity) {
    }

    /**
     * 通用接口错误返回
     */
    protected boolean handleError() {
        if (isViewAttached()) {
            if (isDialogAttached()) {
                getDialog().proDialogDissmiss();
            }
        }
        return isViewAttached();
    }


    protected abstract class PMapCallback extends MapCallback {

        @Override
        public void onSuccess(String result) {
            if (isViewAttached()) {
                super.onSuccess(result);
            }
        }

        @Override
        public void flyFinished() {
            super.flyFinished();
            if (!isDialogAttached()) return;
            getDialog().proDialogDissmiss();
        }

    }

    protected abstract class PBaseCallback extends BaseBeanCallback {

        @Override
        public void onSuccess(String result) {
            if (isViewAttached()) {
                super.onSuccess(result);
            }
        }

        @Override
        public void flyFinished() {
            super.flyFinished();
            if (!isDialogAttached()) return;
            getDialog().proDialogDissmiss();
        }

    }

    protected abstract class PStringCallback extends StringCallback {

        @Override
        public void onSuccess(String result) {
            if (isViewAttached()) {
                super.onSuccess(result);
            }
        }

        @Override
        public void flyFinished() {
            super.flyFinished();
            if (!isDialogAttached()) return;
            getDialog().proDialogDissmiss();
        }

    }

    protected abstract class PDataCallback<V> extends DataCallback<V> {

        public PDataCallback() {

        }

        public PDataCallback(String listKey) {
            super(listKey);
        }

        @Override
        public void onSuccess(String result) {
            if (isViewAttached()) {
                super.onSuccess(result);
            }
        }


        @Override
        public void flyFinished() {
            super.flyFinished();
            if (!isDialogAttached()) return;
            getDialog().proDialogDissmiss();
        }

    }

    protected abstract class PListDataCallback extends ListDataCallback {

        public PListDataCallback(String listKey, Class tClass) {
            super(listKey, tClass);
        }

        @Override
        public void onSuccess(String result) {
            if (isViewAttached()) {
                super.onSuccess(result);
            }
        }

        @Override
        public void flyFinished() {
            super.flyFinished();
            if (!isDialogAttached()) return;
            getDialog().proDialogDissmiss();
        }
    }

}
