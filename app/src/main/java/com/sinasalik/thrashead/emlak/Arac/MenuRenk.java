package com.sinasalik.thrashead.emlak.Arac;

import android.content.Context;

import com.sinasalik.thrashead.emlak.R;

public class MenuRenk {
    public enum RenkTip {
        ArkaPlan,
        Metin,
    }

    public static int RenkDon(Context context, RenkTip renkTip, int seviye) {
        switch (renkTip) {
            case ArkaPlan:
                switch (seviye) {
                    case 1:
                        return context.getResources().getColor(R.color.menuSeviye1);
                    case 2:
                        return context.getResources().getColor(R.color.menuSeviye2);
                    case 3:
                        return context.getResources().getColor(R.color.menuSeviye3);
                    case 4:
                        return context.getResources().getColor(R.color.menuSeviye4);
                    case 5:
                        return context.getResources().getColor(R.color.menuSeviye5);
                    case 6:
                        return context.getResources().getColor(R.color.menuSeviye6);
                    case 7:
                        return context.getResources().getColor(R.color.menuSeviye7);
                    case 8:
                        return context.getResources().getColor(R.color.menuSeviye8);
                    case 9:
                        return context.getResources().getColor(R.color.menuSeviye9);
                    case 10:
                        return context.getResources().getColor(R.color.menuSeviye10);
                }
            case Metin:
                switch (seviye) {
                    case 1:
                        return context.getResources().getColor(R.color.menuTextSeviye1);
                    case 2:
                        return context.getResources().getColor(R.color.menuTextSeviye2);
                    case 3:
                        return context.getResources().getColor(R.color.menuTextSeviye3);
                    case 4:
                        return context.getResources().getColor(R.color.menuTextSeviye4);
                    case 5:
                        return context.getResources().getColor(R.color.menuTextSeviye5);
                    case 6:
                        return context.getResources().getColor(R.color.menuTextSeviye6);
                    case 7:
                        return context.getResources().getColor(R.color.menuTextSeviye7);
                    case 8:
                        return context.getResources().getColor(R.color.menuTextSeviye8);
                    case 9:
                        return context.getResources().getColor(R.color.menuTextSeviye9);
                    case 10:
                        return context.getResources().getColor(R.color.menuTextSeviye10);
                }
            default:
                switch (seviye) {
                    case 1:
                        return context.getResources().getColor(R.color.menuSeviye1);
                    case 2:
                        return context.getResources().getColor(R.color.menuSeviye2);
                    case 3:
                        return context.getResources().getColor(R.color.menuSeviye3);
                    case 4:
                        return context.getResources().getColor(R.color.menuSeviye4);
                    case 5:
                        return context.getResources().getColor(R.color.menuSeviye5);
                    case 6:
                        return context.getResources().getColor(R.color.menuSeviye6);
                    case 7:
                        return context.getResources().getColor(R.color.menuSeviye7);
                    case 8:
                        return context.getResources().getColor(R.color.menuSeviye8);
                    case 9:
                        return context.getResources().getColor(R.color.menuSeviye9);
                    case 10:
                        return context.getResources().getColor(R.color.menuSeviye10);
                }
        }
        return 0;
    }
}
