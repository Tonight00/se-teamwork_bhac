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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shakespace.template.R;
import com.shakespace.template.copy.BhacTag;
import com.shakespace.template.datepicker.CustomDatePicker;
import com.shakespace.template.datepicker.DateFormatUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.shakespace.template.ui.MainActivity.server_location;

public class fabuhuodong extends AppCompatActivity {

    private String ddl,begin,end;

    private EditText fabu_title;
    private EditText fabu_ddl;
    private EditText fabu_begin;
    private EditText fabu_place;
    private EditText fabu_end;
    private EditText fabu_brief;
    private EditText fabu_limitp;
    private Spinner fabu_tag_spinner;
    private Spinner fabu_isopen_spinner;
    private Button fabu_btn_sure;
    private Button fabu_back;


    private TextView ddl_selected_time;
    private TextView begin_selected_time;
    private TextView end_selected_time;
    private CustomDatePicker mTimerPicker_ddl;
    private CustomDatePicker mTimerPicker_begin;
    private CustomDatePicker mTimerPicker_end;

    private int selected_tagid;
    private int selected_isopen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fabuhuodong_page);       //在activity中使用java代码显示XML布局文件的内容


        //连接对应部件
        fabu_tag_spinner = (Spinner) findViewById(R.id.fabu_tag_spinner);
        fabu_isopen_spinner = (Spinner) findViewById(R.id.fabu_isopen_spinner);
        fabu_back = (Button) findViewById(R.id.fabu_back);
        fabu_btn_sure = (Button) findViewById(R.id.fabu_btn_sure);
        fabu_title = (EditText) findViewById(R.id.fabu_title);
        fabu_place = (EditText) findViewById(R.id.fabu_place);
        fabu_brief = (EditText) findViewById(R.id.fabu_brief);
        fabu_limitp = (EditText) findViewById(R.id.fabu_limitp);

        ddl_selected_time = findViewById(R.id.ddl_selected_time);
        begin_selected_time = findViewById(R.id.begin_selected_time);
        end_selected_time = findViewById(R.id.end_selected_time);


        /**********************动态添加Tag单选按钮****************/
        get_getTags();

        //根据下拉列表框的选择，改变selected_tagid的值
        fabu_tag_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //parent就是父控件spinner
            //view就是spinner内填充的textview,id=@android:id/text1
            //position是值所在数组的位置
            //id是值所在行的位置，一般来说与positin一致
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                selected_tagid = pos;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        //根据下拉列表框的选择，改变selected_isopen的值
        fabu_isopen_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //parent就是父控件spinner
            //view就是spinner内填充的textview,id=@android:id/text1
            //position是值所在数组的位置
            //id是值所在行的位置，一般来说与positin一致
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                selected_isopen = pos;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


        initTimerPicker();
        //添加事件监听器
        //三个时间选择器
        findViewById(R.id.fabu_ddl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 日期格式为yyyy-MM-dd HH:mm
                mTimerPicker_ddl.show(ddl_selected_time.getText().toString());
            }
        });
        findViewById(R.id.fabu_begin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 日期格式为yyyy-MM-dd HH:mm
                mTimerPicker_begin.show(begin_selected_time.getText().toString());
            }
        });
        findViewById(R.id.fabu_end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 日期格式为yyyy-MM-dd HH:mm
                mTimerPicker_end.show(end_selected_time.getText().toString());
            }
        });



        fabu_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //确定Tag的选择  i=  0:全部  1:...  2:...


                fabu_title = (EditText) findViewById(R.id.fabu_title);
                fabu_brief = (EditText) findViewById(R.id.fabu_brief);
                fabu_limitp = (EditText) findViewById(R.id.fabu_limitp);

                String title = fabu_title.getText().toString();
                ddl = ddl_selected_time.getText().toString() + ":00";
                begin = begin_selected_time.getText().toString() + ":00";
                end = end_selected_time.getText().toString() + ":00";
                String brief = fabu_brief.getText().toString();
                String limitp = fabu_limitp.getText().toString();
                String place = fabu_place.getText().toString();


                    //检查：1.必填项非空
                    if (TextUtils.isEmpty(title)) {
                        fabu_title.setError("此项不能为空");
                    }else if (TextUtils.isEmpty(brief)) {
                        fabu_brief.setError("此项不能为空");
                    }else if (TextUtils.isEmpty(limitp)) {
                        fabu_limitp.setError("此项不能为空");
                    }else if(TextUtils.isEmpty(place)){
                        fabu_place.setError("此项不能为空");
                    }else {
                        //此为检查成功后，进行的操作
                        //Toast.makeText(login.this,"检查成功",Toast.LENGTH_SHORT).show();
                        post_addActivity(selected_tagid+1);
                    }


            }
        });




        fabu_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





    }

    public void get_getTags(){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/untoken/tags?pageNum=1&limit=1000";
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(fabuhuodong.this, "get 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                String result = response.body().string();

                List<BhacTag> list = new ArrayList<BhacTag>();
                list = (List<BhacTag>) JSON.parseArray(result,BhacTag.class);
                int size = list.size();

                List<String> tagnames = new ArrayList<String>();
                int i;
                for(i=0;i<size;i++){
                    tagnames.add(list.get(i).getName());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(fabuhuodong.this,android.R.layout.simple_spinner_item,tagnames);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        fabu_tag_spinner.setAdapter(adapter);
                    }
                });


                Looper.loop();
            }
        });

    }

    public void post_addActivity(int tag){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/activities";
        SharedPreferences local_user_information = getSharedPreferences("local_user_information", MODE_PRIVATE);
        String token =local_user_information.getString("token", "#null#");

        ArrayList<Integer> a = new ArrayList<Integer>();
        a.add(1);
        a.add(2);
        a.add(3);

        FormBody body = new FormBody.Builder()
                .add("title", fabu_title.getText().toString())
                .add("ddl", ddl)
                .add("begin", begin)
                .add("end", end)
                .add("brief", fabu_brief.getText().toString())
                .add("limitPeopleNum",fabu_limitp.getText().toString())
                .add("category",""+tag)
                .add("isOpen",String.valueOf(selected_isopen))
                .add("place",fabu_place.getText().toString())
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
                Toast.makeText(fabuhuodong.this, "post2 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                Toast.makeText(fabuhuodong.this, "post2 成功", Toast.LENGTH_SHORT).show();


                String result = response.body().string();
                // 将其解析为JSON对象
                JsonObject responseBodyJSONObject = (JsonObject) new JsonParser().parse(result);
                // 使用JSON对象获取具体值
                String  message = responseBodyJSONObject.get("message").getAsString();


                //创建一个对话框
                AlertDialog logout_dialog = new AlertDialog.Builder(fabuhuodong.this).create();
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



    private void initTimerPicker() {
        String beginTime = "2018-10-17 18:00";
        String endTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);

        ddl_selected_time.setText(endTime);
        begin_selected_time.setText(endTime);
        end_selected_time.setText(endTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker_ddl = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                ddl_selected_time.setText(DateFormatUtils.long2Str(timestamp, true));
            }
        }, beginTime, "2022-10-17 18:00");
        mTimerPicker_begin = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                begin_selected_time.setText(DateFormatUtils.long2Str(timestamp, true));
            }
        }, beginTime, "2022-10-17 18:00");
        mTimerPicker_end = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                end_selected_time.setText(DateFormatUtils.long2Str(timestamp, true));
            }
        }, beginTime, "2022-10-17 18:00");
        // 允许点击屏幕或物理返回键关闭
        mTimerPicker_ddl.setCancelable(true);
        mTimerPicker_begin.setCancelable(true);
        mTimerPicker_end.setCancelable(true);
        // 显示时和分
        mTimerPicker_ddl.setCanShowPreciseTime(true);
        mTimerPicker_begin.setCanShowPreciseTime(true);
        mTimerPicker_end.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPicker_ddl.setScrollLoop(true);
        mTimerPicker_begin.setScrollLoop(true);
        mTimerPicker_end.setScrollLoop(true);
        // 允许滚动动画
        mTimerPicker_ddl.setCanShowAnim(true);
        mTimerPicker_begin.setCanShowAnim(true);
        mTimerPicker_end.setCanShowAnim(true);
    }
}

