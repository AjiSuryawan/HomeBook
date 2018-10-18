package com.example.myapplication2;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication2.Model.MahasiswaModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recycler_view_list_film;
    public ProgressBar pgloading;
    public ArrayList<MahasiswaModel> listFilm = new ArrayList<>();
    public SectionListDataAdapter3 adapterAllTipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String URL = "content://com.gnzlt.navigationview.StudentsProvider";

        Uri students = Uri.parse(URL);
        Cursor c = managedQuery(students, null, null, null, "name");


        ArrayList<MahasiswaModel> mStringList= new ArrayList<>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            MahasiswaModel m= new MahasiswaModel();
            m.setName(c.getString(c.getColumnIndex("name")));
            m.setNim(c.getString(c.getColumnIndex("grade")));
            m.setUrl(c.getString(c.getColumnIndex("urlku")));
            mStringList.add(m);
        }

        Log.d("makan", "onCreate: "+mStringList.size());
        recycler_view_list_film = (RecyclerView) findViewById(R.id.recycler_view_list_film2);
        pgloading = (ProgressBar) findViewById(R.id.pgloading2);
        pgloading.setVisibility(View.GONE);
        listFilm = new ArrayList<>();

        listFilm = mStringList;
        recycler_view_list_film.setHasFixedSize(true);
        adapterAllTipe = new SectionListDataAdapter3(MainActivity.this, listFilm);
        recycler_view_list_film.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        recycler_view_list_film.setAdapter(adapterAllTipe);
        recycler_view_list_film.setNestedScrollingEnabled(false);
        recycler_view_list_film.setVisibility(View.VISIBLE);


    }
}
