package com.shakespace.template.in_fragment4;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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

public class edit_info extends AppCompatActivity {
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
    private EditText edit_username;
    private EditText edit_phoneNum;
    private EditText edit_email;
    private EditText edit_firstName;
    private EditText edit_lastName;
    private EditText edit_studentId;
    private RadioGroup edit_genderswitch;
    private Button edit_btn_sure;
    private Button edit_btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_info_page);     //在activity中使用java代码显示XML布局文件的内容

        //连接对应部件
        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_phoneNum = (EditText) findViewById(R.id.edit_phoneNum);
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_firstName = (EditText) findViewById(R.id.edit_firstName);
        edit_lastName = (EditText) findViewById(R.id.edit_lastName);
        edit_studentId = (EditText) findViewById(R.id.edit_studentId);
        edit_genderswitch = (RadioGroup) findViewById(R.id.edit_genderswitch);
        edit_btn_sure=(Button)findViewById(R.id.edit_btn_sure);
        edit_btn_back=(Button)findViewById(R.id.edit_btn_back);

        //添加事件监听器

        edit_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int have_username = 0;
                int have_phoneNum = 0;
                int hava_email = 0;
                int have_firstName = 0;
                int have_lastName = 0;
                int have_studentId = 0;
                int have_gender = 0;

                //确定性别的选择  i=  0:男  1:女  2:保密
                RadioButton r= null;
                int i;
                for(i = 0;i<edit_genderswitch.getChildCount();i++){
                    r = (RadioButton)edit_genderswitch.getChildAt(i);
                    if(r.isChecked()){
                        break;
                    }
                }
                have_gender=i;

                username = edit_username.getText().toString();
                phoneNum = edit_phoneNum.getText().toString();
                email = edit_email.getText().toString();
                firstName = edit_firstName.getText().toString();
                lastName= edit_lastName.getText().toString();
                studentId= edit_studentId.getText().toString();
                //确定选填项是否有值
                if (!TextUtils.isEmpty(username)){
                    have_username=1;
                }
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


                //检查：利用正则表达式进行输入校验:填了的项
                if(have_username == 0  && have_phoneNum == 0 && hava_email == 0 && have_lastName == 0 && have_firstName == 0 && have_studentId == 0 ){
                    edit_username.setError("您还未进行任何修改！");
                }else if(have_username == 1 && !check_username(username)){
                    edit_username.setError("用户名由字母和数字组成，且长度要在4-16位之间");
                }else if(have_phoneNum == 1 && !check_phoneNum(phoneNum)){
                    edit_phoneNum.setError("必须是中国大陆手机号");
                }else if(hava_email == 1 && !check_email(email)){
                    edit_email.setError("必须是合法邮箱");
                }else if(have_lastName == 1 && !check_lastname(lastName)){
                    edit_lastName.setError("必须是 合法姓氏");
                }else if(have_firstName == 1 && !check_firstname(firstName)){
                    edit_firstName.setError("必须是 合法名");
                }else if(have_studentId == 1 && !check_studentid(studentId)) {
                    edit_studentId.setError("必须是合法学号（8位）");
                //此为检查成功后，进行的操作
                }else{
                    put_edit_info(have_username,have_phoneNum,hava_email,have_lastName,have_firstName,have_studentId,have_gender);
                }




            }
        });

        edit_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回功能选择界面
            }
        });

    }


    /****************PUT方式提交修改的信息******************************/
    public void put_edit_info(int have_username,int have_phoneNum,int hava_email,int have_lastName,int have_firstName,int have_studentId,int have_gender){

        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/users/self";

        FormBody.Builder builder = new FormBody.Builder();
        builder .add("type", "0");

        if(have_username==1){
            builder.add("username", username);
        }
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

        SharedPreferences local_user_information = getSharedPreferences("local_user_information", MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String token =local_user_information.getString("token", "#null#");

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer " + token)
                .put(body)
                .build();

        Call call = client.newCall(request);


        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(edit_info.this, "put 失败", Toast.LENGTH_SHORT).show();
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


                if(code.equals("SUCC_USER_EDIT")){
                    message = "修改成功";

                    //创建一个对话框
                    AlertDialog logout_dialog = new AlertDialog.Builder(edit_info.this).create();
                    logout_dialog.setMessage(message);
                    logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //结束掉本activity
                            finish();
                        }
                    });//确定按钮
                    logout_dialog.show();

                }else if(code.equals("ERR_USER_DUP_UNAME")){
                    message = "该用户名已注册";
                    //创建一个对话框
                    AlertDialog logout_dialog = new AlertDialog.Builder(edit_info.this).create();
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
                    AlertDialog logout_dialog = new AlertDialog.Builder(edit_info.this).create();
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
                    AlertDialog logout_dialog = new AlertDialog.Builder(edit_info.this).create();
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