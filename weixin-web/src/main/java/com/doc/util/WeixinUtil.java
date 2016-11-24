package com.doc.util;


import com.alibaba.fastjson.JSONObject;
import com.doc.com.doc.bean.AccessToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 微信接口的工具类
 * Created by brenda on 2016/11/25.
 */
public class WeixinUtil {

    private static final String APPID = "wx5025fd6a8c099101";
    private static final String APPSECRET = "65c7eff1a66988fce77f1690d63a76f6";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * get 请求
     * @param utl
     * @return
     */
    public static JSONObject doGetStr(String utl ){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(utl);
        JSONObject jsonObject = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                String result = EntityUtils.toString(entity, "UTF-8");
                System.out.println("result = " + result);

                jsonObject = JSONObject.parseObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Post 请求
     * @param url
     * @param outStr
     * @return
     */
    public static JSONObject doPostStr(String url , String outStr){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        try {
            httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            jsonObject = JSONObject.parseObject(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    //获取微信的access_token;
    public static AccessToken getAccessToken(){
        AccessToken token = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        System.out.println("url = " + url );
        JSONObject jsonObject = doGetStr(url);
        System.out.println("jsonOb= " + jsonObject.toJSONString());
        if(jsonObject != null){
            token.setToken(jsonObject.getString("access_token"));
            token.setExpiresIn(jsonObject.getInteger("expires_in"));
        }
        return token;
    }
}
