package com.shakespace.template.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.shakespace.template.base.BaseFragment;
import com.shakespace.template.copy.BhacActivity;
import com.shakespace.template.copy.BhacTag;
import com.shakespace.template.in_fragment1.activity_detail;
import com.shakespace.template.in_fragment1.fabuhuodong;
import com.shakespace.template.in_fragment1.select_which_activity;
import com.shakespace.template.in_fragment4.log_state;
import com.shakespace.template.in_fragment4.login;
import com.shakespace.template.in_fragment4.register;


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

import static android.content.Context.MODE_PRIVATE;
import static com.shakespace.template.ui.MainActivity.server_location;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends BaseFragment {

    //部件定义
    //private TextView thisisone;
    private EditText f1_getactivities_edittext;
    private Spinner f1_tag_spinner;
    private Button fg1_search_button;
    private Button cainixihuan;
    //private Button fg1_fabu;
    private ListView fg1_activities_listview;
    private int selected_tagid;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    protected void initListener(View view) {

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //连接对应部件
        //thisisone = (TextView)getActivity().findViewById(R.id.thisisone);
        f1_getactivities_edittext = (EditText)getActivity().findViewById(R.id.f1_getactivities_edittext);
        f1_tag_spinner = (Spinner)getActivity().findViewById(R.id.f1_tag_spinner);
        fg1_search_button = (Button) getActivity().findViewById(R.id.fg1_search_button);
        cainixihuan = (Button) getActivity().findViewById(R.id.cainixihuan);
        //fg1_fabu = (Button) getActivity().findViewById(R.id.fg1_fabu);
        fg1_activities_listview = (ListView)getActivity().findViewById(R.id.fg1_activities_listview);


        /**********************自动登录功能 实现****************/
        if(log_state.getvalue()==0){
            SharedPreferences local_user_information = getActivity().getSharedPreferences("local_user_information", MODE_PRIVATE);
            String token =local_user_information.getString("token", "#null#");
            get_autologin_bytoken(token);

        }

        /**********************动态添加Tag单选按钮****************/
        get_getTags();

        /**********************直接展示所有活动****************/
        get_getActivities_inStart();


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
                        //thisisone.setText("登录状态:" + "已登录");//
                        //fg1_fabu.setVisibility(View.VISIBLE);
                    }else{
                        //thisisone.setText("登录状态:" + "未登录");//
                        //fg1_fabu.setVisibility(View.INVISIBLE);
                    }

                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);



        //部件具体
        /*fg1_fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity().getApplicationContext(), fabuhuodong.class);
                //启动
                startActivity(intent);

            }
        });*/


        //主要根据下拉列表框的选择，改变selected_tagid的值
        f1_tag_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        cainixihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //得到 推荐列表
                get_favouriteActivities();

            }
        });

        fg1_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Tag的选择  i=  0:全部  1:...  2:...

                //得到 符合 title tagid 的活动列表 ，并动态改变listview
                get_getActivities(f1_getactivities_edittext.getText().toString(),selected_tagid);

            }
        });


        fg1_activities_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map = (Map<String,Object>)parent.getItemAtPosition(position);
                //Toast.makeText(getActivity(),map.get("id").toString(),Toast.LENGTH_LONG).show();

                select_which_activity.setid((int)map.get("id"));

                Intent intent =new Intent(getActivity().getApplicationContext(), activity_detail.class);
                //启动
                startActivity(intent);

            }
        });



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
                Toast.makeText(getActivity(), "get 失败", Toast.LENGTH_SHORT).show();
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
                //最开始补一个tag“全部”
                tagnames.add("全部");
                for(i=0;i<size;i++){
                    tagnames.add(list.get(i).getName());
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,tagnames);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        f1_tag_spinner.setAdapter(adapter);
                    }
                });


                Looper.loop();
            }
        });

    }

    public void get_getActivities(String title,int tid){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/untoken/activities?pageNum=1&limit=1000&title="+ title +"&tid="+ tid;
        Request request = new Request.Builder()
                .get()
                .url(url)
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

                        fg1_activities_listview.setAdapter(adapter);
                    }
                });


                Looper.loop();
            }
        });

    }

    public void get_getActivities_inStart(){
        String title = "";
        int tid = 0;

        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/untoken/activities?pageNum=1&limit=1000&title="+ title +"&tid="+ tid;
        Request request = new Request.Builder()
                .get()
                .url(url)
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

                        fg1_activities_listview.setAdapter(adapter);
                    }
                });


                Looper.loop();
            }
        });

    }

    public void get_favouriteActivities(){
        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/activities/favorite";
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

                List<BhacActivity> list = new ArrayList<BhacActivity>();

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

                        fg1_activities_listview.setAdapter(adapter);
                    }
                });


                Looper.loop();
            }
        });

    }
}
