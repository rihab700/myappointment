package com.example.myappointement;

public class Doctors {

    private  String adresse;
    private String name;
    private long tel;

    private Doctors(){}
    private Doctors(String name,String adresse,long tel){
        this.name= name;
        this.adresse = adresse;
        this.tel = tel;
    }

    public String getName(){
        return name;
    }
    public String getAdresse(){
        return adresse;
    }
    public long getTel(){
        return tel;
    }
}
