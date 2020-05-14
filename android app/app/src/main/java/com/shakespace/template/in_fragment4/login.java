package com.shakespace.template.in_fragment4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.os.Bundle;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shakespace.template.R;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.shakespace.template.ui.MainActivity.server_location;

public class login extends AppCompatActivity {
    private String code;
    private String message;
    private String token;

    private String username ;
    private String password ;

    //部件定义
    private EditText login_username;
    private EditText login_password;
    private CheckBox checkBox1;

    private RadioGroup loginswitch;

    private Button btn_login;
    private Button btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);       //在activity中使用java代码显示XML布局文件的内容

        //连接对应部件
        login_username = (EditText) findViewById(R.id.login_username);
        login_password = (EditText) findViewById(R.id.login_password);
        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        loginswitch = (RadioGroup) findViewById(R.id.loginswitch);

        btn_login=(Button)findViewById(R.id.btn_login);
        btn_back=(Button)findViewById(R.id.btn_back);

        //默认设定密码隐藏
        login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());


        //添加事件监听器
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //确定登录方式    i =  0:用户名  1:手机号  2:邮箱
                RadioButton  r= null;
                int i;
                for(i = 0;i<loginswitch.getChildCount();i++){//检查以何种方式登陆
                    r = (RadioButton)loginswitch.getChildAt(i);
                    if(r.isChecked()){
                        break;
                    }
                }

                username = login_username.getText().toString();
                password = login_password.getText().toString();


                //检查：1.必填项非空
                if (TextUtils.isEmpty(username)) {
                    login_username.setError("此项不能为空");
                }else if (TextUtils.isEmpty(password)) {
                    login_password.setError("密码不能为空");
                //检查：2.利用正则表达式进行输入校验
                }else if(i==0&&!check_username(username)){
                    login_username.setError("用户名不合法");
                }else if(i==1&&!check_phoneNum(username)){
                    login_username.setError("必须是中国大陆手机号");
                    //检查：3.利用正则表达式进行输入校验:选填项中填了的项
                }else if(i==2&&!check_email(username)){
                    login_username.setError("必须是合法邮箱");
                    //此为检查成功后，进行的操作
                }else{
                    //Toast.makeText(login.this,"检查成功",Toast.LENGTH_SHORT).show();
                    post_login(i);
                }


            }
        });




        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //如果选中，显示密码
                    login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回个人信息界面
            }
        });


    }


    /****************POST方式提交登录的信息******************************/
    public void post_login(int i){

        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/users/login";

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("password", password);

        if(i==0){
            builder.add("username", username);
        }
        if(i==1){
            builder.add("phoneNum", username);
        }
        if(i==2){
            builder.add("email", username);
        }

        FormBody body = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(login.this, "post 失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();


                String result = response.body().string();
                // 将其解析为JSON对象
                JsonObject responseBodyJSONObject = (JsonObject) new JsonParser().parse(result);
                // 使用JSON对象获取具体值
                code = responseBodyJSONObject.get("code").getAsString();

                if(code.equals("SUCC_USER_LOGIN")){
                    token = responseBodyJSONObject.get("token").getAsString();
                    message = "登录成功";
                    log_state.setvalue(1);//登录进系统 状态设为1

                    //得到的token，用户名，密码，以SharedPreferences的形式保存在本地，实现自动登录功能
                    SharedPreferences local_user_information = getSharedPreferences("local_user_information", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = local_user_information.edit();//获取编辑器
                    //初始化值
                    editor.putString("token", "#null#");
                    editor.putString("username", "#null#");
                    editor.putString("phoneNum", "#null#");
                    editor.putString("email", "#null#");
                    editor.putString("password", "#null#");

                    editor.putString("token", token);
                    if(i==0){
                        editor.putString("username", username);
                    }
                    if(i==1){
                        editor.putString("phoneNum", username);
                    }
                    if(i==2){
                        editor.putString("email", username);
                    }

                    editor.putString("password", password);
                    editor.commit();//提交修改


                    //创建一个对话框
                    AlertDialog logout_dialog = new AlertDialog.Builder(login.this).create();
                    logout_dialog.setMessage(message);
                    logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //刷新主页面，结束掉本activity
                            Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                            intent.putExtra("data","refresh");
                            LocalBroadcastManager.getInstance(login.this).sendBroadcast(intent);
                            sendBroadcast(intent);
                            finish();
                        }
                    });//确定按钮
                    logout_dialog.show();

                }else if(code.equals("ERR_USER_NO_UNAME")){
                    message = "该用户名不存在";
                    //创建一个对话框
                    AlertDialog logout_dialog = new AlertDialog.Builder(login.this).create();
                    logout_dialog.setMessage(message);
                    logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });//确定按钮
                    logout_dialog.show();
                }else if(code.equals("ERR_USER_NO_PN")){
                    message = "该手机号不存在";
                    //创建一个对话框
                    AlertDialog logout_dialog = new AlertDialog.Builder(login.this).create();
                    logout_dialog.setMessage(message);
                    logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });//确定按钮
                    logout_dialog.show();
                }else if(code.equals("ERR_USER_NO_MAIL")){
                    message = "该邮箱不存在";
                    //创建一个对话框
                    AlertDialog logout_dialog = new AlertDialog.Builder(login.this).create();
                    logout_dialog.setMessage(message);
                    logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });//确定按钮
                    logout_dialog.show();
                }else if(code.equals("ERR_USER_VERI_FAILED")){
                    message = "密码错误";
                    //创建一个对话框
                    AlertDialog logout_dialog = new AlertDialog.Builder(login.this).create();
                    logout_dialog.setMessage(message);
                    logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });//确定按钮
                    logout_dialog.show();
                }


                Looper.loop();


            }
        });


    }


    /**********************正则表达式校验函数******************/
    // 校验必须是合法用户名
    private boolean check_username(String account) {

        // 用户名仅有字母和数字组成，且长度要在4-16位之间
        String pattern = "^[A-Za-z0-9]{4,16}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(account);
        return m.matches();

    }
    // 校验密码
    private boolean check_password(String password) {
        // 仅有并且要同时含有数字和字母，且长度要在8-16位之间
        String pattern = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(password);
        return m.matches();
    }
    // 校验是中国大陆手机号
    private boolean check_phoneNum(String account) {

        // 首位为1, 第二位为3-9, 剩下九位为 0-9, 共11位数字
        String pattern = "^[1]([3-9])[0-9]{9}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(account);
        return m.matches();
    }
    // 校验是合法邮箱
    private boolean check_email(String account) {
        String pattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(account);
        return m.matches();
    }
}

