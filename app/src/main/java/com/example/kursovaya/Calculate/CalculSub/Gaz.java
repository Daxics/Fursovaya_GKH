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

public class Gaz extends AppCompatActivity implements View.OnClickListener{
    EditText Gaz;
    Button CalcBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaz);
        init();
    }

    private void init(){
        if(getSupportActionBar() != null){
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Газоснобжение");
        }
        Gaz = (EditText) findViewById(R.id.Gaz);

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
        if (Gaz.getText().length() != 0) {
            SharedPreferences gaz = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences gaz_pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

            double electr_cont = Double.parseDouble(gaz.getString("gas", "1"));

            SharedPreferences.Editor editor = gaz_pref.edit();
            editor.putString("saved_gaz", String.valueOf(electr_cont *
                    Double.parseDouble(Gaz.getText().toString()))).commit();
        }
        onBackPressed();
    }
}