package com.arpith.dmucs.adapter;

import com.arpith.dmucs.DonateFragment;
import com.arpith.dmucs.ReliefFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class DonateTabsPagerAdapter extends FragmentPagerAdapter {
 
    public DonateTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            return new DonateFragment();
        case 1:
            return new ReliefFragment();
        
        }
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }
 
}
