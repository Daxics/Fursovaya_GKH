package com.example.kursovaya.Calculate.CalculSub;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kursovaya.R;

public class Otoplenie extends AppCompatActivity implements  View.OnClickListener  {
    EditText Otop;
    Button CalcBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otoplenie);
        init();
    }

    private void init(){
        if(getSupportActionBar() != null){
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Отопление");
        }
        Otop = (EditText) findViewById(R.id.Otop);

        CalcBtn = (Button) findViewById(R.id.CalcBtn);
        CalcBtn.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (Otop.getText().length() != 0) {

            SharedPreferences otopl = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences otopl_pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

            double otop = Double.parseDouble(otopl.getString("home", "1"));

            SharedPreferences.Editor editor = otopl_pref.edit();
            editor.putString("saved_otopl", String.valueOf(otop *
                    Double.parseDouble(Otop.getText().toString()))).commit();
        }
        onBackPressed();
    }
}