package com.gnzlt.navigationview.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gnzlt.navigationview.Database.MahasiswaHelper;
import com.gnzlt.navigationview.Model.MahasiswaModel;
import com.gnzlt.navigationview.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class MyFav extends Fragment {
    View rootView;
    public RecyclerView recycler_view_list_film;
    public ProgressBar pgloading;
    public ArrayList<MahasiswaModel> listFilm = new ArrayList<>();
    public SectionListDataAdapter3 adapterAllTipe;
    MahasiswaHelper mahasiswaHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
        recycler_view_list_film = (RecyclerView) rootView.findViewById(R.id.recycler_view_list_film2);
        pgloading = (ProgressBar) rootView.findViewById(R.id.pgloading2);
        pgloading.setVisibility(View.GONE);
        listFilm = new ArrayList<>();
        mahasiswaHelper = new MahasiswaHelper(rootView.getContext());
        mahasiswaHelper.open();
        listFilm = mahasiswaHelper.getAllData();
        recycler_view_list_film.setHasFixedSize(true);
        adapterAllTipe = new SectionListDataAdapter3(rootView.getContext(), listFilm);
        recycler_view_list_film.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view_list_film.setAdapter(adapterAllTipe);
        recycler_view_list_film.setNestedScrollingEnabled(false);
        recycler_view_list_film.setVisibility(View.VISIBLE);
        return rootView;
    }
}
