package com.example.kursovaya.scanner;

import android.content.Intent;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.kursovaya.R;


public class Scanner extends Fragment implements View.OnClickListener {

    private ImageButton scanBaton_1, scanBaton_2, scanBaton_3, scanBaton_4, scanBaton_5, scanBaton_6;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        scanBaton_1 = (ImageButton) view.findViewById(R.id.scanBTN_1);
        scanBaton_1.setOnClickListener(this);
        scanBaton_2 = (ImageButton) view.findViewById(R.id.scanBTN_2);
        scanBaton_2.setOnClickListener(this);
        scanBaton_3 = (ImageButton) view.findViewById(R.id.scanBTN_3);
        scanBaton_3.setOnClickListener(this);
        scanBaton_4 = (ImageButton) view.findViewById(R.id.scanBTN_4);
        scanBaton_4.setOnClickListener(this);
        scanBaton_5 = (ImageButton) view.findViewById(R.id.scanBTN_5);
        scanBaton_5.setOnClickListener(this);
        scanBaton_6 = (ImageButton) view.findViewById(R.id.scanBTN_6);
        scanBaton_6.setOnClickListener(this);
        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scanBTN_1:
                OpenSex("Сводный счёт");
                break;
            case R.id.scanBTN_2:
                OpenSex("Капитальный ремонт");
                break;
            case R.id.scanBTN_3:
                OpenSex("Газ");
                break;
            case R.id.scanBTN_4:
                OpenSex("Вода");
                break;
            case R.id.scanBTN_5:
                OpenSex("Электричество");
                break;
            case R.id.scanBTN_6:
                OpenSex("Другое");
                break;
            case R.id.button:
                OpenSex(null);
                break;
        }
    }

    private void OpenSex(String text){
        Intent intent = new Intent(getActivity(), ScanActivity.class);
        intent.putExtra("Description", text);
        startActivity(intent);
    }

}