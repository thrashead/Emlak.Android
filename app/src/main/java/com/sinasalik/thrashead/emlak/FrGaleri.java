package com.sinasalik.thrashead.emlak;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;

import com.sinasalik.thrashead.emlak.Emlak.Araclar;

public class FrGaleri extends Fragment {
    private Context context;
    private View view;

    private Gallery glGaleri;

    private String ilanurl, baslik, enlem, boylam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_galeri, container, false);

        SayfaHazirla();

        return view;
    }

    private void SayfaHazirla() {
        Nesneler();
        Olaylar();
    }

    private void Nesneler() {
        context = getActivity().getApplicationContext();

        glGaleri = (Gallery) view.findViewById(R.id.glGaleri);

        Bundle bundle = getActivity().getIntent().getExtras();

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
    }

    private void Olaylar() {
        if (ilanurl != null) {
            Araclar.GaleriDoldur(context, ilanurl, glGaleri);
        }
    }
}
