package com.example.ics.pindaiopname.model;

public class OpnameModel {
    private String Tgl, Jam, BrgID, BrgName, LokasiID, LokasiName, UnitID, UnitName, Satuan, userID;
    private int Status;

    private int Qty;

    public OpnameModel(String tgl, String jam, String brgName, String lokasiName, String unitName, String satuan, int qty, String UserID) {
        Tgl = tgl;
        Jam = jam;
        BrgName = brgName;
        LokasiName = lokasiName;
        UnitName = unitName;
        Satuan = satuan;
        Qty = qty;
        userID = UserID;
    }

    public OpnameModel(String brgID, String brgName, String satuan) {
        BrgID = brgID;
        BrgName = brgName;
        Satuan = satuan;
    }

    public String getTgl() {
        return Tgl;
    }

    public String getUserID() {
        return userID;
    }

    public void setTgl(String tgl) {
        Tgl = tgl;
    }

    public String getJam() {
        return Jam;
    }

    public void setJam(String jam) {
        Jam = jam;
    }

    public String getBrgID() {
        return BrgID;
    }

    public void setBrgID(String brgID) {
        BrgID = brgID;
    }

    public String getBrgName() {
        return BrgName;
    }

    public void setBrgName(String brgName) {
        BrgName = brgName;
    }

    public String getLokasiID() {
        return LokasiID;
    }

    public void setLokasiID(String lokasiID) {
        LokasiID = lokasiID;
    }

    public String getLokasiName() {
        return LokasiName;
    }

    public void setLokasiName(String lokasiName) {
        LokasiName = lokasiName;
    }

    public String getUnitID() {
        return UnitID;
    }

    public void setUnitID(String unitID) {
        UnitID = unitID;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public String getSatuan() {
        return Satuan;
    }

    public void setSatuan(String satuan) {
        Satuan = satuan;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public int getStatus() {
        return Status;
    }
}
