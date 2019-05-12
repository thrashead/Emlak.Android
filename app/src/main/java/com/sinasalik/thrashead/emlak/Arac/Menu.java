package com.sinasalik.thrashead.emlak.Arac;

import android.app.Activity;
import android.content.Context;

import com.sinasalik.thrashead.emlak.R;
import com.sinasalik.thrashead.emlak.SayfaAnaSayfa;
import com.sinasalik.thrashead.emlak.SayfaHaberler;
import com.sinasalik.thrashead.emlak.SayfaIcerik;
import com.sinasalik.thrashead.emlak.SayfaIlanlar;

import com.sinasalik.thrashead.tdmenu.TDMenu;
import com.sinasalik.thrashead.tdlibrary.TDAraclar;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Menu {
    public static ArrayList<TDMenu.MenuNesne> MenuDoldur(Activity activity, final MenuTip menuTip) {
        switch (menuTip) {
            case Icerik:
                return IcerikList(activity);
            case Kategori:
                return KategoriList(activity);
            case KategoriTip:
                return KategoriTipList(activity);
            case AltMenu:
                return AltMenuList(activity);
            default:
                return IcerikList(activity);
        }
    }

    //Icerik
    private static ArrayList<TDMenu.MenuNesne> IcerikList(Activity activity) {
        ArrayList<TDMenu.MenuNesne> menuList = new ArrayList<>();

        try {
            String theString;

            final URL finalResult_URI = TDAraclar.URLDon(TDAraclar.KaynakDegerDon(activity.getApplicationContext(), R.string.servis_root) + "Bilgiler/");

            URLConnection con = finalResult_URI.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            theString = builder.toString();

            final JSONArray jsonData = new JSONArray(theString);

            TDMenu.MenuNesne menu = new TDMenu.MenuNesne();
            menu.Baslik = "Ana Sayfa";
            menu.LinkTipi = TDMenu.MenuNesne.LinkTip.Sayfa;
            menu.Parametreler.putString("parametre", "ana-sayfa");
            menu.Sinif = SayfaAnaSayfa.class;
            menu.ArkaPlanRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.ArkaPlan, 1);
            menu.MetinRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.Metin, 1);
            menuList.add(menu);

            menu = new TDMenu.MenuNesne();
            menu.Baslik = "Haberler";
            menu.LinkTipi = TDMenu.MenuNesne.LinkTip.Sayfa;
            menu.Parametreler.putString("parametre", "haberler");
            menu.Sinif = SayfaHaberler.class;
            menu.ArkaPlanRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.ArkaPlan, 1);
            menu.MetinRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.Metin, 1);
            menuList.add(menu);

            for (int i = 0; i < jsonData.length(); i++) {
                try {
                    menu = new TDMenu.MenuNesne();
                    menu.Baslik = jsonData.getJSONObject(i).getString("Name");
                    menu.LinkTipi = TDMenu.MenuNesne.LinkTip.Sayfa;
                    menu.Parametreler.putString("parametre", jsonData.getJSONObject(i).getString("Url"));
                    menu.Sinif = SayfaIcerik.class;
                    menu.ArkaPlanRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.ArkaPlan, 1);
                    menu.MetinRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.Metin, 1);

                    menuList.add(menu);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return menuList;
    }

    //Kategori
    static int menuSeviye;
    private static ArrayList<TDMenu.MenuNesne> KategoriList(Activity activity) {
        ArrayList<TDMenu.MenuNesne> menuList = new ArrayList<>();

        try {
            String cacheJson = (String) TDAraclar.BellektenOku(activity, "menukategori", "tdmenu", String.class);

            JSONArray jsonData = null;

            if (cacheJson == null) {
                try {
                    String theString;

                    URL finalResult_URI = TDAraclar.URLDon(TDAraclar.KaynakDegerDon(activity.getApplicationContext(), R.string.servis_root) + "Kategoriler/");

                    URLConnection con = finalResult_URI.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    StringBuilder builder = new StringBuilder();
                    String line;

                    while ((line = br.readLine()) != null) {
                        builder.append(line);
                    }

                    br.close();

                    theString = builder.toString();
                    jsonData = new JSONArray(theString);

                    TDAraclar.BellegeYaz(activity, "menukategori", "tdmenu", jsonData.toString());

                } catch (JSONException e) {

                }
            } else {
                jsonData = new JSONArray(cacheJson);
            }

            menuSeviye = 1;
            menuList = KategoriDondur(activity.getApplicationContext(), jsonData, "0", menuSeviye);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return menuList;
    }
    private static ArrayList<TDMenu.MenuNesne> KategoriDondur(Context context, JSONArray jsonObject, String ID, int _seviye) {
        ArrayList<TDMenu.MenuNesne> menuList = new ArrayList<>();

        TDMenu.MenuNesne menu = null;

        for (int i = 0; i < jsonObject.length(); i++) {
            try {
                if (jsonObject.getJSONObject(i).getString("ParentID").equals(ID)) {
                    menu = new TDMenu.MenuNesne();
                    menu.Baslik = jsonObject.getJSONObject(i).getString("CategoryName");
                    menu.LinkTipi = TDMenu.MenuNesne.LinkTip.Sayfa;
                    menu.Sinif = SayfaIlanlar.class;
                    menu.ArkaPlanRenk = MenuRenk.RenkDon(context, MenuRenk.RenkTip.ArkaPlan, _seviye);
                    menu.MetinRenk = MenuRenk.RenkDon(context, MenuRenk.RenkTip.Metin, _seviye);
                    menu.Parametreler.putString("kategori", jsonObject.getJSONObject(i).getString("Url"));
                    menu.Parametreler.putString("parentid", jsonObject.getJSONObject(i).getString("ParentID"));
                    menu.Parametreler.putString("id", jsonObject.getJSONObject(i).getString("ID"));

                    Object jsonAltKategori = jsonObject.getJSONObject(i).get("SubCategories");
                    JSONArray jsonList;

                    if (!jsonAltKategori.equals(null)) {
                        jsonList = jsonObject.getJSONObject(i).getJSONArray("SubCategories");

                        if (jsonList.length() > 0) {
                            menuSeviye++;
                            menu.AltKategoriler = KategoriDondur(context, jsonList, jsonObject.getJSONObject(i).getString("ID"), menuSeviye);
                            menuSeviye--;
                        }
                    } else {
                        menu.AltKategoriler = null;
                    }
                    menuList.add(menu);
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
        return menuList;
    }

    //KategoriTip
    private static ArrayList<TDMenu.MenuNesne> KategoriTipList(Activity activity) {
        ArrayList<TDMenu.MenuNesne> menuList = new ArrayList<>();
        TDMenu.MenuNesne menu;

        menu = new TDMenu.MenuNesne();
        menu.Baslik = "Satılık İlanlar";
        menu.LinkTipi = TDMenu.MenuNesne.LinkTip.Sayfa;
        menu.Sinif = SayfaIlanlar.class;
        menu.Parametreler.putString("parametre", "satilik");
        menu.ArkaPlanRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.ArkaPlan, 1);
        menu.MetinRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.Metin, 1);
        menuList.add(menu);

        menu = new TDMenu.MenuNesne();
        menu.Baslik = "Kiralık İlanlar";
        menu.LinkTipi = TDMenu.MenuNesne.LinkTip.Sayfa;
        menu.Sinif = SayfaIlanlar.class;
        menu.Parametreler.putString("parametre", "kiralik");
        menu.ArkaPlanRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.ArkaPlan, 1);
        menu.MetinRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.Metin, 1);
        menuList.add(menu);

        menu = new TDMenu.MenuNesne();
        menu.Baslik = "Yeni İlanlar";
        menu.LinkTipi = TDMenu.MenuNesne.LinkTip.Sayfa;
        menu.Sinif = SayfaIlanlar.class;
        menu.Parametreler.putString("parametre", "yeni");
        menu.ArkaPlanRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.ArkaPlan, 1);
        menu.MetinRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.Metin, 1);
        menuList.add(menu);

        menu = new TDMenu.MenuNesne();
        menu.Baslik = "Tüm İlanlar";
        menu.LinkTipi = TDMenu.MenuNesne.LinkTip.Sayfa;
        menu.Sinif = SayfaIlanlar.class;
        menu.Parametreler.putString("parametre", "tum");
        menu.ArkaPlanRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.ArkaPlan, 1);
        menu.MetinRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.Metin, 1);
        menuList.add(menu);

        return menuList;
    }

    //KategoriTip
    private static ArrayList<TDMenu.MenuNesne> AltMenuList(Activity activity) {
        ArrayList<TDMenu.MenuNesne> menuList = new ArrayList<>();
        TDMenu.MenuNesne menu;

        menu = new TDMenu.MenuNesne();
        menu.Baslik = "Web Sitesine Git";
        menu.Ekstra = TDAraclar.KaynakDegerDon(activity.getApplicationContext(), R.string.web_root);
        menu.LinkTipi = TDMenu.MenuNesne.LinkTip.WebSayfasi;
        menu.ArkaPlanRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.ArkaPlan, 1);
        menu.MetinRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.Metin, 1);
        menuList.add(menu);

        menu = new TDMenu.MenuNesne();
        menu.Baslik = "Çıkış";
        menu.LinkTipi = TDMenu.MenuNesne.LinkTip.Cikis;
        menu.CikisIkon = R.mipmap.logo;
        menu.ArkaPlanRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.ArkaPlan, 1);
        menu.MetinRenk = MenuRenk.RenkDon(activity.getApplicationContext(), MenuRenk.RenkTip.Metin, 1);
        menuList.add(menu);

        return menuList;
    }

    public enum MenuTip {
        Kategori,
        Icerik,
        KategoriTip,
        AltMenu
    }
}