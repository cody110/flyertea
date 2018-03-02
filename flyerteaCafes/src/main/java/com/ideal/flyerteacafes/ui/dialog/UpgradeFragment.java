package com.ideal.flyerteacafes.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.UpgradeBean;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by fly on 2016/12/14.
 */

public class UpgradeFragment extends DialogFragment {
    private UpgradeBean upgradeBean;

    @ViewInject(R.id.upgrade_version_title)
    TextView upgrade_version_title;
    @ViewInject(R.id.upgrade_version_content)
    TextView upgrade_version_content;
    @ViewInject(R.id.upgrade_version_choose_upgrade)
    View upgrade_version_choose_upgrade;
    @ViewInject(R.id.upgrade_version_once_upgrade)
    View upgrade_version_once_upgrade;
    @ViewInject(R.id.upgrade_root)
    View upgrade_root;
    @ViewInject(R.id.upgrade_content_layout)
    View upgrade_content_layout;

    public static final String BUNDLE_DATA = "data";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        upgradeBean = (UpgradeBean) getArguments().getSerializable(BUNDLE_DATA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//去掉背景色
        View view = inflater.inflate(R.layout.fragment_dialog_upgrade, container, false);
        x.view().inject(this, view);
        if (upgradeBean == null) return view;
        upgrade_content_layout.setOnClickListener(null);
        WidgetUtils.setText(upgrade_version_title, upgradeBean.getTitle());
        WidgetUtils.setText(upgrade_version_content, upgradeBean.getUpdates());
        if (TextUtils.equals(upgradeBean.getForceupdate(), "1")) {
            upgrade_version_once_upgrade.setVisibility(View.VISIBLE);
            upgrade_version_choose_upgrade.setVisibility(View.GONE);

            getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                    return false;
                }
            });

        } else {
            upgrade_version_choose_upgrade.setVisibility(View.VISIBLE);
            upgrade_version_once_upgrade.setVisibility(View.GONE);
            upgrade_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Event({R.id.upgrade_version_upgrade, R.id.upgrade_version_once_upgrade, R.id.upgrade_version_close})
    private void click(View view) {
        switch (view.getId()) {
            case R.id.upgrade_version_close:
                dismiss();
                break;
            case R.id.upgrade_version_upgrade:
                toUpgrade();
                break;
            case R.id.upgrade_version_once_upgrade:
                toUpgrade();
                break;
        }
    }

    private void toUpgrade() {
        if (upgradeBean != null) {
            Intent intent = new Intent(getActivity(), TbsWebActivity.class);
            intent.putExtra("url", upgradeBean.getUplink());
            startActivity(intent);
        }
        dismiss();
    }

}
