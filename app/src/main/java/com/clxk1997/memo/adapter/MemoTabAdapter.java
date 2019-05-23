package com.clxk1997.memo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.clxk1997.memo.fragment.MediaFragment;
import com.clxk1997.memo.fragment.TextFragment;


public class MemoTabAdapter extends FragmentPagerAdapter {
    public static final String[] TITLES_IN_MEMO = {"Text","Voice"};

    public MemoTabAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new TextFragment();
            case 1:
                return new MediaFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TITLES_IN_MEMO.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES_IN_MEMO[position];
    }
}
