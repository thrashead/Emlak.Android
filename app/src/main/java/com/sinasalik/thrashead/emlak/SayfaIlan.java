package com.sinasalik.thrashead.emlak;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.sinasalik.thrashead.emlak.Arac.MenuHazirla;
import com.sinasalik.thrashead.emlak.Emlak.Araclar;

public class SayfaIlan extends AppCompatActivity {
    private Context context = this;
    private AlertDialog.Builder alertKapat;

    private Bundle bundle;

    private ImageView imgBanner, imgLogo;

    private String ilanurl, baslik, enlem, boylam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aktivite_ilan);

        SayfaHazirla();
    }

    private void SayfaHazirla() {
        Nesneler();
        Olaylar();
    }

    private void Nesneler() {
        //Alert Kapat
        alertKapat = new AlertDialog.Builder(context);
        alertKapat.setTitle(R.string.alertCikisBaslik)
                .setMessage(R.string.alertCikisMesaj)
                .setIcon(R.mipmap.logo)
                .setPositiveButton(R.string.btnTamamText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Kapat();
                    }
                })
                .setNegativeButton(R.string.btnIptalText, null);

        imgBanner = (ImageView) findViewById(R.id.imgAnaSayfaBanner);
        imgLogo = (ImageView) findViewById(R.id.imgAnaSayfaLogo);

        bundle = getIntent().getExtras();

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
        MenuHazirla.MenuHazirla(this);

        Araclar.ResimDon(context, imgBanner, "banner");
        Araclar.ResimDon(context, imgLogo, "logo");

        FrIlan frIlan = new FrIlan();
        frIlan.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.flytIlan, frIlan, "FrIlan").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sag_menu_ilan, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void Kapat() {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menuCikis:
                alertKapat.show();
                return true;
            case R.id.menuIlan:
                FrIlan myFragment = (FrIlan) getSupportFragmentManager().findFragmentByTag("FrIlan");
                if (myFragment == null || !myFragment.isVisible()) {
                    FrIlan frIlan = new FrIlan();
                    frIlan.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.flytIlan, frIlan, "FrIlan").addToBackStack("FrIlan").commit();
                    return true;
                }
                else {
                    return false;
                }
            case R.id.menuOzellikler:
                FrOzellikler frOzellikler = new FrOzellikler();
                frOzellikler.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.flytIlan, frOzellikler, "FrOzellikler").addToBackStack("FrOzellikler").commit();
                return true;
            case R.id.menuGaleri:
                FrGaleri frGaleri = new FrGaleri();
                frGaleri.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.flytIlan, frGaleri, "FrGaleri").addToBackStack("FrGaleri").commit();
                return true;
            case R.id.menuHarita:
                FrHarita frHarita = new FrHarita();
                frHarita.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.flytIlan, frHarita, "FrHarita").addToBackStack("FrHarita").commit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}