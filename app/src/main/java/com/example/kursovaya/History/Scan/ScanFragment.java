package com.example.kursovaya.History.Scan;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kursovaya.DBHelper;
import com.example.kursovaya.History.Calc.CalcAdapter;
import com.example.kursovaya.History.Calc.CalcItem;
import com.example.kursovaya.R;

import java.util.ArrayList;
import java.util.List;


public class ScanFragment extends Fragment {
    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private final List<ScanItem> scanItemList = new ArrayList<>();
    private ScanAdapter favAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dbHelper = new DBHelper(getContext());

        loadData();
        return view;
    }



    private void loadData() {
        if (scanItemList != null) {
            scanItemList.clear();
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_SCAN, null, null, null, null, null, null);
        try {
            while (cursor.moveToNext()) {
                int title = (cursor.getColumnIndex(DBHelper.KEY_PURPOSE));
                int id = (cursor.getColumnIndex(DBHelper.KEY_ID));
                int sum = (cursor.getColumnIndex(DBHelper.KEY_SUM));
                ScanItem favItem = new ScanItem(cursor.getString(title), cursor.getString(id), cursor.getString(sum));
                scanItemList.add(favItem);
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

        favAdapter = new ScanAdapter(getActivity(), scanItemList);

        recyclerView.setAdapter(favAdapter);
    }
}