package com.example.ics.pindaiopname.model;

public class LokasiModel {
    private String LokasiID, LokasiName;

    public LokasiModel(String LokasiID, String lokasiName) {
        this.LokasiID = LokasiID;
        LokasiName = lokasiName;
    }

    public String getLokasiID() {
        return LokasiID;
    }

    public String getLokasiName() {
        return LokasiName;
    }
}
