package com.shakespace.template.in_fragment1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
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
import com.shakespace.template.copy.BhacJoinuseractivity;
import com.shakespace.template.copy.BhacPost;
import com.shakespace.template.in_fragment4.log_state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.shakespace.template.ui.MainActivity.server_location;

public class pizhun extends AppCompatActivity {
    //定义一些全局变量 定义部件

    private  Button pizhun_back;
    private ListView pizhun_listview;

    private String temp_user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pizhun_page);       //在activity中使用java代码显示XML布局文件的内容

        //连接对应部件
        pizhun_back = (Button) findViewById(R.id.pizhun_back);
        pizhun_listview = (ListView)findViewById(R.id.pizhun_listview);


        /**********************直接展示所有申请（列表）****************/
        get_GetAllApplications();


        //添加事件监听器
        pizhun_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map = (Map<String,Object>)parent.getItemAtPosition(position);
                //Toast.makeText(getActivity(),map.get("id").toString(),Toast.LENGTH_LONG).show();

                int aid = (int)map.get("aid");
                int uid =  (int)map.get("uid");

                put_Accept(aid,uid);


            }
        });


        pizhun_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


    public void get_GetAllApplications(){
        int aid = 0;

        aid = select_which_activity.getid();

        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/untoken/activities/getAllApplications?pageNum=1&limit=2000&aid="+ aid ;
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(pizhun.this, "未连接到互联网 get 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();

                String result = response.body().string();

                List<BhacJoinuseractivity> list = new ArrayList<BhacJoinuseractivity>();

                list = (List<BhacJoinuseractivity>) JSON.parseArray(result,BhacJoinuseractivity.class);
                int size = list.size();

                List<Map<String,Object>> listitem = new ArrayList<Map<String,Object>>();
                for(int i = 0;i<size;i++){
                    Map<String,Object> map = new HashMap<String,Object>();
                    get_user_byid(list.get(i).getUid());//
                    map.put("title","用户:" + temp_user );
                    map.put("aid",list.get(i).getAid());
                    map.put("uid",list.get(i).getUid());
                    map.put("image",R.drawable.listview_item_image);
                    listitem.add(map);
                }

                SimpleAdapter adapter = new SimpleAdapter(pizhun.this,listitem,R.layout.single_listview_item,new String[]{"title"},new int[]{R.id.single_listview_item_title});

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //动态改变listview  fg1_activities_listview

                        pizhun_listview.setAdapter(adapter);
                    }
                });


                Looper.loop();
            }
        });

    }

    public void put_Accept(int aid,int uid){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/activities/accept";

        FormBody.Builder builder = new FormBody.Builder();
        builder .add("aid", String.valueOf(aid))
                .add("uid",  String.valueOf(uid));

        FormBody body = builder.build();

        SharedPreferences local_user_information = getSharedPreferences("local_user_information", MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String token =local_user_information.getString("token", "#null#");

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer " + token)
                .put(body)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(pizhun.this, "未连接到互联网 put 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                //Toast.makeText(fatie.this, "post 成功", Toast.LENGTH_SHORT).show();

                String result = response.body().string();
                // 将其解析为JSON对象
                JsonObject responseBodyJSONObject = (JsonObject) new JsonParser().parse(result);
                // 使用JSON对象获取具体值
                String message = responseBodyJSONObject.get("message").getAsString();


                //创建一个对话框
                AlertDialog logout_dialog = new AlertDialog.Builder(pizhun.this).create();
                logout_dialog.setMessage(message);
                logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //刷新列表
                        get_GetAllApplications();
                    }
                });//确定按钮
                logout_dialog.show();

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
                //System.out.println(responseBodyJSONObject.get("username").getAsString());//
                temp_user = responseBodyJSONObject.get("username").getAsString();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}
