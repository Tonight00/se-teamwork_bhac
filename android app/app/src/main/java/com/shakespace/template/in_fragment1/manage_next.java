package com.shakespace.template.in_fragment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.shakespace.template.R;

public class manage_next extends AppCompatActivity {
    //定义一些全局变量 定义部件
    private Button manage_next_pizhun;
    private Button manage_next_edit;
    private Button manage_next_notify;
    private Button manage_next_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_next_page);       //在activity中使用java代码显示XML布局文件的内容

        //连接对应部件

        manage_next_pizhun = (Button)findViewById(R.id.manage_next_pizhun);
        manage_next_edit = (Button)findViewById(R.id.manage_next_edit);
        manage_next_notify = (Button)findViewById(R.id.manage_next_notify);
        manage_next_back = (Button)findViewById(R.id.manage_next_back);

        manage_next_pizhun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(manage_next.this, pizhun.class);
                //启动
                startActivity(intent);
            }
        });
        manage_next_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(manage_next.this, edit_huodong.class);
                //启动
                startActivity(intent);
            }
        });
        manage_next_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(manage_next.this, notify.class);
                //启动
                startActivity(intent);
            }
        });








        manage_next_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

}
