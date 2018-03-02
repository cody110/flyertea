package com.ideal.flyerteacafes.caff;

import org.xml.sax.XMLReader;

import com.ideal.flyerteacafes.ui.activity.ShowWebImageActivity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;

public class MyTagHandler implements TagHandler {

    private Context context;

    public MyTagHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output,
                          XMLReader xmlReader) {
        // TODO Auto-generated method stub

        if (tag.toLowerCase().equals("img")) {
            int len = output.length();
            ImageSpan[] images = output.getSpans(len - 1, len, ImageSpan.class);
            String imgURL = images[0].getSource();

            output.setSpan(new ImageClick(context, imgURL), len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private class ImageClick extends ClickableSpan {

        private String url;
        private Context context;

        public ImageClick(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        @Override
        public void onClick(View widget) {
            int index = url.indexOf("smiley");
            if (index != -1) {
                return;
            }
            Intent intent = new Intent(context, ShowWebImageActivity.class);
            intent.putExtra("image", url);
            context.startActivity(intent);
        }

    }

}
