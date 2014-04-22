package edu.pitt.cs1635.pittsburgh311.model;

import android.content.Context;

import com.orm.SugarRecord;

import java.io.File;

/**
 * Created by ulanowicz on 4/18/14.
 */
public class Incident {

    String comment, lat, lon, category, photoName, address;
    File photo;
    private static Incident myIncident = null;

    public Incident(){} //empty constructor for singleton

    public static Incident getInstance(){
        if(myIncident == null){
            myIncident = new Incident();
        }
        return myIncident;
    }

    public String getComment(){
        return comment;
    }

    public String getLat(){
        return lat;
    }

    public String getLon(){
        return lon;
    }

    public String getCategory(){
        return category;
    }

    public String getPhotoName(){
        return photoName;
    }

    public File getPhoto(){
        return photo;
    }

    public String getAddress(){
        return address;
    }

    public void setComment(String comment1){
        this.comment = comment1;
    }

    public void setLat(String lat1){
        this.lat = lat1;
    }

    public void setLon(String lon1){
        this.lon = lon1;
    }

    public void setCategory(String category1){
        this.category = category1;
    }

    public void setPhotoName(String photoName1){
        this.photoName = photoName1;
    }

    public void setAddress(String address1){
        this.address = address1;
    }

    public void setPhoto(File photo1){
        this.photo = photo1;
    }
}
