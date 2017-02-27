package com.example.administrator.flash.ui.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.administrator.flash.AppContext;
import com.example.administrator.flash.R;
import com.example.administrator.flash.core.entity.FileInfo;
import com.example.administrator.flash.core.utils.FileUtils;
import com.example.administrator.flash.core.utils.ToastUtils;
import com.example.administrator.flash.ui.ChooseFileActivity;
import com.example.administrator.flash.ui.adapter.FileInfoAdapter;
import com.example.administrator.flash.utils.AnimationUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by KingHua on 2017/2/22.
 */
public class FileInfoFragments extends Fragment {

    GridView gv;
    ProgressBar pd;
    private int mType = FileInfo.TYPE_APK;
    private List<FileInfo> mFileInfoList;
    private FileInfoAdapter mFileInfoAdapter;

    public FileInfoFragments() {
    }

    public FileInfoFragments(int type) {
        this.mType = type;
    }

    public static FileInfoFragments newInstance(int type) {
        FileInfoFragments fragments = new FileInfoFragments(type);
        return fragments;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_apk, container, false);
        gv= (GridView) rootView.findViewById(R.id.gv);
        pd= (ProgressBar) rootView.findViewById(R.id.pd);
        if (mType == FileInfo.TYPE_APK) {//apk
            gv.setNumColumns(4);
        } else if (mType == FileInfo.TYPE_JPG) {//jpg
            gv.setNumColumns(3);
        } else {//mp3  mp4
            gv.setNumColumns(1);
        }

        if (mType == FileInfo.TYPE_APK) {
            new GetFileInfoListTask(getContext(), FileInfo.TYPE_APK).executeOnExecutor(AppContext.MAIN_EXECUTOR);
        } else if (mType == FileInfo.TYPE_JPG) {
            new GetFileInfoListTask(getContext(), FileInfo.TYPE_JPG).executeOnExecutor(AppContext.MAIN_EXECUTOR);

        } else if (mType == FileInfo.TYPE_MP3) {
            new GetFileInfoListTask(getContext(), FileInfo.TYPE_MP3).executeOnExecutor(AppContext.MAIN_EXECUTOR);

        } else if (mType == FileInfo.TYPE_MP4) {
            new GetFileInfoListTask(getContext(), FileInfo.TYPE_MP4).executeOnExecutor(AppContext.MAIN_EXECUTOR);

        }
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileInfo fileInfo = mFileInfoList.get(position);
                if (AppContext.getAppContext().isExist(fileInfo)) {
                    AppContext.getAppContext().delFileInfo(fileInfo);
                    updateFileInfoAdapter();
                } else {
                    AppContext.getAppContext().addFileInfo(fileInfo);
                    View startView = null;
                    View targetView = null;
                    startView = view.findViewById(R.id.iv_shortcut);
                    if (getActivity() != null && getActivity() instanceof ChooseFileActivity) {
                        ChooseFileActivity chooseFileActivity = (ChooseFileActivity) getActivity();
                        targetView = chooseFileActivity.getSelectedView();
                    }
                    AnimationUtils.setAddTaskAnimation(getActivity(), startView, targetView, null);
                }
                mFileInfoAdapter.notifyDataSetChanged();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        updateFileInfoAdapter();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /*显示进度*/
    public void showProgress() {
        if (pd != null) {
            pd.setVisibility(View.VISIBLE);
        }
    }

    /*隐藏进度条*/
    public void hideProgress() {
        if (pd != null && pd.isShown()) {
            pd.setVisibility(View.GONE);
        }
    }

    /*更新ChooseFileActivity选中的view*/
    private void updateSelectedView() {
        if (getActivity() != null && (getActivity() instanceof ChooseFileActivity)) {
            ChooseFileActivity chooseFileActivity = (ChooseFileActivity) getActivity();
            chooseFileActivity.getSelectedView();
        }
    }

    /*更新FileInfoAdapter*/
    private void updateFileInfoAdapter() {
        if (mFileInfoAdapter != null) {
            mFileInfoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    class GetFileInfoListTask extends AsyncTask<String, Integer, List<FileInfo>> {
        Context context = null;
        int sType = FileInfo.TYPE_APK;
        List<FileInfo> sFileInfo = null;

        public GetFileInfoListTask(Context context, int sType) {
            this.context = context;
            this.sType = sType;
        }

        @Override
        protected void onPreExecute() {
            showProgress();
            super.onPreExecute();
        }

        @Override
        protected List<FileInfo> doInBackground(String... params) {
            if (sType == FileInfo.TYPE_APK) {
                sFileInfo = FileUtils.getSpecificTypeFile(context, new String[]{FileInfo.EXTEND_APK});
                sFileInfo = FileUtils.getDetailFileInfos(context, sFileInfo, FileInfo.TYPE_APK);
            } else if (sType == FileInfo.TYPE_JPG) {
                sFileInfo = FileUtils.getSpecificTypeFile(context, new String[]{FileInfo.EXTEND_JPG, FileInfo.EXTEND_JPEG});
                sFileInfo = FileUtils.getDetailFileInfos(context, sFileInfo, FileInfo.TYPE_JPG);
            } else if (sType == FileInfo.TYPE_MP3) {
                sFileInfo = FileUtils.getSpecificTypeFile(context, new String[]{FileInfo.EXTEND_MP3});
                sFileInfo = FileUtils.getDetailFileInfos(context, sFileInfo, FileInfo.TYPE_MP3);
            } else if (sType == FileInfo.TYPE_MP4) {
                sFileInfo = FileUtils.getSpecificTypeFile(context, new String[]{FileInfo.EXTEND_MP4});
                sFileInfo = FileUtils.getDetailFileInfos(context, sFileInfo, FileInfo.TYPE_MP4);
            }
            mFileInfoList = sFileInfo;
            Log.i("sFileInfo----->", sFileInfo.size() + "");
            return sFileInfo;
        }

        @Override
        protected void onPostExecute(List<FileInfo> fileInfos) {
            hideProgress();
            Log.i("Execute_sFileInfo--->", sFileInfo.size() + "");
            if (sFileInfo.size() > 0 && sFileInfo != null) {
                Log.i("Execute type--->",mType+"");
                if (mType == FileInfo.TYPE_APK) {
                    mFileInfoAdapter = new FileInfoAdapter(context, sFileInfo, FileInfo.TYPE_APK);
                    gv.setAdapter(mFileInfoAdapter);
                } else if (mType == FileInfo.TYPE_JPG) {
                    mFileInfoAdapter = new FileInfoAdapter(context, sFileInfo, FileInfo.TYPE_JPG);
                    gv.setAdapter(mFileInfoAdapter);
                } else if (mType == FileInfo.TYPE_MP3) {
                    mFileInfoAdapter = new FileInfoAdapter(context, sFileInfo, FileInfo.TYPE_MP3);
                    gv.setAdapter(mFileInfoAdapter);
                } else if (mType == FileInfo.TYPE_MP4) {
                    mFileInfoAdapter = new FileInfoAdapter(context, sFileInfo, FileInfo.TYPE_MP4);
                    gv.setAdapter(mFileInfoAdapter);
                }
            } else {
                ToastUtils.show(context, getResources().getString(R.string.tip_has_no_apk_info));
            }

        }
    }
}
