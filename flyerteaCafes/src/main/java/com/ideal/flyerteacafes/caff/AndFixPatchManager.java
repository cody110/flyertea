package com.ideal.flyerteacafes.caff;

import android.content.Context;

import com.alipay.euler.andfix.patch.PatchManager;
import com.ideal.flyerteacafes.utils.tools.Tools;

/**
 * Created by fly on 2017/8/24.
 */

public class AndFixPatchManager {

    private static AndFixPatchManager mInstance = null;

    private static PatchManager mPatchManager = null;

    public static AndFixPatchManager getInstance() {
        if (mInstance == null) {
            synchronized (AndFixPatchManager.class) {
                if (mInstance == null) {
                    mInstance = new AndFixPatchManager();
                }
            }
        }
        return mInstance;
    }

    //初始化AndFix方法
    public void initPatch(Context context) {
        mPatchManager = new PatchManager(context);
        mPatchManager.init(Tools.getVersion(context));
        mPatchManager.loadPatch();
    }

    //加载我们的patch文件
    public void addPatch(String path) {
        try {
            if (mPatchManager != null) {
                mPatchManager.addPatch(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
