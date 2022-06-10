package com.example.kursovaya;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.kursovaya.Calculate.Calculate;
import com.example.kursovaya.History.History;
import com.example.kursovaya.databinding.ActivityMainBinding;
import com.example.kursovaya.scanner.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        replaceFragment(new Calculate());
        binding.bottomNavigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.calculate:
                    replaceFragment(new Calculate());
                    break;
                case R.id.scanner:
                    replaceFragment(new Scanner());
                    break;
                case R.id.history:
                    replaceFragment(new History());
                    break;
            }
        return true;
        });
    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }


    private void init(){
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void onClick(View view) {

    }
}
