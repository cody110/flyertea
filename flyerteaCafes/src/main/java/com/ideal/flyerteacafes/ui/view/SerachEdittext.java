package com.ideal.flyerteacafes.ui.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;

/**
 * Created by fly on 2016/12/16.
 */

public class SerachEdittext extends LinearLayout {


    private EditText hotel_search_et;
    ImageView hotel_search_clear_img;

    TextWatcher textWatcher;

    public SerachEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditText getEditText() {
        return hotel_search_et;
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_serach_edittext_layout, this);
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setBackgroundResource(R.drawable.light_grey_bg);
//        this.setBackgroundResource(R.drawable.white_corners_bg);
        this.setGravity(Gravity.CENTER);

        hotel_search_et = (EditText) findViewById(R.id.hotel_search_et);
        hotel_search_clear_img = (ImageView) findViewById(R.id.hotel_search_clear_img);

        hotel_search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (textWatcher != null)
                    textWatcher.beforeTextChanged(charSequence, i, i1, i2);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (textWatcher != null)
                    textWatcher.onTextChanged(charSequence, i, i1, i2);
                if (i + i2 > 0) {
                    hotel_search_clear_img.setVisibility(View.VISIBLE);
                } else {
                    hotel_search_clear_img.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (textWatcher != null)
                    textWatcher.afterTextChanged(editable);
            }
        });

        hotel_search_clear_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hotel_search_et.setText("");
            }
        });


    }


    public void addTextChangedListener(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }


    private IClickSearch iClickSearch;

    public void setIClickSearch(IClickSearch iClickSearch) {
        this.iClickSearch = iClickSearch;
    }

    public interface IClickSearch {
        void onClickSearch(String content);
    }

    public interface ISearchClick {
        void onSearchClick(String text);
    }

    /**
     * 搜索监听
     *
     * @param iSearchClick
     */
    public void setISearchClick(final ISearchClick iSearchClick) {
        hotel_search_et.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        hotel_search_et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        hotel_search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    iSearchClick.onSearchClick(hotel_search_et.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
    }


    public void setText(String text) {
        hotel_search_et.setText(text);
        if (!TextUtils.isEmpty(text)) {
            hotel_search_et.setSelection(text.length());
        }
    }

    public void setSelection(int index) {
        hotel_search_et.setSelection(index);
    }

    public void setHint(String hint) {
        hotel_search_et.setHint(hint);
    }

    public String getHint() {
        return hotel_search_et.getHint().toString();
    }

}
