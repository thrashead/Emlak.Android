package com.sinasalik.thrashead.emlak;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.sinasalik.thrashead.emlak.Emlak.Araclar;

public class FrOzellikler extends Fragment {
    private Context context;
    private View view;

    private WebView wvOzellik;

    private String ilanurl, baslik, enlem, boylam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_ozellikler, container, false);

        SayfaHazirla();

        return view;
    }

    private void SayfaHazirla() {
        Nesneler();
        Olaylar();
    }

    private void Nesneler() {
        context= getActivity().getApplicationContext();

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

        wvOzellik = (WebView) view.findViewById(R.id.wvOzellik);
    }

    private void Olaylar() {
        Araclar.OzellikDoldur(context, ilanurl, wvOzellik);
    }

    public void Kapat() {
        getActivity().finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
