package com.shakespace.template.in_fragment1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shakespace.template.R;
import com.shakespace.template.in_fragment4.log_state;
import com.shakespace.template.in_fragment4.register;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.shakespace.template.ui.MainActivity.server_location;

public class activity_detail extends AppCompatActivity {

    //部件定义
    private TextView activity_detail_title;
    private TextView activity_detail_ddl;
    private TextView activity_detail_begin;
    private TextView activity_detail_end;
    private TextView activity_detail_brief;
    private TextView activity_detail_isOpen;
    private TextView activity_detail_limitPeopleNum;
    private TextView activity_detail_place;
    private Button activity_detail_canjia;
    private Button activity_detail_quxiaoshenqing;
    private Button activity_detail_tuichu;
    private Button activity_detail_taolunqu;
    private Button activity_detail_back;
    private ImageButton activity_detail_manage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);       //在activity中使用java代码显示XML布局文件的内容


        //连接对应部件
        activity_detail_title = (TextView) findViewById(R.id.activity_detail_title);
        activity_detail_ddl = (TextView) findViewById(R.id.activity_detail_ddl);
        activity_detail_begin = (TextView) findViewById(R.id.activity_detail_begin);
        activity_detail_place = (TextView) findViewById(R.id.activity_detail_place);
        activity_detail_end = (TextView) findViewById(R.id.activity_detail_end);
        activity_detail_brief = (TextView) findViewById(R.id.activity_detail_brief);
        activity_detail_isOpen = (TextView) findViewById(R.id.activity_detail_isOpen);
        activity_detail_limitPeopleNum = (TextView) findViewById(R.id.activity_detail_limitPeopleNum);

        activity_detail_canjia = (Button) findViewById(R.id.activity_detail_canjia);
        activity_detail_quxiaoshenqing = (Button) findViewById(R.id.activity_detail_quxiaoshenqing);
        activity_detail_tuichu = (Button) findViewById(R.id.activity_detail_tuichu);
        activity_detail_taolunqu = (Button) findViewById(R.id.activity_detail_taolunqu);
        activity_detail_back = (Button) findViewById(R.id.activity_detail_back);
        activity_detail_manage = (ImageButton) findViewById(R.id.activity_detail_manage);



        //动态添加信息 到文本框
        GetActivity(select_which_activity.getid());
        //选择显示出哪一个按钮
        GetJoinInfo(select_which_activity.getid());
        //选择是否显示"管理"按钮
        get_IsManagedByMe(select_which_activity.getid());


        //添加监听器
        activity_detail_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(activity_detail.this, manage.class);
                //启动
                startActivity(intent);
            }
        });

        activity_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        activity_detail_taolunqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(activity_detail.this, taolunqu.class);
                //启动
                startActivity(intent);
            }
        });

        activity_detail_canjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                put_enroll();

            }
        });
        activity_detail_quxiaoshenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                put_unenroll();

            }
        });
        activity_detail_tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                put_unenroll();
            }
        });


        activity_detail_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(activity_detail.this, manage_next.class);
                //启动
                startActivity(intent);
            }
        });


    }


    public void GetActivity(int id){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/untoken/activities/" + id ;
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(activity_detail.this, "get 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();

                String result = response.body().string();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //在这里改变部件值
                        JsonObject responseBodyJSONObject = (JsonObject) new JsonParser().parse(result);




                        if(responseBodyJSONObject.get("title")!=null){
                            String title = responseBodyJSONObject.get("title").getAsString();
                            activity_detail_title.setText(title);
                        }

                        if(responseBodyJSONObject.get("ddl")!=null){
                            String ddl = responseBodyJSONObject.get("ddl").getAsString();
                            activity_detail_ddl.setText(ddl);
                        }

                        if(responseBodyJSONObject.get("begin")!=null){
                            String begin = responseBodyJSONObject.get("begin").getAsString();
                            activity_detail_begin.setText(begin);
                        }

                        if(responseBodyJSONObject.get("end")!=null){
                            String end = responseBodyJSONObject.get("end").getAsString();
                            activity_detail_end.setText(end);
                        }

                        if(responseBodyJSONObject.get("brief")!=null){
                            String brief = responseBodyJSONObject.get("brief").getAsString();
                            activity_detail_brief.setText(brief);
                        }

                        System.out.println(responseBodyJSONObject.get("isOpen"));
                        if(responseBodyJSONObject.get("isOpen")!=null){
                            String isOpen = responseBodyJSONObject.get("isOpen").getAsString();
                            if(isOpen.equals("1")){
                                activity_detail_isOpen.setText("未开放");
                            }else{
                                activity_detail_isOpen.setText("开放");
                            }

                        }
                        if(responseBodyJSONObject.get("limitPeopleNum")!=null){

                            String limitPeopleNum = responseBodyJSONObject.get("limitPeopleNum").getAsString();

                            if(limitPeopleNum.equals("-1")){
                                activity_detail_limitPeopleNum.setText("无限制");
                            }else{
                                activity_detail_limitPeopleNum.setText(limitPeopleNum);
                            }

                        }
                        if(responseBodyJSONObject.get("place")!=null){
                            String place = responseBodyJSONObject.get("place").getAsString();
                            activity_detail_place.setText(place);
                        }



                    }
                });


                Looper.loop();
            }
        });

    }

    public void GetJoinInfo(int id){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/join/info?aid=" + id;
        SharedPreferences local_user_information = getSharedPreferences("local_user_information", MODE_PRIVATE);
        String token =local_user_information.getString("token", "#null#");
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("Authorization","Bearer "+ token)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(activity_detail.this, "get 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();

                String result = response.body().string();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //在这里改变部件值

                        //如果还没登录 三个按钮都不显示
                        if(log_state.getvalue()==0){
                            activity_detail_canjia.setVisibility(View.INVISIBLE);
                            activity_detail_quxiaoshenqing.setVisibility(View.INVISIBLE);
                            activity_detail_tuichu.setVisibility(View.INVISIBLE);
                        }else{

                            if(result.equals("\"null\"")){
                                activity_detail_canjia.setVisibility(View.VISIBLE);
                                activity_detail_quxiaoshenqing.setVisibility(View.INVISIBLE);
                                activity_detail_tuichu.setVisibility(View.INVISIBLE);
                            }else{

                                JsonObject responseBodyJSONObject = (JsonObject) new JsonParser().parse(result);
                                String state = responseBodyJSONObject.get("state").getAsString();

                                if(state.equals("0")){
                                    activity_detail_canjia.setVisibility(View.INVISIBLE);
                                    activity_detail_quxiaoshenqing.setVisibility(View.INVISIBLE);
                                    activity_detail_tuichu.setVisibility(View.VISIBLE);
                                }else if (state.equals("1")){
                                    activity_detail_canjia.setVisibility(View.INVISIBLE);
                                    activity_detail_quxiaoshenqing.setVisibility(View.VISIBLE);
                                    activity_detail_tuichu.setVisibility(View.INVISIBLE);
                                }


                            }



                        }


                    }
                });


                Looper.loop();
            }
        });

    }

    public void get_IsManagedByMe(int id){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/activities/isPostedByMe?aid=" + id;
        SharedPreferences local_user_information = getSharedPreferences("local_user_information", MODE_PRIVATE);
        String token =local_user_information.getString("token", "#null#");
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("Authorization","Bearer "+ token)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(activity_detail.this, "get 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();

                String result = response.body().string();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //在这里改变部件值

                        if(result.equals("false")){
                            activity_detail_manage.setVisibility(View.INVISIBLE);
                        }else{
                            activity_detail_manage.setVisibility(View.VISIBLE);
                        }


                    }
                });


                Looper.loop();
            }
        });

    }

    public void put_enroll(){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/activities/enroll";
        SharedPreferences local_user_information = getSharedPreferences("local_user_information", MODE_PRIVATE);
        String token =local_user_information.getString("token", "#null#");
        FormBody body = new FormBody.Builder()
                .add("aid", ""+select_which_activity.getid())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+ token)
                .put(body)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(activity_detail.this, "put 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                Toast.makeText(activity_detail.this, "put 成功", Toast.LENGTH_SHORT).show();

                String result = response.body().string();
                // 将其解析为JSON对象
                JsonObject responseBodyJSONObject = (JsonObject) new JsonParser().parse(result);
                // 使用JSON对象获取具体值
                String  message = responseBodyJSONObject.get("message").getAsString();


                //创建一个对话框
                AlertDialog logout_dialog = new AlertDialog.Builder(activity_detail.this).create();
                logout_dialog.setMessage(message);
                logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //结束掉本activity
                        //finish();
                        //选择显示出哪一个按钮(再次)
                        GetJoinInfo(select_which_activity.getid());
                    }
                });//确定按钮
                logout_dialog.show();

                Looper.loop();
            }
        });


    }

    public void put_unenroll(){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/activities/unenroll";
        SharedPreferences local_user_information = getSharedPreferences("local_user_information", MODE_PRIVATE);
        String token =local_user_information.getString("token", "#null#");
        FormBody body = new FormBody.Builder()
                .add("aid", ""+select_which_activity.getid())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+ token)
                .put(body)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(activity_detail.this, "put 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                Toast.makeText(activity_detail.this, "put 成功", Toast.LENGTH_SHORT).show();

                String result = response.body().string();
                // 将其解析为JSON对象
                JsonObject responseBodyJSONObject = (JsonObject) new JsonParser().parse(result);
                // 使用JSON对象获取具体值
                String  message = responseBodyJSONObject.get("message").getAsString();


                //创建一个对话框
                AlertDialog logout_dialog = new AlertDialog.Builder(activity_detail.this).create();
                logout_dialog.setMessage(message);
                logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //结束掉本activity
                        //finish();
                        //recreate();
                        //选择显示出哪一个按钮(再次)
                        GetJoinInfo(select_which_activity.getid());
                    }
                });//确定按钮
                logout_dialog.show();

                Looper.loop();
            }
        });


    }


}
