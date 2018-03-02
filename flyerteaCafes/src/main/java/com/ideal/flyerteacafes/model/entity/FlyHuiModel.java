package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.List;

import com.ideal.flyerteacafes.utils.tools.StringTools;

/***
 * 首页列表
 ***/
public class FlyHuiModel extends BaseBean {

    private String clicked_zhide;
    private List<DisList> mythreads;


    public String getClicked_zhide() {
        return clicked_zhide;
    }

    public void setClicked_zhide(String clicked_zhide) {
        this.clicked_zhide = clicked_zhide;
    }

    public List<DisList> getMythreads() {
        return mythreads;
    }

    public void setMythreads(List<DisList> mythreads) {
        this.mythreads = mythreads;
    }

    public static class DisList implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private String id;
        private String diynum;
        private String uid;
        private String author;
        private String title;
        private String cate;
        private String pic;
        private String yuanjia;
        private String xianjia;
        private String shangjia;
        private String url;
        private String info;
        private String begintime;
        private String endtime;
        private String biaoqian;
        private String view;
        private String jifen;
        private String shoucangshu;
        private String dpcount;
        private String zhide;
        private String buzhide;
        private String tuijian;
        private String top;
        private String display;
        private String dateline;
        private String youhuitype;
        private String diqu;
        private String shangjia2;
        private String cate3;
        private String cate_5;
        private String cate_2;
        private String cate_name;
        private String cate_2_name;
        private String shangjianame;

        public String getId() {
            return StringTools.empty(id);
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDiynum() {
            return StringTools.empty(diynum);
        }

        public void setDiynum(String diynum) {
            this.diynum = diynum;
        }

        public String getUid() {
            return StringTools.empty(uid);
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAuthor() {
            return StringTools.empty(author);
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return StringTools.empty(title);
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCate() {
            return StringTools.empty(cate);
        }

        public void setCate(String cate) {
            this.cate = cate;
        }

        public String getPic() {
            return StringTools.empty(pic);
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getYuanjia() {
            return StringTools.empty(yuanjia);
        }

        public void setYuanjia(String yuanjia) {
            this.yuanjia = yuanjia;
        }

        public String getXianjia() {
            return StringTools.empty(xianjia);
        }

        public void setXianjia(String xianjia) {
            this.xianjia = xianjia;
        }

        public String getShangjia() {
            return StringTools.empty(shangjia);
        }

        public void setShangjia(String shangjia) {
            this.shangjia = shangjia;
        }

        public String getUrl() {
            return StringTools.empty(url);
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getInfo() {
            return StringTools.empty(info);
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getBegintime() {
            return StringTools.empty(begintime);
        }

        public void setBegintime(String begintime) {
            this.begintime = begintime;
        }

        public String getEndtime() {
            return StringTools.empty(endtime);
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getBiaoqian() {
            return StringTools.empty(biaoqian);
        }

        public void setBiaoqian(String biaoqian) {
            this.biaoqian = biaoqian;
        }

        public String getView() {
            return StringTools.empty(view);
        }

        public void setView(String view) {
            this.view = view;
        }

        public String getJifen() {
            return StringTools.empty(jifen);
        }

        public void setJifen(String jifen) {
            this.jifen = jifen;
        }

        public String getShoucangshu() {
            return StringTools.empty(shoucangshu);
        }

        public void setShoucangshu(String shoucangshu) {
            this.shoucangshu = shoucangshu;
        }

        public String getDpcount() {
            return StringTools.empty(dpcount);
        }

        public void setDpcount(String dpcount) {
            this.dpcount = dpcount;
        }

        public String getZhide() {
            return StringTools.empty(zhide);
        }

        public void setZhide(String zhide) {
            this.zhide = zhide;
        }

        public String getBuzhide() {
            return StringTools.empty(buzhide);
        }

        public void setBuzhide(String buzhide) {
            this.buzhide = buzhide;
        }

        public String getTuijian() {
            return StringTools.empty(tuijian);
        }

        public void setTuijian(String tuijian) {
            this.tuijian = tuijian;
        }

        public String getTop() {
            return StringTools.empty(top);
        }

        public void setTop(String top) {
            this.top = top;
        }

        public String getDisplay() {
            return StringTools.empty(display);
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getDateline() {
            return StringTools.empty(dateline);
        }

        public void setDateline(String dateline) {
            this.dateline = dateline;
        }

        public String getYouhuitype() {
            return StringTools.empty(youhuitype);
        }

        public void setYouhuitype(String youhuitype) {
            this.youhuitype = youhuitype;
        }

        public String getDiqu() {
            return StringTools.empty(diqu);
        }

        public void setDiqu(String diqu) {
            this.diqu = diqu;
        }

        public String getShangjia2() {
            return StringTools.empty(shangjia2);
        }

        public void setShangjia2(String shangjia2) {
            this.shangjia2 = shangjia2;
        }

        public String getCate3() {
            return StringTools.empty(cate3);
        }

        public void setCate3(String cate3) {
            this.cate3 = cate3;
        }

        public String getCate_5() {
            return StringTools.empty(cate_5);
        }

        public void setCate_5(String cate_5) {
            this.cate_5 = cate_5;
        }

        public String getCate_2() {
            return StringTools.empty(cate_2);
        }

        public void setCate_2(String cate_2) {
            this.cate_2 = cate_2;
        }

        public String getCate_name() {
            return StringTools.empty(cate_name);
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }

        public String getCate_2_name() {
            return StringTools.empty(cate_2_name);
        }

        public void setCate_2_name(String cate_2_name) {
            this.cate_2_name = cate_2_name;
        }

        public String getShangjianame() {
            return StringTools.empty(shangjianame);
        }

        public void setShangjianame(String shangjianame) {
            this.shangjianame = shangjianame;
        }
    }
}
