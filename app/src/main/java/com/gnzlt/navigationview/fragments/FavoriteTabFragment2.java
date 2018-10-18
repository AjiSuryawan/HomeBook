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
import android.widget.TextView;

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
public class FavoriteTabFragment2 extends Fragment {
    View rootView;
    public RecyclerView recycler_view_list_film;
    public ProgressBar pgloading;
    public ArrayList<Film> listFilm = new ArrayList<>();
    public SectionListDataAdapter adapterAllTipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
        recycler_view_list_film = (RecyclerView) rootView.findViewById(R.id.recycler_view_list_film2);
        pgloading = (ProgressBar) rootView.findViewById(R.id.pgloading2);
        new AktualLoader2().execute();
        return rootView;
    }

    public class AktualLoader2 extends AsyncTask<Void, Void, JSONObject> {


        @Override
        protected void onPreExecute() {
            pgloading.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject jsonObject;
            try {
                Log.d("lala1", "doInBackground: ");
                String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=05d24d1094bc376832434c74ca08824f&language=en-US";
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
            Log.d("lala2", "doInBackground: ");
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
                    adapterAllTipe = new SectionListDataAdapter(rootView.getContext(), listFilm);
                    recycler_view_list_film.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
                    recycler_view_list_film.setAdapter(adapterAllTipe);
                    recycler_view_list_film.setNestedScrollingEnabled(false);
                    recycler_view_list_film.setVisibility(View.VISIBLE);
                    pgloading.setVisibility(View.GONE);
                } catch (Exception ignored) {
                    Log.d("lalaerror", "doInBackground: "+ignored.toString());
                }
            } else {
            }
            super.onPostExecute(jsonObject);
        }
    }

}
