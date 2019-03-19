package com.example.ics.pindaiopname.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LokasiDefault implements Parcelable {

    private String ID_UNIT, NAMA_UNIT, ID_LOKASI, NAMA_LOKASI;
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getID_UNIT() {
        return ID_UNIT;
    }

    public void setID_UNIT(String ID_UNIT) {
        this.ID_UNIT = ID_UNIT;
    }

    public String getNAMA_UNIT() {
        return NAMA_UNIT;
    }

    public void setNAMA_UNIT(String NAMA_UNIT) {
        this.NAMA_UNIT = NAMA_UNIT;
    }

    public String getID_LOKASI() {
        return ID_LOKASI;
    }

    public void setID_LOKASI(String ID_LOKASI) {
        this.ID_LOKASI = ID_LOKASI;
    }

    public String getNAMA_LOKASI() {
        return NAMA_LOKASI;
    }

    public void setNAMA_LOKASI(String NAMA_LOKASI) {
        this.NAMA_LOKASI = NAMA_LOKASI;
    }

    public  LokasiDefault(){

    }
    protected LokasiDefault(Parcel in) {
        ID = in.readInt();
        ID_UNIT = in.readString();
        NAMA_UNIT = in.readString();
        ID_LOKASI = in.readString();
        NAMA_LOKASI = in.readString();
    }

    public static final Creator<LokasiDefault> CREATOR = new Creator<LokasiDefault>() {
        @Override
        public LokasiDefault createFromParcel(Parcel in) {
            return new LokasiDefault(in);
        }

        @Override
        public LokasiDefault[] newArray(int size) {
            return new LokasiDefault[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(ID_UNIT);
        dest.writeString(NAMA_UNIT);
        dest.writeString(ID_LOKASI);
        dest.writeString(NAMA_LOKASI);
    }
}
