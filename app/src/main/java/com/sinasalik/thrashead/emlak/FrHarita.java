package com.sinasalik.thrashead.emlak;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class FrHarita extends Fragment implements OnMapReadyCallback {
    private Context context;
    private View view;

    private GoogleMap mMap;
    private Location lokasyon;

    private double benEnlem;
    private double benBoylam;
    private double emlakEnlem;
    private double emlakBoylam;

    private boolean kontrol;
    private String ilanurl, baslik, enlem, boylam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_harita, container, false);

        SayfaHazirla();

        return view;
    }

    private void SayfaHazirla() {
        Nesneler();
        Olaylar();
    }

    private void Nesneler() {
        context = getActivity().getApplicationContext();

        Bundle bundle = getActivity().getIntent().getExtras();
        kontrol = true;

        if (bundle.getString("ilanurl") != null) {
            ilanurl = bundle.getString("ilanurl");
        }

        if (bundle.getString("enlem") != null) {
            enlem = bundle.getString("enlem");
            emlakEnlem = Double.valueOf(enlem);
        } else {
            kontrol = false;
        }

        if (bundle.getString("boylam") != null) {
            boylam = bundle.getString("boylam");
            emlakBoylam = Double.valueOf(boylam);
        } else {
            kontrol = false;
        }

        if (bundle.getString("baslik") != null) {
            baslik = bundle.getString("baslik");
        }
    }

    private void Olaylar() {
        if (kontrol == true) {
            lokasyon = KonumumuGetir();

            if (lokasyon != null) {
                benEnlem = lokasyon.getLatitude();
                benBoylam = lokasyon.getLongitude();
            }

            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frHarita);
            mapFragment.getMapAsync(this);
        }
    }

    Location KonumumuGetir() {
        LocationManager yonetici = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria kriter = new Criteria();
        String servisSaglayici = yonetici.getBestProvider(kriter, false);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        return yonetici.getLastKnownLocation(servisSaglayici);
    }

    private void Isaretle(String baslik, double latitude, double longitude) {
        MarkerOptions mOpt = new MarkerOptions();
        mOpt.title(baslik);
        mOpt.draggable(true);
        mOpt.position(new LatLng(latitude, longitude));
        mMap.addMarker(mOpt);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mMap != null) {
            LatLng benimYerim = new LatLng(benEnlem, benBoylam);
            LatLng emlakYeri = new LatLng(emlakEnlem, emlakBoylam);

            mMap.addMarker(new MarkerOptions()
                    .position(benimYerim)
                    .title("Siz Buradasınız"));

            mMap.addMarker(new MarkerOptions()
                    .position(emlakYeri)
                    .title(baslik)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.maphome)));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(emlakYeri, 13));

            //Çizgi çekmek için
            PolylineOptions options = new PolylineOptions()
                    .add(emlakYeri)
                    .add(benimYerim)
                    .width(3)
                    .color(Color.YELLOW)
                    .visible(true)
                    .geodesic(true);

            mMap.addPolyline(options);

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            UiSettings uiSettings = googleMap.getUiSettings();
            //Pusula gösterilsin
            uiSettings.setCompassEnabled(true);
            //Zoom gösterilsin
            uiSettings.setZoomControlsEnabled(true);
            //Bulunduğum konumu gösteren buton görünür
            uiSettings.setMyLocationButtonEnabled(true);
        }
    }
}