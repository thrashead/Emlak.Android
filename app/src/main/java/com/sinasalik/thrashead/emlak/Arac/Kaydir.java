package com.sinasalik.thrashead.emlak.Arac;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Kaydir implements OnTouchListener {

    private final GestureDetector gestureDetector;

    public Kaydir(Context context){
        gestureDetector = new GestureDetector(context, new DokunmaKontrol());
    }

    @Override
    public boolean onTouch(View v, MotionEvent hareket) {
        return gestureDetector.onTouchEvent(hareket);
    }

    public void SagaKaydir() {
    }

    public void SolaKaydir() {
    }

    public void YukariKaydir() {
    }

    public void AsagiKaydir() {
    }

    //lytTempIlan.setOnTouchListener(new Kaydir(context) {
    //    @Override
    //    public void SagaKaydir() {
    //    }
    //
    //    @Override
    //    public void SolaKaydir() {
    //
    //    }
    //});

    private final class DokunmaKontrol extends SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent hareket) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent hareket1, MotionEvent hareket2, float hizX, float hizY) {
            boolean result = false;
            try {
                float diffY = hareket2.getY() - hareket1.getY();
                float diffX = hareket2.getX() - hareket1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(hizX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            SagaKaydir();
                        } else {
                            SolaKaydir();
                        }
                    }
                    result = true;
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(hizY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        AsagiKaydir();
                    } else {
                        YukariKaydir();
                    }
                }
                result = true;

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }
}