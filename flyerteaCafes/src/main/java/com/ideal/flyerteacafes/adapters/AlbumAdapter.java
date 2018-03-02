package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.flyer.tusdk.utils.Utils;
import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.loca.AlbumItemInfo;
import com.ideal.flyerteacafes.model.loca.ImageFloder;
import com.ideal.flyerteacafes.model.params.AlbumImgaeNumEvent;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.TaskUtil;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DateUtil;

import org.lasque.tusdk.core.TuSdkContext;
import org.lasque.tusdk.core.struct.TuSdkSize;
import org.lasque.tusdk.video.editor.TuSDKVideoImageExtractor;
import org.lasque.tusdk.video.mixer.TuSDKMediaDataSource;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/11/15.
 */

public class AlbumAdapter extends CommonAdapter<AlbumItemInfo> {

    //时间紧，暂时没写缓存机制
    public HashMap<String, Bitmap> maps = new HashMap<>();
    private List<String> mSelectedImage;
    private int maxPic;
    private int w_screen;

    public AlbumAdapter(Context context, List<AlbumItemInfo> datasAll, int layoutId, List<String> mSelectedImage, int maxPic) {
        super(context, datasAll, layoutId);
        this.mSelectedImage = mSelectedImage;
        this.maxPic = maxPic;
        w_screen = SharedPreferencesString.getInstances().getW_Screen();
    }

    @Override
    public void convert(ViewHolder holder, AlbumItemInfo albumItemInfo) {

        String riqi = "";
        int times = DateUtil.daysBetween(String.valueOf(DateUtil.getDateline()), albumItemInfo.getDateline());
        if (times == 0) {
            riqi = "今天";
        } else if (times == 1) {
            riqi = "昨天";
        } else {
            riqi = DateUtil.getDateToString(albumItemInfo.getDateline());
        }

        holder.setText(R.id.item_album_time, riqi + "，" + getWeekDay(albumItemInfo.getDateline()));
        GridView gridView = holder.getView(R.id.item_album_gv);


        if (albumItemInfo.getType() == ImageFloder.TYPE_VIDEO) {

            SelectItemAdapter adapter = (SelectItemAdapter) gridView.getTag();
            if (adapter == null) {
                adapter = new SelectItemAdapter<String>(mContext, albumItemInfo.getImgList(), R.layout.de_ph_grid_item) {
                    @Override
                    public void convert(final ViewHolder holder, final String s) {
                        final ImageView igv = holder.getView(R.id.id_item_image);

                        if (maps.get(s) != null) {
                            igv.setImageBitmap(maps.get(s));
                        } else {
                            TaskUtil.postOnBackgroundThread(new Runnable() {
                                @Override
                                public void run() {
                                    final Bitmap bitmap = BitmapTools.getVideoThumbnail(s, w_screen / 4, w_screen / 4);
                                    maps.put(s, bitmap);
                                    TaskUtil.postOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            igv.setImageBitmap(bitmap);
                                        }
                                    });
                                }
                            });
                        }
                        if (!DataUtils.isEmpty(mSelectedImage) && TextUtils.equals(s, mSelectedImage.get(0))) {
                            holder.setImageResource(R.id.id_item_select, R.drawable.select_xiangce_lv);
                        } else {
                            holder.setImageResource(R.id.id_item_select, R.drawable.pictures_selected);
                        }
                        holder.setOnClickListener(R.id.id_item_select, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mSelectedImage.clear();
                                mSelectedImage.add(s);
                                AlbumAdapter.this.notifyDataSetChanged();
                                EventBus.getDefault().post(new TagEvent(TagEvent.TAG_VIDEO_LIST_SELECT));
                            }
                        });
                    }
                };
                gridView.setAdapter(adapter);
            } else {
                adapter.refresh(albumItemInfo.getImgList());
                adapter.notifyDataSetChanged();
            }

        } else {
            AlbumImageAdapter adapter;
            if (gridView.getTag() == null) {
                adapter = new AlbumImageAdapter(mContext, albumItemInfo.getImgList(), R.layout.de_ph_grid_item, mSelectedImage, maxPic);
                gridView.setAdapter(adapter);
                gridView.setTag(adapter);
            } else {
                adapter = (AlbumImageAdapter) gridView.getTag();
                adapter.refresh(albumItemInfo.getImgList());
                adapter.notifyDataSetChanged();
            }
        }

    }

    public String getWeekDay(String datatime) {
        Date date = new Date(Long.parseLong(datatime));
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        return dateFm.format(date);
    }


}
