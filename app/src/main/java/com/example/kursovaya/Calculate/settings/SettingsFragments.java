package com.example.kursovaya.Calculate.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.kursovaya.R;

import java.util.prefs.PreferenceChangeEvent;

public class SettingsFragments extends PreferenceFragment {
    private EditTextPreference electricity_mono,
            electricity_duo_1, electricity_duo_2,
            electricity_trio_1, electricity_trio_2, electricity_trio_3;
    private ListPreference elect;
    private String electr_cont;
    private PreferenceScreen PrefScr;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_screen);
        init();
        Create();

        elect.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                Del((String) o);
                return true;
            }
        });
    }


    private void init(){
        elect = (ListPreference) findPreference("elect");
        SharedPreferences electr = PreferenceManager.getDefaultSharedPreferences(getActivity());
        electr_cont = electr.getString("elect", "Единая ставка");
        PrefScr = (PreferenceScreen) findPreference("PrefScr");
    }


    private void CreateMono(){
        electricity_mono = new EditTextPreference(getActivity());
        electricity_mono.setDefaultValue("");
        electricity_mono.setKey("electricity_mono");
        electricity_mono.setTitle("Единой ставка");
        electricity_mono.setDialogTitle("руб./кВт•ч");
        TextView textView = electricity_mono.getEditText();
        textView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        PrefScr.addPreference(electricity_mono);
    }


    private void CreateDuo(){
        electricity_duo_1 = new EditTextPreference(getActivity());
        electricity_duo_1.setDefaultValue("");
        electricity_duo_1.setKey("electricity_duo_1");
        electricity_duo_1.setTitle("Дневная зона");
        electricity_duo_1.setDialogTitle("руб./кВт•ч");
        TextView textView = electricity_duo_1.getEditText();
        textView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        PrefScr.addPreference(electricity_duo_1);

        electricity_duo_2 = new EditTextPreference(getActivity());
        electricity_duo_2.setDefaultValue("");
        electricity_duo_2.setKey("electricity_duo_2");
        electricity_duo_2.setTitle("Ночная зона");
        electricity_duo_2.setDialogTitle("руб./кВт•ч");
        TextView textView_2 = electricity_duo_2.getEditText();
        textView_2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        PrefScr.addPreference(electricity_duo_2);
    }


    private void CreateTrio(){
        electricity_trio_1 = new EditTextPreference(getActivity());
        electricity_trio_1.setDefaultValue("");
        electricity_trio_1.setKey("electricity_trio_1");
        electricity_trio_1.setTitle("Дневная зона");
        electricity_trio_1.setDialogTitle("руб./кВт•ч");
        TextView textView = electricity_trio_1.getEditText();
        textView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        PrefScr.addPreference(electricity_trio_1);

        electricity_trio_2 = new EditTextPreference(getActivity());
        electricity_trio_2.setDefaultValue("");
        electricity_trio_2.setKey("electricity_trio_2");
        electricity_trio_2.setTitle("Пиковая зона");
        electricity_trio_2.setDialogTitle("руб./кВт•ч");
        TextView textView_2 = electricity_trio_2.getEditText();
        textView_2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        PrefScr.addPreference(electricity_trio_2);

        electricity_trio_3 = new EditTextPreference(getActivity());
        electricity_trio_3.setDefaultValue("");
        electricity_trio_3.setKey("electricity_trio_3");
        electricity_trio_3.setTitle("Ночная зона");
        electricity_trio_3.setDialogTitle("руб./кВт•ч");
        TextView textView_3 = electricity_trio_3.getEditText();
        textView_3.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        PrefScr.addPreference(electricity_trio_3);
    }


    private void Del(String string){
        switch (string){
            case "Единая ставка":
                Rem();
                CreateMono();
                break;
            case "Две суточные зоны":
                Rem();
                CreateDuo();
                break;
            case "Три суточные зоны":
                Rem();
                CreateTrio();
                break;
        }
    }


    private void Rem(){
        if (electricity_mono != null){
            PrefScr.removePreference(electricity_mono);
            electricity_mono = null;
        }
        if (electricity_duo_1 != null && electricity_duo_2 != null){
            PrefScr.removePreference(electricity_duo_1);
            PrefScr.removePreference(electricity_duo_2);
            electricity_duo_1 = null;
            electricity_duo_2 = null;
        }
        if (electricity_trio_1 != null && electricity_trio_2 != null && electricity_trio_2 != null ){
            PrefScr.removePreference(electricity_trio_1);
            PrefScr.removePreference(electricity_trio_2);
            PrefScr.removePreference(electricity_trio_3);
            electricity_trio_1 = null;
            electricity_trio_2 = null;
            electricity_trio_3 = null;
        }
    }


    private void Create(){
        switch (electr_cont){
            case "Единая ставка":
                CreateMono();
                break;
            case "Две суточные зоны":
                CreateDuo();
                break;
            case "Три суточные зоны":
                CreateTrio();
                break;
        }
    }
}
