package com.ideal.flyerteacafes.model.entity;

import java.util.List;

/**
 * Created by fly on 2018/2/8.
 */

public class HotKeyBean {

    private List<DefaultWord> default_word;

    private List<Hotword> hotword;


    public List<DefaultWord> getDefault_word() {
        return default_word;
    }

    public void setDefault_word(List<DefaultWord> default_word) {
        this.default_word = default_word;
    }

    public List<Hotword> getHotword() {
        return hotword;
    }

    public void setHotword(List<Hotword> hotword) {
        this.hotword = hotword;
    }

    public static class Hotword {
        private String keyword;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
    }

    public static class DefaultWord {
        private String keyword;
        private String type;
        private String url_id;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl_id() {
            return url_id;
        }

        public void setUrl_id(String url_id) {
            this.url_id = url_id;
        }
    }


}
