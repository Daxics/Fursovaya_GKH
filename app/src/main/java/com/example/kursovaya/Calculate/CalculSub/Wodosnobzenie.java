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

public class Wodosnobzenie extends AppCompatActivity implements View.OnClickListener {
    EditText Cold, Hot;
    Button CalcBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wodosnobzenie);
        init();
    }

    private void init(){
        if(getSupportActionBar() != null){
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Водоснобжение");
        }
        Cold = (EditText) findViewById(R.id.Cold);
        Hot = (EditText) findViewById(R.id.Hot);

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
        if (Cold.getText().length() != 0 || Hot.getText().length() != 0) {
            SharedPreferences electr = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences elect_pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

            double wootw_cold = Double.parseDouble(electr.getString("cold", "1"));
            double wootw_hot = Double.parseDouble(electr.getString("hot", "1"));
            double wootw_drainage = Double.parseDouble(electr.getString("drainage", "1"));

            double mid = (Double.parseDouble(Cold.getText().toString()) +
                    Double.parseDouble(Hot.getText().toString())) * wootw_drainage;

            SharedPreferences.Editor editor = elect_pref.edit();
            editor.putString("saved_wodos", String.valueOf(wootw_cold *
                    Double.parseDouble(Cold.getText().toString()) + wootw_hot *
                    Double.parseDouble(Hot.getText().toString()) + mid)).commit();
        }
        onBackPressed();
    }
}