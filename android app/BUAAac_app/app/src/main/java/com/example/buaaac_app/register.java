package com.example.buaaac_app;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class register extends AppCompatActivity {
    //部件定义
    private EditText reg_username;
    private EditText reg_password;
    private EditText reg_password2;
    private EditText reg_phoneNum;
    private EditText reg_email;
    private EditText reg_firstName;
    private EditText reg_lastName;
    private EditText reg_studentId;
    private EditText reg_gender;
    private Button reg_btn_sure;
    private Button reg_btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpage);       //在activity中使用java代码显示XML布局文件的内容

        //连接对应部件
        reg_username = (EditText) findViewById(R.id.reg_username);
        reg_password = (EditText) findViewById(R.id.reg_password);
        reg_password2 = (EditText) findViewById(R.id.reg_password2);
        reg_phoneNum = (EditText) findViewById(R.id.reg_phoneNum);
        reg_email = (EditText) findViewById(R.id.reg_email);
        reg_firstName = (EditText) findViewById(R.id.reg_firstName);
        reg_lastName = (EditText) findViewById(R.id.reg_lastName);
        reg_studentId = (EditText) findViewById(R.id.reg_studentId);
        reg_gender = (EditText) findViewById(R.id.reg_gender);
        reg_btn_sure=(Button)findViewById(R.id.reg_btn_sure);
        reg_btn_login=(Button)findViewById(R.id.reg_btn_login);

        //添加事件监听器
        reg_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = reg_username.getText().toString();
                String password = reg_password.getText().toString();
                String password2 = reg_password2.getText().toString();
                //检查：1.非空2.两次输入的密码一致3.利用正则表达式进行输入校验
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
                    Toast.makeText(register.this, "各项均不能为空", Toast.LENGTH_SHORT).show();
                }else if(!TextUtils.equals(password, password2)){
                    Toast.makeText(register.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                }

            }
        });




        reg_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回登录界面
            }
        });


    }
}
