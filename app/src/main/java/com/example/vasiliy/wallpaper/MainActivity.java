package com.example.vasiliy.wallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.vasiliy.wallpaper.GIFdecode.GifRun;

import java.io.IOException;

public class MainActivity extends FragmentActivity {

    Button btnSetWallPaper;
    ImageButton btnExit;

    ViewPager pager;
    PagerAdapter pagerAdapter;

    LinearLayout linImg;
    ImageView img;
    Animation animRotate;
    Animation animAlphaVilible;
    Animation animAlphaInvilible;

    float additionalWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linImg = (LinearLayout) findViewById(R.id.linImg);
        img = (ImageView) findViewById(R.id.imageView);
        animRotate = AnimationUtils.loadAnimation(this, R.anim.rotation_proccess);
        animAlphaVilible = AnimationUtils.loadAnimation(this, R.anim.alpha_vilible);
        animAlphaInvilible = AnimationUtils.loadAnimation(this, R.anim.alpha_invilible);

        animAlphaVilible.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                img.startAnimation(animRotate);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linImg.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animAlphaInvilible.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linImg.setVisibility(View.INVISIBLE);
                img.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //img.startAnimation(animation);

        /*


        SurfaceView v = (SurfaceView) findViewById(R.id.surfaceView);
        GifRun w = new GifRun();
        w.LoadGiff(v, this, R.drawable.proc123);
*/

        /*
        additionalWidth = 0;

        ((Button) findViewById(R.id.btnFix)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additionalWidth = 0;

                linImg.startAnimation(animAlphaVilible);
            }
        });

        ((Button) findViewById(R.id.btnFluid1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additionalWidth = (float) 0.1;
                //img.clearAnimation();
                linImg.startAnimation(animAlphaInvilible);
            }
        });

        ((Button) findViewById(R.id.btnFluid2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additionalWidth = (float) 0.4;
            }
        });
        */

        btnSetWallPaper = (Button) findViewById(R.id.btnSetWallpaper);
        btnSetWallPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setWallpaperToBackground();
                (new setWallpaperAsyncTask()).execute();
            }
        });

        btnExit = (ImageButton) findViewById(R.id.btnExitFromMyApp);
        btnExit.setOnClickListener(new View.OnClickListener() {
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

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int dispayWidth = metrics.widthPixels;
        int dispayHeight = metrics.heightPixels;

        Log.d("QWERTY", String.valueOf(dispayWidth));
        Log.d("QWERTY", String.valueOf(dispayHeight));

        //Toast.makeText(getApplicationContext(), String.valueOf(dispayWidth) + " " + String.valueOf(dispayHeight), Toast.LENGTH_LONG).show();

        //wallpaperManager.suggestDesiredDimensions(dispayWidth, dispayHeight);


       if(!DisplayInfo.isCorrespondsToTheDensityResolution(dispayWidth, dispayHeight)) {
           try {
               wallpaperManager.setResource(Wallpapers.images[pager.getCurrentItem()]);



           } catch (IOException e) {
               e.printStackTrace();
           }
       } else {


           Bitmap bitmap = BitmapFactory.decodeResource(getResources(), Wallpapers.images[pager.getCurrentItem()]);
           bitmap = Bitmap.createScaledBitmap(bitmap, dispayWidth, dispayHeight, true);
           //Toast.makeText(getApplicationContext(), "FALSE", Toast.LENGTH_LONG).show();

            try {
                wallpaperManager.setBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }


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

    private class setWallpaperAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            buttonSetEnabled(btnExit, false);
            buttonSetEnabled(btnSetWallPaper, false);
            linImg.startAnimation(animAlphaVilible);
        }

        @Override
        protected void onPostExecute(Void param) {
            super.onPostExecute(param);

            buttonSetEnabled(btnExit, true);
            buttonSetEnabled(btnSetWallPaper, true);
            linImg.startAnimation(animAlphaInvilible);

            Context context = getApplicationContext();
            CharSequence text = "Обои успешно установлены!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        @Override
        protected Void doInBackground(Void... param) {
            setWallpaperToBackground();
            return null;
        }
    }

    void buttonSetEnabled(View view, boolean enabled) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if(enabled == true) {
                view.setAlpha((float) 1.0);
            } else {
                view.setAlpha((float) 0.5);
            }
        }
        view.setEnabled(enabled);
        /*
        if(enabled == true) {
            view.setTextColor(getResources().getColor(R.color.colorWhite));
        } else {
            view.setTextColor(getResources().getColor(R.color.colorGrey));
        }
        */
    }

}

