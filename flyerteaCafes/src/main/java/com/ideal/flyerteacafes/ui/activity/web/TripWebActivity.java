package com.ideal.flyerteacafes.ui.activity.web;

import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.utils.HmacSha256;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.tools.StringTools;

/**
 * Created by fly on 2017/4/11.
 * 旅行的加密appkey 跟茶馆的不一样，需重写拼接加密方法
 */

public class TripWebActivity extends TbsWebActivity {

    @Override
    public String urlAppedToken(String url) {
        if (UserManger.getUserInfo() == null) {
            return url;
        }
        String token = UserManger.getUserInfo().getMember_uid() + "|" + DateUtil.getDateline();
        String newToken = HmacSha256.getSignature(token, "d8b80b0bf392ce6d5a59f9861c6bd67e");
        newToken = StringTools.encodeBase64((token + "|" + newToken).getBytes());
        return url + "&appkey=33432a0557f0e29ce9&token=" + newToken;
    }
}
