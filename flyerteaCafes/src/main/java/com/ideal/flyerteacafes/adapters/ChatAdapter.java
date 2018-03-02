package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.MyTagHandler;
import com.ideal.flyerteacafes.model.entity.ChatBean;
import com.ideal.flyerteacafes.ui.activity.UserDatumActvity;
import com.ideal.flyerteacafes.utils.ContextUtils;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.UploadTask;
import com.ideal.flyerteacafes.utils.xutils.XutilsHelp;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private List<ChatBean> chatList;
    private Context context;
    private LayoutInflater inflater;
    private String touid;

    private ListView chatListview;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            TextView textView = (TextView) chatListview
                    .findViewWithTag(msg.arg1);
            if (textView != null)
                textView.setText((CharSequence) msg.obj);
        }

        ;
    };

    public interface IChatAdapterClick{
        void faceClick(int pos);
    }

    private IChatAdapterClick iChatAdapterClick;

    public void setIChatAdapterClick(IChatAdapterClick iChatAdapterClick){
        this.iChatAdapterClick=iChatAdapterClick;
    }

    public ChatAdapter(Context context, List<ChatBean> chatList, String touid,
                       ListView chatListview) {
        this.chatList = chatList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.touid = touid;
        this.chatListview = chatListview;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return chatList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return chatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_chat_item_layout,
                    null);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ChatBean bean = (ChatBean) getItem(position);
        if (!touid.equals(bean.getTouid())) {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            if (DataUtils.isPictureMode(context))
                DataUtils.downloadPicture(holder.leftFaceImg, UploadTask.getSplit(bean.getAuthorid()), R.drawable.def_face);
            holder.leftBodyText.setTag(position);
            holder.leftBodyText.setAutoLinkMask(Linkify.ALL);
            holder.leftBodyText.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            if (DataUtils.isPictureMode(context))
                DataUtils.downloadPicture(holder.rightFaceImg, UploadTask.getSplit(bean.getAuthorid()), R.drawable.def_face);
            holder.rightBodyText.setTag(position);
            holder.rightBodyText.setAutoLinkMask(Linkify.ALL);
            holder.rightBodyText.setMovementMethod(LinkMovementMethod.getInstance());
        }

        holder.leftFaceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iChatAdapterClick.faceClick(position);
            }
        });

        holder.rightFaceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iChatAdapterClick.faceClick(position);
            }
        });

        final Message msg = Message.obtain();
        new Thread(new Runnable() {

            @Override
            public void run() {
                CharSequence test = Html.fromHtml(bean.getSubject(),
                        ContextUtils.getInstance().imgGetter, new MyTagHandler(context));
                msg.what = 0x101;
                msg.obj = test;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }
        }).start();


        holder.leftFaceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (faceListener != null) faceListener.faceClick(position);
            }
        });


        holder.rightFaceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (faceListener != null) faceListener.faceClick(position);
            }
        });

        return convertView;
    }

    ViewHolder holder = null;

    private class ViewHolder {
        @ViewInject(R.id.listview_item_left_layout)
        View leftLayout;
        @ViewInject(R.id.listview_item_left_layout_face)
        ImageView leftFaceImg;
        @ViewInject(R.id.listview_item_left_layout_body_text)
        TextView leftBodyText;
        @ViewInject(R.id.listview_item_right_layout)
        View rightLayout;
        @ViewInject(R.id.listview_item_right_layout_face)
        ImageView rightFaceImg;
        @ViewInject(R.id.listview_item_right_layout_body_text)
        TextView rightBodyText;
    }


    public interface IFaceListener {
        void faceClick(int pos);
    }

    IFaceListener faceListener;

    public void setFaceClickListener(IFaceListener listener) {
        faceListener = listener;
    }

}
