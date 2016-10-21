package com.elf.elfstudent.model;

/**
 * Created by nandhu on 13/10/16.
 * The Rest model for institution Fethced for Autocomplete textview
 */

public class InstitutionModel {
    private String insName;
    private String district;
    private String State;
    private String cityName;
    private String ins_id;

    public String getIns_id() {
        return ins_id;
    }

    public void setIns_id(String ins_id) {
        this.ins_id = ins_id;
    }

    public InstitutionModel(String insName, String district, String cityName, String ins_id) {
        this.insName = insName;
        this.district = district;

        this.cityName = cityName;
        this.ins_id = ins_id;
    }

    public String getInsName() {
        return insName;
    }

    public void setInsName(String insName) {
        this.insName = insName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


}
