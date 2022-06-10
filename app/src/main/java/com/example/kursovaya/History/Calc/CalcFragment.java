package com.example.kursovaya.History.Calc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kursovaya.DBHelper;
import com.example.kursovaya.R;

import java.util.ArrayList;
import java.util.List;


public class CalcFragment extends Fragment {
    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private final List<CalcItem> calcItemList = new ArrayList<>();
    private CalcAdapter favAdapter;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Log.d("MyLog", "onActivityCreated");
//    }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        Log.d("MyLog", "onAttach");
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.d("MyLog", "onStart");
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calc, container, false);
        Log.d("MyLog", "onCreateView");

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dbHelper = new DBHelper(getContext());
        loadData();
        setup();
        return root;
    }

    private void loadData() {
        calcItemList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_CALC,null, null, null, null, null, null);
        try {
            while (cursor.moveToNext()) {
                int title = (cursor.getColumnIndex(DBHelper.KEY_DISC));
                int id = (cursor.getColumnIndex(DBHelper.KEY_ID));
                int sum = (cursor.getColumnIndex(DBHelper.KEY_SUM));
                CalcItem favItem = new CalcItem(cursor.getString(title), cursor.getString(id), cursor.getString(sum));
                calcItemList.add(favItem);
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }
    }
    private void setup(){
        favAdapter = new CalcAdapter(getActivity(), calcItemList);
        recyclerView.setAdapter(favAdapter);
    }
}