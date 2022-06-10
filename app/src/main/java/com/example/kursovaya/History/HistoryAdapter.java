package com.example.kursovaya.History;

import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.kursovaya.History.Calc.CalcFragment;
import com.example.kursovaya.History.Scan.ScanFragment;

public class HistoryAdapter extends FragmentPagerAdapter {
    int mTotalTabs;
    int count;

    public HistoryAdapter(FragmentManager fragmentManager, int totalTabs){
        super(fragmentManager);
        this.mTotalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CalcFragment();
            case 1:
                return new ScanFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTotalTabs;
    }
}
