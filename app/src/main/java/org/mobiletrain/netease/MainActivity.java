package org.mobiletrain.netease;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    List<NewsBean> list = (List<NewsBean>) msg.obj;
                    MyAdapter adapter = new MyAdapter(MainActivity.this, list);
                    listView.setAdapter(adapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv);
        initData();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = HttpUtil.getData("http://c.m.163.com/nc/article/headline/T1348647853363/0-20.html");
                List<NewsBean> list = ParseJson.parseJson2NewsBean(data);
                Message message = mHandler.obtainMessage();
                message.obj = list;
                message.what = 0;
                mHandler.sendMessage(message);
            }
        }).start();
    }
}
