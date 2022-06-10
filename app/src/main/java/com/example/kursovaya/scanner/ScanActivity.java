package com.example.kursovaya.scanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kursovaya.DBHelper;
import com.example.kursovaya.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScanActivity extends AppCompatActivity implements View.OnClickListener {
    EditText FIO, Adress, CorrespAcc, payeeINN, PersonalAcc, PersAcc, Purpose, COR_PER, Sum;
    Button SaveBtn;
    String Descr;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        Intent intent = getIntent();
        Descr = intent.getStringExtra("Description");
        Purpose.setText(Descr);
        if (!Objects.equals(Descr, null)){
            scanCode();
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

    private void init(){
        setContentView(R.layout.activity_scan);
        if(getSupportActionBar() != null){
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Сканер");
        }
        FIO = (EditText) findViewById(R.id.FIO);
        Adress = (EditText) findViewById(R.id.Adress);
        CorrespAcc = (EditText) findViewById(R.id.CorrespAcc);
        payeeINN = (EditText) findViewById(R.id.payeeINN);
        PersonalAcc = (EditText) findViewById(R.id.PersonalAcc);
        PersAcc = (EditText) findViewById(R.id.PersAcc);
        Purpose = (EditText) findViewById(R.id.Purpose);
        COR_PER = (EditText) findViewById(R.id.COR_PER);
        Sum = (EditText) findViewById(R.id.Sum);

        SaveBtn = (Button) findViewById(R.id.CalcBtn_3);
        SaveBtn.setOnClickListener(this);

        dbHelper = new DBHelper(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() != null && IsTrue(result.getContents())){
                SetContent(result.getContents());
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Во время чения кода произошла ошибка");
                builder.setTitle("Ошибка");
                builder.setNegativeButton("Отсканировать ещё раз", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scanCode();
                    }
                }).setPositiveButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(ScannerFragments.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Сканирование QR кода");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }


    @SuppressLint("SetTextI18n")
    private void SetContent(String string){
        Pattern regex_PersonalAcc = Pattern.compile("[Pp]ersonal[aA]cc=(\\d*)");
        Pattern regex_PayeeINN = Pattern.compile("[pP]ayee[iI][nN][nN]=(\\d*)");
        Pattern regex_BIC = Pattern.compile("[bB][iI][cC]=(\\d*)");
        Pattern regex_PersAcc = Pattern.compile("[Pp]ers[Aa]cc=([a-zA-Z0-9-]*)");
        Pattern regex_Adress = Pattern.compile("[pP]ayer[Aa]ddress=([\\w, .а-яА-Я-:]*)");

        Pattern regex_lastName = Pattern.compile("[lL]ast[nN]ame=([a-zA-Zа-яА-Я0-9]*)");
        Pattern regex_firstName = Pattern.compile("[Ff]irst[nN]ame=([a-zA-Zа-яА-Я0-9]*)");
        Pattern regex_middleName = Pattern.compile("[mM]iddle[nN]ame=([a-zA-Zа-яА-Я0-9]*)");

        Pattern regex_paymPeriod = Pattern.compile("[pP]aym[pP]eriod=(\\d*)");
        Pattern regex_Sum = Pattern.compile("[sS]um=(\\d*)");
        Pattern regex_Purpose = Pattern.compile("[pP]urpose=([a-zA-Zа-яА-Я0-9]*)");

        Matcher matcher_PersonalAcc = regex_PersonalAcc.matcher(string);
        Matcher matcher_PayeeINN = regex_PayeeINN.matcher(string);
        Matcher matcher_BIC = regex_BIC.matcher(string);
        Matcher matcher_PersAcc = regex_PersAcc.matcher(string);
        Matcher matcher_Adress = regex_Adress.matcher(string);

        Matcher matcher_lastName = regex_lastName.matcher(string);
        Matcher matcher_firstName = regex_firstName.matcher(string);
        Matcher matcher_middleName = regex_middleName.matcher(string);

        Matcher matcher_paymPeriod = regex_paymPeriod.matcher(string);
        Matcher matcher_Sum = regex_Sum.matcher(string);
        Matcher matcher_Purpose = regex_Purpose.matcher(string);

        String last ="";
        String first = "";
        String mid = "";
        while (matcher_lastName.find()){
            last = matcher_lastName.group(1);
        }
        while (matcher_firstName.find()){
            first = matcher_firstName.group(1);
        }
        while (matcher_middleName.find()){
            mid = matcher_middleName.group(1);
        }
        FIO.setText(last + " " +
                first + " " +
                mid);
        while (matcher_Adress.find()){
            Adress.setText(matcher_Adress.group(1));
        }
        while (matcher_BIC.find()){
            CorrespAcc.setText(matcher_BIC.group(1));
        }
        while (matcher_PayeeINN.find()){
             payeeINN.setText(matcher_PayeeINN.group(1));
        }
        while (matcher_PersonalAcc.find()){
             PersonalAcc.setText(matcher_PersonalAcc.group(1));
        }
        while (matcher_PersAcc.find()){
             PersAcc.setText(matcher_PersAcc.group(1));
        }
        while (matcher_Purpose.find()){
             Purpose.setText(matcher_Purpose.group(1));
        }
        while (matcher_paymPeriod.find()){
             COR_PER.setText(matcher_paymPeriod.group(1));
        }
        while (matcher_Sum.find()){
             String sumn = matcher_Sum.group(1);
             assert sumn != null;
             Sum.setText(sumn.substring(0, sumn.length()-2) + "." + sumn.substring(sumn.length()-2));
        }
    }

    private boolean IsTrue(String string){
        Pattern regex_Name = Pattern.compile("\\|[nN]ame=([a-zA-Zа-яА-Я0-9\" ]*)\\|");
        Matcher matcher_Name = regex_Name.matcher(string);
        while (matcher_Name.find()){
            String str = matcher_Name.group(1);
            if (str != "" || str != null ) {
                 return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        checkCreditsDetails();
    }

    private void checkCreditsDetails() {
        String fio = FIO.getText().toString();
        String adr = Adress.getText().toString();
        String bic = CorrespAcc.getText().toString();
        String inn = payeeINN.getText().toString();
        String Acc = PersonalAcc.getText().toString();
        String Pers = PersAcc.getText().toString();
        String Porp = Purpose.getText().toString();
        String summ = Sum.getText().toString();

        if (fio.isEmpty()){
            showError(FIO, "Это поле должно быть заполнено");
        }
        else if (bic.isEmpty()){
            showError(CorrespAcc, "Это поле должно быть заполнено");
        }
        else if (inn.isEmpty()){
            showError(payeeINN, "Это поле должно быть заполнено");
        }
        else if (Acc.isEmpty()){
            showError(PersonalAcc, "Это поле должно быть заполнено");
        }
        else if (Pers.isEmpty()){
            showError(PersAcc, "Это поле должно быть заполнено");
        }
         else if (Porp.isEmpty()){
            showError(Purpose, "Это поле должно быть заполнено");
        }
        else if (summ.isEmpty()){
            showError(Sum, "Это поле должно быть заполнено");
        }
        else {
            saveBD(fio, adr, bic, inn, Acc, Pers, Porp, summ);
        }
    }

    private void saveBD(String fio,String adr, String bic, String inn, String acc, String pers, String porp, String summ) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_FIO, fio);
        contentValues.put(DBHelper.KEY_ADRESS, adr);
        contentValues.put(DBHelper.KEY_BIC, bic);
        contentValues.put(DBHelper.KEY_INN, inn);
        contentValues.put(DBHelper.KEY_PERSONAL_ACC, acc);
        contentValues.put(DBHelper.KEY_PERS_ACC, pers);
        contentValues.put(DBHelper.KEY_PURPOSE, porp);
        contentValues.put(DBHelper.KEY_SUM, summ);

        database.insert(DBHelper.TABLE_SCAN, null, contentValues);
        onBackPressed();
    }

    private void showError(EditText editText, String string){
        editText.setError(string);
    }
}