package com.example.android.io2014.fire;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.io2014.R;


import java.io.IOException;

public class Details extends AppCompatActivity {
    private TextView txt,txt1,txt2,txt3,txt4,txt5;
    private ImageView img;
    private EditText lat,log;
    FloatingActionButton fab,fab2;
    Double l,lg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab2=(FloatingActionButton)findViewById(R.id.fab2);
        txt=(TextView)findViewById(R.id.nameg);
        txt1=(TextView)findViewById(R.id.service);
        txt2=(TextView)findViewById(R.id.description);
        txt3=(TextView)findViewById(R.id.contact);
        txt4=(TextView)findViewById(R.id.address);
        txt5=(TextView)findViewById(R.id.specification);
        img=(ImageView)findViewById(R.id.photo);

        lat=(EditText)findViewById(R.id.lat);
        log=(EditText)findViewById(R.id.log);

        Intent getImage = getIntent();
        String gettingImageUrl = getImage.getStringExtra("image");
        decodeimage(gettingImageUrl,(ImageView) findViewById(R.id.card_photo_3).findViewById(R.id.photo));

        //Glide.with(DetailsActivity.this).load(gettingImageUrl).into(img);
//        img.setImageResource(getIntent().getIntExtra("image_id",00));
        txt.setText(getIntent().getStringExtra("name"));
        txt1.setText(getIntent().getStringExtra("services"));
        txt2.setText(getIntent().getStringExtra("description"));
        txt3.setText(getIntent().getStringExtra("contact"));
        txt4.setText(getIntent().getStringExtra("address"));
        txt5.setText(getIntent().getStringExtra("specifications"));

        lat.setText(getIntent().getStringExtra("latitude"));
        log.setText(getIntent().getStringExtra("logitude"));


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+getIntent().getStringExtra("contact")));
                startActivity(intent);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?f=d&hl=en&daddr="+Double.parseDouble(lat.getText().toString())+","+Double.parseDouble( log.getText().toString());
                Intent intent2 = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent2);
            }
        });
    }
    public void decodeimage(String url,ImageView img){
        if (!url.contains("http")) {
            try {
                Bitmap imageBitmap = decodeFromFirebaseBase64(url);
                img.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            Glide.with(Details.this)
                    .load(url)
                    .into(img);
        }
    }
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    @Override
    public void onBackPressed() {
        // Write your code here

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


}
