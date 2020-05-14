package com.shakespace.template.in_fragment4;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Looper;
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
import android.os.Bundle;
import android.widget.RadioButton;
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

public class edit_password extends AppCompatActivity {
    private String code;
    private String message;
    private String token;
    private String password_old ;
    private String password_new ;
    private String password_new2 ;
    //部件定义
    private EditText edit_password_old;
    private EditText edit_password_new;
    private EditText edit_password_new2;
    private CheckBox edit_checkBox1;
    private Button edit_btn_sure;
    private Button edit_btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_password_page);     //在activity中使用java代码显示XML布局文件的内容

        //连接对应部件
        edit_password_old = (EditText) findViewById(R.id.edit_password_old);
        edit_password_new = (EditText) findViewById(R.id.edit_password_new);
        edit_password_new2 = (EditText) findViewById(R.id.edit_password_new2);
        edit_checkBox1 = (CheckBox) findViewById(R.id.edit_checkBox1);
        edit_btn_sure=(Button)findViewById(R.id.edit_btn_sure);
        edit_btn_back=(Button)findViewById(R.id.edit_btn_back);

        //默认设定密码隐藏
        edit_password_old.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edit_password_new.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edit_password_new2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        //密码可见性设置
        edit_checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //如果选中，显示密码
                    edit_password_old.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edit_password_new.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edit_password_new2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    edit_password_old.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edit_password_new.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edit_password_new2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        //添加事件监听器

        edit_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password_old = edit_password_old.getText().toString();
                password_new = edit_password_new.getText().toString();
                password_new2 = edit_password_new2.getText().toString();

                //检查：1.必填项非空
                if (TextUtils.isEmpty(password_old)) {
                    edit_password_old.setError("不能为空");
                }else if (TextUtils.isEmpty(password_new)) {
                    edit_password_new.setError("不能为空");
                }else if (TextUtils.isEmpty(password_new2)) {
                    edit_password_new2.setError("不能为空");
                //检查：2.两次输入的密码一致
                }else if(!TextUtils.equals(password_new, password_new2)){
                    edit_password_new2.setError("两次输入的密码不一致");
                //检查：3.利用正则表达式进行输入校验
                }else if(!check_password(password_old)){
                    edit_password_old.setError("密码仅有并且要同时含有数字和字母，且长度要在8-16位之间");
                }  else if(!check_password(password_new)){
                    edit_password_new.setError("密码仅有并且要同时含有数字和字母，且长度要在8-16位之间");
                }else if(!check_password(password_new2)){
                    edit_password_new2.setError("密码仅有并且要同时含有数字和字母，且长度要在8-16位之间");
                //此为检查成功后，进行的操作
                }else{
                    put_edit_password();
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
    public void put_edit_password(){

        OkHttpClient client = new OkHttpClient();
        String url = server_location + ":8080/users/self";

        FormBody.Builder builder = new FormBody.Builder();
        builder .add("type", "1");

        builder.add("oldPassword",password_old);
        builder.add("newPassword",password_new);

        FormBody body = builder.build();

        SharedPreferences local_user_information = getSharedPreferences("local_user_information", MODE_PRIVATE);
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
                Toast.makeText(edit_password.this, "put 失败", Toast.LENGTH_SHORT).show();
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
                    AlertDialog logout_dialog = new AlertDialog.Builder(edit_password.this).create();
                    logout_dialog.setMessage(message);
                    logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //结束掉本activity
                            finish();
                        }
                    });//确定按钮
                    logout_dialog.show();

                }else if(code.equals("ERR_USER_VERI_FAILED")){
                    message = "原密码输入错误";
                    //创建一个对话框
                    AlertDialog logout_dialog = new AlertDialog.Builder(edit_password.this).create();
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
    // 校验密码
    private boolean check_password(String password) {
        // 仅有并且要同时含有数字和字母，且长度要在8-16位之间
        String pattern = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(password);
        return m.matches();
    }

}