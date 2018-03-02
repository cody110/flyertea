package com.ideal.flyerteacafes.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.model.loca.ImageFloder;
import com.ideal.flyerteacafes.ui.activity.base.BaseToolbarActivity;
import com.ideal.flyerteacafes.ui.view.ToolBar;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.TaskUtil;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.ideal.flyerteacafes.utils.tools.V;

import java.util.HashMap;
import java.util.List;

import static com.umeng.analytics.a.s;

/**
 * Created by fly on 2015/11/23.
 */
public class AlbumListActivity extends BaseToolbarActivity {

    private ListView mListDir;

    private List<ImageFloder> datas;
    private HashMap<String, Bitmap> maps = new HashMap<>();

    @Override
    protected void setTitleBar(ToolBar mToolbar) {
        mToolbar.setTitle("照片");
    }

    @Override
    protected View createBodyView(Bundle savedInstanceState) {
        datas = (List<ImageFloder>) getIntent().getSerializableExtra("datas");
        View view = getView(R.layout.de_ph_list_dir);
        mListDir = V.f(view, R.id.id_list_dir);
        mListDir.setAdapter(new CommonAdapter<ImageFloder>(this, datas,
                R.layout.de_ph_list_dir_item) {
            @Override
            public void convert(ViewHolder helper, final ImageFloder item) {
                helper.setText(R.id.id_dir_item_name, item.getName());
                if (item.getName().contains("Camera")) {
                    helper.setText(R.id.id_dir_item_name, "手机相册");
                } else if (item.getName().contains("/Screenshots")) {
                    helper.setText(R.id.id_dir_item_name, "屏幕截图");
                } else {
                    helper.setText(R.id.id_dir_item_name, item.getName().replace("/", ""));
                }
                if (item.getType() == ImageFloder.TYPE_VIDEO) {
                    helper.setText(R.id.id_dir_item_count, "");
                    final ImageView igv = helper.getView(R.id.id_dir_item_image);
                    if (maps.get(item.getFirstImagePath()) != null) {
                        igv.setImageBitmap(maps.get(item.getFirstImagePath()));
                    } else {
                        TaskUtil.postOnBackgroundThread(new Runnable() {
                            @Override
                            public void run() {
                                final Bitmap bitmap = BitmapTools.getVideoThumbnail(item.getFirstImagePath(), SharedPreferencesString.getInstances().getW_Screen() / 4, SharedPreferencesString.getInstances().getW_Screen() / 4);
                                maps.put(item.getFirstImagePath(), bitmap);
                                TaskUtil.postOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        igv.setImageBitmap(bitmap);
                                    }
                                });
                            }
                        });
                    }
                } else {
                    helper.setImageUrl(R.id.id_dir_item_image, item.getFirstImagePath(), R.drawable.icon_def);
                    helper.setText(R.id.id_dir_item_count, "(" + item.getCount() + ")");
                }
            }
        });
        mListDir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name;
                if (datas.get(position).getName().contains("Camera")) {
                    name = "手机相册";
                } else if (datas.get(position).getName().contains("/Screenshots")) {
                    name = "屏幕截图";
                } else {
                    name = datas.get(position).getName().replace("/", "");
                }
                Bundle bundle = new Bundle();
                bundle.putString("title", name);
                bundle.putSerializable("data", datas.get(position));
                jumpActivitySetResult(bundle);
            }
        });
        return view;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //遍历map中的值
        for (Bitmap bit : maps.values()) {
            if (bit != null && !bit.isRecycled()) {
                bit.recycle();
            }
        }
        maps.clear();
    }
}
