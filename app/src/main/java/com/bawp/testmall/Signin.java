package com.bawp.testmall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class Signin extends Fragment {
    private TextView Do_not_have_account;
    private FrameLayout parent_frame;
    private EditText sign_in_email;
    private EditText sign_in_password;
    private Button signin;
    private TextView forgot;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private String Email_pattren = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    public Signin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        Do_not_have_account = view.findViewById(R.id.Signin_to_Signup);
        parent_frame = getActivity().findViewById(R.id.frame_Layout);
        sign_in_email = view.findViewById(R.id.Sign_in_email);
        sign_in_password = view.findViewById(R.id.Sign_in_password);
        signin = view.findViewById(R.id.Sign_in);
        progressBar = view.findViewById(R.id.progressBar2);
        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Do_not_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.frame_Layout);
                if(fragment!=null)
                {
                    fragment = new Signup();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_Layout,fragment)
                            .commit();
                }
            }
        });
        sign_in_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sign_in_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkemailandpassword();
            }
        });
    }
    private void checkemailandpassword() {
        if(sign_in_email.getText().toString().matches(Email_pattren)){
            if(sign_in_password.length()>8){
                signin.setEnabled(false);
                signin.setTextColor(Color.argb(50,255,255,255));
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(sign_in_email.getText().toString(),sign_in_password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Intent intent = new Intent(getActivity(),main.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }else{
                                    signin.setEnabled(true);
                                    signin.setTextColor(Color.rgb(255,255,255));
                                    progressBar.setVisibility(View.INVISIBLE);
                                    String erroe = task.getException().getMessage();
                                    Toast.makeText(getActivity(),erroe,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else{
                Toast.makeText(getActivity(),"email or password not correct",Toast.LENGTH_SHORT).show();
            }
        }else
        {
            Toast.makeText(getActivity(),"email or password not correct",Toast.LENGTH_SHORT).show();
        }
    }

    private void checkinputs() {
        if(!TextUtils.isEmpty(sign_in_email.getText().toString())){
            if(!TextUtils.isEmpty(sign_in_password.getText().toString()) )
            {
                signin.setEnabled(true);
                signin.setTextColor(Color.rgb(255,255,255));
            }else{
                signin.setEnabled(false);
                signin.setTextColor(Color.argb(50,255,255,255));
            }
        }else{
            signin.setEnabled(false);
            signin.setTextColor(Color.argb(50,255,255,255));
            Toast.makeText(getActivity(),"Email is not valid",Toast.LENGTH_SHORT).show();
        }
    }
}
