package com.ideal.flyerteacafes.xmlparser;


import com.ideal.flyerteacafes.model.TypeMode;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2016/5/7.
 */
public class TypeModeHandler extends DefaultHandler {

    /**
     * 存储所有的解析对象
     */
    private List<TypeMode> typeModeList = new ArrayList<>();
    //声明一个Beauty类型的变量
    private TypeMode typeMode;

    public List<TypeMode> getDataList() {
        return typeModeList;
    }

    @Override
    public void startDocument() throws SAXException {
        // 当读到第一个开始标签的时候，会触发这个方法
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        // 当遇到开始标记的时候，调用这个方法
       if (qName.equals("typemode")) {
           typeMode = new TypeMode();
            typeMode.setName(attributes.getValue(0));
            typeMode.setType(attributes.getValue(1));
       }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        // 遇到结束标记的时候，会调用这个方法
        if (qName.equals("typemode")) {
            typeModeList.add(typeMode);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
    }

}
