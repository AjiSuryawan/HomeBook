package com.gnzlt.navigationview;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.gnzlt.navigationview.fragments.FavoriteTabFragment;
import com.gnzlt.navigationview.fragments.SectionListDataAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyBroadcastReceiver2 extends BroadcastReceiver {
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        System.out.println("broadcast2");
        new AktualLoader().execute();
    }


    @SuppressLint("StaticFieldLeak")
    public class AktualLoader extends AsyncTask<Void, Void, JSONObject> {


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject jsonObject;
            try {
                String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=05d24d1094bc376832434c74ca08824f&language=en-US";
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
                    JSONArray aktualData = jsonObject.getJSONArray("results");
                    for (int x = 0; x < aktualData.length(); x++) {
                        String dateku=aktualData.getJSONObject(x).getString("release_date");
                        String name=aktualData.getJSONObject(x).getString("title");
                        try {
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            Date date1 = Calendar.getInstance().getTime();
                            String formattedDate = df.format(date1);
                            System.out.println("Current Date => " + formattedDate);
                            Date date2;
                            //dibawah dari getUrl
                            date2 = df.parse(dateku);
                            if (date1.compareTo(date2) == 0) {
                                //here
                                System.out.println("Date1 is equal to Date2");
                                try {
                                    NotificationCompat.Builder builder =
                                            new NotificationCompat.Builder(context)
                                                    .setSmallIcon(R.drawable.ic_add_24dp)
                                                    .setContentTitle(name)
                                                    .setContentText("Hari ini "+name+" release");
                                    Intent notificationIntent = new Intent(context, MainActivity.class);
                                    PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                                            PendingIntent.FLAG_UPDATE_CURRENT);
                                    builder.setContentIntent(contentIntent);
                                    builder.setAutoCancel(true);
                                    // Add as notification
                                    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                    manager.notify(1, builder.build());
                                }catch (Exception e){
                                    System.out.println("errorku "+e.toString());
                                }
                            }else  {

                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception ignored) {
                    Log.d("lalaku", "onPostExecute: "+ignored.toString());
                }
            } else {
            }
            super.onPostExecute(jsonObject);
        }
    }
}
