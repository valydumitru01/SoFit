package com.example.sofit.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelExercise implements Parcelable{

    private String name;
    private String image;

    public ModelExercise(String name, String image) {
        this.name = name;
        this.image = image;
    }

    protected ModelExercise(Parcel in) {
        name = in.readString();
        image = in.readString();
    }

    public ModelExercise() {

    }


    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }






    public static final Creator<ModelExercise> CREATOR = new Creator<ModelExercise>() {
        @Override
        public ModelExercise createFromParcel(Parcel in) {
            return new ModelExercise(in);
        }

        @Override
        public ModelExercise[] newArray(int size) {
            return new ModelExercise[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(image);
    }
}
