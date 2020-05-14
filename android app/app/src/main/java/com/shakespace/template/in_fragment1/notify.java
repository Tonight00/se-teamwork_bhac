package com.shakespace.template.in_fragment1;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shakespace.template.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.shakespace.template.ui.MainActivity.server_location;

public class notify extends AppCompatActivity {
    //定义一些全局变量 定义部件

    private EditText notify_title;
    private EditText notify_content;
    private Button notify_btn_sure;
    private Button notify_btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_page);       //在activity中使用java代码显示XML布局文件的内容

        //连接对应部件
        notify_title = (EditText) findViewById(R.id.notify_title);
        notify_content = (EditText) findViewById(R.id.notify_content);
        notify_btn_sure = (Button) findViewById(R.id.notify_btn_sure);
        notify_btn_back = (Button) findViewById(R.id.notify_btn_back);


        //添加事件监听器

        notify_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = notify_title.getText().toString();
                String content = notify_content.getText().toString();

                //检查：1.必填项非空
                if (TextUtils.isEmpty(title)) {
                    notify_title.setError("此项不能为空");
                }else if (TextUtils.isEmpty(content)) {
                    notify_content.setError("此项不能为空");
                }else{
                    //此为检查成功后进行的操作
                    post_AddPost();

                }

            }
        });


        notify_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });






    }



    public void post_AddPost(){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/posts";

        FormBody.Builder builder = new FormBody.Builder();
        builder .add("title", notify_title.getText().toString())
                .add("content", notify_content.getText().toString())
                .add("aid", String.valueOf(select_which_activity.getid()))
                .add("type", String.valueOf(2));

        FormBody body = builder.build();

        SharedPreferences local_user_information = getSharedPreferences("local_user_information", MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String token =local_user_information.getString("token", "#null#");

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer " + token)
                .post(body)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(notify.this, "post 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                //Toast.makeText(notify.this, "post 成功", Toast.LENGTH_SHORT).show();

                String result = response.body().string();
                // 将其解析为JSON对象
                JsonObject responseBodyJSONObject = (JsonObject) new JsonParser().parse(result);
                // 使用JSON对象获取具体值
                String message = responseBodyJSONObject.get("message").getAsString();


                //创建一个对话框
                AlertDialog logout_dialog = new AlertDialog.Builder(notify.this).create();
                logout_dialog.setMessage(message);
                logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //结束掉本activity
                        finish();
                    }
                });//确定按钮
                logout_dialog.show();

                Looper.loop();
            }
        });


    }

}


