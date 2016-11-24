package com.doc.com.doc.test;

import com.doc.com.doc.bean.AccessToken;
import com.doc.util.WeixinUtil;

/**
 * Created by brenda on 2016/11/25.
 */
public class WeixinTest {

    public static void main(String[] args){
        AccessToken token = WeixinUtil.getAccessToken();
        System.out.println("票据：" + token.getToken());
        System.out.println("有效时间：" + token.getExpiresIn());

    }
}
