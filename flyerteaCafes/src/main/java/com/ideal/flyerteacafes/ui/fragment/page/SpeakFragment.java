package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;

public class SpeakFragment extends BaseFragment {

    private View view = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_chat, null);
        return view;

    }

}
