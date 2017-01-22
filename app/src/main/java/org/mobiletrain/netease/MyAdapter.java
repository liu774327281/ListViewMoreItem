package org.mobiletrain.netease;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wangsong on 2016/6/18.
 */
public class MyAdapter extends BaseAdapter{
    private final static int ADS = 0;
    private final static int GENERAL = 1;
    private final static int PHOTOSET = 2;
    private List<NewsBean> list;
    private Context context;
    private LayoutInflater inflater;

    public MyAdapter(Context context, List<NewsBean> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount(){
        return list.size();
    }

    //返回ListView中一共有多少个不同类型的item
    @Override
    public int getViewTypeCount(){
        return 3;
    }

    //返回item的类型,参数表示当前要加载的item的位置信息
    @Override
    public int getItemViewType(int position){
        NewsBean newsBean = list.get(position);
        if(newsBean.getAds() != null){
            return ADS;
        }else if(newsBean.getImgextra() != null){
            return PHOTOSET;
        }else{
            return GENERAL;
        }
    }

    @Override
    public Object getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        AdsViewHolder adsViewHolder = null;
        GeneralViewHolder generalViewHolder = null;
        PhotoViewHolder photoViewHolder = null;
        //获取当前要加载的item的类型，是广告栏还是照片集还是普通item
        int itemViewType = getItemViewType(position);
        if(convertView == null){
            switch(itemViewType){
                case ADS:
                    convertView = inflater.inflate(R.layout.item_viewpager, parent, false);
                    adsViewHolder = new AdsViewHolder();
                    adsViewHolder.title = (TextView) convertView.findViewById(R.id.title);
                    adsViewHolder.viewPager = (ViewPager) convertView.findViewById(R.id.viewpager);
                    convertView.setTag(adsViewHolder);
                    break;
                case GENERAL:
                    convertView = inflater.inflate(R.layout.item_general, parent, false);
                    generalViewHolder = new GeneralViewHolder();
                    generalViewHolder.title = (TextView) convertView.findViewById(R.id.title);
                    generalViewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv);
                    convertView.setTag(generalViewHolder);
                    break;
                case PHOTOSET:
                    convertView = inflater.inflate(R.layout.item_photoset, parent, false);
                    photoViewHolder = new PhotoViewHolder();
                    photoViewHolder.iv1 = (ImageView) convertView.findViewById(R.id.iv1);
                    photoViewHolder.iv2 = (ImageView) convertView.findViewById(R.id.iv2);
                    photoViewHolder.iv3 = (ImageView) convertView.findViewById(R.id.iv3);
                    photoViewHolder.title = (TextView) convertView.findViewById(R.id.title);
                    convertView.setTag(photoViewHolder);
                    break;
            }
        }else{
            switch(itemViewType){
                case ADS:
                    adsViewHolder = (AdsViewHolder) convertView.getTag();
                    break;
                case GENERAL:
                    generalViewHolder = (GeneralViewHolder) convertView.getTag();
                    break;
                case PHOTOSET:
                    photoViewHolder = (PhotoViewHolder) convertView.getTag();
                    break;
            }
        }
        final NewsBean newsBean = list.get(position);
        switch(itemViewType){
            case ADS:
                VpAdapter adapter = new VpAdapter(context, newsBean.getAds());
                adsViewHolder.viewPager.setAdapter(adapter);
                adsViewHolder.title.setText(newsBean.getAds().get(0).getTitle());
                final AdsViewHolder finalAdsViewHolder = adsViewHolder;
                adsViewHolder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
                    }

                    @Override
                    public void onPageSelected(int position){
                        finalAdsViewHolder.title.setText(newsBean.getAds().get(position).getTitle());
                    }

                    @Override
                    public void onPageScrollStateChanged(int state){
                    }
                });
                break;
            case GENERAL:
                generalViewHolder.title.setText(newsBean.getTitle());
                Glide.with(context).load(newsBean.getImgsrc()).into(generalViewHolder.imageView);
                break;
            case PHOTOSET:
                //Glide.with(context).load(newsBean.getImgsrc()).into(photoViewHolder.iv1);
                Glide.with(context).load(newsBean.getImgextra().get(0).getImgsrc()).into(photoViewHolder.iv2);
                Glide.with(context).load(newsBean.getImgextra().get(1).getImgsrc()).into(photoViewHolder.iv3);
              //  Glide.with(context).load(newsBean.getImgextra().get(2).getImgsrc()).into(photoViewHolder.iv3);
                photoViewHolder.title.setText(newsBean.getTitle());
                break;
        }
        return convertView;
    }

    class AdsViewHolder{
        ViewPager viewPager;
        TextView title;
    }

    class GeneralViewHolder{
        ImageView imageView;
        TextView title;
    }

    class PhotoViewHolder{
        ImageView iv1, iv2, iv3;
        TextView title;
    }
}
