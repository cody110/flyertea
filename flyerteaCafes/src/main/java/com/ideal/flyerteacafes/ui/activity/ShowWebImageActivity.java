package com.ideal.flyerteacafes.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.controls.ZoomableImageView;
import com.ideal.flyerteacafes.utils.tools.BitmapTools;
import com.umeng.analytics.MobclickAgent;

public class ShowWebImageActivity extends Activity {
    private TextView imageTextView = null;
    private String imagePath = null;
    private ZoomableImageView imageView = null;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Bitmap bit = (Bitmap) msg.obj;
            imageView.setImageBitmap(bit);
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_webimage);
        this.imagePath = getIntent().getStringExtra("image");

        this.imageTextView = (TextView) findViewById(R.id.show_webimage_imagepath_textview);
        imageTextView.setText(this.imagePath);
        imageView = (ZoomableImageView) findViewById(R.id.show_webimage_imageview);

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapTools.loadImageFromUrl(imagePath);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Message msg = new Message();
                msg.obj = bitmap;
                handler.sendMessage(msg);
            }
        }).start();


        findViewById(R.id.show_image_back).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
