/**
 * Chris McLane
 */
package com.example.weatherapp;

public class MyRecyclerViewData {

    private String mv_city;
    private String mv_zip;
    private String mv_cond;
    private int mv_temp;

    public MyRecyclerViewData(){
    }

    public MyRecyclerViewData(String city, String zip, int temp, String cond){
        mv_city = city;
        mv_zip = zip;
        mv_temp = temp;
        mv_cond = cond;
    }

    public String getCity(){
        return this.mv_city;
    }

    public String getZip(){
        return this.mv_zip;
    }

    public int getTemp(){
        return this.mv_temp;
    }

    public String getCond() {
        return this.mv_cond;
    }

    public void setCity(String city){
        mv_city = city;
    }

    public void setZip(String zip){
        mv_zip = zip;
    }

    public void setTemp(int temp){
        mv_temp = temp;
    }
}
