package com.sinasalik.thrashead.emlak.Emlak.EmlakKontroller;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.sinasalik.thrashead.emlak.R;

public class EmlakIcerikView extends LinearLayout {

    private Context context;

    private float scale;
    private int pixel;

    public String Baslik;

    private EmlakIcerikNesne nesneData;

    public EmlakIcerikView(Context context) {
        super(context);
        init(null);
    }

    public EmlakIcerikView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(@Nullable final AttributeSet attrs) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EmlakIcerikView, 0, 0);

        Baslik = a.getString(R.styleable.EmlakIcerikView_icerikBaslik);

        context = getContext();
        nesneData = new EmlakIcerikNesne();

        scale = context.getResources().getDisplayMetrics().density;
        pixel = (int) (1 * scale + 0.5f);
    }

    public void IcerikDoldur(EmlakIcerikNesne icerik) {

    }
}
