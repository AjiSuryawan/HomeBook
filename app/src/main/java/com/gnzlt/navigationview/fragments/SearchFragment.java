package com.gnzlt.navigationview.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

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

public class SearchFragment extends Fragment {
    View view;
    public RecyclerView recycler_view_list_film;
    public ProgressBar pgloading;
    Button btnsearch;
    TextInputLayout textInputLayout;

    public ArrayList<Film> listFilm = new ArrayList<>();
    public SectionListDataAdapter adapterAllTipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home, container, false);
        recycler_view_list_film = (RecyclerView) view.findViewById(R.id.recycler_view_list_film);
        pgloading = (ProgressBar) view.findViewById(R.id.pgloading);
        textInputLayout = (TextInputLayout) view.findViewById(R.id.textInputLayout);
        btnsearch = (Button) view.findViewById(R.id.btnsearch);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AktualLoader().execute();
            }
        });
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    public class AktualLoader extends AsyncTask<Void, Void, JSONObject> {


        @Override
        protected void onPreExecute() {
            pgloading.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject jsonObject;
            try {
                String url = "https://api.themoviedb.org/3/search/movie?api_key=05d24d1094bc376832434c74ca08824f&language=en-US&query="+textInputLayout.getEditText().getText().toString();
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        inputStream, "iso-8859-1"
                ), 8);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                inputStream.close();
                String json = stringBuilder.toString();
                jsonObject = new JSONObject(json);
            } catch (Exception e) {
                jsonObject = null;
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                try {
                    listFilm = new ArrayList<>();
                    JSONArray aktualData = jsonObject.getJSONArray("results");
                    for (int x = 0; x < aktualData.length(); x++) {
                        Film Film = new Film();
                        Film.setId(aktualData.getJSONObject(x).getInt("id"));
                        Film.setTitle(aktualData.getJSONObject(x).getString("title"));
                        Film.setPoster_path(aktualData.getJSONObject(x).getString("poster_path"));
                        Film.setOverview(aktualData.getJSONObject(x).getString("overview"));
                        Film.setRelease_date(aktualData.getJSONObject(x).getString("release_date"));
                        listFilm.add(Film);
                    }
                    recycler_view_list_film.setHasFixedSize(true);
                    adapterAllTipe = new SectionListDataAdapter(view.getContext(), listFilm);
                    recycler_view_list_film.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_list_film.setAdapter(adapterAllTipe);
                    recycler_view_list_film.setNestedScrollingEnabled(false);
                    recycler_view_list_film.setVisibility(View.VISIBLE);
                    pgloading.setVisibility(View.GONE);
                } catch (Exception ignored) {
                    Log.d("lalaku2", "onPostExecute: "+ignored.toString());
                }
            } else {
            }
            super.onPostExecute(jsonObject);
        }
    }
}
