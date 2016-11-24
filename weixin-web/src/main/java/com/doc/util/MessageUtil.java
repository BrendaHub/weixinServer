package com.doc.util;

import com.doc.com.doc.bean.NewsMessage;
import com.doc.com.doc.bean.TextMessage;
import com.doc.com.doc.bean.subNews;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.StringBufferConverter;
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
    public static final String MESSAGE_NEWS = "NEWS";

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
    public static String firstMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("本套课程介绍微信公众号开发， 主要涉及公众号介绍，编辑模式介绍，开发模式价绍等。");
        return sb.toString();
    }
    public static String secondMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("慕课网是一家从事互联网在线教学的网络教育企业。秉承“开拓、创新、公平、分享”的精神，将互联网特性全面的应用在教育领域，致力于为教育机构及求学者打造一站在线互动学习的教育品牌。");
        return sb.toString();
    }

    //微信的图文消息对象转换成xml
    public static String newsMessageToXml(NewsMessage newsMessage){
        XStream xstream = new XStream();
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new subNews().getClass());
        return xstream.toXML(newsMessage);
    }
    //初始化图文消息
    public static String initNewsMessage(String toUserName, String fromUserName){
        String message = null;
        List<subNews> newsList = new ArrayList<>();
        NewsMessage newsMessage = new NewsMessage();

        subNews news = new subNews();
        news.setTitle("默克尔介绍");
        news.setDescription("描述内容， 描述内空");
        news.setPicUrl("##");
        news.setUrl("www.doctor330.com");

        newsList.add(news);

        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);

        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.MESSAGE_NEWS);


        return message;

    }
}
