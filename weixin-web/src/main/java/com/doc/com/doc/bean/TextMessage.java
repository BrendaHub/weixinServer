package com.doc.com.doc.bean;

/**
 * 微信的文本对象的信息
 * Created by XYZ on 16/11/23.
 */
public class TextMessage extends  BaseMessage{

    private String Content;
    private String MsgId;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}
