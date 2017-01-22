package org.mobiletrain.netease;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wangsong on 2016/6/18.
 */
public class HttpUtil {
    public static String getData(String urlStr) {
        HttpURLConnection con = null;
        StringBuffer result = new StringBuffer();
        BufferedReader bufferedReader;
        try {
            URL url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5 * 1000);
            con.setReadTimeout(5 * 1000);
            con.connect();
            if (con.getResponseCode() == 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    result.append(str);
                }
                return result.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
