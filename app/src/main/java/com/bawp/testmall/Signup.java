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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Signup extends Fragment {
    private TextView Already_have_account;
    private FrameLayout parent_frame;
    private EditText Username;
    private EditText sign_up_email;
    private EditText sign_up_password;
    private EditText sign_up_conform_password;
    private Button signup;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private String Email_pattren = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private FirebaseFirestore firebaseFirestore;

    public Signup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        Already_have_account = view.findViewById(R.id.Signup_to_Signin);
        parent_frame = getActivity().findViewById(R.id.frame_Layout);
        Username = view.findViewById(R.id.Sign_up_username);
        sign_up_email = view.findViewById(R.id.Sign_up_email);
        sign_up_password = view.findViewById(R.id.Sign_up_password);
        sign_up_conform_password = view.findViewById(R.id.Sign_up_conform_password);
        signup = view.findViewById(R.id.Singn_up);
        progressBar = view.findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Already_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.frame_Layout);
                if(fragment!=null){
                    fragment = new Signin();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_Layout,fragment).commit();
                }
            }
        });

        sign_up_email.addTextChangedListener(new TextWatcher() {
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
        Username.addTextChangedListener(new TextWatcher() {
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
        sign_up_password.addTextChangedListener(new TextWatcher() {
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
        sign_up_conform_password.addTextChangedListener(new TextWatcher() {
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
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkemailandpassword();
            }
        });
    }
    private void checkemailandpassword() {
        if(sign_up_email.getText().toString().matches(Email_pattren)){
            if(sign_up_password.getText().toString().equals(sign_up_conform_password.getText().toString())){

                progressBar.setVisibility(View.VISIBLE);
                signup.setEnabled(false);
                signup.setTextColor(Color.argb(50,255,255,255));
                firebaseAuth.createUserWithEmailAndPassword(sign_up_email.getText().toString(),sign_up_password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Map<String,Object> userdata = new HashMap<>();
                                    userdata.put("fullname",Username.getText().toString());
                                    firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                                            .set(userdata)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        CollectionReference userDataReference = firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                                                                .collection("USER_DATA");

                                                        Map<String, Object> wishList = new HashMap<>();
                                                        wishList.put("list_size", (long) 0);

                                                        Map<String, Object> ratingsMap = new HashMap<>();
                                                        ratingsMap.put("list_size", (long) 0);

                                                        Map<String, Object> cartMap = new HashMap<>();
                                                        cartMap.put("list_size", (long) 0);

                                                        Map<String, Object> NotificationMap = new HashMap<>();
                                                        NotificationMap.put("list_size", (long) 0);

                                                        final List<String > documentNames = new ArrayList<>();
                                                        documentNames.add("MY_WISHLIST");
                                                        documentNames.add("MY_RATINGS");
                                                        documentNames.add("MY_CART");
                                                        documentNames.add("MY_Notifications");

                                                       List<Map<String,Object>> documentFields = new ArrayList<>();
                                                       documentFields.add(wishList);
                                                        documentFields.add(ratingsMap);
                                                        documentFields.add(cartMap);
                                                        documentFields.add(NotificationMap);

                                                        for (int x=0; x < documentNames.size(); x++){
                                                            final int finalX = x;
                                                            userDataReference.document(documentNames.get(x))
                                                                    .set(documentFields.get(x)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){
                                                                        if(finalX ==documentNames.size()-1) {
                                                                            Intent intent = new Intent(getContext(), main.class);
                                                                            startActivity(intent);
                                                                            getActivity().finish();
                                                                        }
                                                                    }
                                                                    else{
                                                                        progressBar.setVisibility(View.INVISIBLE);
                                                                        signup.setEnabled(true);
                                                                        signup.setTextColor(Color.rgb(255,255,255));
                                                                        String error = task.getException().getMessage();
                                                                        Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                        }

//                                                        firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
//                                                                .collection("USER_DATA").document("MY_WISHLIST")
//                                                                .set(listSize).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                            @Override
//                                                            public void onComplete(@NonNull Task<Void> task) {
////                                                                if(task.isSuccessful()){
////                                                                    Intent intent = new Intent(getContext(),main.class);
////                                                                    startActivity(intent);
////                                                                    getActivity().finish();
////                                                                }
////                                                                else{
////                                                                    progressBar.setVisibility(View.INVISIBLE);
////                                                                    signup.setEnabled(true);
////                                                                    signup.setTextColor(Color.rgb(255,255,255));
////                                                                    String error = task.getException().getMessage();
////                                                                    Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
////                                                                }
//                                                            }
//                                                        });
                                                    }
                                                    else {
//                                                        progressBar.setVisibility(View.INVISIBLE);
//                                                        signup.setEnabled(true);
//                                                        signup.setTextColor(Color.rgb(255,255,255));
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
//                                    Intent intent = new Intent(getActivity(),main.class);
//                                    startActivity(intent);
//                                    getActivity().finish();
                                }else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signup.setEnabled(true);
                                    signup.setTextColor(Color.rgb(255,255,255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else{
                sign_up_conform_password.setError("password doesn't match!");
            }

        }else{
            sign_up_email.setError("invalid Email !");
        }
    }

    private void checkinputs() {
        if(!TextUtils.isEmpty(sign_up_email.getText())){
            if (!TextUtils.isEmpty(Username.getText())){
                if (!TextUtils.isEmpty(sign_up_password.getText()) && sign_up_password.length()>8) {
                    if (!TextUtils.isEmpty(sign_up_conform_password.getText())){
                        signup.setEnabled(true);
                        signup.setTextColor(Color.rgb(255,255,255));

                    }
                    else {
                        signup.setEnabled(false);
                        signup.setTextColor(Color.argb(50,255,255,255));
                    }
                }
                else {
                    signup.setEnabled(false);
                    signup.setTextColor(Color.argb(50,255,255,255));
                    sign_up_password.setError("password leangth must be greater than 8");
                }
            }
            else {
                signup.setEnabled(false);
                signup.setTextColor(Color.argb(50,255,255,255));
            }
        }
        else {
            signup.setEnabled(false);
            signup.setTextColor(Color.argb(50,255,255,255));
        }
    }
}
