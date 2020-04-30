package com.example.buaaac_app;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    //部件定义
    private EditText username;
    private EditText password;
    private Button btn_login;
    private Button btn_reg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);     //在activity中使用java代码显示XML布局文件的内容

        //连接对应部件
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_reg=(Button)findViewById(R.id.btn_reg);

        //添加事件监听器

        btn_login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //给登录按钮添加点击响应事件
                Intent intent =new Intent(MainActivity.this,function.class);
                //启动
                startActivity(intent);
                finish();
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //给注册按钮添加点击响应事件
                Intent intent =new Intent(MainActivity.this,register.class);
                //启动
                startActivity(intent);
            }
        });
    }
}