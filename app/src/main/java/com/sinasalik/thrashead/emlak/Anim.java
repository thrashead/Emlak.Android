package com.sinasalik.thrashead.emlak;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.sinasalik.thrashead.emlak.Emlak.Araclar;
import com.sinasalik.thrashead.tdlibrary.TDAraclar;

public class Anim extends AppCompatActivity {
    private Context context = this;
    private ImageView imgLogo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim);

        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        Araclar.ResimDon(context, imgLogo, "logo");

        SayfaGec();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SayfaGec();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SayfaGec();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SayfaGec();
    }

    public void Kapat() {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @SuppressWarnings("deprecation")
    public void SayfaGec() {
        if (TDAraclar.InternetVarmi(getBaseContext()) == false) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                    .setTitle("İnternet Kontrol")
                    .setIcon(R.drawable.exit_icon)
                    .setMessage("İnternet olmadığı için program başlatılamayacaktır.")
                    .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Kapat();
                }
            });
            alertDialog.show();
        } else {
            Thread acilis = new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(3000);
                        startActivity(new Intent(context, SayfaAnaSayfa.class));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            acilis.start();
        }
    }
}
