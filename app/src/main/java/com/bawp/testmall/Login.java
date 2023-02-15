package com.bawp.testmall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.frame_Layout);

        if(fragment==null)
        {
            fragment = new Signup();
            fragmentManager.beginTransaction()
                    .add(R.id.frame_Layout,fragment)
                    .commit();
        }

    }
}
