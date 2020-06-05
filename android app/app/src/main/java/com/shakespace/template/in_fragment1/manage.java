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

public class manage extends AppCompatActivity {
    //定义一些全局变量 定义部件
    private Button manage_pizhun;
    private Button manage_edit;
    private Button manage_notify;
    private Button manage_back;

    private int temp_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_page);       //在activity中使用java代码显示XML布局文件的内容

        //连接对应部件
        manage_pizhun = (Button) findViewById(R.id.manage_pizhun);
        manage_edit = (Button) findViewById(R.id.manage_edit);
        manage_notify = (Button) findViewById(R.id.manage_notify);
        manage_back = (Button) findViewById(R.id.manage_back);


        //
        get_activity_byid(select_which_activity.getid());


        //添加事件监听器
        manage_pizhun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(manage.this, pizhun.class);
                //启动
                startActivity(intent);
            }
        });


        manage_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(manage.this, edit_huodong.class);
                //启动
                startActivity(intent);
            }
        });

        manage_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(manage.this, notify.class);
                //启动
                startActivity(intent);
            }
        });

        manage_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }


    public void get_activity_byid(int id) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //创建Request
                    String url = server_location + ":8080/untoken/activities/"+ id;
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
                        //System.out.println(responseBodyJSONObject.get("name").getAsString());//
                        temp_state = Integer.valueOf(responseBodyJSONObject.get("state").getAsString());
                        //System.out.println(temp_state);//

                        System.out.println("here!");//
                        if(temp_state == 0){

                            manage_pizhun.setVisibility(View.INVISIBLE);
                            manage_notify.setVisibility(View.INVISIBLE);
                        }


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }



}
