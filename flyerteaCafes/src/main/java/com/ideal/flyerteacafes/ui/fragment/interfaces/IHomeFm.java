package com.ideal.flyerteacafes.ui.fragment.interfaces;

import com.ideal.flyerteacafes.model.entity.AdvertBean;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.model.entity.HomeIconBean;
import com.ideal.flyerteacafes.model.entity.InterlocutionBean;
import com.ideal.flyerteacafes.model.entity.PostBean;
import com.ideal.flyerteacafes.model.entity.UpgradeBean;

import java.util.List;

/**
 * Created by fly on 2016/5/31.
 */
public interface IHomeFm {

    /**
     * 广告数据
     * @param advertBeanList
     */
    void callbackAdvertList(List<AdvertBean> advertBeanList);

    /**
     * 热门互动
     * @param interlocutionBeanList
     */
    void callbackInterlocution(List<InterlocutionBean> interlocutionBeanList);

    /**
     * 推荐帖子
     * @param postBeanList
     */
    void callbackPost(List<ArticleTabBean> postBeanList);

    /**
     * 刷新热门互动
     */
    void refreshInterlocutionAdapter();


    /**
     * 拉动作完成，回复初始样式
     */
    void pullToRefreshViewComplete();

    /**
     * 隐藏热门互动view
     */
    void hintInterlocutionView();

    void footerRefreshSetListviewScrollLocation(int oldSize);

    void showUpgradeDialog(UpgradeBean upgradeBean);


}
