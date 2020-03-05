package com.foodies.customer.ActivitiesAndFragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.foodies.customer.Adapters.RestaurantsAdapter;
import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Utils.NotificationUtils;
import com.foodies.customer.R;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class MainActivity extends FragmentActivity {
    private PagerMainActivity mCurrentFrag;
    public static Context context;
    private long mBackPressed;
    public static SharedPreferences sPre;
    public static boolean FLAG_MAIN;
    public static RecyclerView.LayoutManager recyclerViewlayoutManager;
    public static RestaurantsAdapter recyclerViewadapter;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
         sPre = getSharedPreferences(PreferenceClass.user,MODE_PRIVATE);


        FLAG_MAIN = true;
        context = MainActivity.this;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        AllConstants.width= size.x;
        AllConstants.height = size.y;


        mCurrentFrag = new PagerMainActivity();
        if(mCurrentFrag!=null) {
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, mCurrentFrag).commit();

        }

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                     FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);


                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {

                    String message = intent.getStringExtra("message");

                    sendMyNotification(message);

                    sendBroadcast(new Intent("newOrder"));
                }

            }
        };


    }


    @Override
    public void onBackPressed() {
        if (!mCurrentFrag.onBackPressed()) {
            int count = this.getSupportFragmentManager().getBackStackEntryCount();
            if (count == 0) {
                if (mBackPressed + 2000 > System.currentTimeMillis()) {
                    super.onBackPressed();
                    return;
                } else {
                    Toast.makeText(getBaseContext(), "Tap Again To Exit", Toast.LENGTH_SHORT).show();

                    mBackPressed = System.currentTimeMillis();

                }
            } else {
                super.onBackPressed();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RestaurantMenuItemsFragment.PERMISSION_DATA_CART_ADED) {

            if(resultCode == RESULT_OK) {
                Intent intent = new Intent();
                intent.setAction("AddToCart");
                sendBroadcast(intent);
                SearchFragment.FLAG_COUNTRY_NAME = true;
            }
        }
    }

    @Override
    protected void onResume() {
        // Other onResume() code here

        super.onResume();


        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

         LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));
         NotificationUtils.clearNotifications(getApplicationContext());

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();

    }


    private void sendMyNotification(String message) {
        //On click of notification it redirect to this Activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel("channel-01",
                    "Foodomia",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"channel-01");
        builder.setSmallIcon(R.drawable.app_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon))
                .setColor(ContextCompat.getColor(this,R.color.colorRed))
                .setContentTitle("Foodomia")
                .setContentIntent(pendingIntent)
                .setContentText(String.format(message))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setChannelId("channel-01");

        notificationManager.notify(0, builder.build());

    }

}

