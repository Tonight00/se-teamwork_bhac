package com.shakespace.template.in_fragment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shakespace.template.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.shakespace.template.ui.MainActivity.server_location;

public class manage_next extends AppCompatActivity {
    //定义一些全局变量 定义部件
    private Button manage_next_pizhun;
    private Button manage_next_edit;
    private Button manage_next_notify;
    private Button manage_next_back;

    private int temp_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_next_page);       //在activity中使用java代码显示XML布局文件的内容

        //连接对应部件

        manage_next_pizhun = (Button)findViewById(R.id.manage_next_pizhun);
        manage_next_edit = (Button)findViewById(R.id.manage_next_edit);
        manage_next_notify = (Button)findViewById(R.id.manage_next_notify);
        manage_next_back = (Button)findViewById(R.id.manage_next_back);

        //
        get_activity_byid(select_which_activity.getid());


        manage_next_pizhun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(manage_next.this, pizhun.class);
                //启动
                startActivity(intent);
            }
        });
        manage_next_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(manage_next.this, edit_huodong.class);
                //启动
                startActivity(intent);
            }
        });
        manage_next_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(manage_next.this, notify.class);
                //启动
                startActivity(intent);
            }
        });








        manage_next_back.setOnClickListener(new View.OnClickListener() {
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
                        if(temp_state == 0){
                            System.out.println("here!");//
                            manage_next_pizhun.setVisibility(View.INVISIBLE);
                            manage_next_notify.setVisibility(View.INVISIBLE);
                        }


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
