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

    public InstitutionModel(String insName, String district, String state,String cityName) {
        this.insName = insName;
        this.district = district;
        this.State = state;
        this.cityName = cityName;
    }
}
