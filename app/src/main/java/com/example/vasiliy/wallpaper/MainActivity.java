package com.example.vasiliy.wallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends FragmentActivity {

    ViewPager pager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.btnSetWallpaper)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWallpaperToBackground();
            }
        });

        ((ImageButton) findViewById(R.id.btnExitFromMyApp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new MyFragmentPageAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    void setWallpaperToBackground() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int dispayWidth = metrics.widthPixels;
        int dispayHeight = metrics.heightPixels;

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        wallpaperManager.suggestDesiredDimensions(dispayWidth, dispayHeight);
        if(DisplayInfo.isCorrespondsToTheDensityResolution(dispayWidth, dispayHeight)) {
            Bitmap tempbitMap = BitmapFactory.decodeResource(getResources(), Wallpapers.images[pager.getCurrentItem()]);
            Bitmap bitmap = Bitmap.createScaledBitmap(tempbitMap, dispayWidth, dispayHeight, true);
            try {
                wallpaperManager.setBitmap(bitmap);

                Context context = getApplicationContext();
                CharSequence text = "Обои успешно установлены!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*
            Context context = getApplicationContext();
            CharSequence text = "TRUE";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            */
        } else {
            try {
                wallpaperManager.setResource(Wallpapers.images[pager.getCurrentItem()]);

                Context context = getApplicationContext();
                CharSequence text = "Обои успешно установлены!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*
            Context context = getApplicationContext();
            CharSequence text = "FALSE";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            */
        }
    }

    private class MyFragmentPageAdapter extends FragmentPagerAdapter {

        private int[] images = Wallpapers.images;

        private int imagesCount = images.length;

        public MyFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return PageFragment.newInstance(images[i]);
        }

        @Override
        public int getCount() {
            return imagesCount;
        }

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            if (hasFocus) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }
}

