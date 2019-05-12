package com.sinasalik.thrashead.emlak;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;

import com.sinasalik.thrashead.emlak.Arac.MenuHazirla;
import com.sinasalik.thrashead.emlak.Emlak.Araclar;

public class SayfaIcerik extends AppCompatActivity {
    private Context context = this;
    private AlertDialog.Builder alertKapat;

    private ImageView imgBanner, imgLogo;

    private Bundle bundle;
    private String contenturl;
    private WebView wvIcerik;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bunu eklersen network thread sorunu gider json veri çekerken.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.aktivite_icerik);

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

        wvIcerik = (WebView) findViewById(R.id.wvIcerik);

        bundle = getIntent().getExtras();

        if (bundle.getString("parametre") != null) {
            contenturl = bundle.getString("parametre");
        }
    }

    private void Olaylar() {
        MenuHazirla.MenuHazirla(this);

        Araclar.ResimDon(context, imgBanner, "banner");
        Araclar.ResimDon(context, imgLogo, "logo");

        Araclar.IcerikDoldur(context, contenturl, wvIcerik, Araclar.IcerikTip.Icerik, false);
    }

    public void Kapat() {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sag_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menuCikis:
                alertKapat.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
