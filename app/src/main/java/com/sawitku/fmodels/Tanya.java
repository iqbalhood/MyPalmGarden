package com.sawitku.fmodels;

/**
 * Created by vamsi on 18-Jul-16.
 */

public class Tanya {

    public String id;
    public String time;
    public String nama;
    public String question;
    public String alamat;
    public String profil_picture;

    public Tanya(){

    }
    public Tanya(String id, String nama, String time, String question, String alamat, String profil_picture){
        this.id = id;
        this.nama = nama;
        this.time = time;
        this.question = question;
        this.alamat = alamat;
        this.profil_picture = profil_picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }


    public String getProfil_picture() {
        return profil_picture;
    }

    public void setProfil_picture(String profil_picture) {
        this.profil_picture = profil_picture;
    }



  
}
