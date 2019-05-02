package com.example.ics.pindaiopname.model;

public class BarangModel {
    private String BrgName, BrgID, Satuan;

    public BarangModel(String brgName, String brgID, String satuan) {
        BrgName = brgName;
        BrgID = brgID;
        Satuan = satuan;
    }

    public String getBrgName() {
        return BrgName;
    }

    public void setBrgName(String brgName) {
        BrgName = brgName;
    }

    public String getBrgID() {
        return BrgID;
    }

    public void setBrgID(String brgID) {
        BrgID = brgID;
    }

    public String getSatuan() {
        return Satuan;
    }

    public void setSatuan(String satuan) {
        Satuan = satuan;
    }
}
