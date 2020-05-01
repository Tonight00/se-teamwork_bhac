package com.example.buaaac_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class function extends AppCompatActivity {
    //部件定义
    private Button btn_logout;
    private Button btn_editinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.functionpage);     //在activity中使用java代码显示XML布局文件的内容

        //连接对应部件
        btn_logout=(Button)findViewById(R.id.btn_logout);
        btn_editinfo=(Button)findViewById(R.id.btn_editinfo);

        //添加事件监听器
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog logout_dialog = new AlertDialog.Builder(function.this).create();//创建一个对话框
                logout_dialog.setMessage("您确定要退出当前账户吗？");
                logout_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(function.this,"已取消",Toast.LENGTH_SHORT).show();
                    }
                });//取消按钮
                logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(function.this,"是",Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(function.this,MainActivity.class);
                        startActivity(intent);
                    }
                });//确定按钮
                logout_dialog.show();
            }
        });

        btn_editinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(function.this,editinfo.class);
                startActivity(intent);
            }
        });

    }
}