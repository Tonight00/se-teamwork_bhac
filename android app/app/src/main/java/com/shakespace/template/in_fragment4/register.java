package com.shakespace.template.in_fragment4;

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

public class register extends AppCompatActivity {
    private String code;
    private String message;
    private String token;

    private String username ;
    private String password ;
    private String password2 ;
    private String phoneNum ;
    private String email ;
    private String firstName ;
    private String lastName ;
    private String studentId ;

    //部件定义
    private EditText reg_username;
    private EditText reg_password;
    private EditText reg_password2;
    private CheckBox checkBox1;
    private EditText reg_phoneNum;
    private EditText reg_email;
    private EditText reg_firstName;
    private EditText reg_lastName;
    private EditText reg_studentId;
    private RadioGroup genderswitch;
    private Button reg_btn_sure;
    private Button reg_btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpage);       //在activity中使用java代码显示XML布局文件的内容

        //连接对应部件
        reg_username = (EditText) findViewById(R.id.reg_username);//
        reg_password = (EditText) findViewById(R.id.reg_password);//
        reg_password2 = (EditText) findViewById(R.id.reg_password2);//
        reg_phoneNum = (EditText) findViewById(R.id.reg_phoneNum);
        reg_email = (EditText) findViewById(R.id.reg_email);
        reg_firstName = (EditText) findViewById(R.id.reg_firstName);
        reg_lastName = (EditText) findViewById(R.id.reg_lastName);
        reg_studentId = (EditText) findViewById(R.id.reg_studentId);
        genderswitch = (RadioGroup) findViewById(R.id.genderswitch);
        reg_btn_sure=(Button)findViewById(R.id.reg_btn_sure);
        reg_btn_login=(Button)findViewById(R.id.reg_btn_login);

        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);

        //默认设定密码隐藏
        reg_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        reg_password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        //密码可见性设置
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //如果选中，显示密码
                    reg_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    reg_password2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    reg_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    reg_password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        //添加事件监听器
        reg_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int have_phoneNum = 0;
                int hava_email = 0;
                int have_firstName = 0;
                int have_lastName = 0;
                int have_studentId = 0;
                int have_gender = 0;

                //确定性别的选择  i=  0:男  1:女  2:保密
                RadioButton  r= null;
                int i;
                for(i = 0;i<genderswitch.getChildCount();i++){
                    r = (RadioButton)genderswitch.getChildAt(i);
                    if(r.isChecked()){
                        break;
                    }
                }
                have_gender=i;

                phoneNum = reg_phoneNum.getText().toString();
                email = reg_email.getText().toString();
                firstName = reg_firstName.getText().toString();
                lastName= reg_lastName.getText().toString();
                studentId= reg_studentId.getText().toString();
                //确定选填项是否有值
                if (!TextUtils.isEmpty(phoneNum)){
                    have_phoneNum=1;
                }
                if (!TextUtils.isEmpty(email)){
                    hava_email=1;
                }
                if (!TextUtils.isEmpty(firstName)){
                    have_firstName=1;
                }
                if (!TextUtils.isEmpty(lastName)){
                    have_lastName=1;
                }
                if (!TextUtils.isEmpty(studentId)){
                    have_studentId=1;
                }


                username = reg_username.getText().toString();
                password = reg_password.getText().toString();
                password2 = reg_password2.getText().toString();
                //检查：1.必填项非空
                if (TextUtils.isEmpty(username)) {
                    reg_username.setError("用户名不能为空");
                }else if (TextUtils.isEmpty(password)) {
                    reg_password.setError("密码不能为空");
                }else if (TextUtils.isEmpty(password2)) {
                    reg_password2.setError("密码不能为空");
                //检查：2.两次输入的密码一致
                }else if(!TextUtils.equals(password, password2)){
                    reg_password2.setError("两次输入的密码不一致");
                //检查：3.利用正则表达式进行输入校验:必填的3项
                }else if(!check_username(username)){
                    reg_username.setError("用户名由字母和数字组成，且长度要在4-16位之间");
                }else if(!check_password(password)){
                    reg_password.setError("密码仅有并且要同时含有数字和字母，且长度要在8-16位之间");
                //检查：3.利用正则表达式进行输入校验:选填项中填了的项
                }else if(have_phoneNum == 1 && !check_phoneNum(phoneNum)){
                    reg_phoneNum.setError("必须是中国大陆手机号");
                }else if(hava_email == 1 && !check_email(email)){
                    reg_email.setError("必须是合法邮箱");
                }else if(have_lastName == 1 && !check_lastname(lastName)){
                    reg_lastName.setError("必须是 合法姓氏");
                }else if(have_firstName == 1 && !check_firstname(firstName)){
                    reg_firstName.setError("必须是 合法名");
                }else if(have_studentId == 1 && !check_studentid(studentId)){
                    reg_studentId.setError("必须是合法学号（8位）");
                //此为检查成功后，进行的操作
                }else{
                    //Toast.makeText(register.this,"检查成功",Toast.LENGTH_SHORT).show();
                    post_register(have_phoneNum,hava_email,have_lastName,have_firstName,have_studentId,have_gender);
                }

            }
        });

        reg_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    /****************POST方式提交注册的信息******************************/
    public void post_register(int have_phoneNum,int hava_email,int have_lastName,int have_firstName,int have_studentId,int have_gender){

        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/users";

        FormBody.Builder builder = new FormBody.Builder();
        builder .add("username", username)
                .add("password", password);

        if(have_phoneNum==1){
            builder.add("phoneNum", phoneNum);
        }
        if(hava_email==1){
            builder.add("email", email);
        }
        if(have_lastName==1){
            builder.add("lastName",lastName);
        }
        if(have_firstName==1){
            builder.add("firstName",firstName);
        }
        if(have_studentId==1){
            builder.add("studentId",studentId);
        }
        if(have_gender==0){
            builder.add("gender","1");
        }else if(have_gender==1){
            builder.add("gender","2");
        }else{
            builder.add("gender","0");
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
                Toast.makeText(register.this, "post 失败", Toast.LENGTH_SHORT).show();
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


                if(code.equals("SUCC_USER_REG")){
                    token = responseBodyJSONObject.get("token").getAsString();
                    message = "注册成功!";
                    log_state.setvalue(1);//登录进系统 状态设为1

                    //得到的token，用户名，密码，以SharedPreferences的形式保存在本地，实现自动登录功能
                    SharedPreferences local_user_information = getSharedPreferences("local_user_information", MODE_PRIVATE);
                    SharedPreferences.Editor editor = local_user_information.edit();//获取编辑器
                    //初始化值
                    editor.putString("token", "#null#");
                    editor.putString("username", "#null#");
                    editor.putString("phoneNum", "#null#");
                    editor.putString("email", "#null#");
                    editor.putString("password", "#null#");

                    editor.putString("token", token);
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();//提交修改


                    //创建一个对话框
                    AlertDialog logout_dialog = new AlertDialog.Builder(register.this).create();
                    logout_dialog.setMessage(message);
                    logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //刷新主页面，结束掉本activity
                            Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                            intent.putExtra("data","refresh");
                            LocalBroadcastManager.getInstance(register.this).sendBroadcast(intent);
                            sendBroadcast(intent);
                            finish();
                        }
                    });//确定按钮
                    logout_dialog.show();

                }else if(code.equals("ERR_USER_DUP_UNAME")){
                    message = "该用户名已注册";
                    //创建一个对话框
                    AlertDialog logout_dialog = new AlertDialog.Builder(register.this).create();
                    logout_dialog.setMessage(message);
                    logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });//确定按钮
                    logout_dialog.show();
                }else if(code.equals("ERR_USER_DUP_PN")){
                    message = "该手机号已注册";
                    //创建一个对话框
                    AlertDialog logout_dialog = new AlertDialog.Builder(register.this).create();
                    logout_dialog.setMessage(message);
                    logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });//确定按钮
                    logout_dialog.show();
                }else if(code.equals("ERR_USER_DUP_MAIL")){
                    message = "该邮箱已注册";
                    //创建一个对话框
                    AlertDialog logout_dialog = new AlertDialog.Builder(register.this).create();
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
    // 校验是合法 姓
    private boolean check_lastname(String account) {
        //1~2个汉字
        String pattern = "^[\\u4E00-\\u9FA5]{1,2}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(account);
        return m.matches();
    }
    // 校验是合法 名
    private boolean check_firstname(String account) {
        //1~2个汉字
        String pattern = "^[\\u4E00-\\u9FA5]{1,2}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(account);
        return m.matches();
    }
    // 校验是合法 学号
    private boolean check_studentid(String account) {
        //8位数字 17373352
        String pattern = "^[0-9]{8}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(account);
        return m.matches();
    }




}

