package com.bawp.testmall;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawp.testmall.Adapter.Cart_Adapter;
import com.bawp.testmall.Data.DBqueries;
import com.bawp.testmall.model.cart_item_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DelevaryActivity extends AppCompatActivity implements PaymentResultListener {

    private RecyclerView delevary_recycler;
    private Button changeoraddAddress;
    private Button continueButton;
    private ConstraintLayout ship;
    private Dialog payment_dialog;
    private TextView totalAmount;
    private FirebaseFirestore firebaseFirestore;
    ImageView paytm_btn;
    ImageView COD;
    String delopt;
    TextView name;
    TextView Shipping_Address;
    TextView Pincodee;
    int Total;
    private ConstraintLayout OrderConformationLayout;
//    private  TextView Ordertextid;
//    private ImageView cnt_btn;
    String Order_id = UUID.randomUUID().toString().substring(0,28);
    public static List<cart_item_model>CartItemList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delevary);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delevary");
        firebaseFirestore = FirebaseFirestore.getInstance();
        delevary_recycler = findViewById(R.id.delevary_recycle);
        Checkout.preload(getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        delevary_recycler.setLayoutManager(layoutManager);
        changeoraddAddress = findViewById(R.id.change_or_add_address_btn);
        continueButton = findViewById(R.id.cart_continue_btn);
        totalAmount = findViewById(R.id.Total_cart_amount);
        name = findViewById(R.id.full_name);
        Pincodee = findViewById(R.id.pincode);
        Shipping_Address = findViewById(R.id.Address);
        String city = getIntent().getStringExtra("city");
        String full_address =getIntent().getStringExtra("Full_Address");
        String pincode = getIntent().getStringExtra("pincode");

        name.setText(city);
        Pincodee.setText(pincode);

        Shipping_Address.setText(full_address);
//        OrderConformationLayout = findViewById(R.id.Oredre_conformation_layput);
//        cnt_btn = findViewById(R.id.continue_shop_btn);
//        Ordertextid = findViewById(R.id.Order_id);
//        OrderConformationLayout.setVisibility(View.GONE);

        if(DBqueries.cartItemModelList.size()==0) {
            DBqueries.cartList.clear();
            DBqueries.loadCartList(DelevaryActivity.this,true);
        }

//        totalAmount.setText(cart_item_model.getTotal_amount());

        /// payment Dialog
        payment_dialog = new Dialog(DelevaryActivity.this);
        payment_dialog.setContentView(R.layout.payment_method);
        payment_dialog.setCancelable(true);
        payment_dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.border_background));
        payment_dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paytm_btn = payment_dialog.findViewById(R.id.paytm);
        COD = payment_dialog.findViewById(R.id.codbtn);
//        List<cart_item_model> cart_item_modelList = new ArrayList<>();

//        cart_item_modelList.add(new cart_item_model(0,R.mipmap.phone,"Vivo v19",2,"Rs.49999/-","Rs.59999/-",1,0,0));
//        cart_item_modelList.add(new cart_item_model(0,R.mipmap.phone,"Vivo v19",2,"Rs.49999/-","Rs.59999/-",1,0,0));
//        cart_item_modelList.add(new cart_item_model(0,R.mipmap.phone,"Vivo v19",2,"Rs.49999/-","Rs.59999/-",1,0,0));
//        cart_item_modelList.add(new cart_item_model(1,"Vivo v19","Rs.9999/-","Free","Rs.169999/-","Rs.9999/-"));

        Cart_Adapter cart_adapter = new Cart_Adapter(CartItemList);
        delevary_recycler.setAdapter(cart_adapter);
        changeoraddAddress.setVisibility(View.GONE);
        Total = cart_adapter.getTotalAmount();
        totalAmount.setText("Rs.99");
        cart_adapter.notifyDataSetChanged();
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_dialog.show();
            }
        });

        paytm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delopt="paytm";
                placeOrderDetails();
                startPayment();
            }
        });

        COD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delopt="COD";
                placeOrderDetails();
                Toast.makeText(DelevaryActivity.this,"sucessfull",Toast.LENGTH_SHORT).show();
                Map<String,Object>  UpdateStatus = new HashMap<>();
                UpdateStatus.put("PaymentStatus","not paid");
                UpdateStatus.put("OrderStatus","Ordered");
                firebaseFirestore.collection("ORDERS").document(Order_id).update(UpdateStatus)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Map<String,Object> User_orders = new HashMap<>();
                                    User_orders.put("order_id",Order_id);
                                    firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_ORDERS")
                                            .document(Order_id).set(User_orders).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(DelevaryActivity.this,"sucessfull",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(DelevaryActivity.this,Orderconform.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(DelevaryActivity.this,"Failed to update",Toast.LENGTH_SHORT);
                                            }
                                        }
                                    });
                                }else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(DelevaryActivity.this,error,Toast.LENGTH_LONG).show();
                                }
                            }
                        });
//                Intent intent = new Intent(DelevaryActivity.this,Orderconform.class);
//                startActivity(intent);
//                finish();
            }
        });

//        paytm_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                payment_dialog.dismiss();
//                if (ContextCompat.checkSelfPermission(DelevaryActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(DelevaryActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
//                }
//
//                final String MID = "LLBbCl39463909996997";
//                final String CID = FirebaseAuth.getInstance().getUid();
//                final String Order_id = UUID.randomUUID().toString().substring(0,28);
//                String URL = "http://hemanth1711.epizy.com/sample.php?i=1";
//                final String callBackUrl="https://pguat.paytm.com/paytmchecksum/paytmcallback.jsp";
//
//                RequestQueue requestQueue = Volley.newRequestQueue(DelevaryActivity.this);
//
//
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if(jsonObject.has("CHECKSUMHASH"))
//                            {
//                                String CHECKSUMHASH = jsonObject.getString("CHECKSUMHASH");
//
//                                PaytmPGService paytmPGService = PaytmPGService.getStagingService("");
//                                Map<String, String> paramMap = new HashMap<String,String>();
//                                paramMap.put( "MID" , MID);
//                                paramMap.put( "ORDER_ID" , Order_id);
//                                paramMap.put( "CUST_ID" , CID);
//                                paramMap.put( "CHANNEL_ID" , "WAP");
//                                paramMap.put( "TXN_AMOUNT" ,"99");
//                                paramMap.put( "WEBSITE" , "WEBSTAGING");
//                                paramMap.put( "INDUSTRY_TYPE_ID" , "Retail");
//                                paramMap.put( "CALLBACK_URL", callBackUrl);
//                                paramMap.put("CHECKSUMHASH",CHECKSUMHASH);
//
//                                PaytmOrder order = new PaytmOrder((HashMap<String, String>) paramMap);
//                                paytmPGService.initialize(order,null);
//                                paytmPGService.startPaymentTransaction(DelevaryActivity.this, true, true, new PaytmPaymentTransactionCallback() {
//                                    @Override
//                                    public void onTransactionResponse(Bundle inResponse) {
//                                        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
//                                    }
//
//                                    @Override
//                                    public void networkNotAvailable() {
//                                        Toast.makeText(getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();
//                                    }
//
//                                    @Override
//                                    public void clientAuthenticationFailed(String inErrorMessage) {
//                                        Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
//                                    }
//
//                                    @Override
//                                    public void someUIErrorOccurred(String inErrorMessage) {
//                                        Toast.makeText(getApplicationContext(), "UI Error " + inErrorMessage , Toast.LENGTH_LONG).show();
//                                    }
//
//                                    @Override
//                                    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
//                                        Toast.makeText(getApplicationContext(), "Unable to load webpage " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
//                                    }
//
//                                    @Override
//                                    public void onBackPressedCancelTransaction() {
//                                        Toast.makeText(getApplicationContext(), "Transaction cancelled" , Toast.LENGTH_LONG).show();
//                                    }
//
//                                    @Override
//                                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
//                                        Toast.makeText(getApplicationContext(), "Transaction cancled " + inResponse.toString(), Toast.LENGTH_LONG).show();
//                                    }
//                                });
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(DelevaryActivity.this,"Transaction failed",Toast.LENGTH_SHORT).show();
//                    }
//                }){
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> paramMap = new HashMap<String,String>();
//                        paramMap.put( "MID" , MID);
//                        paramMap.put( "ORDER_ID" , Order_id);
//                        paramMap.put( "CUST_ID" , CID);
//                        paramMap.put( "CHANNEL_ID" , "WAP");
//                        paramMap.put( "TXN_AMOUNT" ,"99");
//                        paramMap.put( "WEBSITE" , "WEBSTAGING");
//                        paramMap.put( "INDUSTRY_TYPE_ID" , "Retail");
//                        paramMap.put( "CALLBACK_URL", callBackUrl);
//                        return paramMap;
//                    }
//                };
//
//                requestQueue.add(stringRequest);
//
//            }
//        });
    }

    private void startPayment() {
//        checkout.setKeyID("<YOUR_KEY_ID>");
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
//        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();
            options.put("name", "paytm");
            options.put("description", "Reference No. #123456");
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order id");//from response of step 3.
//            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount",  "9900");//pass amount in currency subunits
//            options.put("prefill.email", "gaurav.kumar@example.com");
//            options.put("prefill.contact","9988776655");
            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("good", "Error in starting Razorpay Checkout", e);
        }

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPaymentSuccess(String s) {
        Map<String,Object>  UpdateStatus = new HashMap<>();
        UpdateStatus.put("PaymentStatus","paid");
        UpdateStatus.put("OrderStatus","Ordered");
        firebaseFirestore.collection("ORDERS").document(Order_id).update(UpdateStatus)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Map<String,Object> User_orders = new HashMap<>();
                            User_orders.put("order_id",Order_id);
                            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_ORDERS")
                                    .document(Order_id).set(User_orders).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(DelevaryActivity.this,"sucessfull",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(DelevaryActivity.this,Orderconform.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(DelevaryActivity.this,"Failed to update",Toast.LENGTH_SHORT);
                                    }
                                }
                            });
                        }else {
                            String error = task.getException().getMessage();
                            Toast.makeText(DelevaryActivity.this,error,Toast.LENGTH_LONG).show();
                        }
                    }
                });
//        Toast.makeText(this,"sucessfull",Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(DelevaryActivity.this,Orderconform.class);
//        startActivity(intent);
//        finish();
//        Ordertextid.setText("Order Id: "+ Order_id);
//        OrderConformationLayout.setVisibility(View.VISIBLE);
//        cnt_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show();
//        OrderConformationLayout.setVisibility(View.INVISIBLE);
    }

    private void placeOrderDetails(){
        String User = FirebaseAuth.getInstance().getUid();
        for(cart_item_model cartItemModel : CartItemList) {

            if(cartItemModel.getType() == cart_item_model.cart_item)
            {
                String city = getIntent().getStringExtra("city");
                String full_address =getIntent().getStringExtra("Full_Address");
                String pincode = getIntent().getStringExtra("pincode");
                Map<String,Object> order_detail = new HashMap<>();
                order_detail.put("OrderId",Order_id);
                order_detail.put("productID",cartItemModel.getProduct_ID());
                order_detail.put("UserID",User);
                order_detail.put("product image",cartItemModel.getProduct_image());
                order_detail.put("product title",cartItemModel.getProduct_title());
//                order_detail.put("ProductQty",cartItemModel.getProduct_Qty());
//                order_detail.put("cuttedPrice",cartItemModel.getCutted_price());
                order_detail.put("productPrice",cartItemModel.getProduct_price());
//                order_detail.put("cuponID",cartItemModel.getFree_coupons());
//                order_detail.put("DiscountPrice",cartItemModel.getOffers_applied());
                order_detail.put("Date", FieldValue.serverTimestamp());
                order_detail.put("PaymentMode",delopt);
                order_detail.put("Address",full_address);
                order_detail.put("FullName", city);
                order_detail.put("Pincode",pincode);
                order_detail.put("PaymentStatus","not paid");
                order_detail.put("OrderStatus","Ordered");
                firebaseFirestore.collection("ORDERS").document(Order_id).collection("order_items")
                        .document(cartItemModel.getProduct_ID())
                        .set(order_detail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            String error = task.getException().getMessage();
                            Toast.makeText(DelevaryActivity.this,error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else{
                Map<Object,Object> order_detail= new HashMap<>();
                order_detail.put("TotalItems",cartItemModel.getTotal_items());
                order_detail.put("TotalItems price",cartItemModel.getTotalItemPrice());
                order_detail.put("Delivary Price",cartItemModel.getDelivaryPrice());
                order_detail.put("Total Amount",cartItemModel.getTotalAmount());
                order_detail.put("Saved Amount",cartItemModel.getSavedAmount());
                order_detail.put("PaymentStatus","not paid");
                order_detail.put("OrderStatus","cancelled");
                firebaseFirestore.collection("ORDERS").document(Order_id)
                        .set(order_detail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(DelevaryActivity.this,error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }
    }
}
