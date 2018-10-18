package com.gnzlt.navigationview.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gnzlt.navigationview.R;

import java.util.ArrayList;

public class SectionListDataAdapter2 extends RecyclerView.Adapter<SectionListDataAdapter2.SingleItemRowHolder> {

    private View view;
    ArrayList<Film> articleFilm;
    Context context;

    public SectionListDataAdapter2(Context context, ArrayList<Film> articleFilm) {
        Log.d("lala3", "doInBackground: ");
        Log.d("di adapter", "SectionListDataAdapter: "+articleFilm.size());
        this.articleFilm=articleFilm;
        this.context=context;
    }

    @SuppressLint("InflateParams")
    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
        SingleItemRowHolder rowHolder = new SingleItemRowHolder(view);
        return rowHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SectionListDataAdapter2.SingleItemRowHolder holder, int position) {
        final Film singleItem = articleFilm.get(position);
        holder.tvTitle.setText(singleItem.getTitle());
        holder.tvdesc.setText(singleItem.getOverview().substring(0,5)+"...");
        holder.release_date.setText(singleItem.getRelease_date());

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185"+singleItem.getPoster_path())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(holder.ivposter);
        holder.btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, singleItem.getTitle());
                view.getContext().startActivity(Intent.createChooser(sharingIntent, "share"));
            }
        });
        holder.btndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(),""+singleItem.getTitle(), Toast.LENGTH_SHORT).show();
                Intent in=new Intent(view.getContext(),DetailActivity2.class);
                in.putExtra("title",singleItem.getTitle());
                in.putExtra("desc",singleItem.getOverview());
                in.putExtra("tanggal",singleItem.getRelease_date());
                in.putExtra("img","http://image.tmdb.org/t/p/w342"+singleItem.getPoster_path());
                view.getContext().startActivity(in);

            }
        });


    }

    @Override
    public int getItemCount() {
        return articleFilm.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView ivposter;
        private TextView tvdesc;
        private TextView release_date;
        private Button btndetail;
        private Button btnshare;

        private SingleItemRowHolder(final View view) {
            super(view);
            this.tvTitle = (TextView)view.findViewById(R.id.tvjudul);
            this.ivposter = (ImageView)view.findViewById(R.id.ivcover);
            this.tvdesc = (TextView)view.findViewById(R.id.tvdesc);
            this.release_date = (TextView)view.findViewById(R.id.release_date);
            this.btndetail = (Button) view.findViewById(R.id.btdetail);
            this.btnshare = (Button) view.findViewById(R.id.btshare);

        }

    }
}
