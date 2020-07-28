package com.example.dell.firstcry.Model;


public class VacType {

    public String VAC_CODE;
    public int VAC_WEEK_START;
    public int VAC_WEEK_END;
    public LatLng LATLNG;
    public VacType(String VAC_CODE, int VAC_WEEK_START, int VAC_WEEK_END) {
        this.VAC_CODE = VAC_CODE;
        this.VAC_WEEK_START = VAC_WEEK_START;
        this.VAC_WEEK_END = VAC_WEEK_END;
    }

    public VacType() {
    }
}
