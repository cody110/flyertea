package com.ideal.flyerteacafes.callback;

import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.utils.LogFly;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by fly on 2016/5/24.
 */
public abstract class ListDataCallback extends Callback<ListDataBean> {


    /** 报告 report*/
    public static final String LIST_KEY_ARTICLE = "article";
    /**问答 热门互动*/
    public static final String LIST_KEY_INTERLOCUTION = "forum_threadlist";
    /** 社区版块**/
    public static final String LIST_KEY_DATA = "data";
    /**活动*/
    public static final String LIST_KEY_LIST_EVENTLIST = "list,eventlist";
    /** list*/
    public static final String List_KEY_LIST = "list";
    /**小白*/
    public static final String LIST_KEY_LISTS = "data,articles";
    public static final String DATA = "data";
    /**帖子评论*/
    public static final String LIST_KEY_DATA_POSTS = "data,posts";
    /** 社区首页*/
    public static final String LIST_KEY_DATA_THREADS = "data,threads";
    public static final String LIST_KEY_DATA_FORUM_THREADLIST = "data,forum_threadlist";
    /** 版块帖子列表*/
    public static final String LIST_KEY_FORUM_THREADLIST = "data,forum_threadlist";
    /**我的关注**/
    public static final String LIST_KEY_MY_FAOORITE = "data,forums";
    /**搜索*/
    public static final String LIST_KEY_SEARCH = "datalist";
    /**广告*/
    public static final String LIST_KEY_ADV = "data,data";
    /**消息中心*/
    public static final String LIST_KEY_NOTIFICATION = "data,notifications";
    /**服务中心*/
    public static final String LIST_KEY_SERVICE = "list,servicelist";
    /**刷卡任务名*/
    public static final String LIST_KEY_TASKNAME = "data,missions";
    /**卡组织*/
    public static final String LIST_KEY_CHANNELS = "data,channels";
    /**银行*/
    public static final String LIST_KEY_BANK = "data,banks";
    /** 威望*/
    public static final String LIST_KEY_CREDITS = "data,credits";
    /** 位置搜索*/
    public static final String LIST_KEY_SERACH_LOCAION = "list,items";
    /** 帖子标签列表*/
    public static final String LIST_KEY_TAG_THREAD_LIST="html,tagthread,threadlist";
    /**文章标签列表*/
    public static final String LIST_KEY_TAG_ARTICLE_LIST="html,tagarticle,articlelist";
    /**热词*/
    public static final String LIST_KEY_HOTWORD="hotword";
    /**默认词*/
    public static final String LIST_KEY_DEFAULT_WORD="default_word";


    /**
     * listkey命名规则
     * 最后一个key为list数据
     * list上一层为含有是否有下一页信息的，对应ListDataBean
     */
    protected String listKey;
    protected Class tClass;

    public ListDataCallback(String listKey, Class tClass) {
        this.listKey = listKey;
        this.tClass = tClass;
    }

    @Override
    public ListDataBean parseNetworkResponse(String response) throws IOException, JSONException {
        return JsonUtils.jsonToListData(response, listKey, tClass);
    }

    @Override
    public void flyStart() {
        super.flyStart();

    }
}
