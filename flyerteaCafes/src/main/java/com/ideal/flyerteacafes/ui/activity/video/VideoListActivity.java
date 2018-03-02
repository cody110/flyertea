package com.ideal.flyerteacafes.ui.activity.video;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.AlbumAdapter;
import com.ideal.flyerteacafes.adapters.SelectItemAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.loca.AlbumItemInfo;
import com.ideal.flyerteacafes.model.loca.ImageFloder;
import com.ideal.flyerteacafes.model.params.AlbumImgaeNumEvent;
import com.ideal.flyerteacafes.ui.activity.AlbumActivity;
import com.ideal.flyerteacafes.ui.activity.AlbumListActivity;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IVideoList;
import com.ideal.flyerteacafes.ui.activity.presenter.VideoListPresenter;
import com.ideal.flyerteacafes.ui.view.ToolBar;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.PermissionUtils;
import com.ideal.flyerteacafes.utils.TaskUtil;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2018/1/15.
 */

@ContentView(R.layout.activity_video_list)
public class VideoListActivity extends MVPBaseActivity<IVideoList, VideoListPresenter> implements IVideoList {

    @ViewInject(R.id.listview)
    ListView listView;
    @ViewInject(R.id.next_btn)
    TextView next_btn;
    @ViewInject(R.id.btn_title)
    TextView btn_title;

    private static final int REQUEST_ALBUMLIST = 1, REQUEST_MOVIEPREVIEWANDCUT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        EventBus.getDefault().register(this);
        next_btn.setEnabled(false);

        // 申请权限
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_READ_EXTERNAL_STORAGE, mPermissionGrant);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (albumAdapter != null) {
            //遍历map中的值
            for (Bitmap bit : albumAdapter.maps.values()) {
                if (bit != null && !bit.isRecycled()) {
                    bit.recycle();
                }
            }
            albumAdapter.maps.clear();
        }
    }

    @Override
    protected VideoListPresenter createPresenter() {
        return new VideoListPresenter();
    }

    /**
     * 选择数量变更提醒
     */
    public void onEventMainThread(TagEvent event) {
        if (TagEvent.TAG_VIDEO_LIST_SELECT == event.getTag()) {
            next_btn.setEnabled(true);
            next_btn.setTextColor(getResources().getColor(R.color.text_blue));
            next_btn.setBackground(getResources().getDrawable(R.drawable.blue_frame));
        }
    }


    /**
     * 成功
     */
    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
//                            getLoadMedia();
                    mPresenter.init(VideoListActivity.this);
                    break;

            }
        }
    };

    /**
     * 申请权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ALBUMLIST:
                    String title = data.getStringExtra("title");
                    btn_title.setText(title);
                    ImageFloder imageFloder = (ImageFloder) data.getSerializableExtra("data");
                    mPresenter.selectFloder(imageFloder);
                    break;

                case REQUEST_MOVIEPREVIEWANDCUT:
                    finish();
                    break;

            }
        }
    }

    @Event({R.id.next_btn, R.id.btn_close, R.id.btn_title})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.next_btn:
                if (!DataUtils.isEmpty(mPresenter.mSelectImage)) {
                    Intent intent = new Intent(this, MoviePreviewAndCutActivity.class);
                    intent.putExtra("videoPath", mPresenter.mSelectImage.get(0));
                    startActivityForResult(intent, REQUEST_MOVIEPREVIEWANDCUT, null);
                }
                break;
            case R.id.btn_close:
                finish();
                break;
            case R.id.btn_title:
                List<ImageFloder> imageFloderList = new ArrayList<>();
                Collection<ImageFloder> floders = mPresenter.floderMap.values();
                imageFloderList.addAll(floders);
                Bundle bundle = new Bundle();
                bundle.putSerializable("datas", (Serializable) imageFloderList);
                jumpActivityForResult(AlbumListActivity.class, bundle, REQUEST_ALBUMLIST);
                break;
        }
    }

    AlbumAdapter albumAdapter;

    @Override
    public void actionSetAlbumAdapter(List<AlbumItemInfo> albumItemInfoList, List<String> mSelectedImage) {
        if (albumAdapter == null) {
            albumAdapter = new AlbumAdapter(this, albumItemInfoList, R.layout.item_album, mSelectedImage, 1);
            listView.setAdapter(albumAdapter);
        } else {
            albumAdapter.notifyDataSetChanged();
        }
    }


}
