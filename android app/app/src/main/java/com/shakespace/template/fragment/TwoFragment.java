package com.shakespace.template.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.shakespace.template.R;
import com.shakespace.template.base.BaseFragment;
import com.shakespace.template.copy.BhacActivity;
import com.shakespace.template.copy.BhacTag;
import com.shakespace.template.in_fragment4.log_state;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.shakespace.template.ui.MainActivity.server_location;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends BaseFragment {
    //部件定义 及 变量定义
    private TextView thisistwo;
    private CalendarView fg2_calendarview;
    private ListView fg2_activities_listview;
    private Button fg2_refresh;

    public TwoFragment() {
        // Required empty public constructor
    }
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_two, container, false);
    }
    @Override
    protected void initListener(View view) {
    }


    private boolean isCreated=false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated=true;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreated){
            return;
        }else {
            //进行刷新操作 这个貌似有点BUG,当点击别的Fragment时,他也会执行刷新操作
            //在CalendarView上标记出所有的有活动天数
            if(log_state.getvalue()==1){
                get_GetDatesWithAct();
            }
        }
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


        //连接对应部件
        //thisistwo = (TextView)getActivity().findViewById(R.id.thisistwo);
        fg2_calendarview = (CalendarView)getActivity().findViewById(R.id.fg2_calendarview);
        fg2_activities_listview = (ListView)getActivity().findViewById(R.id.fg2_activities_listview);
        //fg2_refresh = (Button)getActivity().findViewById(R.id.fg2_refresh);



        /**********************自动登录功能 实现****************/
        if(log_state.getvalue()==0){
            SharedPreferences local_user_information = getActivity().getSharedPreferences("local_user_information", MODE_PRIVATE);
            String token =local_user_information.getString("token", "#null#");
            get_autologin_bytoken(token);

        }else{
            //thisistwo.setText("登录状态:" + "已登录");//
        }


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
                    if(log_state.getvalue()==1){
                        //thisistwo.setText("登录状态:" + "已登录");//
                        fg2_calendarview.setVisibility(View.VISIBLE);
                        fg2_activities_listview.setVisibility(View.VISIBLE);
                    }else{
                        //thisistwo.setText("登录状态:" + "未登录");//
                        fg2_calendarview.setVisibility(View.INVISIBLE);
                        fg2_activities_listview.setVisibility(View.INVISIBLE);
                    }

                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);






        //在CalendarView上标记出所有的有活动天数
        if(log_state.getvalue()==1){
            get_GetDatesWithAct();
        }



        //事件   在日历上点击每个日子 在下面的列表显示当日所有活动
        fg2_calendarview.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(com.haibin.calendarview.Calendar calendar, boolean isClick) {
                if(log_state.getvalue()==1){

                    Toast.makeText(getActivity(), "你选择了:"+calendar.getYear()+"年"+calendar.getMonth()+"月"+calendar.getDay()+"日", Toast.LENGTH_SHORT).show();
                    get_GetActByDate(calendar.getYear(),calendar.getMonth(),calendar.getDay());

                }else{
                    Toast.makeText(getActivity(), "未登录", Toast.LENGTH_SHORT).show();
                }
            }
        });


        /*fg2_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //在CalendarView上标记出所有的有活动天数
                if(log_state.getvalue()==1){
                    get_GetDatesWithAct();
                }
            }
        });*/




    }


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
                Toast.makeText(getActivity(), "get 失败", Toast.LENGTH_SHORT).show();
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
                //刷新主页面
                Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                intent.putExtra("data","refresh");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                getActivity().sendBroadcast(intent);

                Looper.loop();
            }
        });

    }


    private void get_GetDatesWithAct(){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/activities/getDates";

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
                //Toast.makeText(getActivity(), "get 成功", Toast.LENGTH_SHORT).show();

                String result = response.body().string();
                List<String> list = new ArrayList<String>();
                list = (List<String>) JSON.parseArray(result,String.class);
                int size = list.size();
                int i;

                List<Calendar> list_days = new ArrayList<Calendar>();

                for(i=0;i<size;i++){
                    Calendar c = new Calendar();

                    //012345678910
                    //yyyy-mm-dd hh:mm:ss
                    c.setYear(Integer.parseInt(list.get(i).substring(0,4)));
                    c.setMonth(Integer.parseInt(list.get(i).substring(5,7)));
                    c.setDay(Integer.parseInt(list.get(i).substring(8,10)));

                    list_days.add(c);
                }


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fg2_calendarview.setSchemeDate(list_days);
                    }
                });

                Looper.loop();
            }
        });

    }


    public void get_GetActByDate(int year,int month,int day){
        OkHttpClient client = new OkHttpClient();
        SharedPreferences local_user_information = getActivity().getSharedPreferences("local_user_information", MODE_PRIVATE);
        String token =local_user_information.getString("token", "#null#");


        String url = server_location + ":8080/activities/getActByDate?date=" + ymdtostring(year,month,day);
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

                List<BhacActivity> list = new ArrayList<BhacActivity>();

                if(result.charAt(0)!='{'){
                    list = (List<BhacActivity>) JSON.parseArray(result,BhacActivity.class);
                    int size = list.size();

                    List<Map<String,Object>> listitem = new ArrayList<Map<String,Object>>();
                    for(int i = 0;i<size;i++){
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("id",list.get(i).getId());
                        map.put("title",list.get(i).getTitle() + "\n开始时间:" + list.get(i).getBegin());
                        map.put("image",R.drawable.listview_item_image);
                        listitem.add(map);
                    }

                    SimpleAdapter adapter = new SimpleAdapter(getActivity(),listitem,R.layout.single_listview_item,new String[]{"title","image"},new int[]{R.id.single_listview_item_title,R.id.single_listview_item_image});

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //动态改变listview  fg1_activities_listview

                            fg2_activities_listview.setAdapter(adapter);
                        }
                    });
                }




                Looper.loop();
            }
        });

    }


    public String ymdtostring (int y,int m,int d){
        String mm = String.format("%02d",m);
        String dd = String.format("%02d",d);
        return  ""+y+"-"+mm+"-"+dd+" 01:01:01";
    }





}
