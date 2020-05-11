package com.shakespace.template.in_fragment1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
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
                Toast.makeText(taolunqu.this, "get 失败", Toast.LENGTH_SHORT).show();
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


                    if(list.get(i).getType()==2){
                        map.put("title","[通知]"+list.get(i).getTitle() );
                    }else{
                        map.put("title",list.get(i).getTitle() );
                    }



                    map.put("image",R.drawable.listview_item_image);
                    listitem.add(map);
                }

                SimpleAdapter adapter = new SimpleAdapter(taolunqu.this,listitem,R.layout.single_listview_item,new String[]{"title","image"},new int[]{R.id.single_listview_item_title,R.id.single_listview_item_image});

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




}
