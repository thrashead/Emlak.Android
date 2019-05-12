package com.sinasalik.thrashead.emlak;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinasalik.thrashead.tdlibrary.TDAraclar;
import com.sinasalik.thrashead.tdlibrary.TDJson;
import com.sinasalik.thrashead.tdlibrary.TDJsonListener;

import org.json.JSONException;
import org.json.JSONObject;

import com.sinasalik.thrashead.emlak.Emlak.Araclar;

public class FrIlan extends Fragment {
    private Context context;
    private View view;

    private String ilanurl, baslik, enlem, boylam;

    private Bundle bundle;
    private Gallery glGaleri;
    private TextView lblBaslik;

    private TextView lblIlanIcerik;
    private TextView lblIlanSehir;
    private TextView lblIlanSahip;
    private TextView lblIlanKod;
    private TextView lblIlanFiyat;

    private float scale;
    private int pixel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_ilan, container, false);

        SayfaHazirla();

        return view;
    }

    private void SayfaHazirla() {
        Nesneler();
        Olaylar();
    }

    private void Nesneler() {
        context = getActivity().getApplicationContext();

        scale = context.getResources().getDisplayMetrics().density;
        pixel = (int) (1 * scale + 0.5f);

        bundle = getActivity().getIntent().getExtras();

        lblBaslik = (TextView) view.findViewById(R.id.lblBaslik);
        glGaleri = (Gallery) view.findViewById(R.id.glGaleri);

        if (bundle.getString("ilanurl") != null) {
            ilanurl = bundle.getString("ilanurl");
        }

        if (bundle.getString("enlem") != null) {
            enlem = bundle.getString("enlem");
        }

        if (bundle.getString("boylam") != null) {
            boylam = bundle.getString("boylam");
        }

        if (bundle.getString("baslik") != null) {
            baslik = bundle.getString("baslik");
        }

        lblIlanIcerik = (TextView) view.findViewById(R.id.lblIlanIcerik);
        lblIlanSehir = (TextView) view.findViewById(R.id.lblIlanSehir);
        lblIlanSahip = (TextView) view.findViewById(R.id.lblIlanSahip);
        lblIlanKod = (TextView) view.findViewById(R.id.lblIlanKod);
        lblIlanFiyat = (TextView) view.findViewById(R.id.lblIlanFiyat);
    }

    private void Olaylar() {
        if (ilanurl != null) {
            Doldur();

            Araclar.GaleriDoldur(context, ilanurl, glGaleri);
        }

        if (TDAraclar.CihazKucukmu(context)) {
            try {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 120 * pixel
                );
                params.setMargins(0, 0, 0, 0);
                glGaleri.setLayoutParams(params);
                glGaleri.setGravity(Gravity.CENTER);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void Doldur() {
        new TDJson(new JSONObject(), new TDJsonListener() {
            @Override
            public void successCallBack(String jsonResult) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);

                    lblBaslik.setText(jsonObject.getString("Baslik"));

                    lblIlanSehir.setText(jsonObject.getString("Sehir") + " / " + jsonObject.getString("Ilce") + " / " + jsonObject.getString("Semt"));
                    lblIlanIcerik.setText(TDAraclar.LabelaYaz(jsonObject.getString("Icerik")));
                    lblIlanSahip.setText(jsonObject.getString("Sahip"));
                    lblIlanKod.setText(jsonObject.getString("Kod"));
                    lblIlanFiyat.setText(jsonObject.getString("Fiyat") + " TL");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void errorCallBack() {

            }
        }, true).execute(TDAraclar.KaynakDegerDon(context, R.string.servis_root) + "Ilan?ilanurl=" + ilanurl);
    }
}
