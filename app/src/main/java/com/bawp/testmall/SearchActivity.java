package com.bawp.testmall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.bawp.testmall.Adapter.wishlist_adapter;
import com.bawp.testmall.model.wish_list_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private TextView textView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        textView = findViewById(R.id.textsearch);
        recyclerView = findViewById(R.id.recyclerviewsearch);
        searchView = findViewById(R.id.search);

        recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        final List<wish_list_model> list = new ArrayList<>();
        final List<String> ids = new ArrayList<>();
        final Adapter adapter = new Adapter(list,false);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                list.clear();
                ids.clear();

                final String[] tags = newText.toLowerCase().split(" ");
                list.clear();
                ids.clear();
                for(final String tag : tags ){
                    tag.trim();
                    FirebaseFirestore.getInstance().collection("PRODUCTS").whereArrayContains("tags",tag).get().
                            addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful())
                                    {
                                        for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                            wish_list_model model = new wish_list_model(documentSnapshot.get("product_image_1").toString()
                                                    , documentSnapshot.get("product_title").toString(),
                                                    (long) documentSnapshot.get("free_coupens"),
                                                    documentSnapshot.get("average_rating").toString(),
                                                    (long) documentSnapshot.get("total_ratings"),
                                                    documentSnapshot.get("product_price").toString(),
                                                    documentSnapshot.get("cutted_price").toString(),
                                                    (boolean) documentSnapshot.get("COD"));

                                            if(!ids.contains(model.getProduct_image()))
                                            {
                                                list.add(model);
                                                ids.add(model.getProduct_image());
                                            }
                                        }

                                            if(list.size()==0)
                                            {
                                                textView.setVisibility(View.VISIBLE);
                                                recyclerView.setVisibility(View.INVISIBLE);
                                            }
                                            else
                                            {
                                                textView.setVisibility(View.INVISIBLE);
                                                recyclerView.setVisibility(View.VISIBLE);
                                                adapter.getFilter().filter(newText);
                                            }

                                        }

                                    else
                                    {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(SearchActivity.this,error,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                return false;
            }
        });
    }
    class Adapter extends wishlist_adapter implements Filterable{

        public Adapter(List<wish_list_model> wish_list_modelList, Boolean wishlist) {
            super(wish_list_modelList, wishlist);
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    /// filter logic ///
                    return null;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    notifyDataSetChanged();

                }
            };
        }
    }
}
