package com.sinasalik.thrashead.emlak.Emlak.RealEstateData;

public class RealEstateData {
    private String ID = "";

    private String KategoriAdi = "";
    private String Baslik = "";
    private String Resim = "";
    private String Fiyat = "";
    private String Yer = "";
    private String Url = "";
    private String Yeni = "";
    private String Enlem = "";
    private String Boylam = "";

    /*********** Set Methods ******************/

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setKategoriAdi(String KategoriAdi) {
        this.KategoriAdi = KategoriAdi;
    }

    public void setBaslik(String Baslik) {
        this.Baslik = Baslik;
    }

    public void setResim(String Resim) {
        this.Resim = Resim;
    }

    public void setFiyat(String Fiyat) {
        this.Fiyat = Fiyat;
    }

    public void setYer(String Yer) {
        this.Yer = Yer;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public void setYeni(String Yeni) {
        this.Yeni = Yeni;
    }

    public void setEnlem(String enlem) {
        Enlem = enlem;
    }

    public void setBoylam(String boylam) {
        Boylam = boylam;
    }

    /*********** Get Methods ****************/

    public String getID() {
        return this.ID;
    }

    public String getKategoriAdi() {
        return this.KategoriAdi;
    }

    public String getBaslik() {
        return this.Baslik;
    }

    public String getResim() {
        return this.Resim;
    }

    public String getFiyat() {
        return this.Fiyat + " TL";
    }

    public String getYer() {
        return this.Yer;
    }

    public String getUrl() {
        return this.Url;
    }

    public String getYeni() {
        return this.Yeni;
    }

    public String getEnlem() {
        return Enlem;
    }

    public String getBoylam() {
        return Boylam;
    }
}