package com.foodies.customer.ActivitiesAndFragments;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class RestReveiwActivity extends AppCompatActivity {
    SharedPreferences sPref;
    TextView rest_name;
    String restaurant_id,restaurant_name,imageUrl,order_id,user_id;
    RatingBar reviewRatingBar;
    EditText ed_message;
    RelativeLayout submitBtn;
    private static boolean RIDER_REVIEW;
    float rating_;
    ImageView clos_menu_items_detail;
    CircleImageView rest_img;
    CamomileSpinner progress;
    RelativeLayout transparent_layer,progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_review_alert);
        initUI();
    }

    public void initUI(){
        sPref = getSharedPreferences(PreferenceClass.user,MODE_PRIVATE);
        submitBtn = findViewById(R.id.submitBtn);
        ed_message = findViewById(R.id.ed_message);
        reviewRatingBar = findViewById(R.id.reviewRatingBar);
        clos_menu_items_detail = findViewById(R.id.clos_menu_items_detail);
        rest_img = findViewById(R.id.rest_img);

        progressDialog = findViewById(R.id.progressDialog);
        transparent_layer = findViewById(R.id.transparent_layer);

        progress = findViewById(R.id.addToCartProgress);
        progress.start();

        user_id = sPref.getString(PreferenceClass.pre_user_id,"");
        String type = sPref.getString(PreferenceClass.REVIEW_TYPE,"");

        Log.d(AllConstants.tag,type);

        clos_menu_items_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestReveiwActivity.this.finish();
                SharedPreferences.Editor editor = sPref.edit();
                editor.putBoolean("isOpen",true).commit();
            }
        });

        if(type.equalsIgnoreCase("order_review")){
            RIDER_REVIEW = false;
            restaurant_name = sPref.getString(PreferenceClass.RESTAURANT_NAME_NOTIFY,"");
            restaurant_id = sPref.getString(PreferenceClass.RESTAURANT_ID_NOTIFY,"");
            imageUrl = sPref.getString(PreferenceClass.REVIEW_IMG_PIC,"");
            order_id=sPref.getString(PreferenceClass.ORDER_ID_NOTIFY,"");

            rest_name = findViewById(R.id.rest_name);
            rest_name.setText(restaurant_name);

            Picasso.with(this).load(Config.imgBaseURL+imageUrl).
                    fit().centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.unknown_deal).into(rest_img);

            Log.d(AllConstants.tag,restaurant_name);
            Log.d(AllConstants.tag,Config.imgBaseURL+imageUrl);
        }

        reviewRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating_ = reviewRatingBar.getRating();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(reviewRatingBar.getRating()>0){
                    postReview();

                }
                else {
                    Toast.makeText(RestReveiwActivity.this,"Please select at-least ONE star.",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void postReview(){

        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user_id",""+user_id);
                jsonObject.put("restaurant_id",""+restaurant_id);
                jsonObject.put("comment",""+ed_message.getText().toString());
                jsonObject.put("order_id",""+order_id);
                jsonObject.put("star",""+reviewRatingBar.getRating());
            } catch (JSONException e) {
                e.printStackTrace();
            }



        ApiRequest.Call_Api(this, Config.AddRestaurantRating, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {

                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject1 = new JSONObject(resp);
                    int code = Integer.parseInt(jsonObject1.optString("code"));
                    if(code==200){

                        Toast.makeText(RestReveiwActivity.this,"Thanks for review",Toast.LENGTH_SHORT).show();
                        RestReveiwActivity.this.finish();
                        SharedPreferences.Editor editor = sPref.edit();
                        editor.putBoolean("isOpen",true).commit();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }



}
