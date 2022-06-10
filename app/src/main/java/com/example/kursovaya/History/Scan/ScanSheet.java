package com.example.kursovaya.History.Scan;

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

public class ScanSheet extends AppCompatActivity implements View.OnClickListener {
    TextView fio, Adres, Bic, INN, Personal_Acc, Pers_Acc, Purp, COR, Summm;
    Button button2;
    DBHelper dbHelper;
    SQLiteDatabase db;
    int ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_sheet);
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

        fio = findViewById(R.id.fio);
        Adres = findViewById(R.id.Adres);
        Bic = findViewById(R.id.BIC);
        INN = findViewById(R.id.INN);
        Personal_Acc = findViewById(R.id.Personal_Acc);
        Pers_Acc = findViewById(R.id.Pers_Acc);
        Purp = findViewById(R.id.Purp);
        COR = findViewById(R.id.COR);
        Summm = findViewById(R.id.Summm);

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }


    private void loadData() {
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DBHelper.TABLE_SCAN,null, null, null, null, null, null);
        try {
            cursor.moveToPosition(ID);

            int FIO = (cursor.getColumnIndex(DBHelper.KEY_FIO));
            int Adress = (cursor.getColumnIndex(DBHelper.KEY_ADRESS));
            int BIC = (cursor.getColumnIndex(DBHelper.KEY_BIC));
            int payeeINN = (cursor.getColumnIndex(DBHelper.KEY_INN));
            int PersonalAcc = (cursor.getColumnIndex(DBHelper.KEY_PERSONAL_ACC));
            int PersAcc = (cursor.getColumnIndex(DBHelper.KEY_PERS_ACC));
            int Purpose = (cursor.getColumnIndex(DBHelper.KEY_PURPOSE));
            int COR_PER = (cursor.getColumnIndex(DBHelper.KEY_COR_PER));
            int sum = (cursor.getColumnIndex(DBHelper.KEY_SUM));

            fio.setText(cursor.getString(FIO));
            Adres.setText(cursor.getString(Adress));
            Bic.setText(cursor.getString(BIC));
            INN.setText(cursor.getString(payeeINN));
            Personal_Acc.setText(cursor.getString(PersonalAcc));
            Pers_Acc.setText(cursor.getString(PersAcc));
            Purp.setText(cursor.getString(Purpose));
            COR.setText(cursor.getString(COR_PER));
            Summm.setText(cursor.getString(sum));

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
                Cursor cursor = db.query(DBHelper.TABLE_SCAN, null,null,null,null, null,null);
                cursor.moveToPosition(ID);
                int id = (cursor.getColumnIndex(DBHelper.KEY_ID));

                int delCount = db.delete(DBHelper.TABLE_SCAN, DBHelper.KEY_ID + " =" + cursor.getString(id), null);
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