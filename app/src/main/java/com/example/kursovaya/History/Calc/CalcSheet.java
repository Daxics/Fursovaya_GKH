package com.example.kursovaya.History.Calc;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kursovaya.DBHelper;
import com.example.kursovaya.R;

public class CalcSheet extends AppCompatActivity implements View.OnClickListener {
    TextView Discr, Otopl, Wodosnob, Gazos, Electro, Others, Summ;
    Button button2;
    DBHelper dbHelper;
    SQLiteDatabase db;
    int ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_sheet);
        init();
        Intent intent = getIntent();
        ID = intent.getIntExtra("ID", -1);
        loadData();
    }

    private void init(){
        if(getSupportActionBar() != null){
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        Discr = findViewById(R.id.Discr);
        Otopl = findViewById(R.id.Otopl);
        Wodosnob = findViewById(R.id.Wodosnob);
        Gazos = findViewById(R.id.Gazos);
        Electro = findViewById(R.id.Electro);
        Others = findViewById(R.id.Others);
        Summ = findViewById(R.id.Summ);

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }


    private void loadData() {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_CALC,null, null, null, null, null, null);
        try {
            cursor.moveToPosition(ID);

            int disc = (cursor.getColumnIndex(DBHelper.KEY_DISC));
            int otopl = (cursor.getColumnIndex(DBHelper.KEY_OTOPL));
            int wodos = (cursor.getColumnIndex(DBHelper.KEY_WODOS));
            int gaz = (cursor.getColumnIndex(DBHelper.KEY_GAZ));
            int elect = (cursor.getColumnIndex(DBHelper.KEY_ELECT));
            int other = (cursor.getColumnIndex(DBHelper.KEY_OTHER));
            int sum = (cursor.getColumnIndex(DBHelper.KEY_SUM));

            Discr.setText(cursor.getString(disc));
            Otopl.setText(cursor.getString(otopl));
            Wodosnob.setText(cursor.getString(wodos));
            Gazos.setText(cursor.getString(gaz));
            Electro.setText(cursor.getString(elect));
            Others.setText(cursor.getString(other));
            Summ.setText(cursor.getString(sum));

        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Вы точно хотите удалить запись?");
        builder.setTitle("Подтверждение");
        builder.setNegativeButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query(DBHelper.TABLE_CALC, null,null,null,null, null,null);
                cursor.moveToPosition(ID);
                int id = (cursor.getColumnIndex(DBHelper.KEY_ID));

                int delCount = db.delete(DBHelper.TABLE_CALC, DBHelper.KEY_ID + " =" + cursor.getString(id), null);
                db.execSQL("VACUUM");
                finish();
            }
        }).setPositiveButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}