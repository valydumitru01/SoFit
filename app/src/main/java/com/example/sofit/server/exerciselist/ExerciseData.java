package com.example.sofit.server.exerciselist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExerciseData {

    @SerializedName("bodyPart")
    @Expose
    private String bodyPart;
    @SerializedName("equipment")
    @Expose
    private String equipment;
    @SerializedName("gifUrl")
    @Expose
    private String gifUrl;
    @SerializedName("name")
    @Expose
    private String name = null;
    @SerializedName("target")
    @Expose
    private String target;
    @SerializedName("id")
    @Expose
    private String id;

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
