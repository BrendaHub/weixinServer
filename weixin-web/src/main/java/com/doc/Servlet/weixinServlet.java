package com.doc.Servlet;

import com.doc.com.doc.bean.TextMessage;
import com.doc.util.CheckUtil;
import com.doc.util.MessageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * weixin servlet
 * Created by XYZ on 16/11/23.
 */

@WebServlet(name = "weixinServlet", urlPatterns = "/weixinServlet")
public class weixinServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        doPost(request, response);
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        //获取到了四个参数，后需要添加一个工具类

        PrintWriter out = resp.getWriter();
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = null;
        //设置响应的内容的编码格式
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        try{
            out = resp.getWriter();
            Map<String, String> map =  MessageUtil.xmlToMap(req);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String createTime = map.get("CreateTime");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            String msgId = map.get("MsgId");

            //根据msgType 判断消息类型
            String message = null;
            if("text".equals(msgType)){
                TextMessage text = new TextMessage();
                text.setFromUserName(toUserName);
                text.setToUserName(fromUserName);
                text.setMsgType("text");
                text.setCreateTime(String.valueOf(new Date().getTime()));
                text.setContent("您发送的消息是："+content);
                message = MessageUtil.textToXML(text);
                out.print(message);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            out.close();
        }
    }
}
