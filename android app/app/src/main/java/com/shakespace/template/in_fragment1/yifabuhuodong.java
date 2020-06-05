package com.shakespace.template.in_fragment1;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shakespace.template.R;
import com.shakespace.template.copy.BhacActivity;
import com.shakespace.template.copy.BhacTag;
import com.shakespace.template.datepicker.CustomDatePicker;
import com.shakespace.template.datepicker.DateFormatUtils;

import java.io.IOException;
import java.time.LocalDateTime;
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

public class yifabuhuodong extends AppCompatActivity {
    //部件定义
    private ListView yifabuhuodong_listview;
    private Button yifabuhuodong_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yifabuhuodong_page);       //在activity中使用java代码显示XML布局文件的内容


        //连接对应部件
        yifabuhuodong_back = (Button) findViewById(R.id.yifabuhuodong_back);
        yifabuhuodong_listview = (ListView)findViewById(R.id.yifabuhuodong_listview);


        /**********************直接展示所有已发布活动****************/
        get_GetReleasedActivites();


        yifabuhuodong_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        yifabuhuodong_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map = (Map<String,Object>)parent.getItemAtPosition(position);
                //Toast.makeText(getActivity(),map.get("id").toString(),Toast.LENGTH_LONG).show();

                select_which_activity.setid((int)map.get("id"));

                Intent intent =new Intent(getApplicationContext(), activity_detail.class);
                //启动
                startActivity(intent);

            }
        });



    }



    public void get_GetReleasedActivites(){

        SharedPreferences local_user_information = getSharedPreferences("local_user_information", MODE_PRIVATE);
        String token =local_user_information.getString("token", "#null#");

        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/activities/released?pageNum=1&limit=1000";
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
                Toast.makeText(yifabuhuodong.this, "未连接到互联网(get 失败)", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();

                String result = response.body().string();

                List<BhacActivity> list = new ArrayList<BhacActivity>();

                list = (List<BhacActivity>) JSON.parseArray(result,BhacActivity.class);
                int size = list.size();

                List<Map<String,Object>> listitem = new ArrayList<Map<String,Object>>();
                for(int i = 0;i<size;i++){
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("id",list.get(i).getId());
                    map.put("title",list.get(i).getTitle() );
                    map.put("title2","开始时间:" + list.get(i).getBegin());//
                    map.put("image",R.drawable.listview_item_image);
                    listitem.add(map);
                }

                SimpleAdapter adapter = new SimpleAdapter(yifabuhuodong.this,listitem,R.layout.single_listview_item2,new String[]{"title","image","title2"},new int[]{R.id.single_listview_item2_title,R.id.single_listview_item2_image,R.id.single_listview_item2_title2});

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //动态改变listview  fg1_activities_listview

                        yifabuhuodong_listview.setAdapter(adapter);
                    }
                });


                Looper.loop();
            }
        });

    }





}