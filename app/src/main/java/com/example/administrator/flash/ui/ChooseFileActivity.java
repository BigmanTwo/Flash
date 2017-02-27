package com.example.administrator.flash.ui;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.flash.AppContext;
import com.example.administrator.flash.Constant;
import com.example.administrator.flash.R;
import com.example.administrator.flash.common.BaseActivity;
import com.example.administrator.flash.core.entity.FileInfo;
import com.example.administrator.flash.core.receiver.SelectedFileListChangedBroadcastReceiver;
import com.example.administrator.flash.ui.adapter.ResPageAdapter;
import com.example.administrator.flash.ui.fragments.FileInfoFragments;
import com.example.administrator.flash.ui.view.ShowSelectedFileInfoDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChooseFileActivity extends BaseActivity {
    private static final String TAG=ChooseFileActivity.class.getSimpleName();
    /*top bar相关*/
    @Bind(R.id.tv_back)
    TextView tv_Back;
    @Bind(R.id.iv_search)
    ImageView iv_Search;
    @Bind(R.id.search_view)
    SearchView search_View;

    @Bind(R.id.tv_titlea)
    TextView tv_Title;
    /*bottom bar相关*/
    @Bind(R.id.btn_selected)
    Button btn_Selected;
    @Bind(R.id.btn_next)
    Button btn_Next;
    @Bind(R.id.view_pager)
    ViewPager view_Pager;
    @Bind(R.id.tab_layout)
    TabLayout tab_Layout;
    //
    FileInfoFragments mCurrentFragment;
    FileInfoFragments mApkInfoFragment;
    FileInfoFragments mJpgInfoFragment;
    FileInfoFragments mMp3InfoFragment;
    FileInfoFragments mMp4InfoFragment;
    List<FileInfoFragments> listFragments;
    private boolean isWebTransfer=false;

    ShowSelectedFileInfoDialog mShowSelectedFileInfoDialog;
    SelectedFileListChangedBroadcastReceiver  mSelectedFileListChangedBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_file);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        tv_Title.setText(getResources().getString(R.string.title_choose_file));
        tv_Title.setVisibility(View.VISIBLE);
        iv_Search.setVisibility(View.INVISIBLE);
        search_View.setVisibility(View.GONE);
        isWebTransfer=getIntent().getBooleanExtra(Constant.KEY_WEB_TRANSFER_FLAG,false);
        listFragments=new ArrayList<>();
        mApkInfoFragment=FileInfoFragments.newInstance(FileInfo.TYPE_APK);
        mJpgInfoFragment=FileInfoFragments.newInstance(FileInfo.TYPE_JPG);
        mMp3InfoFragment=FileInfoFragments.newInstance(FileInfo.TYPE_MP3);
        mMp4InfoFragment=FileInfoFragments.newInstance(FileInfo.TYPE_MP4);
        mCurrentFragment=mApkInfoFragment;
        listFragments.add(mApkInfoFragment);
        listFragments.add(mJpgInfoFragment);
        listFragments.add(mMp3InfoFragment);
        listFragments.add(mMp4InfoFragment);
        Log.i(TAG,listFragments.size()+"");
        String[] titles=getResources().getStringArray(R.array.array_res);
        Log.i(TAG,titles[0]);
        view_Pager.setAdapter(new ResPageAdapter(getSupportFragmentManager(),titles,listFragments));
        view_Pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        view_Pager.setOffscreenPageLimit(4);
        tab_Layout.setTabMode(TabLayout.MODE_FIXED);
        tab_Layout.setupWithViewPager(view_Pager);
        setSelectedViewStyle(false);
        mShowSelectedFileInfoDialog=new ShowSelectedFileInfoDialog(getContext());
        mSelectedFileListChangedBroadcastReceiver=new SelectedFileListChangedBroadcastReceiver() {
            @Override
            public void onSelectedFileChanged() {
                updata();
            }
        };
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(SelectedFileListChangedBroadcastReceiver.ACTION_CHOOSE_FILE_CHANGED);
        registerReceiver(mSelectedFileListChangedBroadcastReceiver,intentFilter);
    }
    private void updata(){
        if(mApkInfoFragment != null) mApkInfoFragment.updateFileInfoAdapter();
        if(mJpgInfoFragment != null) mJpgInfoFragment.updateFileInfoAdapter();
        if(mMp3InfoFragment != null) mMp3InfoFragment.updateFileInfoAdapter();
        if(mMp4InfoFragment != null) mMp4InfoFragment.updateFileInfoAdapter();

        //更新已选中Button
        getSelectedView();
    }

    /**
     * 选中view的样式
     * @param isEnable
     */
    private void setSelectedViewStyle(boolean isEnable) {
        if (isEnable){
            btn_Selected.setEnabled(true);
            btn_Selected.setBackgroundResource(R.drawable.selector_bottom_text_common);
            btn_Selected.setTextColor(getResources().getColor(R.color.colorPrimary));
        }else {
            btn_Selected.setEnabled(false);
            btn_Selected.setBackgroundResource(R.drawable.shape_bottom_text_unenable);
            btn_Selected.setTextColor(getResources().getColor(R.color.darker_gray));
        }
    }

    /***
     * 获取选中文件的view
     * @return
     */

    public View getSelectedView(){
        if (AppContext.getAppContext().getmFileInfoMap()!=null&&AppContext.getAppContext().getAllFileInfoSendSize()>0){
            setSelectedViewStyle(true);
            int size=AppContext.getAppContext().getmFileInfoMap().size();
            btn_Selected.setText(getResources().getString(R.string.str_has_selected_detail,size));
        }else {
            setSelectedViewStyle(false);
            btn_Selected.setText(getContext().getString(R.string.str_has_selected));
        }
        return btn_Selected;
    }
}
