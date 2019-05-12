package com.sinasalik.thrashead.emlak.Emlak.Haberler;

import android.content.Context;
import com.sinasalik.thrashead.emlak.R;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import com.sinasalik.thrashead.tdlibrary.TDAraclar;
import com.sinasalik.thrashead.emlak.Emlak.EmlakKontroller.EmlakIcerikNesne;

public class Haberler {
    public static ArrayList<EmlakIcerikNesne> HaberListe(Context context) {
        ArrayList<EmlakIcerikNesne> haberler = new ArrayList<>();

        try {
            String theString;

            final URL finalResult_URI = TDAraclar.URLDon(TDAraclar.KaynakDegerDon(context, R.string.servis_root) + "Haberler/");

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

            EmlakIcerikNesne haber = null;

            for (int i = 0; i < jsonObject.length(); i++) {
                try {
                    haber = new EmlakIcerikNesne();

                    haber.Baslik = jsonObject.getJSONObject(i).getString("Title");
                    haber.Url = jsonObject.getJSONObject(i).getString("Url");

                    haberler.add(haber);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return haberler;
    }
}