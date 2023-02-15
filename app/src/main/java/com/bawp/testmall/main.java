package com.bawp.testmall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawp.testmall.Data.DBqueries;
import com.bawp.testmall.model.Home_page_model;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class main extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener{
    private FrameLayout frame;
    private ImageView no_internet;
    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT= 1;
    private static final int MYORDER_FRAGMENT= 2;
    private static final int MYWISHLISTFRAGMENT= 3;
    private static final int MYACCOUNTFRAGMENT= 4;

    private NavigationView navigationView;

    private static int currentFragment;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        frame = findViewById(R.id.Frame_layout);
//        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        navigationView.getMenu().getItem(0).setChecked(true);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        Fragment fragment = new MainFragment();
        no_internet = findViewById(R.id.no_internet_conn);
        updateNavHeader();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!= null && networkInfo.isConnected()==true) {
            no_internet.setVisibility(View.GONE);
            setFragment(new MainFragment(), HOME_FRAGMENT);
        }
        else
        {
            Glide.with(this).load(R.mipmap.no_internet).into(no_internet);
            no_internet.setVisibility(View.VISIBLE);
        }

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(frame.getId(),fragment)
//                .commit();

        navigationView.getMenu().getItem(0).setChecked(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();


                if(id==R.id.nav_my_mall)
                {
                    invalidateOptionsMenu();
                    setFragment(new MainFragment(), HOME_FRAGMENT);
                }
                else if(id == R.id.nav_orders){
                    setFragment(new My_ordersFragment(),MYORDER_FRAGMENT);
                }
                else if(id == R.id.nav_cart)
                {
                    setFragment(new My_cart_Fragment(),CART_FRAGMENT);
                }
                else if(id == R.id.whishlist) {
                    setFragment(new My_wishListFragment(), MYWISHLISTFRAGMENT);
                }
//                else if(id == R.id.account){
//                    setFragment(new My_AccountFragment(),MYACCOUNTFRAGMENT);
//                }
//                else if(id == R.id.nav_signout){
//                    Fragment fragment1 = new Signup();
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(frame.getId(),fragment1)
//                .commit();
//        navigationView.setVisibility(View.GONE);
//                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if(currentFragment == HOME_FRAGMENT)
            {
                super.onBackPressed();
            }
            else{
                invalidateOptionsMenu();
                setFragment(new MainFragment(), HOME_FRAGMENT);
                navigationView.getMenu().getItem(0).setChecked(true);

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(currentFragment == HOME_FRAGMENT) {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        DBqueries.loadNotificationdata(false);
        super.onStart();
    }

    @Override
    protected void onPause() {
        DBqueries.loadNotificationdata(true);
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.search){
            Intent searchIntent = new Intent(this,SearchActivity.class);
            startActivity(searchIntent);
            return true;
        }
        else if(id == R.id.notifications)
        {
            Intent notify = new Intent(this,NotificationActivity.class);
            startActivity(notify);
            return true;
        }
        else if(id == R.id.cart){
            mycart();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mycart() {
        invalidateOptionsMenu();
        Fragment fragment = new My_cart_Fragment();
        setFragment(new My_cart_Fragment(), CART_FRAGMENT);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(frame.getId(),fragment)
//                .commit();
        navigationView.getMenu().getItem(2).setChecked(true);
    }

//    @Override
//    public boolean onNavigationItemSelected( MenuItem item) {
//        int id = item.getItemId();
//        if(id == R.id.nav_orders)
//        {
//            setFragment(new MainFragment(),HOME_FRAGMENT);
//
//        }
//        else if(id == R.id.nav_myrewards){
//
//        }
//        else if(id == R.id.nav_cart){
//            mycart();
//
//        }
//        else if(id == R.id.whishlist){
//
//        }
//        else if(id == R.id.account){
//
//        }
//
//        DrawerLayout drawer= findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//
//    }

    private void setFragment(Fragment fragment,int fragmentnumber)
    {
        currentFragment = fragmentnumber;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(frame.getId(),fragment)
                .commit();
    }

    public void updateNavHeader(){
       navigationView = findViewById(R.id.nav_view);
       View headerView = navigationView.getHeaderView(0);
        TextView userEmail = headerView.findViewById(R.id.current_email);
        final TextView userstatus = headerView.findViewById(R.id.textView);
        userEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).get().
                addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                           String name =  documentSnapshot.get("fullname").toString().trim();
                            userstatus.setText("user Loged as "+ name);
                        }
                        else {
                            String error = task.getException().getMessage();
                            Toast.makeText(main.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
