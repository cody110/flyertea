package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.AlbumAdapter;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.loca.AlbumItemInfo;
import com.ideal.flyerteacafes.model.params.AlbumImgaeNumEvent;
import com.ideal.flyerteacafes.model.loca.ImageFloder;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IAlbum;
import com.ideal.flyerteacafes.ui.activity.presenter.AlbumPresenter;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteMajorThreadContentActivity;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/11/15.
 */
@ContentView(R.layout.activity_album)
public class AlbumActivity extends MVPBaseActivity<IAlbum, AlbumPresenter> implements IAlbum {
    @ViewInject(R.id.album_list)
    ListView listView;
    @ViewInject(R.id.next_btn)
    TextView next_btn;
    @ViewInject(R.id.btn_title)
    TextView btn_title;

    AlbumAdapter albumAdapter = null;

    public static final int REQUEST_PREVIEW = 1, REQUEST_ALBUMLIST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        EventBus.getDefault().register(this);
        mPresenter.init(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PREVIEW:
                    mPresenter.actionPreviewBack(data);
                    break;

                case REQUEST_ALBUMLIST:
                    String title = data.getStringExtra("title");
                    WidgetUtils.setText(btn_title, title);
                    ImageFloder imageFloder = (ImageFloder) data.getSerializableExtra("data");
                    mPresenter.selectFloder(imageFloder);
                    break;

            }
        }
    }

    /**
     * 选择数量变更提醒
     */
    public void onEventMainThread(AlbumImgaeNumEvent event) {
        next_btn.setText("下一步(" + event.getNum() + "/" + mPresenter.maxPic + ")");
    }

    @Override
    protected AlbumPresenter createPresenter() {
        return new AlbumPresenter();
    }

    @Override
    public void actionSetAlbumAdapter(List<AlbumItemInfo> albumItemInfoList, List<String> mSelectedImage) {
        if (albumAdapter == null) {
            albumAdapter = new AlbumAdapter(this, albumItemInfoList, R.layout.item_album, mSelectedImage, mPresenter.maxPic);
            listView.setAdapter(albumAdapter);
        } else {
            albumAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void actionOk(List<String> mSelectImage) {
        EventBus.getDefault().post(new TagEvent(TagEvent.TAG_FINISH_WRITETHREADACTIVITY));
        Intent intent = getIntent();
        intent.putExtra("mSelectedImage", (Serializable) mSelectImage);
        intent.setClass(this, WriteMajorThreadContentActivity.class);
        startActivity(intent);
        finish();
    }


    @Event({R.id.btn_title, R.id.btn_close, R.id.album_bootom_layout_next, R.id.album_preview_tv})
    private void click(View view) {
        switch (view.getId()) {
            case R.id.btn_title:
                mPresenter.actionAlbumList();
                break;

            case R.id.btn_close:
                finish();
                break;

            case R.id.album_preview_tv://预览
                mPresenter.actionPreview();
                break;

            case R.id.album_bootom_layout_next:
                mPresenter.actionOk();
                break;
        }
    }

}
