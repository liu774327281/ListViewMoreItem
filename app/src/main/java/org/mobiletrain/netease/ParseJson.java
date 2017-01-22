package org.mobiletrain.netease;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsong on 2016/6/18.
 * GsonFormat
 */
public class ParseJson {
    public static List<NewsBean> parseJson2NewsBean(String json) {
        List<NewsBean> list = new ArrayList<>();
        try {
            JSONObject jo = new JSONObject(json);
            JSONArray jsonArr = jo.getJSONArray("T1348647853363");
            for (int i = 0; i < jsonArr.length(); i++) {
                NewsBean newsBean = new NewsBean();
                JSONObject data = jsonArr.getJSONObject(i);
                //有广告数据，再解析
                if (data.has("ads")) {
                    JSONArray adsArr = data.getJSONArray("ads");
                    List<NewsBean.AdsBean> adsBeanList = new ArrayList<>();
                    for (int j = 0; j < adsArr.length(); j++) {
                        JSONObject ads = adsArr.getJSONObject(j);
                        String title = ads.getString("title");
                        String imgsrc = ads.getString("imgsrc");
                        NewsBean.AdsBean adsBean = new NewsBean.AdsBean();
                        adsBean.setTitle(title);
                        adsBean.setImgsrc(imgsrc);
                        adsBeanList.add(adsBean);
                    }
                    newsBean.setAds(adsBeanList);
                }
                if (data.has("imgextra")) {
                    JSONArray imgextraArr = data.getJSONArray("imgextra");
                    List<NewsBean.ImgextraBean> imgextraBeanList = new ArrayList<>();
                    for (int j = 0; j < imgextraArr.length(); j++) {
                        JSONObject imgextra = imgextraArr.getJSONObject(j);
                        String imgsrc = imgextra.getString("imgsrc");
                        NewsBean.ImgextraBean imgextraBean = new NewsBean.ImgextraBean();
                        imgextraBean.setImgsrc(imgsrc);
                        imgextraBeanList.add(imgextraBean);
                    }
                    newsBean.setImgextra(imgextraBeanList);
                }
                String imgsrc = data.getString("imgsrc");
                String title = data.getString("title");
                newsBean.setTitle(title);
                newsBean.setImgsrc(imgsrc);
                list.add(newsBean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
