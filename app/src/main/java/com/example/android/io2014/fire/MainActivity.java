package com.example.android.io2014.fire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.android.io2014.Add_garage;
import com.example.android.io2014.Config;
import com.example.android.io2014.Model;
import com.example.android.io2014.R;
import com.example.android.io2014.login.SessionManager;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Garage_Adapter adapter;
    private ArrayList<Model> productList;
    private ProgressDialog dialog;
    FloatingActionButton fab;
    EditText search;

    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        fab=(FloatingActionButton)findViewById(R.id.fab);
         search=(EditText)findViewById(R.id.search);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Add_garage.class));
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recycler);

        productList = new ArrayList<>();
        adapter = new Garage_Adapter(this, productList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);
        Firebase.setAndroidContext(this);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Model post = productList.get(position);
                Intent intent=new Intent(MainActivity.this, Details.class);
                intent.putExtra("name",post.getGaragename());
                intent.putExtra("address",post.getAddress());
                intent.putExtra("services",post.getService());
                intent.putExtra("contact",post.getContact());
                intent.putExtra("specifications",post.getSpecification());
                intent.putExtra("description",post.getDescription());
                intent.putExtra("logitude",post.getLogitude());
                intent.putExtra("latitude",post.getLatitude());
                intent.putExtra("image",post.getThumbnail());
                startActivity(intent);

            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        Firebase fire = new Firebase(Config.FIREBASE_PRODUCTS_URL);
        dialog = new ProgressDialog(this);
        dialog.setMessage("loading garages...");
        dialog.show();
        fire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(int i=productList.size()-1; i>=0; i--)
                    productList.remove(i);
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Model newproduct = postSnapshot.getValue(Model.class);
                    productList.add(newproduct);
                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        addTextListener();
    }

    public void addTextListener(){

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                ArrayList<Model> filterdNames = new ArrayList<>();

                for (int i = 0; i < productList.size(); i++) {

                    final String text = productList.get(i).getSpecification().toLowerCase();
                    if (text.contains(query)) {

                        filterdNames.add(productList.get(i));
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter = new Garage_Adapter(MainActivity.this, filterdNames);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();  // data set changed
            }
        });
    }

}
