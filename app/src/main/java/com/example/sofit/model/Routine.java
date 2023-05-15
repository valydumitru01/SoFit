package com.example.sofit.model;

import java.util.Arrays;

public class Routine {
    String name;
    byte[] image; //Blob
    String imageUrl;
    String user;

    @Override
    public String toString() {
        return "Routine{" +
                "name='" + name + '\'' +
                ", image=" + Arrays.toString(image) +
                ", imageUrl='" + imageUrl + '\'' +
                ", user='" + user + '\'' +
                '}';
    }

    public Routine(){

    }
    public Routine(String name,String user, byte[] image) {
        this.name = name;
        this.user=user;
        this.image = image;
    }

    public void setImage(Object image){
        this.image = (byte[]) image;
    }

    public byte[] getImage(){return this.image;}

    public String getImageUrl(){return this.imageUrl;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
