package com.sinasalik.thrashead.emlak;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinasalik.thrashead.emlak.Emlak.Araclar;
import com.sinasalik.thrashead.emlak.Arac.MenuHazirla;
import com.sinasalik.thrashead.emlak.Emlak.EmlakKontroller.EmlakListView;
import com.sinasalik.thrashead.emlak.Emlak.EmlakKontroller.IlanLayout;
import com.sinasalik.thrashead.emlak.Emlak.Haberler.Haberler;

public class SayfaAnaSayfa extends AppCompatActivity {
    private Context context = this;
    private AlertDialog.Builder alertKapat;

    private ImageView imgBanner, imgLogo, imgHakkimizda, imgHaberler;
    private IlanLayout ilytGunun, ilytRasgele, ilytVitrin, ilytYeni;
    private TextView lblHakkimizda, lblHaberler;
    private WebView wvHakkimizda;
    private EmlakListView emlakHaberListView;

    private Haberler haber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bunu eklersen network thread sorunu gider json veri çekerken.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.aktivite_anasayfa);

        SayfaHazirla();
    }

    private void SayfaHazirla() {
        MenuHazirla.MenuHazirla(this);

        Nesneler();
        Olaylar();

        Araclar.ResimDon(context, imgBanner, "banner");
        Araclar.ResimDon(context, imgLogo, "logo");

        Araclar.ResimDon(context, imgHakkimizda, "hakkimizda");
        Araclar.ResimDon(context, imgHaberler, "haberler");
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

        //Hakkımızda
        imgHakkimizda = (ImageView) findViewById(R.id.imgAnaSayfaHakkimizda);
        wvHakkimizda = (WebView) findViewById(R.id.wvAnaSayfaHakkimizda);
        lblHakkimizda = (TextView) findViewById(R.id.lblAnaSayfaHakkimizda);
        lblHakkimizda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SayfaIcerik.class);
                intent.putExtra("parametre", "hakkimizda");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        //Haberler
        imgHaberler = (ImageView) findViewById(R.id.imgAnaSayfaHaberler);
        emlakHaberListView = (EmlakListView) findViewById(R.id.emlakAnaSayfaHaberListView);
        lblHaberler = (TextView) findViewById(R.id.lblAnaSayfaHaberler);
        lblHaberler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SayfaHaberler.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        haber = new Haberler();

        //IlanLayout
        ilytGunun = (IlanLayout) findViewById(R.id.ilytGunun);
        ilytRasgele = (IlanLayout) findViewById(R.id.ilytRasgele);
        ilytVitrin = (IlanLayout) findViewById(R.id.ilytVitrin);
        ilytYeni = (IlanLayout) findViewById(R.id.ilytYeni);

    }

    private void Olaylar() {
        ilytGunun.LayoutDoldur();
        ilytRasgele.LayoutDoldur();
        ilytVitrin.LayoutDoldur();
        ilytYeni.LayoutDoldur();

        Haberler();
        Hakkimizda();
    }

    private void Haberler() {
        emlakHaberListView.ListeDoldur(haber.HaberListe(context));
    }

    private void Hakkimizda() {
        Araclar.IcerikDoldur(context,"hakkimizda", wvHakkimizda, Araclar.IcerikTip.Icerik, true);
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
            alertKapat.show();
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
