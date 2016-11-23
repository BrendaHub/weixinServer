package com.doc.util;

import com.doc.com.doc.bean.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信处理XML请求参数的工具类，
 * Created by XYZ on 16/11/23.
 */
public class MessageUtil {

    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";

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

    public static String initText(String toUserName, String fromUserName, String content){
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setCreateTime(String.valueOf(new Date().getTime()));
        text.setContent(content);
        return textToXML(text);
    }

    //拼接一个初次关注的子菜单
    public static String menuText(){
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎您的关注， 请按照菜单提示进行操作：\n\n");
        sb.append("1、课程介绍\n");
        sb.append("2、慕课网介绍\n");
        sb.append("回复 ？ 调出此菜单。");
        return sb.toString();
    }
}
