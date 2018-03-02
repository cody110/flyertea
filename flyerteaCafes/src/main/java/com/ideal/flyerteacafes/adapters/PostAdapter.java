package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.model.entity.PostBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fly on 2016/6/6.
 */
public class PostAdapter extends CommonAdapter<ArticleTabBean> {

    public PostAdapter(Context context, List<ArticleTabBean> datasAll, int layoutId) {
        super(context, datasAll, layoutId);
    }

    @Override
    public void convert(final ViewHolder holder, final ArticleTabBean postBean) {
        holder.setText(R.id.item_post_subject, postBean.getTitle());

        ImageView iv = holder.getView(R.id.item_post_pic);
        if (DataUtils.isEmpty(postBean.getPic())) {
            iv.setVisibility(View.GONE);
        } else {
            DataUtils.downloadPicture(iv, postBean.getPic(), R.drawable.post_def);
            iv.setVisibility(View.VISIBLE);
        }

    }


}
