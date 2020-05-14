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

public class fatie  extends AppCompatActivity {
    //定义一些全局变量 定义部件
    private int selected_type;

    private EditText fatie_title;
    private EditText fatie_content;
    private Button fatie_btn_sure;
    private Button fatie_btn_back;
    private Spinner fatie_type_spinner;
    private RatingBar fatie_ratingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fatie_page);       //在activity中使用java代码显示XML布局文件的内容

        //连接对应部件
        fatie_title = (EditText) findViewById(R.id.fatie_title);
        fatie_content = (EditText) findViewById(R.id.fatie_content);
        fatie_btn_sure = (Button) findViewById(R.id.fatie_btn_sure);
        fatie_btn_back = (Button) findViewById(R.id.fatie_btn_back);
        fatie_ratingbar = (RatingBar) findViewById(R.id.fatie_ratingbar);

        fatie_type_spinner = (Spinner) findViewById(R.id.fatie_type_spinner);



        //添加事件监听器
        //根据下拉列表框的选择，改变selected_type的值
        fatie_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //parent就是父控件spinner
            //view就是spinner内填充的textview,id=@android:id/text1
            //position是值所在数组的位置
            //id是值所在行的位置，一般来说与positin一致
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                selected_type = pos;
                //设置星级评分条的可见性  pos=0显示  =1隐藏
                if(pos==0){
                    fatie_ratingbar.setVisibility(View.VISIBLE);
                }else{
                    fatie_ratingbar.setVisibility(View.INVISIBLE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        fatie_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = fatie_title.getText().toString();
                String content = fatie_content.getText().toString();

                //检查：1.必填项非空
                if (TextUtils.isEmpty(title)) {
                    fatie_title.setError("此项不能为空");
                }else if (TextUtils.isEmpty(content)) {
                    fatie_content.setError("此项不能为空");
                }else{
                    //此为检查成功后进行的操作
                    post_AddPost();

                }

            }
        });


        fatie_btn_back.setOnClickListener(new View.OnClickListener() {
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
        builder .add("title", fatie_title.getText().toString())
                .add("content", fatie_content.getText().toString())
                .add("aid", String.valueOf(select_which_activity.getid()))
                .add("type", String.valueOf(selected_type));

        if(selected_type==0){
            builder.add("rate", String.valueOf((int)fatie_ratingbar.getRating()));
        }

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
                Toast.makeText(fatie.this, "post 失败", Toast.LENGTH_SHORT).show();
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
                AlertDialog logout_dialog = new AlertDialog.Builder(fatie.this).create();
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


