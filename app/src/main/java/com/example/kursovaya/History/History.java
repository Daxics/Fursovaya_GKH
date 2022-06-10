package com.example.kursovaya.History;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.kursovaya.History.Calc.CalcAdapter;
import com.example.kursovaya.R;
import com.google.android.material.tabs.TabLayout;


public class History extends Fragment {
    TabLayout TabLayout;
    ViewPager ViewPager;
    HistoryAdapter HistAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        Log.d("MyLog", "onCreateView");

        TabLayout = (TabLayout) view.findViewById(R.id.TabLayout);
        ViewPager = (ViewPager) view.findViewById(R.id.ViewPager);
        HistAdapter = new HistoryAdapter(getFragmentManager(), TabLayout.getTabCount());
        ViewPager.setAdapter(HistAdapter);
        TabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(com.google.android.material.tabs.TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(com.google.android.material.tabs.TabLayout.Tab tab) {

            }
        });
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager.setAdapter(new HistoryAdapter(getChildFragmentManager(), TabLayout.getTabCount() ));
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewPager.setAdapter(new HistoryAdapter(getChildFragmentManager(), TabLayout.getTabCount() ));

    }
}



//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        dbHelper = new DBHelper(getContext());
//
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//        Cursor cursor = database.query(DBHelper.TABLE_CALC, null, null, null, null, null, null);
//
//        if (cursor.moveToFirst()) {
//            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
//            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_DISC);
//            int emailIndex = cursor.getColumnIndex(DBHelper.KEY_SUM);
//            do {
//                Log.d("MyLog", "ID = " + cursor.getInt(idIndex) +
//                        ", name = " + cursor.getString(nameIndex) +
//                        ", email = " + cursor.getString(emailIndex));
//            } while (cursor.moveToNext());
//        } else
//            Log.d("MyLog","0 rows");
//
//        cursor.close();
//        return inflater.inflate(R.layout.fragment_history, container, false);
//    }
//
