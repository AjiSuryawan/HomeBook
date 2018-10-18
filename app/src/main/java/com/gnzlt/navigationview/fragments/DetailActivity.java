package com.gnzlt.navigationview.fragments;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gnzlt.navigationview.Database.MahasiswaHelper;
import com.gnzlt.navigationview.Model.MahasiswaModel;
import com.gnzlt.navigationview.R;
import com.gnzlt.navigationview.StudentsProvider;

public class DetailActivity extends AppCompatActivity {
    Bundle extras;
    ImageView ivgambar;
    TextView tvjudul;
    TextView tvdesc;
    String url;
    String tanggal;
    String judul;
    String desc;
    Button btn;
    MahasiswaHelper mahasiswaHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ivgambar=(ImageView)findViewById(R.id.ivgambar);
        btn=(Button) findViewById(R.id.btnfav);
        mahasiswaHelper = new MahasiswaHelper(DetailActivity.this);
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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new myfavasyntask().execute();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class myfavasyntask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean suksesLoad=true;
            mahasiswaHelper.open();
            mahasiswaHelper.beginTransaction();
            MahasiswaModel m=new MahasiswaModel(judul,desc,url,tanggal);

            ContentValues values = new ContentValues();
            values.put(StudentsProvider.NAME, ""+judul);

            values.put(StudentsProvider.GRADE, ""+desc);
            values.put(StudentsProvider.URLGAMBAR, ""+url);

            Uri uri = getContentResolver().insert(
                    StudentsProvider.CONTENT_URI, values);
            Log.d("kontenku", "doInBackground: "+uri.toString());
            mahasiswaHelper.insertTransaction(m);
            mahasiswaHelper.setTransactionSuccess();
            mahasiswaHelper.endTransaction();
            mahasiswaHelper.close();
            return suksesLoad;
        }

        @Override
        protected void onPostExecute(Boolean suksesLoad) {
            btn.setEnabled(false);
            super.onPostExecute(suksesLoad);
        }
    }
}