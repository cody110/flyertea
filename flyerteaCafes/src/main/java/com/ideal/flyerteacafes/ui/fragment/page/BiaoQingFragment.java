package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.GridviewBiaoQingAdapter;
import com.ideal.flyerteacafes.model.entity.SmileyBean;

import org.xutils.x;

import java.lang.reflect.Field;
import java.util.List;

import de.greenrobot.event.EventBus;

public class BiaoQingFragment extends Fragment {

    private List<SmileyBean> smileyList;

    public BiaoQingFragment(List<SmileyBean> smileyList) {
        this.smileyList = smileyList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_replypost_biaoqing_layout, container, false);
        x.view().inject(this, view);

        GridView gv = (GridView) view.findViewById(R.id.fragment_replypost_biaoqing_gridview);

        GridviewBiaoQingAdapter adapter = new GridviewBiaoQingAdapter(getActivity(), smileyList);
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (!TextUtils.isEmpty(smileyList.get(position).getImage())) {
                    EventBus.getDefault().post(smileyList.get(position));
                }
            }
        });

        return view;
    }

}
