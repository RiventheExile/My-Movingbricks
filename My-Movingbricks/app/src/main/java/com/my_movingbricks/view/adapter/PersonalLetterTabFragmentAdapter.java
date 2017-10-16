package com.my_movingbricks.view.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.my_movingbricks.base.BaseMvpFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/30 0030.
 */

public class PersonalLetterTabFragmentAdapter extends FragmentPagerAdapter {

    private String[] strs = null ;
    private ArrayList<BaseMvpFragment> fragments ;

    public PersonalLetterTabFragmentAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }


    public void setData(String[] strs,ArrayList<BaseMvpFragment> fragments){
        if(strs == null){
            strs = new String[]{} ;
        }

        this.strs = strs ;

        if(fragments == null){
            fragments = new ArrayList<BaseMvpFragment>();
        }

        this.fragments = fragments ;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments != null && fragments.size() > 0 ? fragments.get(position) : null;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return strs != null && strs.length > position ? strs[position] : "";
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return strs != null ? strs.length : 0;
    }

}

