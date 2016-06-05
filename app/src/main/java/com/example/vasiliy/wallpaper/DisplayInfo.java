package com.example.vasiliy.wallpaper;

import android.util.DisplayMetrics;

/**
 * Created by vasiliy on 05.06.16.
 */
public class DisplayInfo {
    public static final int WIDTH_MEDIUM = 360;
    public static final int HEIGHT_MEDIUM = 640;
    public static final int WIDTH_HIGH = 540;
    public static final int HEIGHT_HIGH = 960;
    public static final int WIDTH_XHIGH = 720;
    public static final int HEIGHT_XHIGH = 1280;
    public static final int WIDTH_XXHIGH = 1080;
    public static final int HEIGHT_XXHIGH = 1920;

    static boolean isCorrespondsToTheDensityResolution(int width, int height) {
        int tempXdpi = 0;
        int tempYdpi = 0;
        switch(width) {
            case WIDTH_MEDIUM:
                tempXdpi = DisplayMetrics.DENSITY_MEDIUM;
                break;
            case WIDTH_HIGH:
                tempXdpi = DisplayMetrics.DENSITY_HIGH;
                break;
            case WIDTH_XHIGH:
                tempXdpi = DisplayMetrics.DENSITY_XHIGH;
                break;
            case WIDTH_XXHIGH:
                tempXdpi = DisplayMetrics.DENSITY_XXHIGH;
                break;
        }
        switch(height) {
            case HEIGHT_MEDIUM:
                tempXdpi = DisplayMetrics.DENSITY_MEDIUM;
                break;
            case HEIGHT_HIGH:
                tempXdpi = DisplayMetrics.DENSITY_HIGH;
                break;
            case HEIGHT_XHIGH:
                tempXdpi = DisplayMetrics.DENSITY_XHIGH;
                break;
            case HEIGHT_XXHIGH:
                tempXdpi = DisplayMetrics.DENSITY_XXHIGH;
                break;
        }
        if(tempXdpi == tempYdpi) {
            return true;
        } else {
            return false;
        }
    }

}
