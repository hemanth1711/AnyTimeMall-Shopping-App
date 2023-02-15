package com.bawp.testmall;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bawp.testmall.Data.Databasehandler;
import com.bawp.testmall.model.login_system;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class sign_in extends Fragment {

    public sign_in() {
        // Required empty public constructor
    }
    private TextView Already_have_an_account;
    private EditText Sign_up_full_name;
    private EditText Sign_up_Email;
    private EditText Sign_up_Password;
    private EditText Sign_up_conform_password;
     private Button Sign_up_btn;

     private Databasehandler db;
     private login_system loginSystem;
     private List<login_system> login_systems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_in, container, false);
        Already_have_an_account = view.findViewById(R.id.SignInto);
        Sign_up_full_name = view.findViewById(R.id.SignUpFullName);
        Sign_up_Email = view.findViewById(R.id.SignUpEmail);
        Sign_up_Password = view.findViewById(R.id.SignUpPassword);
        Sign_up_conform_password = view.findViewById(R.id.SignUpConformPassword);

        Sign_up_btn = view.findViewById(R.id.SignUpBtn);
        return  view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new Databasehandler(getActivity());
//        db.addData(new login_system("hemanth","hemu","hemu","hemugmail"));
        login_systems=db.getAllData();
        for(login_system loginSystem1 : login_systems)
        {
            Log.d("lolo", "onViewCreated: "+ "Email:" + loginSystem1.getEmail() + "password:"+loginSystem1.getPassword());
        }

        Already_have_an_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.frame_Layout);
                if(fragment!=null)
                {
                    fragment = new sign_up();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_Layout,fragment)
                            .commit();
                }
            }
        });

        Sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String full_name;
                String Email;
                String password;
                String conformPassword;
                full_name = Sign_up_full_name.getText().toString().trim();
                Email = Sign_up_Email.getText().toString().trim();
                password = Sign_up_Password.getText().toString().trim();
                conformPassword = Sign_up_conform_password.getText().toString().trim();

                int a = checkifExists(full_name, Email);
                if (a == 0) {
                    //login_systems = db.getAllData();
                    if(password.equals(conformPassword)) {
                        if (!full_name.isEmpty() && !Email.isEmpty() && !password.isEmpty()) {

                            loginSystem = new login_system();
                            loginSystem.setFullName(full_name);
                            loginSystem.setEmail(Email);
                            loginSystem.setPassword(password);
                            loginSystem.setConformPassword(conformPassword);
                            db.addData(loginSystem);
                            login_systems = db.getAllData();
                            for(login_system loginSystem1 : login_systems)
                            {
                                Log.d("hehe", "onClick: "+  loginSystem1.getEmail() + loginSystem1.getPassword());
                            }
                            Intent intent = new Intent(getActivity(), main.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getActivity(), "Some fields are empty please check", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Log.d("uyuy", "onClick: "+password+conformPassword);
                        Toast.makeText(getActivity(),"Password and Conform Password must be same",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private int checkifExists(String full_name, String Email) {
        Log.d("ddd", "checkifExists: "+"dataverify");
        login_systems = db.getAllData();
        int a = 0;
        for(login_system loginSystem1 : login_systems)
        {
            Log.d("ddd", "checkifExists: "+loginSystem1.getFullName());
            if(loginSystem1.getFullName().equals(full_name) || loginSystem1.getEmail().equals(Email))
            {
                 a=1;
                 Toast.makeText(getActivity(),"ful_name or email already exsists please check",Toast.LENGTH_SHORT).show();
                break;
            }
            else
            {
                a=0;
            }
        }
     return a;
    }
}
