package com.example.sofit.model;

public class ModelSession {
    private String name;
    private String routine;
    private byte[] image=new byte[]{};

    public ModelSession(){

    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    public ModelSession(String name, String routine, byte[] image){
        this.name=name;
        this.routine=routine;
        this.image = image;
    }

    public void setImage(byte[] image){ this.image = image; }

    public byte[] getImage(){ return this.image; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
