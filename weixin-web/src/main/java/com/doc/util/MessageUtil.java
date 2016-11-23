package com.doc.util;

import com.doc.com.doc.bean.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信处理XML请求参数的工具类，
 * Created by XYZ on 16/11/23.
 */
public class MessageUtil {

    //将xml 转换成 map集合对象
    public static Map<String, String> xmlToMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader xmlReader = new SAXReader();

        InputStream ins = null;
        try {
            ins = request.getInputStream();
            //通过SAX来读取从请求中获取到的xml 参数流
            Document doc = xmlReader.read(ins);
            //获取xml的根元素
            Element root = doc.getRootElement();

            List<Element> list = root.elements();

            for(Element e : list){
                map.put(e.getName(), e.getText());
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            if(ins != null){
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    //将map 集合对象转换成 xml
    public static String textToXML(TextMessage textMessage){
        XStream xstream = new XStream();
        //将生成的xml格式里的根元素替换成xml
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }
}
