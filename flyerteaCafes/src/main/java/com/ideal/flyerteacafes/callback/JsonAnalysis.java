package com.ideal.flyerteacafes.callback;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ideal.flyerteacafes.model.entity.AdvertBean;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.ChatBean;
import com.ideal.flyerteacafes.model.entity.CitysBean;
import com.ideal.flyerteacafes.model.entity.CollectionBean;
import com.ideal.flyerteacafes.model.entity.CreditCardBackBean;
import com.ideal.flyerteacafes.model.entity.CreditCardBean;
import com.ideal.flyerteacafes.model.entity.DisComment;
import com.ideal.flyerteacafes.model.entity.FeedsBean;
import com.ideal.flyerteacafes.model.entity.FlyHuiModel;
import com.ideal.flyerteacafes.model.entity.HobbiesBean;
import com.ideal.flyerteacafes.model.entity.HotelBean;
import com.ideal.flyerteacafes.model.entity.HotelsInfo;
import com.ideal.flyerteacafes.model.entity.InvitationInfo;
import com.ideal.flyerteacafes.model.entity.ListObjectBean;
import com.ideal.flyerteacafes.model.entity.LoginCodeResponse;
import com.ideal.flyerteacafes.model.entity.MapBean;
import com.ideal.flyerteacafes.model.entity.MessageReadsBean;
import com.ideal.flyerteacafes.model.entity.MyFrirends;
import com.ideal.flyerteacafes.model.entity.MyPostBean;
import com.ideal.flyerteacafes.model.entity.NoticeBean;
import com.ideal.flyerteacafes.model.entity.NotificationBean;
import com.ideal.flyerteacafes.model.entity.PersonalLetterBean;
import com.ideal.flyerteacafes.model.entity.RemindBean;
import com.ideal.flyerteacafes.model.entity.ReplyBean;
import com.ideal.flyerteacafes.model.entity.SearchHistoryBean;
import com.ideal.flyerteacafes.model.entity.SearchMemberBean;
import com.ideal.flyerteacafes.model.entity.SmileyBean;
import com.ideal.flyerteacafes.model.entity.StartAdvertBean;
import com.ideal.flyerteacafes.model.entity.TypeAll;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.GsonTools;
import com.ideal.flyerteacafes.utils.tools.StringTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonAnalysis {

    private static Gson gson = new GsonBuilder().serializeNulls().create();


    public static final String getRegistMsg(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("Variables");
            jsonObject = jsonObject.getJSONObject("list");
            String msg = jsonObject.getString("result");
            return msg;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static final MapBean getMapBean(String jsonString, String key) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("Variables");
            String data = jsonObject.getString("list");
            MapBean bean = GsonTools.jsonToBean(data, MapBean.class);
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 论坛新结构，success
     *
     * @param jsonString
     * @return
     */
    public static BaseBean getSuccessbean(String jsonString) {
        BaseBean bean = new BaseBean();
        bean.setJsonType(BaseBean.type_4);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("Variables");
            String success = jsonObject.getString("success");
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            bean.setSuccess(success);
            bean.setMessage(msg);
            bean.setCode(code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }


    /**
     * success == 1
     *
     * @return
     */
    public static boolean isSuccessEquals1(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) return false;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("Variables");
            String success = jsonObject.getString("success");
            if (TextUtils.equals(success, "1"))
                return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean isSuccessEquals1By2(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) return false;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            String success = jsonObject.getString("success");
            if (TextUtils.equals(success, "1"))
                return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * success == 1
     *
     * @return
     */
    public static String getMessage(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) return null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("Variables");
            String msg = jsonObject.getString("msg");
            return msg;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回酒店搜索列表
     *
     * @param jsonString
     * @return
     */
    public static ListDataBean getHotelSerachList(String jsonString) {
        ListDataBean listDataBean = new ListDataBean();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.has("code"))
                listDataBean.setCode(jsonObject.getString("code"));
            if (jsonObject.has("msg"))
                listDataBean.setMessage(jsonObject.getString("msg"));
            String data = jsonObject.getString("data");
            List<HotelsInfo> list = GsonTools.jsonToList(data, HotelsInfo.class);
            listDataBean.setDataList(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listDataBean;
    }


    /**
     * 第三方登录
     *
     * @param jsonString
     * @return
     */
    public static BaseBean getBasebean(String jsonString) {
        BaseBean bean = new BaseBean();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            String description = jsonObject.getString("description");
            String code = jsonObject.getString("code");
            bean.setMessage(description);
            bean.setCode(code);
            String data = jsonObject.getString("data");
            bean.setData(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }


    public static MessageReadsBean jsonToMessageReads(String jsonString) {
        MessageReadsBean bean = new MessageReadsBean();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("Variables");
            bean.setFormhash(jsonObject.getString("formhash"));
            bean.setGroupid(jsonObject.getString("groupid"));
            bean.setGroupname(jsonObject.getString("groupname"));
            bean.setIsmoderator(jsonObject.getString("ismoderator"));
            bean.setMember_avatar(jsonObject.getString("member_avatar"));
            bean.setMember_uid(jsonObject.getString("member_uid"));
            bean.setMsg(jsonObject.getString("msg"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bean;
    }


    /**
     * 我的主贴/我的回复
     *
     * @param jsonString
     * @param key
     * @return
     */
    @SuppressWarnings("unused")
    public static ListObjectBean jsonToMyPostList(String jsonString,
                                                  String key, int mark) {
        ListObjectBean listBean = new ListObjectBean();
        List<MyPostBean> postList = new ArrayList<MyPostBean>();
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        // 登录了，就没有message，进入catch
        try {
            jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("Message");
            listBean.setCode(Utils.ret_def_code);
            String msg = jsonObject.getString("messagestr");
            listBean.setMessage(msg);
        } catch (JSONException e) {
            try {
                listBean.setCode(Utils.ret_suc_code);
                jsonObject = new JSONObject(jsonString);
                jsonObject = jsonObject.getJSONObject("Variables");
                jsonArray = jsonObject.getJSONArray(key);

                if (jsonArray == null)
                    return null;
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    MyPostBean bean = new MyPostBean();
//                    bean.setMark(mark);
                    bean.setTid(jsonObject.getInt("tid"));
                    bean.setDbdateline(jsonObject.getString("dateline"));
                    try {
                        bean.setForumname(jsonObject.getString("forum_name"));
                    } catch (Exception e2) {
                        bean.setForumname(jsonObject.getString("forumname"));
                    }
//                    bean.setReplies(jsonObject.getInt("replies"));
                    bean.setSubject(jsonObject.getString("subject"));
                    // bean.setPost_type(jsonObject.getInt("post_type"));

                    // if (jsonObject.getInt("post_type") != 0) {
                    // bean.setUser_icon_url(jsonObject.getString("user_icon_url"));
                    // bean.setAuthor(jsonObject.getString("author"));
                    // }
                    postList.add(bean);
                }
                listBean.setDataList(postList);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        return listBean;
    }

    /**
     * 帖子正文详情的点评列表
     *
     * @param replyJsonArray
     * @param replyList
     * @return
     */
    @SuppressWarnings("unused")
    private static List<ReplyBean> jsonArrayToReplyList(
            JSONArray replyJsonArray, List<ReplyBean> replyList) {

        if (replyJsonArray != null && replyJsonArray.length() > 0) {
            JSONObject jsonObject = null;
            for (int i = 0; i < replyJsonArray.length(); i++) {
                ReplyBean bean = new ReplyBean();
                try {
                    jsonObject = replyJsonArray.getJSONObject(i);
                    bean.setAuthor(jsonObject.getString("author"));
                    bean.setDateline(jsonObject.getString("dateline"));
                    // bean.setFace(jsonObject.getString("face"));
                    bean.setMessage(jsonObject.getString("comment"));
                    replyList.add(bean);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        return replyList;
    }

    /**
     * 帖子回复
     *
     * @param jsonString
     * @return
     */
    public static BaseBean jsonToReplyPost(String jsonString) {
        BaseBean bean = new BaseBean();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("Message");
            bean.setCode(jsonObject.getString("messageval"));
            bean.setMessage(jsonObject.getString("messagestr"));
            System.out.println();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bean;
    }

    /**
     * 帖子详情页，回复送鲜花
     *
     * @param jsonString
     * @return
     */
    public static BaseBean jsonToSendFlower(String jsonString) {
        BaseBean bean = new BaseBean();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            bean.setCode(jsonObject.getString("status"));
            bean.setMessage(jsonObject.optString("msg"));
            if (TextUtils.equals(bean.getMessage(), "null")) {
                bean.setMessage(null);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 酒店详情
     *
     * @param jsonString
     * @return
     */
    public static ListObjectBean jsonToHotelListBean(String jsonString) {
        ListObjectBean listBean = new ListObjectBean();
        List<HotelBean> hotelList = new ArrayList<HotelBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject msgObject = jsonObject.getJSONObject("Message");
            listBean.setCode(msgObject.getString("messageval"));
            listBean.setMessage(msgObject.getString("messagestr"));
            JSONArray jsonArray = jsonObject.getJSONArray("lists");
            for (int i = 0; i < jsonArray.length(); i++) {
                HotelBean bean = new HotelBean();
                jsonObject = jsonArray.getJSONObject(i);
                bean.setHotel_id(jsonObject.getInt("hotel_id"));
                bean.setHotel_name(jsonObject.getString("hotel_name"));
                bean.setHotel_icon_url(jsonObject.getString("hotel_icon_url"));
                bean.setInfo(jsonObject.getString("hotel_info"));
                bean.setWeb_site(jsonObject.getString("hotel_web_site"));
                bean.setTelephone(jsonObject.getString("hotel_telephone"));
                bean.setApp_web_site(jsonObject
                        .getString("hotel_app_web_site_andriod"));
                bean.setApp_info(jsonObject.getString("hotel_app_info_andriod"));
                bean.setApp_icon_url(jsonObject
                        .getString("hotel_app_icon_url_andriod"));
                hotelList.add(bean);
            }
            listBean.setDataList(hotelList);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return listBean;
    }

    /**
     * 信用卡，基础数据
     *
     * @param jsonString
     * @param listName
     * @return
     */
    public static ListObjectBean jsonToCreditCardList(String jsonString,
                                                      String listName) {
        ListObjectBean listBean = new ListObjectBean();
        List<CreditCardBean> cardList = new ArrayList<CreditCardBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject msgObject = jsonObject.getJSONObject("Message");
            listBean.setCode(msgObject.getString("messageval"));
            listBean.setMessage(msgObject.getString("messagestr"));
            jsonObject = jsonObject.getJSONObject("lists");
            JSONArray jsonArray = jsonObject.getJSONArray(listName);
            for (int i = 0; i < jsonArray.length(); i++) {
                CreditCardBean bean = new CreditCardBean();
                jsonObject = jsonArray.getJSONObject(i);
                if (listName.equals("mcclist")) {
                    bean.setId(jsonObject.getInt("code"));
                } else {
                    bean.setId(jsonObject.getInt("id"));
                }
                bean.setName(jsonObject.getString("name"));
                cardList.add(bean);
            }
            listBean.setDataList(cardList);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return listBean;
    }


    public static final ListObjectBean jsonToCreditCardBack(String jsonString) {
        ListObjectBean beanList = new ListObjectBean();
        List<CreditCardBackBean> backList = new ArrayList<CreditCardBackBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject msgObject = jsonObject.getJSONObject("Message");
            beanList.setCode(msgObject.getString("messageval"));
            beanList.setMessage(msgObject.getString("messagestr"));

            beanList.setCount(jsonObject.getInt("count"));
            JSONArray jsonArray = jsonObject.getJSONArray("lists");
            for (int i = 0; i < jsonArray.length(); i++) {
                CreditCardBackBean bean = new CreditCardBackBean();
                jsonObject = jsonArray.getJSONObject(i);
                bean.setType_id(jsonObject.getInt("type_id"));
                bean.setPay_type(jsonObject.getString("pay_type"));
                bean.setUsername(jsonObject.getString("username"));
                bean.setDate(jsonObject.getString("date"));
                bean.setScores(jsonObject.getInt("scores"));
                bean.setTypename(jsonObject.getString("typename"));
                bean.setBankname(jsonObject.getString("bankname"));
                backList.add(bean);
            }
            beanList.setDataList(backList);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return beanList;
    }


    /**
     * 表情
     *
     * @param context
     * @param jsonString
     * @return
     */
    public static final List<SmileyBean> jsonToSmiley(Context context,
                                                      String jsonString) {
        List<SmileyBean> listBean = new ArrayList<SmileyBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("Variables");
            JSONArray jsonArray = jsonObject.getJSONArray("smilies");
            for (int i = 0; i < jsonArray.length(); i++) {
                SmileyBean bean = new SmileyBean();
                jsonObject = jsonArray.getJSONObject(i);
                bean.setCode(jsonObject.getString("code"));
                bean.setImage(jsonObject.getString("image"));
                bean.setIid(DataUtils.SubStringDef(context,
                        jsonObject.getString("image")));
                listBean.add(bean);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return listBean;
    }


    /**
     * 签到
     *
     * @param jsonString
     * @return
     */
    public static final BaseBean jsonToSignin(String jsonString) {
        BaseBean bean = new BaseBean();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject msgObject = jsonObject.getJSONObject("Message");
            bean.setCode(msgObject.getString("messageval"));
            bean.setMessage(msgObject.getString("messagestr"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 我的提醒
     *
     * @param jsonString
     * @return
     */
    public static final ListObjectBean jsonToRemindList(String jsonString) {
        ListObjectBean beanList = new ListObjectBean();
        List<RemindBean> remindList = new ArrayList<RemindBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("promptList");
            for (int i = 0; i < jsonArray.length(); i++) {
                RemindBean bean = new RemindBean();
                jsonObject = jsonArray.getJSONObject(i);
                bean.setFace(jsonObject.getString("face"));
                bean.setType(jsonObject.getString("type"));
                bean.setNote(jsonObject.getString("note"));
                bean.setDateline(jsonObject.getString("dateline"));
                bean.setUid(jsonObject.getInt("uid"));
                bean.setAuthor(jsonObject.getString("author"));
                bean.setIsread(jsonObject.getString("new"));
                bean.setType(jsonObject.getString("type"));
                bean.setFrom_id(jsonObject.getInt("from_id"));
                bean.setFrom_idtype(jsonObject.getString("from_idtype"));
                remindList.add(bean);
            }
            beanList.setDataList(remindList);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return beanList;
    }

    /**
     * 我的公告
     *
     * @param jsonString
     * @return
     */
    public static final ListObjectBean jsonToNoticeList(String jsonString) {
        ListObjectBean beanList = new ListObjectBean();
        List<NoticeBean> noticeList = new ArrayList<NoticeBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("Variables");
            JSONArray jsonArray = jsonObject.getJSONArray("announcement");
            for (int i = 0; i < jsonArray.length(); i++) {
                NoticeBean bean = new NoticeBean();
                jsonObject = jsonArray.getJSONObject(i);
                bean.setSubject(jsonObject.getString("subject"));
                bean.setStarttime(jsonObject.getString("starttime"));
                bean.setType(jsonObject.getInt("type"));
                bean.setMessage(jsonObject.getString("message"));
                noticeList.add(bean);
            }
            beanList.setDataList(noticeList);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return beanList;
    }


    /**
     * 聊天对话列表
     *
     * @param jsonString
     * @return
     */
    public static final ListObjectBean jsonToChatList(String jsonString) {
        ListObjectBean beanList = new ListObjectBean();
        List<ChatBean> chatList = new ArrayList<ChatBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            System.out.println();
            jsonObject = jsonObject.getJSONObject("Variables");
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                ChatBean bean = new ChatBean();
                jsonObject = jsonArray.getJSONObject(i);
                bean.setDateline(jsonObject.getString("dateline"));
                bean.setFace(jsonObject.getString("face"));
                bean.setSubject(jsonObject.getString("message"));
                bean.setTouid(jsonObject.getString("touid"));
                bean.setAuthorid(jsonObject.getString("authorid"));
                chatList.add(bean);
            }
            beanList.setDataList(chatList);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return beanList;
    }

    /**
     * 发送私信
     *
     * @param jsonString
     * @return
     */
    public static final BaseBean jsonToSendPm(String jsonString) {
        BaseBean bean = new BaseBean();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("Message");
            bean.setCode(jsonObject.getString("messageval"));
            bean.setMessage(jsonObject.getString("messagestr"));
            System.out.println();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bean;
    }


    /**
     * 我的收藏
     *
     * @param jsonString
     * @return
     */
    public static final ListObjectBean getToMyCollect(String jsonString) {
        ListObjectBean beanList = new ListObjectBean();
        List<CollectionBean> collectionList = new ArrayList<>();
        beanList.setDataList(collectionList);
        JSONObject jsonObject;
        JSONArray jsonArray;
        try {
            jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("Variables");
            jsonArray = jsonObject.getJSONArray("list");

            for (int i = 0; i < jsonArray.length(); i++) {
                CollectionBean bean = new CollectionBean();
                jsonObject = jsonArray.getJSONObject(i);
                bean.setTid(jsonObject.getInt("tid"));
                bean.setFace(jsonObject.getString("face"));
                bean.setAuthor(jsonObject.getString("author"));
                bean.setDateline(jsonObject.getString("dateline"));
                bean.setForum_name(jsonObject.getString("forum_name"));
                String replies = jsonObject.getString("replies");
                if (replies != null)
                    bean.setReplies(DataTools.getInteger(replies));
                bean.setTitle(jsonObject.getString("title"));
                bean.setFavid(jsonObject.getInt("favid"));
                collectionList.add(bean);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return beanList;
    }

    /**
     * 得到收藏帖子的收藏id
     *
     * @param jsonString
     * @return
     */
    public static final int getPostCollectId(String jsonString) {
        int id = 0;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("Variables");
            jsonObject = jsonObject.getJSONObject("list");
            id = jsonObject.getInt("favid");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return id;
    }

    /**
     * 只有message的返回
     */
    public static final BaseBean getToMessage(String jsonString) {
        BaseBean bean = new BaseBean();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("Message");
            bean.setCode(jsonObject.getString("messageval"));
            bean.setMessage(jsonObject.getString("messagestr"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 热词接口
     *
     * @param jsonString
     * @return
     */
    public static final String[] getHotkey(String jsonString) {
        JSONArray jsonArray = getJsonArray(jsonString, "hotword");
        JSONObject jsonObjec = null;
        if (jsonArray != null && jsonArray.length() > 0) {
            String[] hotkey = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    jsonObjec = jsonArray.getJSONObject(i);
                    String word = jsonObjec.getString("keyword");
                    hotkey[i] = word;
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return hotkey;
        }
        return null;
    }

    /**
     * 解析联想词接口
     *
     * @param jsonString
     * @return
     */
    public static final List<SearchHistoryBean> getSearchAssocwordList(
            String jsonString) {
        List<SearchHistoryBean> listBean = new ArrayList<SearchHistoryBean>();
        JSONArray jsonArray = getJsonArray(jsonString, "assocword");
        JSONObject jsonObject = null;
        if (jsonArray != null)
            for (int i = 0; i < jsonArray.length(); i++) {
                SearchHistoryBean bean = new SearchHistoryBean();
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                    bean.setSearchName(jsonObject.getString("tagname"));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    LogFly.e(e.toString());
                }
                listBean.add(bean);
            }
        return listBean;
    }

    /**
     * 解析聊天好友列表
     **/
    public static final MyFrirends getFriends(String jsonString) {
        MyFrirends myFrirends = new MyFrirends();
        myFrirends = gson.fromJson(jsonString, MyFrirends.class);
        return myFrirends;
    }


    /**
     * 根据key 获取值
     *
     * @param jsonString
     * @return
     **/
    public static final String getValueByKey(String jsonString, String key) {
        JSONObject jsonObject = null;
        String value = null;
        try {
            jsonObject = new JSONObject(jsonString);
            if (jsonObject.has("Variables")) {
                jsonObject = jsonObject.getJSONObject("Variables");
            }
            value = jsonObject.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;

    }


    /**
     * 解析搜索结果
     *
     * @param jsonString
     * @return
     */
    public static SearchMemberBean getSearchMember(String jsonString) {
        jsonString = getJsonString(jsonString);

        SearchMemberBean searchMemberBean = gson.fromJson(jsonString,
                SearchMemberBean.class);
        return searchMemberBean;
    }

    /**
     * 解析城市列表
     *
     * @param jsonString
     * @return
     */
    public static List<CitysBean> getCityList(String jsonString) {
        List<CitysBean> listCity = new ArrayList<CitysBean>();
        JSONArray shengList = getJsonArray(jsonString, "district1");
        JSONObject shiJson = getJsonObject(jsonString, "district2");
        for (int i = 1; i <= shengList.length(); i++) {
            CitysBean bean = new CitysBean();
            bean.setCid(i);
            bean.setMark(0);
            try {
                bean.setCity(shengList.getString(i - 1));
                listCity.add(bean);
                JSONArray shiList = shiJson.getJSONArray("" + i);
                for (int j = 0; j < shiList.length(); j++) {
                    CitysBean bean1 = new CitysBean();
                    bean1.setCid(i);
                    bean1.setMark(1);
                    bean1.setCity(shiList.getString(j));
                    listCity.add(bean1);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return listCity;
    }

    /***
     * 解析兴趣
     *
     * @param jsonString
     * @param key
     * @return
     */
    public static List<HobbiesBean> getHobbiesList(String jsonString, String key) {
        List<HobbiesBean> list = new ArrayList<HobbiesBean>();
        JSONArray jsonArray = getJsonArray(jsonString, key);
        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    HobbiesBean bean = new HobbiesBean();
                    bean.setHobbies(jsonArray.getString(i));
                    list.add(bean);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return list;
    }


    public static List<String> getStringList(String jsonString, String key) {
        List<String> list = new ArrayList<String>();
        JSONArray jsonArray = getJsonArray(jsonString, key);
        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    list.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * getJsonObject,只适用这一种接口层次
     *
     * @param jsonString
     * @param key
     * @return
     */
    public static JSONObject getJsonObject(String jsonString, String key) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("Variables");
            jsonObject = jsonObject.getJSONObject(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    /**
     * by young 根据key来解析一级分类的全部json数据字符串
     **/
    public static TypeAll getJsonType(String jsonString) {
        JSONObject jsonObject = null;
        TypeAll typeAll = new TypeAll();
        try {
            jsonObject = new JSONObject(jsonString);
            JSONObject jsonObjectVariables = jsonObject.getJSONObject("Variables");
            JSONObject jsonObjectList = jsonObjectVariables.getJSONObject("list");
            JSONObject jsonObjectCatesAll = jsonObjectList.getJSONObject("cates_all");
            JSONObject jsonObjectMenucategory = jsonObjectList.getJSONObject("menucategory");

            Iterator iterator = jsonObjectMenucategory.keys();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                value = jsonObjectMenucategory.getString(key);
                TypeAll typeItem = new TypeAll();
                typeItem.setCid(0);
                typeItem.setId(Integer.valueOf(key));
                typeItem.setMark(0);
                typeItem.setTypeName(value);
//			   					JSONObject jsonItem=jsonObjectCatesAll.getJSONObject(key);
                if (!key.equals("4") && !key.equals("9")) {
//			   					  jsonString=jsonObjectCatesAll.getJSONObject(key).toString();
                    JSONObject jsonItem = jsonObjectCatesAll.getJSONObject(key);
                    Iterator<String> iteratorItem = jsonItem.keys();
                    String k = null;
                    String v = null;
                    while (iteratorItem.hasNext()) {
                        k = (String) iteratorItem.next();
                        v = jsonItem.getString(k);
                        TypeAll type = new TypeAll();
                        type.setCid(Integer.valueOf(k));
                        type.setId(Integer.valueOf(key));
                        type.setMark(1);
                        type.setTypeName(v);
                        typeItem.getTypes().add(type);
                    }
                }
                typeAll.getTypes().add(typeItem);
            }


            /**地区*/
            List<TypeAll> diquTypeAll = new ArrayList<TypeAll>();
            JSONObject jsonObjectMenudiqu = jsonObjectList.getJSONObject("menudiqu");
            Iterator diquIterator = jsonObjectMenudiqu.keys();
            while (diquIterator.hasNext()) {
                String diquKey = (String) diquIterator.next();
                String diquValue = jsonObjectMenudiqu.getString(diquKey);
                TypeAll diqu = new TypeAll();
                diqu.setId(DataTools.getInteger(diquKey));
                diqu.setTypeName(diquValue);
                diquTypeAll.add(diqu);
            }
            typeAll.setDiquType(diquTypeAll);

            /**商家*/

            List<TypeAll> shangjiaTypeAll = new ArrayList<TypeAll>();
            JSONObject jsonObjectMenushangjia = jsonObjectList.getJSONObject("menushangjia");
            Iterator shangjiaIterator = jsonObjectMenushangjia.keys();
            while (shangjiaIterator.hasNext()) {
                String shangjiaKey = (String) shangjiaIterator.next();
                String shangjiaValue = jsonObjectMenushangjia.getString(shangjiaKey);
                TypeAll shangjia = new TypeAll();
                shangjia.setId(DataTools.getInteger(shangjiaKey));
                shangjia.setTypeName(shangjiaValue);
                shangjiaTypeAll.add(shangjia);
            }
            typeAll.setShangjiaType(shangjiaTypeAll);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return typeAll;
    }


    /**
     * 手机登录，获取验证码
     *
     * @param jsonString
     */
    public static LoginCodeResponse getloginCodeResponse(String jsonString) {
        LoginCodeResponse loginCodeResponse;
        JSONObject object = getJsonObject(jsonString, "list");
        loginCodeResponse = gson.fromJson(object.toString(), LoginCodeResponse.class);
        return loginCodeResponse;
    }

    /**
     * 验证码验证
     *
     * @param jsonString
     * @return
     */
    public static BaseBean getVerify(String jsonString) {
        BaseBean bean = new BaseBean();
        try {
            JSONObject object = new JSONObject(jsonString);
            bean.setCode(object.getString("code"));
            bean.setMessage(object.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }


    /**
     * 直播 点赞  修改状态
     *
     * @param jsonString
     * @return
     */
    public static boolean getStatus(String jsonString) {
        boolean flag = false;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            flag = jsonObject.getBoolean("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return flag;
    }




    public static String getJsonString(String jsonString) {
        String json = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            json = jsonObject.getString("Variables");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return json;
    }


    /**
     * getJsonArray，只适用这一种接口层次
     *
     * @param jsonString
     * @param key
     * @return
     */
    public static JSONArray getJsonArray(String jsonString, String key) {
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        try {
            jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("Variables");
            jsonArray = jsonObject.getJSONArray(key);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonArray;
    }



    public static BaseBean getMessageBean(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonString = jsonObject.getString("Message");
            BaseBean bean = gson.fromJson(jsonString,
                    BaseBean.class);
            return bean;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * gosn 解析
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T getDataBean(String jsonString, Class<T> cls) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            jsonString = jsonObject.getString("Variables");
            T bean = gson.fromJson(jsonString,
                    cls);
            return bean;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将实体POJO转化为JSON
     *
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static <T> String objectToJsonString(T obj) {
        return gson.toJson(obj);
    }


    /**
     * 得到bean
     *
     * @param jsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getBean(String jsonString, Class<T> cls) {
        T bean = gson.fromJson(jsonString,
                cls);
        return bean;
    }





}
