package com.gnzlt.navigationview.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dicoding on 12/6/2016.
 */

public class MahasiswaModel implements Parcelable {
    private int id;
    private String name;
    private String nim;
    private String url;
    private String tanggal;

    public MahasiswaModel(){

    }

    public MahasiswaModel(String name, String nim,String url,String tanggal){
        this.name = name;
        this.nim = nim;
        this.url = url;
        this.tanggal = tanggal;
    }

    public MahasiswaModel(int id, String name, String nim,String url,String tanggal){
        this.id = id;
        this.name = name;
        this.nim = nim;
        this.url = url;
        this.tanggal = tanggal;
    }

    public String getUrl() {
        return url;
    }
    public String gettanggal() {
        return tanggal;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void settanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.nim);
        dest.writeString(this.url);
        dest.writeString(this.tanggal);
    }

    protected MahasiswaModel(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.nim = in.readString();
        this.url = in.readString();
        this.tanggal = in.readString();
    }

    public static final Parcelable.Creator<MahasiswaModel> CREATOR = new Parcelable.Creator<MahasiswaModel>() {
        @Override
        public MahasiswaModel createFromParcel(Parcel source) {
            return new MahasiswaModel(source);
        }

        @Override
        public MahasiswaModel[] newArray(int size) {
            return new MahasiswaModel[size];
        }
    };
}
