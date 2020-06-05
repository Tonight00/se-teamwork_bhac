package com.shakespace.template.in_fragment1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shakespace.template.R;
import com.shakespace.template.copy.BhacActivity;
import com.shakespace.template.copy.BhacPost;
import com.shakespace.template.in_fragment4.log_state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.shakespace.template.ui.MainActivity.server_location;

public class taolunqu extends AppCompatActivity {
    //定义一些全局变量 定义部件
    private Button taolunqu_addone;
    private Button taolunqu_back;

    private ListView taolunqu_tiezi_listview;


    private String temp_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taolunqu_page);       //在activity中使用java代码显示XML布局文件的内容

        //连接对应部件
        taolunqu_addone = (Button) findViewById(R.id.taolunqu_addone);
        taolunqu_back = (Button) findViewById(R.id.taolunqu_back);

        taolunqu_tiezi_listview = (ListView)findViewById(R.id.taolunqu_tiezi_listview);

        //根据是否登录  决定是否显示“发帖”按钮
        if(log_state.getvalue()==0){
            taolunqu_addone.setVisibility(View.INVISIBLE);
        }else{
            taolunqu_addone.setVisibility(View.VISIBLE);
        }


        /**********************直接展示所有帖子（列表）****************/
        get_GetPostsByAid_inStart();



        //广播方法实现页面刷新
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(taolunqu.this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                String msg = intent.getStringExtra("data");
                if("refresh3".equals(msg)){
                    //在这里写刷新的具体内容
                    get_GetPostsByAid_inStart();
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);

        //添加事件监听器
        taolunqu_addone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(taolunqu.this, fatie.class);
                //启动
                startActivity(intent);
            }
        });


        taolunqu_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        taolunqu_tiezi_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map = (Map<String,Object>)parent.getItemAtPosition(position);
                //Toast.makeText(getActivity(),map.get("id").toString(),Toast.LENGTH_LONG).show();

                select_which_post.setid((int)map.get("id"));

                Intent intent =new Intent(getApplicationContext(), taolunqu_post_detail.class);
                //启动
                startActivity(intent);

            }
        });



    }


    public void get_GetPostsByAid_inStart(){
        int aid = 0;

        aid = select_which_activity.getid();

        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/untoken/posts/getPostsByAid?pageNum=1&limit=1000&aid="+ aid ;
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(taolunqu.this, "未连接到互联网 get 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();

                String result = response.body().string();


                List<BhacPost> list = new ArrayList<BhacPost>();

                list = (List<BhacPost>) JSON.parseArray(result,BhacPost.class);
                int size = list.size();

                List<Map<String,Object>> listitem = new ArrayList<Map<String,Object>>();
                for(int i = 0;i<size;i++){
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("id",list.get(i).getId());


                    get_user_byid(list.get(i).getPostedBy());
                    map.put("title",temp_user+":");

                    map.put("title2",list.get(i).getTitle());

                    if(list.get(i).getType()==2){
                        map.put("title3","[通知] 最后编辑于" + list.get(i).getLastEdited().substring(0,10) +" "+ list.get(i).getLastEdited().substring(11,16));
                    }else if(list.get(i).getType()==1){
                        map.put("title3","[帖子] 最后编辑于" + list.get(i).getLastEdited().substring(0,10) +" "+ list.get(i).getLastEdited().substring(11,16));
                    }else{
                        map.put("title3","[评价] 最后编辑于" + list.get(i).getLastEdited().substring(0,10) +" "+ list.get(i).getLastEdited().substring(11,16));
                    }



                    listitem.add(map);
                }

                SimpleAdapter adapter = new SimpleAdapter(taolunqu.this,listitem,R.layout.single_listview_item3,new String[]{"title","title2","title3"},new int[]{R.id.single_listview_item3_title,R.id.single_listview_item3_title2,R.id.single_listview_item3_title3});

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //动态改变listview  fg1_activities_listview

                        taolunqu_tiezi_listview.setAdapter(adapter);
                    }
                });


                Looper.loop();
            }
        });

    }




    public void get_user_byid(int id) {

        try {
            //创建OkHttpClient对象
            OkHttpClient client = new OkHttpClient();
            //创建Request
            String url = server_location + ":8080/untoken/users/"+ id;
            Request request = new Request.Builder()
                    .url(url)//访问连接
                    .get()
                    .build();
            //创建Call对象
            Call call = client.newCall(request);
            //通过execute()方法获得请求响应的Response对象
            Response response = call.execute();
            if (response.isSuccessful()) {
                //处理网络请求的响应，处理UI需要在UI线程中处理
                //...

                String result = response.body().string();
                JsonObject responseBodyJSONObject = (JsonObject) new JsonParser().parse(result);
                temp_user = responseBodyJSONObject.get("username").getAsString();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
