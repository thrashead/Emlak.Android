package com.sinasalik.thrashead.emlak.Arac;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sinasalik.thrashead.emlak.R;

import com.sinasalik.thrashead.tdmenu.TDMenu;

public class MenuHazirla {
    public static void MenuHazirla(Activity activity) {
        SagMenuHazirla(activity);
        SolMenuHazirla(activity);
    }

    private static void SagMenuHazirla(Activity activity) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        ((AppCompatActivity) activity).setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer, toolbar, R.string.nav_hint_ac, R.string.nav_hint_kapat);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private static void SolMenuHazirla(Activity activity) {
        TDMenu menuIcerikMenu = (TDMenu) activity.findViewById(R.id.menuIcerikMenu);
        TDMenu menuKategoriMenu = (TDMenu) activity.findViewById(R.id.menuKategoriMenu);
        TDMenu menuKategoriTipMenu = (TDMenu) activity.findViewById(R.id.menuKategoriTipMenu);
        TDMenu menuAltMenu = (TDMenu) activity.findViewById(R.id.menuAltMenu);

        menuIcerikMenu.MenuListe = Menu.MenuDoldur(activity, Menu.MenuTip.Icerik);
        menuKategoriMenu.MenuListe = Menu.MenuDoldur(activity, Menu.MenuTip.Kategori);
        menuKategoriTipMenu.MenuListe = Menu.MenuDoldur(activity, Menu.MenuTip.KategoriTip);
        menuAltMenu.MenuListe = Menu.MenuDoldur(activity, Menu.MenuTip.AltMenu);

        menuIcerikMenu.MenuDoldur();
        menuKategoriMenu.MenuDoldur();
        menuKategoriTipMenu.MenuDoldur();
        menuAltMenu.MenuDoldur();
    }
}
