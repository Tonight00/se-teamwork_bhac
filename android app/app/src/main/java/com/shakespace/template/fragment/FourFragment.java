package com.shakespace.template.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shakespace.template.R;
import com.shakespace.template.base.BaseFragment;
import com.shakespace.template.copy.BhacActivity;
import com.shakespace.template.copy.BhacTag;
import com.shakespace.template.in_fragment1.activity_detail;
import com.shakespace.template.in_fragment4.edit_info;
import com.shakespace.template.in_fragment4.edit_password;
import com.shakespace.template.in_fragment4.log_state;
import com.shakespace.template.in_fragment4.login;
import com.shakespace.template.in_fragment4.register;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.shakespace.template.ui.MainActivity.server_location;

/**
 * A simple {@link Fragment} subclass.
 */
public class FourFragment extends BaseFragment {

    private static final int GET = 1;
    private static final int POST = 2;
    //部件定义
    private TextView welcome;
    private TextView show_username;
    private Button btn_login;
    private Button btn_reg;
    private Button btn_editinfo;
    private Button btn_editpassword;
    private Button btn_logout;
    private Button getpost;

    public FourFragment() {
        // Required empty public constructor
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_four, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //连接对应部件
        welcome = (TextView)getActivity().findViewById(R.id.welcome);
        show_username = (TextView)getActivity().findViewById(R.id.show_username);
        btn_login= (Button)getActivity().findViewById(R.id.login);
        btn_reg= (Button)getActivity().findViewById(R.id.register);
        btn_editinfo= (Button)getActivity().findViewById(R.id.editinfo);
        btn_editpassword= (Button)getActivity().findViewById(R.id.editpassword);
        btn_logout= (Button)getActivity().findViewById(R.id.logout);
        getpost= (Button)getActivity().findViewById(R.id.getpost);

        /**********************自动登录功能 实现****************/
        if(log_state.getvalue()==0){
            SharedPreferences local_user_information = getActivity().getSharedPreferences("local_user_information", MODE_PRIVATE);
            String token =local_user_information.getString("token", "#null#");
            get_autologin_bytoken(token);

        }

        //设置部件可见性
        setVisibility();

        //广播方法实现Fragment页面刷新
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                String msg = intent.getStringExtra("data");
                if("refresh".equals(msg)){
                    //在这里写刷新的具体内容
                    setVisibility();
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);


        //添加事件监听器
        btn_login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //给登录按钮添加点击响应事件
                Intent intent =new Intent(getActivity().getApplicationContext(), login.class);
                //启动
                startActivity(intent);


            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //给注册按钮添加点击响应事件
                Intent intent =new Intent(getActivity().getApplicationContext(), register.class);
                //启动
                startActivity(intent);
            }
        });

        btn_editinfo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent =new Intent(getActivity().getApplicationContext(), edit_info.class);
                startActivity(intent);
            }
        });

        btn_editpassword.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent =new Intent(getActivity().getApplicationContext(), edit_password.class);
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){


                //创建一个对话框
                AlertDialog logout_dialog = new AlertDialog.Builder(getActivity()).create();
                logout_dialog.setMessage("确定退出账户？");
                logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        log_state.setvalue(0);//登出 状态设为0

                        //删除本地信息
                        SharedPreferences local_user_information = getActivity().getSharedPreferences("local_user_information", MODE_PRIVATE);
                        SharedPreferences.Editor editor = local_user_information.edit();//获取编辑器
                        editor.putString("token", "#null#");//
                        editor.putString("username", "#null#");
                        editor.putString("phoneNum", "#null#");
                        editor.putString("email", "#null#");
                        editor.putString("password", "#null#");
                        editor.commit();//提交修改

                        setVisibility();

                        //刷新主页面
                        Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                        intent.putExtra("data","refresh");
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                        getActivity().sendBroadcast(intent);

                    }
                });//确定按钮
                logout_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });//取消按钮
                logout_dialog.show();



            }
        });

        //get post  试验按钮
        getpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //post2();
            }
        });

    }


    @Override
    protected void initListener(View view) {

    }

    //设置部件可见性
    public void setVisibility(){
        if(log_state.getvalue()==1){
            show_username.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.INVISIBLE);
            btn_reg.setVisibility(View.INVISIBLE);
            btn_editinfo.setVisibility(View.VISIBLE);
            btn_editpassword.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.VISIBLE);

            SharedPreferences local_user_information = getActivity().getSharedPreferences("local_user_information", MODE_PRIVATE);
            String temp =local_user_information.getString("username", "#null#");
            String token = local_user_information.getString("token", "#null#");
            show_username.setText(temp);
            get_fill_localusername(token);

        }else{
            show_username.setVisibility(View.INVISIBLE);
            btn_login.setVisibility(View.VISIBLE);
            btn_reg.setVisibility(View.VISIBLE);
            btn_editinfo.setVisibility(View.INVISIBLE);
            btn_editpassword.setVisibility(View.INVISIBLE);
            btn_logout.setVisibility(View.INVISIBLE);
        }
    }

    /*****************测试用***********************************/
    /****************异步GET请求******************************/
    private void get1(){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/users/self";

        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("Authorization","Bearer "+"eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjYsImV4cCI6MTU4OTI4OTk2NH0.OTFxQiGtWsKZS62zRQK4iAdDdnKF9luLMT4xvEANP-g")
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getActivity(), "get 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                Toast.makeText(getActivity(), "get 成功", Toast.LENGTH_SHORT).show();


                String result = response.body().string();
                // 将其解析为JSON对象
                if(!new JsonParser().parse(result).isJsonNull() ){
                    JsonObject responseBodyJSONObject = (JsonObject) new JsonParser().parse(result);
                }

                // 使用JSON对象获取具体值

                //String code = responseBodyJSONObject.get("code").getAsString();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        welcome.setText(result);
                    }
                });

                Looper.loop();
            }
        });

    }
    public void get2(){

    }
    public void get3(){
        OkHttpClient client = new OkHttpClient();

        int aid =1;
        String url = server_location + ":8080/join/info?aid=" + aid;

        SharedPreferences local_user_information = getActivity().getSharedPreferences("local_user_information", MODE_PRIVATE);
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
                Toast.makeText(getActivity(), "get 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();

                String result = response.body().string();


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //在这里改变部件值
                        //JsonObject responseBodyJSONObject = (JsonObject) new JsonParser().parse(result);


                        welcome.setText(result);

                        /*
                        if(responseBodyJSONObject.get("title")!=null){
                            String title = responseBodyJSONObject.get("title").getAsString();
                            activity_detail_title.setText(title);
                        }else{
                            String title = "无";
                            activity_detail_title.setText(title);
                        }

                        if(responseBodyJSONObject.get("ddl")!=null){
                            String ddl = responseBodyJSONObject.get("ddl").getAsString();
                            activity_detail_ddl.setText(ddl);
                        }else{
                            String ddl = "无";
                            activity_detail_ddl.setText(ddl);
                        }

                        if(responseBodyJSONObject.get("begin")!=null){
                            String begin = responseBodyJSONObject.get("begin").getAsString();
                            activity_detail_begin.setText(begin);
                        }else{
                            String begin = "无";
                            activity_detail_begin.setText(begin);
                        }

                        if(responseBodyJSONObject.get("end")!=null){
                            String end = responseBodyJSONObject.get("end").getAsString();
                            activity_detail_end.setText(end);
                        }else{
                            String end = "无";
                            activity_detail_end.setText(end);
                        }

                        if(responseBodyJSONObject.get("brief")!=null){
                            String brief = responseBodyJSONObject.get("brief").getAsString();
                            activity_detail_brief.setText(brief);
                        }else{
                            String brief = "无";
                            activity_detail_brief.setText(brief);
                        }

                        */


                    }
                });


                Looper.loop();
            }
        });

    }
    /****************POST方式提交字符串******************************/
    public void post1(){
        OkHttpClient client = new OkHttpClient();
        String url = "http://localhost:8080/hello_world";
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String str = "I am Jdqm.";
        RequestBody body = RequestBody.create(mediaType, str);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getActivity(), "Post Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Headers headers = response.headers();

                for (int i = 0; i < headers.size(); i++) {

                }

            }
        });
    }
    /****************POST方式提交键值对******************************/
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void post2(){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/activities";
        SharedPreferences local_user_information = getActivity().getSharedPreferences("local_user_information", MODE_PRIVATE);
        String token =local_user_information.getString("token", "#null#");
        LocalDateTime startTime = LocalDateTime.of(2020, 1, 1, 20, 31, 20);

        FormBody body = new FormBody.Builder()
                .add("title", "活动1")
                //.add("brief", "11111111")
                .add("category", "1")
                .add("begin","2020-06-06 06:06:06")
                //.add("phoneNum", "")
                //.add("email", "1")
                //.add("firstName", "")
                //.add("lastName", "")
                //.add("studentId", "")
                //.add("gender", "0")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization","Bearer "+ token)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getActivity(), "post2 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                Toast.makeText(getActivity(), "post2 成功", Toast.LENGTH_SHORT).show();


                String result = response.body().string();
                // 将其解析为JSON对象
                //JsonObject responseBodyJSONObject = (JsonObject) new JsonParser().parse(result);
                // 使用JSON对象获取具体值
                //String code = responseBodyJSONObject.get("code").getAsString();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        welcome.setText(result);
                    }
                });

                Looper.loop();
            }
        });


    }
    /****************POST方式提交键值对******************************/
    public void put(){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/users/self";
        FormBody body = new FormBody.Builder()
                .add("type", "0")
                .add("username", "nick")
                //.add("password", "1234567a")
                //.add("phoneNum", "")
                //.add("email", "1")
                //.add("firstName", "")
                //.add("lastName", "")
                //.add("studentId", "")
                //.add("gender", "0")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjEzLCJleHAiOjE1ODg2NzgwMTl9.lN2hDY2ol8BMf1J8NYELSKsWE-x3U8tV6LD_-T4FXV8")
                .put(body)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getActivity(), "post2 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                Toast.makeText(getActivity(), "post2 成功", Toast.LENGTH_SHORT).show();


                String result = response.body().string();
                // 将其解析为JSON对象
                //JsonObject responseBodyJSONObject = (JsonObject) new JsonParser().parse(result);
                // 使用JSON对象获取具体值
                //String code = responseBodyJSONObject.get("code").getAsString();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        welcome.setText(result);
                    }
                });

                Looper.loop();
            }
        });


    }


    /*********************************************************/

    public void get_autologin_bytoken(String token){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/users/self";

        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("Authorization","Bearer " + token)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getActivity(), "post 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();

                String result = response.body().string();
                //System.out.println(result);//如果失效是：{"code":"ERR_USER_INVALID_TOKEN","message":"登录失效"}


                // 将其解析为JSON对象
                if(!new JsonParser().parse(result).isJsonNull() ){
                    JsonObject responseBodyJSONObject = (JsonObject) new JsonParser().parse(result);
                    // 使用JSON对象获取具体值

                    if(responseBodyJSONObject.get("code")==null){
                        log_state.setvalue(1);//登录进系统 状态设为1
                        //刷新主页面
                        Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                        intent.putExtra("data","refresh");
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                        getActivity().sendBroadcast(intent);
                    }
                }

                Looper.loop();
            }
        });

    }

    public void get_fill_localusername(String token){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/users/self";

        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("Authorization","Bearer " + token)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getActivity(), "get 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();

                String result = response.body().string();
                //System.out.println(result);//如果失效是：{"code":"ERR_USER_INVALID_TOKEN","message":"登录失效"}


                // 将其解析为JSON对象
                    JsonObject responseBodyJSONObject = (JsonObject) new JsonParser().parse(result);
                    // 使用JSON对象获取具体值

                    show_username.setText(responseBodyJSONObject.get("username").getAsString());


                Looper.loop();
            }
        });

    }

}
