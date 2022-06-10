package com.example.kursovaya.Calculate.CalculSub;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kursovaya.R;

public class Electrichestwo3 extends AppCompatActivity implements View.OnClickListener {
    EditText Peack, Day, Night;
    Button CalcBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrichestwo3);
        init();
    }

    private void init(){
        if(getSupportActionBar() != null){
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Электроэнергия");
        }
        Peack = (EditText) findViewById(R.id.Peack);
        Day = (EditText) findViewById(R.id.Day);
        Night = (EditText) findViewById(R.id.Night);

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
        if (Peack.getText().length() != 0 || Day.getText().length() != 0 || Night.getText().length() != 0) {
            SharedPreferences electr = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences elect_pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

            double electr_peack = Double.parseDouble(electr.getString("electricity_trio_1", "1"));
            double electr_day = Double.parseDouble(electr.getString("electricity_trio_2", "1"));
            double electr_night = Double.parseDouble(electr.getString("electricity_trio_3", "1"));

            SharedPreferences.Editor editor = elect_pref.edit();
            editor.putString("saved_elect", String.valueOf(electr_peack *
                    Double.parseDouble(Peack.getText().toString()) + electr_day *
                    Double.parseDouble(Day.getText().toString()) + electr_night *
                    Double.parseDouble(Night.getText().toString()))).commit();
        }
        onBackPressed();
    }
}