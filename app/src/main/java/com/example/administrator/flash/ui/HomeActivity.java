package com.example.administrator.flash.ui;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.flash.Constant;
import com.example.administrator.flash.R;
import com.example.administrator.flash.common.BaseActivity;
import com.example.administrator.flash.core.utils.FileUtils;
import com.example.administrator.flash.core.utils.TextUtils;
import com.example.administrator.flash.core.utils.ToastUtils;
import com.example.administrator.flash.ui.view.MyScrollView;
import com.example.administrator.flash.utils.NavigatorUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, MyScrollView.OnScrollListener {
    private static final String TAG = HomeActivity.class.getSimpleName();
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    TextView tv_name;
    /*topbar相关*/

    @Bind(R.id.btn_send_big)
    Button btn_Send_Big;
    @Bind(R.id.btn_receive_big)
    Button btn_Receive_Big;
    @Bind(R.id.ll_mini_main)
    LinearLayout ll_Mini_Main;
    @Bind(R.id.ll_main)
    LinearLayout ll_Main;
    @Bind(R.id.iv_device)
    ImageView iv_Device;
    @Bind(R.id.tv_device)
    TextView tv_Device;
    @Bind(R.id.rl_device)
    RelativeLayout rl_Device;
    @Bind(R.id.iv_file)
    ImageView iv_File;
    @Bind(R.id.tv_file)
    TextView tv_File;
    @Bind(R.id.rl_file)
    RelativeLayout rl_File;
    @Bind(R.id.iv_storage)
    ImageView iv_Storage;
    @Bind(R.id.tv_storage)
    TextView tv_Storage;
    @Bind(R.id.rl_storage)
    RelativeLayout rl_Storage;
    @Bind(R.id.msv_content)
    MyScrollView msv_Content;
    @Bind(R.id.iv_mini_avator)
    CircleImageView iv_MiniAvator;
    @Bind(R.id.tv_titles)
    TextView tv_Title;
    //    我们要发送和我要接受按钮的Linearlayout的高度
    int mContentHeight = 0;
    boolean mIsExist = false;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
    }

    /*初始化*/
    private void init() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        String devices = TextUtils.isNullOrBlank(Build.DEVICE) ? Constant.DEFAULT_SSID : Build.DEVICE;
        tv_name = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.tv_names);
        tv_name.setText(devices);
        msv_Content.setOnScrollListener(this);
        ll_Mini_Main.setClickable(false);
        ll_Mini_Main.setVisibility(View.GONE);
        updateBottomData();
    }
    /*获取底部数据（文件数，节省的流量）*/
    private void updateBottomData() {
//        TODO 设备的跟新
//       TODO 文件数
        tv_File.setText(String.valueOf(FileUtils.getReceiveFileCount()));
//      TODO  流量数的更新
        tv_Storage.setText(String.valueOf(FileUtils.getFileReceiveTotalLength()));
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout!=null){
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }else {
                if (mIsExist){
                    this.finish();
                }else {
                    ToastUtils.show(getContext(),getContext().getResources().getString(R.string.tip_call_back_agin_and_exist)
                    .replace("{appName}",getContext().getResources().getString(R.string.app_name)));
                    mIsExist=true;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mIsExist=false;
                        }
                    },2*1000);
                }
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.action_settings){
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
   @OnClick({R.id.btn_send,R.id.btn_receive_big,R.id.btn_receive,R.id.btn_send_big,
   R.id.iv_mini_avator,R.id.rl_file,R.id.rl_storage})
    public void onClick(View view){
       switch (view.getId()){
           case R.id.btn_send:
           case R.id.btn_send_big:{
               NavigatorUtils.toChooseFileUI(getContext());
               break;
           }
           case R.id.btn_receive:
           case R.id.btn_receive_big:{
               NavigatorUtils.toReceiveWaitingUI(getContext());
               break;
           }
           case R.id.iv_mini_avator:
           {
               if (mDrawerLayout!=null){
                   mDrawerLayout.openDrawer(GravityCompat.START);
               }
               break;
           }
           case R.id.rl_file:
           case R.id.rl_storage:
               NavigatorUtils.toSystemFileChooser(getContext());
               break;
       }
   }


}
