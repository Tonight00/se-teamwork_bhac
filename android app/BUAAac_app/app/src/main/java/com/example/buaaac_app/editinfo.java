package com.example.buaaac_app;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class editinfo extends AppCompatActivity {
    //部件定义
    private EditText edit_username;
    private EditText edit_password_old;
    private EditText edit_password_new;
    private EditText edit_password_new2;
    private EditText edit_phoneNum;
    private EditText edit_email;
    private EditText edit_firstName;
    private EditText edit_lastName;
    private EditText edit_studentId;
    private EditText edit_gender;
    private Button edit_btn_sure;
    private Button edit_btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editinfopage);     //在activity中使用java代码显示XML布局文件的内容

        //连接对应部件
        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_password_old = (EditText) findViewById(R.id.edit_password_old);
        edit_password_new = (EditText) findViewById(R.id.edit_password_new);
        edit_password_new2 = (EditText) findViewById(R.id.edit_password_new2);
        edit_phoneNum = (EditText) findViewById(R.id.edit_phoneNum);
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_firstName = (EditText) findViewById(R.id.edit_firstName);
        edit_lastName = (EditText) findViewById(R.id.edit_lastName);
        edit_studentId = (EditText) findViewById(R.id.edit_studentId);
        edit_gender = (EditText) findViewById(R.id.edit_gender);
        edit_btn_sure=(Button)findViewById(R.id.edit_btn_sure);
        edit_btn_back=(Button)findViewById(R.id.edit_btn_back);

        //添加事件监听器

        edit_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回功能选择界面
            }
        });


    }
}