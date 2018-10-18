package com.example.myapplication2;

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
import com.example.myapplication2.Model.MahasiswaModel;

import java.util.ArrayList;

public class SectionListDataAdapter3 extends RecyclerView.Adapter<SectionListDataAdapter3.SingleItemRowHolder> {

    private View view;
    ArrayList<MahasiswaModel> articleFilm;
    Context context;

    public SectionListDataAdapter3(Context context, ArrayList<MahasiswaModel> articleFilm) {
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
    public void onBindViewHolder(@NonNull final SectionListDataAdapter3.SingleItemRowHolder holder, int position) {
        final MahasiswaModel singleItem = articleFilm.get(position);
        holder.tvTitle.setText(singleItem.getName());
        holder.tvdesc.setText(singleItem.getNim().substring(0,5)+"...");
        holder.release_date.setText(singleItem.gettanggal());
        Log.d("makanan", "onBindViewHolder: "+singleItem.getUrl());
        Glide.with(context)
                .load(singleItem.getUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(holder.ivposter);
        holder.btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, singleItem.getName());
                view.getContext().startActivity(Intent.createChooser(sharingIntent, "share"));
            }
        });
        holder.btndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(),""+singleItem.getNim(), Toast.LENGTH_SHORT).show();
                Intent in=new Intent(view.getContext(),DetailActivity.class);
                in.putExtra("title",singleItem.getName());
                in.putExtra("desc",singleItem.getNim());
                in.putExtra("tanggal",singleItem.gettanggal());
                in.putExtra("img",singleItem.getUrl());
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
