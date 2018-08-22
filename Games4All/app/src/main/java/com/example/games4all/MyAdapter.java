package com.example.games4all;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class MyAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Integer> layouts = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public MyAdapter(FragmentManager fm) {
        super(fm);
        addLayouts();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
        //Fragment fragment = null;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addLayouts(){
        fragments.add(new PcTab());
        fragments.add(new Play3Tab());
        fragments.add(new Play4TAb());
        fragments.add(new WiiTab());
        fragments.add(new XboxTab());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
        {
            return "PC";
        }
        if(position == 1)
        {
            return "PLAY 3";
        }
        if(position == 2)
        {
            return "PLAY 4";
        }
        if(position == 3)
        {
            return "WII";
        }
        if(position == 4)
        {
            return "XBOX";
        }

        return null;

    }
}
