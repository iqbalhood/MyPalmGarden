package com.sawitku.fmodels;

/**
 * Created by vamsi on 18-Jul-16.
 */

public class Jawaban {

    public long id;
    public String idUser;
    public String namaUser;
    public String answer;
    public String gambar;

    public Jawaban(){

    }
    public Jawaban(long id, String namaUser, String idUser, String answer, String gambar){
        this.id = id;
        this.namaUser = namaUser;
        this.gambar = gambar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }


  
}
