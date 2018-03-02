package com.ideal.flyerteacafes.xmlparser;

import android.content.res.AssetManager;

import com.ideal.flyerteacafes.caff.FlyerApplication;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by fly on 2016/5/7.
 */
public class XmlParserUtils {

    /**
     * 使用SAX解析器解析XML文件的方法
     */
    public static void doMyMission(DefaultHandler msh,String xmlName){
        try {
            //获取AssetManager管理器对象
            AssetManager as = FlyerApplication.getContext().getAssets();
            //通过AssetManager的open方法获取到beauties.xml文件的输入流
            InputStream is = as.open(xmlName);
            //通过获取到的InputStream来得到InputSource实例
            InputSource is2 = new InputSource(is);
            //使用工厂方法初始化SAXParserFactory变量spf
            SAXParserFactory spf = SAXParserFactory.newInstance();
            //通过SAXParserFactory得到SAXParser的实例
            SAXParser sp = spf.newSAXParser();
            //通过SAXParser得到XMLReader的实例
            XMLReader xr = sp.getXMLReader();
            //初始化自定义的类MySaxHandler的变量msh，将beautyList传递给它，以便装载数据
            //将对象msh传递给xr
            xr.setContentHandler(msh);
            //调用xr的parse方法解析输入流
            xr.parse(is2);

            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
