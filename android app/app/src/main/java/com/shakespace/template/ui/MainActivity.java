package com.shakespace.template.ui;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.shakespace.template.R;
import com.shakespace.template.base.BaseActivity;
import com.shakespace.template.fragment.FourFragment;
import com.shakespace.template.fragment.OneFragment;
import com.shakespace.template.fragment.ThreeFragment;
import com.shakespace.template.fragment.TwoFragment;
import com.shakespace.template.in_fragment1.fabuhuodong;
import com.shakespace.template.in_fragment1.yifabuhuodong;
import com.shakespace.template.in_fragment4.log_state;
import com.shakespace.template.util.BottomNavigationViewHelper;

import butterknife.BindView;
import okhttp3.MediaType;


public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public static final String server_location = "http://114.115.181.247";
    //"http://192.168.0.106"
    //"http://114.115.181.247"

    private static final String TAG = "MainActivity";
    @BindView(R.id.bottom_nav)
    BottomNavigationView mBottomNav;

    @BindView(R.id.container)
    FrameLayout mContainer;

    @BindView(R.id.custom_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

        Fragment mFragment = null;

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    mFragment = new OneFragment();
                    break;
                case 1:
                    mFragment = new TwoFragment();
                    break;
                case 2:
                    mFragment = new ThreeFragment();
                    break;
                case 3:
                    mFragment = new FourFragment();
                    break;
            }
            return mFragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    };
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        // 需要theme 设置成 NoActionBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        // 关联左上角图标和侧滑菜单
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);

        BottomNavigationViewHelper.disableShiftMode(mBottomNav);
        mBottomNav.setOnNavigationItemSelectedListener(this);
        mBottomNav.setSelectedItemId(R.id.nav_one); // 设置默认


        //侧滑栏中的两个按键，使用帮助和关于我们的设置
        NavigationView nv = (NavigationView) findViewById(R.id.slide_menu);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_about:
                        //Toast.makeText(MainActivity.this, "点击了使用帮助", Toast.LENGTH_SHORT).show();
                        //创建一个对话框
                        AlertDialog logout_dialog = new AlertDialog.Builder(MainActivity.this).create();
                        logout_dialog.setMessage("使用帮助:\n        该系统为面向北航全校师生的活动发布、管理和社交平台，目的旨在方便全校的活动组织者和参与者，在活动发布、宣传通知、日程提醒和参与层面，给予一个统一的发布平台，并基于推荐算法为广大师生提供当前北航正在进行的、人气高的或用户感兴趣的活动。\n        " +
                                "目前北航的学术讲座、博雅课堂、社团活动和各个学生会组织的活动并没有一个统一的发布平台和入口，学生、老师及活动组织者需要通过不同的网站进行注册报名，并且这些网站也没有相关的日程提醒功能，也无法让活动参与者对组织者提供反馈，本系统旨在用一个带有网页端的平台，来改变这一现状，方便北航师生的课余生活。\n\n用户能够：\n1.注册登录以及信息编辑。\n" +
                                "2.浏览当前全部开放的活动，或按照关键词搜索相关活动，并申请加入该活动。\n3.以活动发起者的身份发起自己的活动，发起后应可以修改活动相关信息。\n4.基于已经参加的活动，以日程表的形式查看展示即将到来的活动，并设置提醒。\n5.用户也可以对自己参加过的活动进行评价。\n6.点击\"找不到想要的？点这里\" ，系统会根据相应的推荐模型将用户可能感兴趣的热门活动进行推荐。");
                        logout_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });//确定按钮
                        logout_dialog.show();
                        break;
                    case R.id.nav_info:
                        //Toast.makeText(MainActivity.this, "点击了关于我们", Toast.LENGTH_SHORT).show();
                        //创建一个对话框
                        AlertDialog logout_dialog2 = new AlertDialog.Builder(MainActivity.this).create();
                        logout_dialog2.setMessage("BUAA 2020春《软件工程》欧阳元新 王德庆 老师\n" +
                                "小组编号：ARS-AM-2\n小组成员：\n后端：刘宸 曾俊豪 \n前端(android)：陈浩楠 \n前端(web)：王焜");
                        logout_dialog2.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });//确定按钮
                        logout_dialog2.show();
                        break;
                    case R.id.nav_fabuhuodong:

                        if(log_state.getvalue()==0){
                            Toast.makeText(MainActivity.this, "您还未登陆", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent =new Intent(MainActivity.this, fabuhuodong.class);
                            //启动
                            startActivity(intent);
                        }
                        break;
                    case R.id.nav_yifabuhuodong:

                        if(log_state.getvalue()==0){
                            Toast.makeText(MainActivity.this, "您还未登陆", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent =new Intent(MainActivity.this, yifabuhuodong.class);
                            //启动
                            startActivity(intent);
                        }
                        break;





                }
                return false;
            }
        });





    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        int index = 0;
        switch (item.getItemId()) {
            case R.id.nav_one:
                Log.e(TAG, "ONE");
                mToolbar.setTitle("发现活动");
                index = 0;
                break;

            case R.id.nav_two:
                Log.e(TAG, "TWO");
                mToolbar.setTitle("我的参与");
                index = 1;
                break;

            /*case R.id.nav_three:
                Log.e(TAG, "THREE");
                mToolbar.setTitle("消息");
                index = 2;
                break;*/

            case R.id.nav_four:
                Log.e(TAG, "FOUR");
                mToolbar.setTitle("个人信息");
                index = 3;
                break;
        }
        // 判断是否持有过这个fragment 有直接返回，没有则创建
        // 该方法会调用setMenuVisibility 显示和隐藏
        fragment = (Fragment) mAdapter.instantiateItem(mContainer, index);
        // 设置为当前的frament  第二个参数没什么意义？？
        mAdapter.setPrimaryItem(mContainer, 0, fragment);
        // 提交更新
        mAdapter.finishUpdate(mContainer);
        return true;
    }




}
