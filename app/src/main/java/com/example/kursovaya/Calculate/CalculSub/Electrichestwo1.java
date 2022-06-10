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

public class Electrichestwo1 extends AppCompatActivity implements View.OnClickListener {
    EditText Pocaz;
    Button CalcBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrichestwo1);
        init();
    }

    private void init(){
        if(getSupportActionBar() != null){
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Электроэнергия");
        }
        Pocaz = (EditText) findViewById(R.id.Pocaz);

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
        if (Pocaz.getText().length() != 0) {
            SharedPreferences electr = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences elect_pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

            double electr_cont = Double.parseDouble(electr.getString("electricity_mono", "1"));

            SharedPreferences.Editor editor = elect_pref.edit();
            editor.putString("saved_elect", String.valueOf(electr_cont *
                    Double.parseDouble(Pocaz.getText().toString()))).commit();
        }
        onBackPressed();
    }
}