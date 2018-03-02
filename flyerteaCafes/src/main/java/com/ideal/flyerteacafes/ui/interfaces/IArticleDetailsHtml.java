package com.ideal.flyerteacafes.ui.interfaces;

import java.util.ArrayList;

public interface IArticleDetailsHtml {

    void webviewLoadData(String baseUrl, String data);

    void downLoadImage(String url);

    void webviewLoadComment(String data);

    void toWebviewBottom();

    void clickContentImage(ArrayList<String> urlList, int pos);

}
