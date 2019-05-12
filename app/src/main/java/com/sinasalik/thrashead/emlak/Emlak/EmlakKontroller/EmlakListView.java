package com.sinasalik.thrashead.emlak.Emlak.EmlakKontroller;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinasalik.thrashead.emlak.R;
import com.sinasalik.thrashead.emlak.SayfaHaber;

import java.util.ArrayList;

public class EmlakListView extends LinearLayout {
    private Context context;

    private float scale;
    private int pixel;

    public String Baslik;
    public boolean Cizgili;

    private EmlakIcerikNesne nesneData;
    private ArrayList<EmlakIcerikNesne> nesneList;

    public EmlakListView(Context context) {
        super(context);
        init(null);
    }

    public EmlakListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(@Nullable final AttributeSet attrs) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EmlakListView, 0, 0);

        Baslik = a.getString(R.styleable.EmlakListView_listeBaslik);
        Cizgili = a.getBoolean(R.styleable.EmlakListView_listeCizgili, false);

        context = getContext();
        nesneData = new EmlakIcerikNesne();
        nesneList = new ArrayList<>();

        scale = context.getResources().getDisplayMetrics().density;
        pixel = (int) (1 * scale + 0.5f);
    }

    public void ListeDoldur(ArrayList<EmlakIcerikNesne> liste) {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 5 * pixel);

        if(Baslik != null) {
            final TextView lblBaslik = new TextView(context);
            lblBaslik.setLayoutParams(params);
            lblBaslik.setPadding(5 * pixel, 5 * pixel, 5 * pixel, 5 * pixel);
            lblBaslik.setTypeface(null, Typeface.BOLD);
            lblBaslik.setText(Baslik);

            addView(lblBaslik);
        }

        for (EmlakIcerikNesne nesne : liste) {
            final EmlakListItem lblNesne = new EmlakListItem(context);
            lblNesne.setLayoutParams(params);

            if(Cizgili) {
                lblNesne.setBackgroundResource(R.drawable.kenarlik_gri);
            }

            lblNesne.setPadding(5 * pixel, 5 * pixel, 5 * pixel, 5 * pixel);

            lblNesne.setText(nesne.Baslik);

            if(Baslik != null) {
                lblNesne.Baslik = nesne.Baslik;
            }

            lblNesne.Url = nesne.Url;

            lblNesne.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SayfaHaber.class);
                    intent.putExtra("url", lblNesne.Url);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            addView(lblNesne);
        }
    }
}
