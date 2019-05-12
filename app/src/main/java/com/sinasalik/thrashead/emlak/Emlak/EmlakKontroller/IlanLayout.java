package com.sinasalik.thrashead.emlak.Emlak.EmlakKontroller;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinasalik.thrashead.emlak.R;
import com.sinasalik.thrashead.emlak.SayfaIlan;

import java.util.ArrayList;

import com.sinasalik.thrashead.tdlibrary.TDImageView;
import com.sinasalik.thrashead.emlak.Emlak.Araclar;
import com.sinasalik.thrashead.emlak.Emlak.RealEstateData.RealEstateData;

public class IlanLayout extends LinearLayout {
    private Context context;

    public String ID;
    public String Baslik;
    public String Kelime;
    public Integer Adet;
    public boolean BaslikEkle;
    public IlanTipi IlanTip;
    private GoruntulemeTipi GoruntulemeTip;

    private float scale;
    private int pixel;

    private RealEstateData emlakData;
    private ArrayList<RealEstateData> emlakList;

    public IlanLayout(Context context) {
        super(context);
        init(null);
    }

    public IlanLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(@Nullable final AttributeSet attrs) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.IlanLayout, 0, 0);

        context = getContext();
        emlakData = new RealEstateData();
        emlakList = new ArrayList<>();

        scale = context.getResources().getDisplayMetrics().density;
        pixel = (int) (1 * scale + 0.5f);

        BaslikEkle = a.getBoolean(R.styleable.IlanLayout_ilanBaslikEkle, true);
        Baslik = a.getString(R.styleable.IlanLayout_ilanBaslik);
        Kelime = a.getString(R.styleable.IlanLayout_ilanKelime);
        Adet = a.getInteger(R.styleable.IlanLayout_ilanAdet, 0);
        IlanTip = IlanTip == null ? ilanTipiDon(a.getInt(R.styleable.IlanLayout_ilanTip, 0)) : IlanTip;
        GoruntulemeTip = goruntulemeTipiDon(a.getInt(R.styleable.IlanLayout_ilanGoruntuTip, 0));
    }

    public void LayoutDoldur() {
        if (BaslikEkle && Baslik != null) {
            BaslikEkle();
        }

        switch (IlanTip) {
            case Gunun:
                emlakData = Araclar.GununIlani(context);

                addView(LayoutTekil(emlakData));
                return;
            case Kelime:
                emlakList = Araclar.Kelime(context, Kelime, Adet);

                if (emlakList.size() > 0) {
                    Baslik = emlakList.get(0).getKategoriAdi();
                    BaslikEkle();
                }
                break;
            case Kategori:
                emlakList = Araclar.Kategori(context, Kelime, Adet);

                if (emlakList.size() > 0) {
                    Baslik = emlakList.get(0).getKategoriAdi();
                    BaslikEkle();
                }
                break;
            case Vitrin:
                emlakList = Araclar.Vitrin(context, Adet);
                break;
            case Yeni:
                emlakList = Araclar.Yeni(context, Adet);
                break;
            case Rasgele:
                emlakList = Araclar.Rasgele(context, Adet);
                break;
        }

        if (GoruntulemeTip == GoruntulemeTipi.Liste) {
            addView(LayoutListe(emlakList));
        } else if (GoruntulemeTip == GoruntulemeTipi.Tek && emlakList.size() > 0) {
            addView(LayoutTekil(emlakList.get(0)));
        }
    }

    /* Metodlar */
    private LinearLayout LayoutTekil(final RealEstateData emlak) {
        /* Dış Layout */
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        );
        params.gravity = Gravity.CENTER;

        LinearLayout ilanLayout = new LinearLayout(context);
        ilanLayout.setLayoutParams(params);
        ilanLayout.setBackgroundResource(R.drawable.kenarlik_gri);
        ilanLayout.setOrientation(LinearLayout.VERTICAL);
        ilanLayout.setPadding(10 * pixel, 10 * pixel, 10 * pixel, 10 * pixel);
        /* Dış Layout */

        /* Yeni mi? */
        if (emlak.getYeni().equals("1")) {
            params = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.CENTER;
            RelativeLayout relLayout = new RelativeLayout(context);
            relLayout.setLayoutParams(params);

            /* Resim */
            ImageView imageView = new ImageView(context);
            params = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    80 * pixel
            );
            params.gravity = Gravity.CENTER_HORIZONTAL;
            imageView.setLayoutParams(params);
            new TDImageView(imageView).execute(emlak.getResim());
            relLayout.addView(imageView);
            /* Resim */

            ImageView imageYeni = new ImageView(context);
            params = new LayoutParams(
                    30 * pixel,
                    30 * pixel
            );
            imageYeni.setLayoutParams(params);

            imageYeni.setImageResource(R.mipmap.yeni);
            relLayout.addView(imageYeni);

            ilanLayout.addView(relLayout);
        }
        /* Yeni mi? */
        else {
            /* Resim */
            ImageView imageView = new ImageView(context);
            params = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    80 * pixel
            );
            params.gravity = Gravity.CENTER_HORIZONTAL;
            imageView.setLayoutParams(params);
            new TDImageView(imageView).execute(emlak.getResim());
            ilanLayout.addView(imageView);
            /* Resim */
        }
        /* ID */
        TextView textView = new TextView(context);
        textView.setText(emlak.getID());
        textView.setLayoutParams(params);
        textView.setVisibility(GONE);
        ilanLayout.addView(textView);
        /* ID */

        /* İlan İsmi */
        params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                60 * pixel
        );
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(0, 5 * pixel, 0, 5 * pixel);
        textView.setLayoutParams(params);
        textView.setText(emlak.getBaslik());
        ilanLayout.addView(textView);
        /* İlan İsmi */

        /* Fiyat */
        params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                30 * pixel
        );
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setText(emlak.getFiyat());
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(params);
        ilanLayout.addView(textView);
        /* Fiyat */

        /* Detay Buton */
        params = new LayoutParams(
                50 * pixel,
                20 * pixel
        );
        params.gravity = Gravity.CENTER;

        ImageButton imageButton = new ImageButton(context);
        imageButton.setLayoutParams(params);
        imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
        imageButton.setBackgroundResource(R.mipmap.detayicon);
        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SayfaIlan.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ilanurl", emlak.getUrl());
                intent.putExtra("baslik", emlak.getBaslik());
                intent.putExtra("enlem", emlak.getEnlem());
                intent.putExtra("boylam", emlak.getBoylam());
                context.startActivity(intent);
            }
        });

        ilanLayout.addView(imageButton);
        ilanLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SayfaIlan.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ilanurl", emlak.getUrl());
                intent.putExtra("baslik", emlak.getBaslik());
                intent.putExtra("enlem", emlak.getEnlem());
                intent.putExtra("boylam", emlak.getBoylam());
                context.startActivity(intent);
            }
        });
        /* Detay Buton */

        return ilanLayout;
    }

    private LinearLayout LayoutListe(ArrayList<RealEstateData> emlaklar) {
        BaslikEkle = false;

        int i = 0;
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        );

        LinearLayout enDisLayout = new LinearLayout(context);
        enDisLayout.setLayoutParams(params);
        enDisLayout.setOrientation(VERTICAL);

        LinearLayout disLayout = null;

        for (RealEstateData emlak : emlakList) {
            int mod = i % 2;

            if (mod == 0) {
                disLayout = new LinearLayout(context);
                params = new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT
                );
                disLayout.setLayoutParams(params);
                disLayout.setWeightSum(1f);
                disLayout.setOrientation(HORIZONTAL);
            }

            LinearLayout ilanLayout = LayoutTekil(emlak);
            params = new LayoutParams(
                    0 * pixel,
                    LayoutParams.MATCH_PARENT
            );
            params.gravity = Gravity.CENTER;
            params.weight = 0.5f;
            ilanLayout.setLayoutParams(params);

            disLayout.addView(ilanLayout);

            if (mod == 1) {
                enDisLayout.addView(disLayout);
            }

            i++;
        }

        if ((Adet % 2) == 1) {
            enDisLayout.addView(disLayout);
        }

        return enDisLayout;
    }

    private void BaslikEkle() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        params.bottomMargin = -1 * pixel;
        params.gravity = Gravity.LEFT;

        TextView lblBaslik = new TextView(context);
        lblBaslik.setText(Baslik);
        lblBaslik.setTypeface(null, Typeface.BOLD);
        lblBaslik.setLayoutParams(params);
        lblBaslik.setBackgroundResource(R.drawable.kenarlik_gri);
        lblBaslik.setPadding((10 * pixel), (10 * pixel), (10 * pixel), (10 * pixel));
        addView(lblBaslik);
    }

    /* Enumlar */
    public enum IlanTipi {
        Rasgele,
        Yeni,
        Vitrin,
        Gunun,
        Kategori,
        Kelime
    }

    private IlanTipi ilanTipiDon(int id) {
        switch (id) {
            case 0:
                return IlanTipi.Kelime;
            case 1:
                return IlanTipi.Yeni;
            case 2:
                return IlanTipi.Vitrin;
            case 3:
                return IlanTipi.Gunun;
            case 4:
                return IlanTipi.Rasgele;
            case 5:
                return IlanTipi.Kategori;
            default:
                return IlanTipi.Kelime;
        }
    }

    private enum GoruntulemeTipi {
        Tek,
        Liste
    }

    private GoruntulemeTipi goruntulemeTipiDon(int id) {
        switch (id) {
            case 0:
                return GoruntulemeTipi.Tek;
            case 1:
                return GoruntulemeTipi.Liste;
            default:
                return GoruntulemeTipi.Tek;
        }
    }

    /* Özellikler */
    public GoruntulemeTipi getGoruntulemeTipi() {
        return GoruntulemeTip;
    }

    public void setGoruntulemeTipi(GoruntulemeTipi goruntulemeTip) {
        this.GoruntulemeTip = goruntulemeTip;
    }

    public IlanTipi getIlanTipi() {
        return IlanTip;
    }

    public void setIlanTipi(IlanTipi ilanTip) {
        this.IlanTip = ilanTip;
    }

    public String getID() {
        return ID;
    }

    public void setID(String id) {
        this.ID = id;
    }

    public String getKelime() {
        return Kelime;
    }

    public void setKelime(String kelime) {
        this.Kelime = kelime;
    }

    public Integer getAdet() {
        return Adet;
    }

    public void setAdet(Integer adet) {
        this.Adet = adet;
    }

    public Boolean getBaslikEkle() {
        return BaslikEkle;
    }

    public void setBaslikEkle(Boolean baslikEkle) {
        this.BaslikEkle = baslikEkle;
    }
}
