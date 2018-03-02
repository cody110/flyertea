package com.ideal.flyerteacafes.ui.activity.event;

import android.content.Intent;

import com.youzan.sdk.model.goods.GoodsShareModel;
import com.youzan.sdk.web.bridge.IBridgeEnv;
import com.youzan.sdk.web.event.ShareDataEvent;

/**
 * 功能    : 获取当前页面的分享信息
 *
 * 使用场景: 将当前商品页面分享到各大社交平台增加曝光率
 * 触发条件: 在当前页面加载完成后调用{@code YouzanBridge.sharePage()}
 * 说明    : 回调中只会提供分享信息, 之后的分享动作需要开发者自行集成
 * 参数说明:
 *
 * {@link com.youzan.sdk.model.goods.GoodsShareModel}
 *
 *      title:  页面标题
 *      link:   当前页面链接
 *      desc:   商品详细的描述
 *      imgUrl: 商品图片链接
 *      imgWidth:   图片宽度
 *      imgHeight:  图片高度
 *
 */
public final class ShareEvent extends ShareDataEvent {

    /**
     * 回传分享数据, 再调用组件进行分享
     *
     * @param env 一些上下文环境
     * @param data 分享数据
     */
    @Override
    public void call(IBridgeEnv env, GoodsShareModel data) {
        String conent = data.getDesc()+  data.getLink();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, conent);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, data.getTitle());
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.setType("text/plain");

        //可以集成个推分享数据
        env.getActivity().startActivity(sendIntent);
    }
}
