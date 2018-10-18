package com.example.myapplication2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DetailActivity extends AppCompatActivity {
    Bundle extras;
    ImageView ivgambar;
    TextView tvjudul;
    TextView tvdesc;
    String url;
    String tanggal;
    String judul;
    String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ivgambar=(ImageView)findViewById(R.id.ivgambar);

        tvjudul=(TextView)findViewById(R.id.tvjudul);
        tvdesc=(TextView)findViewById(R.id.tvdesc);
        extras = getIntent().getExtras();
        if(extras == null) {
        } else {
            judul=extras.getString("title");
            desc=extras.getString("desc");
            url=extras.getString("img");
            tanggal=extras.getString("tanggal");
        }
        Log.d("gbr", "onCreate: "+url);
        tvjudul.setText(judul);
        tvdesc.setText(desc);
        Glide.with(getApplicationContext())
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(ivgambar);
    }
}