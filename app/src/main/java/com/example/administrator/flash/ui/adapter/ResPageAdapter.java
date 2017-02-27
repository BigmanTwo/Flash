package com.example.administrator.flash.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.flash.ui.fragments.FileInfoFragments;

import java.util.List;

/**
 * Created by KingHua on 2017/2/23.
 */
public class ResPageAdapter extends FragmentPagerAdapter {
    String[] titlesArray;
    List<FileInfoFragments> list;

    public ResPageAdapter(FragmentManager fm, String[] titlesArray,List<FileInfoFragments> list ) {
        super(fm);
        this.titlesArray = titlesArray;
        this.list=list;
    }

    public ResPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return  list.get(position) ;
    }

    @Override
    public int getCount() {
        return titlesArray.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlesArray[position];
    }
}
