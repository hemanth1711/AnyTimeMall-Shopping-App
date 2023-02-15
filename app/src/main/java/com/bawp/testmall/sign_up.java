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
public class sign_up extends Fragment {

    public sign_up() {
        // Required empty public constructor
    }

    private TextView Don_not_have_an_account;
    private EditText Sign_in_Email;
    private EditText Sign_in_Password;

    private Button Sign_in_btn;
    private Databasehandler db ;
    private login_system loginSystem = new login_system();
    private List<login_system> login_systems = new ArrayList<>();


    private Button SignUp_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        Don_not_have_an_account = view.findViewById(R.id.SignUp);
        Sign_in_Email = view.findViewById(R.id.SignIn_email);
        Sign_in_Password = view.findViewById(R.id.SignIn_password);
        Sign_in_btn = view.findViewById(R.id.SignIn_btn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Don_not_have_an_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.frame_Layout);
                if(fragment!=null)
                {
                    fragment = new sign_in();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_Layout,fragment)
                            .commit();
                }

            }
        });
        Sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email;
                String Password;
                Email = Sign_in_Email.getText().toString().trim();
                Password = Sign_in_Password.getText().toString().trim();
                db = new Databasehandler(getActivity());
                int a =0;

                login_systems = db.getAllData();
                for(login_system loginSystem1 : login_systems)
                {
                    Log.d("iuiu", "onClick: "+ loginSystem1.getFullName()+loginSystem1.getEmail());
                }
                for(login_system loginSystem1 : login_systems)
                {
                    if(loginSystem1.getEmail().equals(Email) && loginSystem1.getPassword().equals(Password))
                    {
                        Intent intent = new Intent(getContext(),main.class);
                        startActivity(intent);
                        a=1;
                    }
                }
                if(a==0)
                {
                    Toast.makeText(getActivity(),"Check your Email and password correctly",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
