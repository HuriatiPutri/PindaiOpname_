package com.example.ics.pindaiopname.model;

public class UnitModel {
    private String UnitID, UnitName;

    public UnitModel(String unitID, String unitName) {
        UnitID = unitID;
        UnitName = unitName;
    }

    public String getUnitID() {
        return UnitID;
    }

    public String getUnitName() {
        return UnitName;
    }
}
