package com.foodies.customer.ActivitiesAndFragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.R;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class RiderReviewActivity extends AppCompatActivity {
    SharedPreferences sPref;
    TextView rest_name;
    String restaurant_id,rider_user_id,order_id,rider_name,user_id;
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
       // String type = sPref.getString(PreferenceClass.REVIEW_TYPE,"");

        clos_menu_items_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RiderReviewActivity.this.finish();
                SharedPreferences.Editor editor = sPref.edit();
                editor.putBoolean("isOpen",true).commit();
            }
        });


            rider_name = sPref.getString(PreferenceClass.RIDER_NAME_NOTIFY,"");
            rider_user_id = sPref.getString(PreferenceClass.RIDER_USER_ID_NOTIFY,"");
            order_id = sPref.getString(PreferenceClass.ORDER_ID_NOTIFY,"");

            rest_name = findViewById(R.id.rest_name);
            rest_name.setText(rider_name);


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
                    Toast.makeText(RiderReviewActivity.this,"Please select at-least ONE star.",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void postReview(){

        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("user_id",user_id);
            jsonObject.put("rider_user_id",""+rider_user_id);
            jsonObject.put("comment",ed_message.getText().toString());
            jsonObject.put("order_id",""+order_id);
            jsonObject.put("star",""+reviewRatingBar.getRating());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ApiRequest.Call_Api(this, Config.GiveRatingsToRider, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {


                try {
                    JSONObject jsonObject1 = new JSONObject(resp);
                    int code = Integer.parseInt(jsonObject1.optString("code"));
                    if(code==200){

                        Toast.makeText(RiderReviewActivity.this,"Thanks for review",Toast.LENGTH_SHORT).show();
                        RiderReviewActivity.this.finish();
                        SharedPreferences.Editor editor = sPref.edit();
                        editor.putBoolean("isOpen",true).commit();
                        transparent_layer.setVisibility(View.GONE);
                        progressDialog.setVisibility(View.GONE);
                    }
                    else {

                        // Else Part
                        transparent_layer.setVisibility(View.GONE);
                        progressDialog.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    transparent_layer.setVisibility(View.GONE);
                    progressDialog.setVisibility(View.GONE);
                }


            }
        });

    }

}
