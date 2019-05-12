package com.sinasalik.thrashead.emlak.Emlak;

import android.content.Context;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Gallery;
import android.widget.ImageView;

import com.sinasalik.thrashead.emlak.R;

import com.sinasalik.thrashead.tdlibrary.TDAraclar;
import com.sinasalik.thrashead.tdlibrary.TDGaleri;
import com.sinasalik.thrashead.tdlibrary.TDJson;
import com.sinasalik.thrashead.tdlibrary.TDJsonListener;
import com.sinasalik.thrashead.tdlibrary.TDImageView;

import com.sinasalik.thrashead.emlak.Emlak.RealEstateData.RealEstateData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Araclar {
    public static void ResimDon(final Context context, final ImageView imgView, String kelime) {
        new TDJson(new JSONObject(), new TDJsonListener() {
            @Override
            public void successCallBack(String jsonResult) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);

                    try {
                        new TDImageView(imgView).execute(jsonObject.getString("ImageUrl"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void errorCallBack() {

            }
        }, true).execute(TDAraclar.KaynakDegerDon(context, R.string.servis_root) + "Resim?kelime=" + kelime);
    }

    public static void GaleriDoldur(final Context context, String ilanurl, final Gallery gallery) {
        new TDJson(new JSONObject(), new TDJsonListener() {
            @Override
            public void successCallBack(String jsonResult) {
                try {
                    ArrayList<String> pics = new ArrayList<>();
                    JSONArray jsonObject = new JSONArray(jsonResult);

                    for (int i = 0; i < jsonObject.length(); i++) {
                        try {
                            pics.add(TDAraclar.WebAdresDuzelt(jsonObject.getJSONObject(i).getString("Picture")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    TDGaleri imgAdap = new TDGaleri(context, pics.toArray(new String[pics.size()]));

                    gallery.setAdapter(imgAdap);

                    /* Galeri otomatik load olmuyor. Aşağıdaki kod onu tetikliyor açılışta */
                    gallery.setSelection(1);
                    gallery.onFling(null, null, (float) (1) * 1000, 0);
                    /* Galeri otomatik load olmuyor. Yukarıdaki kod onu tetikliyor açılışta */
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void errorCallBack() {

            }
        }, true).execute(TDAraclar.KaynakDegerDon(context, R.string.servis_root) + "Galeri?ilanurl=" + ilanurl);
    }

    public static void OzellikDoldur(final Context context, String ilanUrl, final WebView wvOzellik) {
        new TDJson(new JSONObject(), new TDJsonListener() {
            @Override
            public void successCallBack(String jsonResult) {
                String sImageVar = "<img src=\"" + TDAraclar.KaynakDegerDon(context, R.string.web_root) + "images/var.png\" width=\"30\" />";
                String sImageYok = "<img src=\"" + TDAraclar.KaynakDegerDon(context, R.string.web_root) + "images/yok.png\" width=\"30\" />";

                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);

                    WebSettings settings = wvOzellik.getSettings();
                    String sIcerik = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>";

                    if (!TDAraclar.CihazKucukmu(context)) {
                        settings.setDefaultFontSize(24);
                    } else {
                        settings.setDefaultFontSize(14);
                    }

                    sIcerik = sIcerik + "<div style=\"width:100%; text-align:center; margin-top:10px;\">";

                    sIcerik = sIcerik + "<b>Oda Sayısı : </b>" + jsonObject.getString("OdaSayisi") + "<br />";
                    sIcerik = sIcerik + "<b>Salon Sayısı : </b>" + jsonObject.getString("SalonSayisi") + "<br />";
                    sIcerik = sIcerik + "<b>Alan : </b>" + jsonObject.getString("Alan") + "<br />";
                    sIcerik = sIcerik + "<b>Kat Sayısı : </b>" + jsonObject.getString("KatSayisi") + "<br />";
                    sIcerik = sIcerik + "<b>Bulunduğu Kat : </b>" + jsonObject.getString("BulunduguKat") + "<br />";
                    sIcerik = sIcerik + "<b>Durumu : </b>" + jsonObject.getString("Durum") + "<br />";
                    sIcerik = sIcerik + "<b>Isınma Tipi : </b>" + jsonObject.getString("IsinmaTipi") + "<br />";
                    sIcerik = sIcerik + "<b>Yakıt Tipi : </b>" + jsonObject.getString("YakitTipi") + "<br />";
                    sIcerik = sIcerik + "<b>Bina Yaşı : </b>" + jsonObject.getString("BinaYasi") + "<br /><br />";

                    if (jsonObject.getString("ArkaCephe").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Arka Cephe" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + "Arka Cephe" + sImageYok + "<br />";

                    if (jsonObject.getString("OnCephe").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Ön Cephe" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Ön Cephe" + sImageYok + "<br />";

                    if (jsonObject.getString("CaddeyeYakin").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Caddeye Yakın" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Caddeye Yakın" + sImageYok + "<br />";

                    if (jsonObject.getString("DenizeYakin").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Denize Yakın" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Denize Yakın" + sImageYok + "<br />";

                    if (jsonObject.getString("Manzara").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Manzaralı" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Manzaralı" + sImageYok + "<br />";

                    if (jsonObject.getString("Merkezde").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Merkezde" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Merkezde" + sImageYok + "<br />";

                    if (jsonObject.getString("Otoban").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Otobana Yakın" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Otobana Yakın" + sImageYok + "<br />";

                    if (jsonObject.getString("TopluUlasim").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Toplu Ulaşıma Yakın" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Toplu Ulaşıma Yakın" + sImageYok + "<br />";

                    if (jsonObject.getString("Asansor").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Asansör" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Asansör" + sImageYok + "<br />";

                    if (jsonObject.getString("Guvenlik").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Güvenlik" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Güvenlik" + sImageYok + "<br />";

                    if (jsonObject.getString("Hidrofor").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Hidrofor" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Hidrofor" + sImageYok + "<br />";

                    if (jsonObject.getString("Mantolama").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Mantolama" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Mantolama" + sImageYok + "<br />";

                    if (jsonObject.getString("DenizeSifir").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Denize Sıfır" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Denize Sıfır" + sImageYok + "<br />";

                    if (jsonObject.getString("Metro").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Metroya Yakın" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Metroya Yakın" + sImageYok + "<br />";

                    if (jsonObject.getString("Bahce").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Bahçeli" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Bahçeli" + sImageYok + "<br />";

                    if (jsonObject.getString("Kapici").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Kapıcı" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Kapıcı" + sImageYok + "<br />";

                    if (jsonObject.getString("Jenerator").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Jeneratör" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Jeneratör" + sImageYok + "<br />";

                    if (jsonObject.getString("Otopark").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Otopark" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Otopark" + sImageYok + "<br />";

                    if (jsonObject.getString("SiteIci").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Site İçerisinde" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Site İçerisinde" + sImageYok + "<br />";

                    if (jsonObject.getString("Alarm").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Alarm" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Alarm" + sImageYok + "<br />";

                    if (jsonObject.getString("GoruntuluDiafon").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Görüntülü Diafon" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Görüntülü Diafon" + sImageYok + "<br />";

                    if (jsonObject.getString("Klima").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Klima" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Klima" + sImageYok + "<br />";

                    if (jsonObject.getString("PVCDograma").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " PVC Doğrama" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " PVC Doğrama" + sImageYok + "<br />";

                    if (jsonObject.getString("YuzmeHavuzu").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Yüzme Havuzu" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Yüzme Havuzu" + sImageYok + "<br />";

                    if (jsonObject.getString("CelikKapi").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Çelik Kapı" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Çelik Kapı" + sImageYok + "<br />";

                    if (jsonObject.getString("KabloTVUydu").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Kablo TV-Uydu" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Kablo TV-Uydu" + sImageYok + "<br />";

                    if (jsonObject.getString("OyunParki").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Oyun Parkı" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Oyun Parkı" + sImageYok + "<br />";

                    if (jsonObject.getString("YanginMerdiveni").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Yangın Merdiveni" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Yangın Merdiveni" + sImageYok + "<br />";

                    if (jsonObject.getString("Balkon").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Balkon" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Balkon" + sImageYok + "<br />";

                    if (jsonObject.getString("Jakuzi").equalsIgnoreCase("v"))
                        sIcerik = sIcerik + " Jakuzi" + sImageVar + "<br />";
                    else
                        sIcerik = sIcerik + " Jakuzi" + sImageYok + "<br />";

                    sIcerik = sIcerik + "</div>";

                    sIcerik = sIcerik + "</body></html>";

                    wvOzellik.loadData(URLEncoder.encode(sIcerik).replaceAll("\\+", " "), "text/html; charset=UTF-8", "UTF-8");
                } catch (Exception e) {

                }
            }

            @Override
            public void errorCallBack() {

            }
        }, true).execute(TDAraclar.KaynakDegerDon(context, R.string.servis_root) + "Ozellikler?ilanurl=" + ilanUrl);
    }

    public static void HaritaDoldurWeb(final Context context, String enlem, String boylam, WebView wvHarita) {
        try {
            wvHarita.getSettings().setJavaScriptEnabled(true);
            wvHarita.setWebViewClient(new WebViewClient());
            String sIcerik = "<iframe width=\"100%\" height=\"100%\" frameborder=\"0\" style=\"border:0\" src=\"https://www.google.com/maps/embed/v1/place?key=" + TDAraclar.KaynakDegerDon(context, R.string.google_maps_key) + "&q=" + enlem + "," + boylam + "&zoom=13&language=tr\"></iframe>";
            //String sIcerik = "<div id=\"googleMap\" style=\"width:100%;height:100%;\" title=\"test\"></div><script>function initMap() {var koor = {lat: \" + enlem + \", lng: \" + boylam + \"};var map = new google.maps.Map(document.getElementById(\"googleMap\"), {zoom: 13,center: koor});var contentString = \"Content\";var infowindow = new google.maps.InfoWindow({content: contentString});var marker = new google.maps.Marker({position: koor,map: map,title: \"" + baslik + "\"});marker.addListener(\"click\", function() {infowindow.open(map, marker);});}</script>";
            wvHarita.loadData(URLEncoder.encode(sIcerik).replaceAll("\\+", " "), "text/html; charset=UTF-8", "UTF-8");
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    public static void IcerikDoldur(final Context context, String contenturl, final WebView wvIcerik, final IcerikTip icerikTip, @Nullable final boolean sadeceMetin) {
        String sayfa = icerikTip == IcerikTip.Icerik ? "Icerik" : "Haber";
        String parametre = icerikTip == IcerikTip.Icerik ? "contenturl" : "haberurl";

        new TDJson(new JSONObject(), new TDJsonListener() {
            @Override
            public void successCallBack(String jsonResult) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);

                    String baslikMetin = icerikTip == IcerikTip.Icerik ? jsonObject.getString("Name") : jsonObject.getString("Title");

                    String icerikMetin = jsonObject.getString("Content");

                    WebSettings settings = wvIcerik.getSettings();
                    String sIcerik = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>";
                    String sImage = null;

                    sIcerik = sIcerik + "<div style=\" max-width:90%; text-align:left; margin-top:10px;\">";

                    if (!sadeceMetin) {
                        if (icerikTip == IcerikTip.Icerik) {
                            sImage = jsonObject.getString("Image").replace("localhost", "10.0.2.2");
                        }

                        if (!TDAraclar.CihazKucukmu(context)) {
                            settings.setDefaultFontSize(24);
                        } else {
                            settings.setDefaultFontSize(14);
                        }

                        sIcerik = sIcerik + "<b>" + baslikMetin + "</b><br /><br />";

                        if (icerikTip == IcerikTip.Icerik && !sImage.equals(null)) {
                            if (sImage.length() > 4) {
                                sIcerik = sIcerik + "<center><img style=\"max-height:200px; max-width:300px;\" src=\"" + sImage + "\" /></center><br /><br />";
                            }
                        }
                    }

                    sIcerik = sIcerik + icerikMetin;

                    sIcerik = sIcerik + "</div>";
                    sIcerik = sIcerik + "</body></html>";

                    wvIcerik.loadData(URLEncoder.encode(sIcerik).replaceAll("\\+", " "), "text/html; charset=UTF-8", "UTF-8");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void errorCallBack() {

            }
        }, true).execute(TDAraclar.KaynakDegerDon(context, R.string.servis_root) + sayfa + "?" + parametre + "=" + contenturl);
    }

    public enum IcerikTip {
        Icerik,
        Haber
    }

    public static RealEstateData GununIlani(final Context context) {
        RealEstateData emlakData = new RealEstateData();

        try {
            final URL finalResult_URI = TDAraclar.URLDon(TDAraclar.KaynakDegerDon(context, R.string.servis_root) + "Ilan/?ilanurl=gunun-ilani");
            String theString;

            URLConnection con = finalResult_URI.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder builder = new StringBuilder();
            String line;
            if (br != null) {
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
            }
            br.close();
            theString = builder.toString();

            JSONObject jsonObject = new JSONObject(theString);

            emlakData.setResim(jsonObject.getString("Resim"));
            emlakData.setID(jsonObject.getString("ID"));
            emlakData.setBaslik(jsonObject.getString("Baslik"));
            emlakData.setKategoriAdi(jsonObject.getString("KategoriAdi"));
            emlakData.setFiyat(jsonObject.getString("Fiyat"));
            emlakData.setYer(jsonObject.getString("Semt") + " / " + jsonObject.getString("Sehir"));
            emlakData.setUrl(jsonObject.getString("Url"));
            emlakData.setYeni(jsonObject.getString("Yeni"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emlakData;
    }

    public static ArrayList<RealEstateData> Rasgele(final Context context, int adet) {
        ArrayList<RealEstateData> emlakList = new ArrayList();
        RealEstateData emlakData;

        try {
            String theString;

            final URL finalResult_URI = TDAraclar.URLDon(TDAraclar.KaynakDegerDon(context, R.string.servis_root) + "Ilanlar/?kelime=rasgele&adet=" + String.valueOf(adet));

            URLConnection con = finalResult_URI.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            theString = builder.toString();

            final JSONArray jsonObject = new JSONArray(theString);

            for (int i = 0; i < jsonObject.length(); i++) {
                emlakData = new RealEstateData();

                try {
                    emlakData.setResim(jsonObject.getJSONObject(i).getString("Resim"));
                    emlakData.setID(jsonObject.getJSONObject(i).getString("ID"));
                    emlakData.setKategoriAdi(jsonObject.getJSONObject(i).getString("KategoriAdi"));
                    emlakData.setBaslik(jsonObject.getJSONObject(i).getString("Baslik"));
                    emlakData.setFiyat(jsonObject.getJSONObject(i).getString("Fiyat"));
                    emlakData.setUrl(jsonObject.getJSONObject(i).getString("Url"));
                    emlakData.setYeni(jsonObject.getJSONObject(i).getString("Yeni"));
                    emlakData.setEnlem(jsonObject.getJSONObject(i).getString("Enlem"));
                    emlakData.setBoylam(jsonObject.getJSONObject(i).getString("Boylam"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                emlakList.add(emlakData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emlakList;
    }

    public static ArrayList<RealEstateData> Yeni(final Context context, int adet) {
        ArrayList<RealEstateData> emlakList = new ArrayList<>();
        RealEstateData emlakData;

        try {
            String theString;

            final URL finalResult_URI = TDAraclar.URLDon(TDAraclar.KaynakDegerDon(context, R.string.servis_root) + "Ilanlar/?kelime=yeni&adet=" + String.valueOf(adet));

            URLConnection con = finalResult_URI.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            theString = builder.toString();

            final JSONArray jsonObject = new JSONArray(theString);

            for (int i = 0; i < jsonObject.length(); i++) {
                emlakData = new RealEstateData();

                try {
                    emlakData.setResim(jsonObject.getJSONObject(i).getString("Resim"));
                    emlakData.setID(jsonObject.getJSONObject(i).getString("ID"));
                    emlakData.setKategoriAdi(jsonObject.getJSONObject(i).getString("KategoriAdi"));
                    emlakData.setBaslik(jsonObject.getJSONObject(i).getString("Baslik"));
                    emlakData.setFiyat(jsonObject.getJSONObject(i).getString("Fiyat"));
                    emlakData.setUrl(jsonObject.getJSONObject(i).getString("Url"));
                    emlakData.setYeni(jsonObject.getJSONObject(i).getString("Yeni"));
                    emlakData.setEnlem(jsonObject.getJSONObject(i).getString("Enlem"));
                    emlakData.setBoylam(jsonObject.getJSONObject(i).getString("Boylam"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                emlakList.add(emlakData);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return emlakList;
    }

    public static ArrayList<RealEstateData> Vitrin(final Context context, int adet) {
        ArrayList<RealEstateData> emlakList = new ArrayList<>();
        RealEstateData emlakData;

        try {
            String theString;

            final URL finalResult_URI = TDAraclar.URLDon(TDAraclar.KaynakDegerDon(context, R.string.servis_root) + "Ilanlar/?kelime=vitrin&adet=" + String.valueOf(adet));

            URLConnection con = finalResult_URI.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            theString = builder.toString();

            final JSONArray jsonObject = new JSONArray(theString);

            for (int i = 0; i < jsonObject.length(); i++) {
                emlakData = new RealEstateData();

                try {
                    emlakData.setResim(jsonObject.getJSONObject(i).getString("Resim"));
                    emlakData.setID(jsonObject.getJSONObject(i).getString("ID"));
                    emlakData.setKategoriAdi(jsonObject.getJSONObject(i).getString("KategoriAdi"));
                    emlakData.setBaslik(jsonObject.getJSONObject(i).getString("Baslik"));
                    emlakData.setFiyat(jsonObject.getJSONObject(i).getString("Fiyat"));
                    emlakData.setUrl(jsonObject.getJSONObject(i).getString("Url"));
                    emlakData.setYeni(jsonObject.getJSONObject(i).getString("Yeni"));
                    emlakData.setEnlem(jsonObject.getJSONObject(i).getString("Enlem"));
                    emlakData.setBoylam(jsonObject.getJSONObject(i).getString("Boylam"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                emlakList.add(emlakData);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return emlakList;
    }

    public static ArrayList<RealEstateData> Kelime(final Context context, String kelime,
                                                   int adet) {
        ArrayList<RealEstateData> emlakList = new ArrayList<>();
        RealEstateData emlakData;

        try {
            String theString;

            final URL finalResult_URI = TDAraclar.URLDon(TDAraclar.KaynakDegerDon(context, R.string.servis_root) + "Ilanlar/?kelime=" + kelime + "&adet=" + String.valueOf(adet));

            URLConnection con = finalResult_URI.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            theString = builder.toString();

            final JSONArray jsonObject = new JSONArray(theString);

            for (int i = 0; i < jsonObject.length(); i++) {
                emlakData = new RealEstateData();

                try {
                    emlakData.setResim(jsonObject.getJSONObject(i).getString("Resim"));
                    emlakData.setID(jsonObject.getJSONObject(i).getString("ID"));
                    emlakData.setKategoriAdi(jsonObject.getJSONObject(i).getString("KategoriAdi"));
                    emlakData.setBaslik(jsonObject.getJSONObject(i).getString("Baslik"));
                    emlakData.setFiyat(jsonObject.getJSONObject(i).getString("Fiyat"));
                    emlakData.setUrl(jsonObject.getJSONObject(i).getString("Url"));
                    emlakData.setYeni(jsonObject.getJSONObject(i).getString("Yeni"));
                    emlakData.setEnlem(jsonObject.getJSONObject(i).getString("Enlem"));
                    emlakData.setBoylam(jsonObject.getJSONObject(i).getString("Boylam"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                emlakList.add(emlakData);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return emlakList;
    }

    public static ArrayList<RealEstateData> Kategori(final Context context, String kategori,
                                                     int adet) {
        ArrayList<RealEstateData> emlakList = new ArrayList<>();
        RealEstateData emlakData;

        try {
            String theString;

            final URL finalResult_URI = TDAraclar.URLDon(TDAraclar.KaynakDegerDon(context, R.string.servis_root) + "KategoriIlanlar/?kategori=" + kategori);

            URLConnection con = finalResult_URI.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            theString = builder.toString();

            final JSONArray jsonObject = new JSONArray(theString);

            for (int i = 0; i < jsonObject.length(); i++) {
                emlakData = new RealEstateData();

                try {
                    emlakData.setResim(jsonObject.getJSONObject(i).getString("Resim"));
                    emlakData.setID(jsonObject.getJSONObject(i).getString("ID"));
                    emlakData.setKategoriAdi(jsonObject.getJSONObject(i).getString("KategoriAdi"));
                    emlakData.setBaslik(jsonObject.getJSONObject(i).getString("Baslik"));
                    emlakData.setFiyat(jsonObject.getJSONObject(i).getString("Fiyat"));
                    emlakData.setUrl(jsonObject.getJSONObject(i).getString("Url"));
                    emlakData.setYeni(jsonObject.getJSONObject(i).getString("Yeni"));
                    emlakData.setEnlem(jsonObject.getJSONObject(i).getString("Enlem"));
                    emlakData.setBoylam(jsonObject.getJSONObject(i).getString("Boylam"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                emlakList.add(emlakData);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return emlakList;
    }
}

