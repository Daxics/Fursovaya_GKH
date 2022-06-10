package com.example.kursovaya.Calculate;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.widget.ThemedSpinnerAdapter;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kursovaya.Calculate.CalculSub.Electrichestwo1;
import com.example.kursovaya.Calculate.CalculSub.Electrichestwo2;
import com.example.kursovaya.Calculate.CalculSub.Electrichestwo3;
import com.example.kursovaya.Calculate.CalculSub.Gaz;
import com.example.kursovaya.Calculate.CalculSub.Wodosnobzenie;
import com.example.kursovaya.Calculate.settings.SettingActivity;
import com.example.kursovaya.Calculate.CalculSub.Otoplenie;
import com.example.kursovaya.DBHelper;
import com.example.kursovaya.R;

public class Calculate extends Fragment implements View.OnClickListener {

     ImageButton Setting;
     EditText otopl, wodos, gaz, elect, other, disc;
     Button otopl_btn, wodos_btn, gaz_btn, elect_btn, save;
     SharedPreferences electr, otopl_pref, wodos_pref, gaz_pref, elect_pref, other_pref, disc_pref;
     final String SAVED_OTOPL = "saved_otopl";
     final String SAVED_WODOS = "saved_wodos";
     final String SAVED_GAZ = "saved_gaz";
     final String SAVED_ELECT = "saved_elect";
     final String SAVED_OTHER = "saved_other";
     final String SAVED_DISC = "saved_disc";
     private String electr_cont;
     DBHelper dbHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculate, container, false);
        Setting = (ImageButton) view.findViewById(R.id.Settings);
        Setting.setOnClickListener(this);
        init(view);
        return view;
    }

    private void init(View view) {
        otopl = (EditText) view.findViewById(R.id.otopl);
        wodos = (EditText) view.findViewById(R.id.wodos);
        gaz = (EditText) view.findViewById(R.id.gaz);
        elect = (EditText) view.findViewById(R.id.elect);
        other = (EditText) view.findViewById(R.id.other);
        disc = (EditText) view.findViewById(R.id.disc);

        otopl_btn = (Button) view.findViewById(R.id.otopl_btn);
        otopl_btn.setOnClickListener(this);
        wodos_btn = (Button) view.findViewById(R.id.wodos_btn);
        wodos_btn.setOnClickListener(this);
        gaz_btn = (Button) view.findViewById(R.id.gaz_btn);
        gaz_btn.setOnClickListener(this);
        elect_btn = (Button) view.findViewById(R.id.elect_btn);
        elect_btn.setOnClickListener(this);
        save = (Button) view.findViewById(R.id.save);
        save.setOnClickListener(this);

        otopl_pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        wodos_pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        gaz_pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        elect_pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        other_pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        disc_pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        electr = PreferenceManager.getDefaultSharedPreferences(getActivity());

        dbHelper = new DBHelper(getContext());
    }


    private void Set(){
        otopl.setText(otopl_pref.getString(SAVED_OTOPL, ""));
        wodos.setText(wodos_pref.getString(SAVED_WODOS, ""));
        gaz.setText(gaz_pref.getString(SAVED_GAZ, ""));
        elect.setText(elect_pref.getString(SAVED_ELECT, ""));
        other.setText(other_pref.getString(SAVED_OTHER, ""));
        disc.setText(disc_pref.getString(SAVED_DISC, ""));
    }

    @Override
    public void onResume() {
        super.onResume();
        Set();
    }

    @Override
    public void onPause() {
        super.onPause();
        Save_Pref();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Save_Pref();
    }

    private void Clear(){
        SharedPreferences.Editor editor = otopl_pref.edit();
        editor.putString(SAVED_OTOPL, null).commit();

        SharedPreferences.Editor editor_1 = wodos_pref.edit();
        editor_1.putString(SAVED_WODOS, null).commit();

        SharedPreferences.Editor editor_2 = gaz_pref.edit();
        editor_2.putString(SAVED_GAZ, null).commit();

        SharedPreferences.Editor editor_3 = elect_pref.edit();
        editor_3.putString(SAVED_ELECT, null).commit();

        SharedPreferences.Editor editor_4 = other_pref.edit();
        editor_4.putString(SAVED_OTHER, null).commit();

        SharedPreferences.Editor editor_5 = disc_pref.edit();
        editor_5.putString(SAVED_DISC, null).commit();
    }

    private void Save_Pref(){
        SharedPreferences.Editor editor = otopl_pref.edit();
        editor.putString(SAVED_OTOPL, otopl.getText().toString()).commit();

        SharedPreferences.Editor editor_1 = wodos_pref.edit();
        editor_1.putString(SAVED_WODOS, wodos.getText().toString()).commit();

        SharedPreferences.Editor editor_2 = gaz_pref.edit();
        editor_2.putString(SAVED_GAZ, gaz.getText().toString()).commit();

        SharedPreferences.Editor editor_3 = elect_pref.edit();
        editor_3.putString(SAVED_ELECT, elect.getText().toString()).commit();

        SharedPreferences.Editor editor_4 = other_pref.edit();
        editor_4.putString(SAVED_OTHER, other.getText().toString()).commit();

        SharedPreferences.Editor editor_5 = disc_pref.edit();
        editor_5.putString(SAVED_DISC, disc.getText().toString()).commit();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.Settings:
                Save_Pref();
                OpenAct(SettingActivity.class);
                break;
            case R.id.otopl_btn:
                Save_Pref();
                OpenAct(Otoplenie.class);
                break;
            case R.id.wodos_btn:
                Save_Pref();
                OpenAct(Wodosnobzenie.class);
                break;
            case R.id.gaz_btn:
                Save_Pref();
                OpenAct(Gaz.class);
                break;
            case R.id.elect_btn:
                electr_cont = electr.getString("elect", "Единая ставка");
                switch (electr_cont) {
                    case "Единая ставка":
                        Save_Pref();
                        OpenAct(Electrichestwo1.class);
                        break;
                    case "Две суточные зоны":
                        Save_Pref();
                        OpenAct(Electrichestwo2.class);
                        break;
                    case "Три суточные зоны":
                        Save_Pref();
                        OpenAct(Electrichestwo3.class);
                        break;
                }
                break;
            case R.id.save:
                Save();
                Clear();
                break;
        }
    }

    private void Save(){
        String Otop = otopl.getText().toString();
        String Wodo = wodos.getText().toString();
        String Gaz = gaz.getText().toString();
        String Elect = elect.getText().toString();
        String Other = other.getText().toString();
        String Disc = disc.getText().toString();

        if (Otop.isEmpty()){
            showError(otopl, "Это поле должно быть заполнено");
        }
        else if (Wodo.isEmpty()){
            showError(wodos, "Это поле должно быть заполнено");
        }
        else if (Gaz.isEmpty()){
            showError(gaz, "Это поле должно быть заполнено");
        }
        else if (Elect.isEmpty()){
            showError(elect, "Это поле должно быть заполнено");
        }
        else if (Other.isEmpty()){
            showError(other, "Это поле должно быть заполнено");
        }
        else if (Disc.isEmpty()){
            showError(disc, "Это поле должно быть заполнено");
        }
        else {
            saveDB(Otop, Wodo, Gaz, Elect, Other, Disc);
            otopl.setText("");
            wodos.setText("");
            gaz.setText("");
            elect.setText("");
            other.setText("");
            disc.setText("");
        }
    }

    private void saveDB(String Otop, String Wodo, String Gaz, String Elect, String Other, String Disc) {
        String sum = String.valueOf(Double.parseDouble(Otop) + Double.parseDouble(Wodo) + Double.parseDouble(Gaz) + Double.parseDouble(Elect) + Double.parseDouble(Other));

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_OTOPL, Otop);
        contentValues.put(DBHelper.KEY_WODOS, Wodo);
        contentValues.put(DBHelper.KEY_GAZ, Gaz);
        contentValues.put(DBHelper.KEY_ELECT, Elect);
        contentValues.put(DBHelper.KEY_OTHER, Other);
        contentValues.put(DBHelper.KEY_DISC, Disc);
        contentValues.put(DBHelper.KEY_SUM, sum);

        database.insert(DBHelper.TABLE_CALC, null, contentValues);
    }

    private void showError(EditText editText, String string){
        editText.setError(string);
    }

    private void OpenAct(Class clas){
        Intent intent = new Intent(getActivity(), clas);
        startActivity(intent);
    }
}