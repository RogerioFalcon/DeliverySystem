package com.foodies.customer.Notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.foodies.customer.ActivitiesAndFragments.RestReveiwActivity;
import com.foodies.customer.ActivitiesAndFragments.RiderReviewActivity;
import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.foodies.customer.ActivitiesAndFragments.MainActivity;


import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    String channelId = "channel-01";

    String restaurant_id,restaurant_name,imageUrl,timestamp,rider_name,order_id,rider_user_id;

    SharedPreferences sPref;
    private LocalBroadcastManager broadcaster;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        sPref = getSharedPreferences(PreferenceClass.user,MODE_PRIVATE);

        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

      /*  // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }
*/
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        if(s==null){

        }else if(s.length()>8){
            storeRegIdInPref(s);

            Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
            registrationComplete.putExtra("token", s);
            LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
        }
    }



    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(PreferenceClass.user, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PreferenceClass.device_token, token);
        editor.commit();
    }


    private void handleDataMessage(JSONObject json) {


        Log.e(AllConstants.tag , json.toString());
        SharedPreferences.Editor editor = sPref.edit();

        try {
          //  JSONObject data = json.getJSONObject("data");

            if(!json.getString("type").isEmpty()) {
                String type = json.getString("type");

                if (type.equalsIgnoreCase("order_review")) {

                    Log.d(AllConstants.tag,type);

                    restaurant_id = json.optString("restaurant_id");
                    restaurant_name = json.optString("restaurant_name");
                    imageUrl = json.optString("img");

                    order_id = json.optString("order_id");

                    editor.putString(PreferenceClass.RESTAURANT_ID_NOTIFY, restaurant_id);
                    editor.putString(PreferenceClass.RESTAURANT_NAME_NOTIFY, restaurant_name);
                    editor.putString(PreferenceClass.REVIEW_IMG_PIC,imageUrl);
                    editor.putString(PreferenceClass.ORDER_ID_NOTIFY, order_id);
                    editor.putString(PreferenceClass.REVIEW_TYPE, "order_review");

                    editor.commit();

                    Intent restIntent = new Intent(this, RestReveiwActivity.class);
                    restIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(restIntent);

                    Intent intent = new Intent("MyData");
                    intent.putExtra("type", type);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);


                } else if(type.equalsIgnoreCase("rider_review")) {

                    Log.d(AllConstants.tag,type);

                    rider_user_id = json.optString("rider_user_id");
                    order_id = json.optString("order_id");
                    rider_name = json.optString("rider_name");

                    editor.putString(PreferenceClass.RIDER_USER_ID_NOTIFY, rider_user_id);
                    editor.putString(PreferenceClass.ORDER_ID_NOTIFY, order_id);
                    editor.putString(PreferenceClass.RIDER_NAME_NOTIFY, rider_name);
                    editor.putString(PreferenceClass.REVIEW_TYPE, "rider_review");
                    editor.commit();

                    Intent riderIntent = new Intent(this, RiderReviewActivity.class);
                    riderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(riderIntent);
                    Intent intent = new Intent("MyData");
                    intent.putExtra("type", type);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);


                }

            }
            else {
                String title = json.getString("title");

                if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                    // app is in foreground, broadcast the push message
                    Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                    pushNotification.putExtra("message", title);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                    // play notification sound
                    NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext(), channelId);
                    notificationUtils.playNotificationSound();
                } else {
                    // app is in background, show the notification in notification tray
                    Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                    resultIntent.putExtra("message", title);

                    // check for image attachment
                    if (TextUtils.isEmpty(imageUrl)) {
                        showNotificationMessage(getApplicationContext(), title, title, timestamp, resultIntent);
                    } else {
                        // image is present, show notification with image
                        showNotificationMessageWithBigImage(getApplicationContext(), title, title, timestamp, resultIntent, imageUrl);
                    }
                }
            }
            } catch (JSONException e) {
                Log.e(TAG, "Json Exception: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }





    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context,channelId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context,channelId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

}
