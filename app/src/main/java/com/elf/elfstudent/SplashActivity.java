package com.elf.elfstudent;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.elf.elfstudent.Activities.EmailActivityPage;
import com.elf.elfstudent.Activities.HomeActivity;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Utils.ScreenUtil;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;


/*This First Activty that gets launcehd ,
*
* Redirets to Register Activity if first time else normal Activty*/

public class SplashActivity extends AppCompatActivity {



    @BindView(R.id.splash_img)
    ImageView mSplashImage;
    private static final String TAG = "ELF";
    private static final long SPLASH_TIME_OUT = 2000;
    private DataStore dataStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

//        loadBitmap(R.id.splash_img,mSplashImage);


        mSplashImage.setImageResource(R.drawable.splah_new_svg);
        dataStore = DataStore.getStorageInstance(getApplicationContext());


        //Check whether user already logged in or not
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                if (isFirstTime()){
                    Log.d(TAG, "onCreate: going to Registration");
                    Intent i = new Intent(getApplicationContext(),EmailActivityPage.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
                else{
                    Log.d(TAG, "goint to Home Activity");
                    Intent i = new Intent(getApplicationContext(),HomeActivity.class);

                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }
                finish();
            }
        }, SPLASH_TIME_OUT);



    }

    public void loadBitmap(int resId, ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(resId);
    }


    private boolean isFirstTime() {

        Log.d(TAG, "isFirstTime: "+dataStore.isFirstTime());
        return dataStore.isFirstTime();
    }


    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private int data = 0;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            return decodeSampledBitmapFromResource(getResources(), data,
                    ScreenUtil.getScreenWidth(getApplication()), ScreenUtil.getScreenHeight(getApplicationContext()));
        }

        private Bitmap decodeSampledBitmapFromResource(Resources resources, int data, int i, int i1) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            BitmapFactory.decodeResource(resources, data, options);
//
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, i, i1);

            // Decode bitmap with inSampleSize set
//            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(resources, data, options);
        }

        private int calculateInSampleSize(BitmapFactory.Options options, int i, int i1) {
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > i1 || width > i) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) >= i1
                        && (halfWidth / inSampleSize) >= i) {
                    inSampleSize *= 2;
                }
            }

            return inSampleSize;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
