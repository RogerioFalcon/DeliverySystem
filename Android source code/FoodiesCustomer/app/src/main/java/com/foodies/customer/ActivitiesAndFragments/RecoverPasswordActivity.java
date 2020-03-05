package com.foodies.customer.ActivitiesAndFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.R;
import com.foodies.customer.Utils.FontHelper;
import com.gmail.samehadar.iosdialog.CamomileSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecoverPasswordActivity extends AppCompatActivity implements View.OnClickListener {


    ViewFlipper viewFlipper;
    EditText recover_email,code_edit, ed_new_pass,ed_confirm_pass;
    SharedPreferences sharedPreferences;
    CamomileSpinner progressBar;
    RelativeLayout transparent_layer, progressDialog;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        sharedPreferences = getSharedPreferences(PreferenceClass.user,Context.MODE_PRIVATE);

        FrameLayout frameLayout = findViewById(R.id.main_recover_pass);
        FontHelper.applyFont(RecoverPasswordActivity.this,frameLayout, AllConstants.verdana);
        initUI();

    }

    public void initUI(){

        progressBar = findViewById(R.id.signUpProgress);
        progressBar.start();
        progressDialog = findViewById(R.id.progressDialog);
        transparent_layer =findViewById(R.id.transparent_layer);



        viewFlipper=findViewById(R.id.viewflliper);

        recover_email = findViewById(R.id.recover_email);
        findViewById(R.id.btn_recover).setOnClickListener(this::onClick);


        code_edit=findViewById(R.id.code_edit);
        findViewById(R.id.sendcode_btn).setOnClickListener(this::onClick);


        ed_new_pass=findViewById(R.id.ed_new_pass);
        ed_confirm_pass=findViewById(R.id.ed_confirm_pass);
        findViewById(R.id.btn_change_pass).setOnClickListener(this::onClick);


        findViewById(R.id.Goback1).setOnClickListener(this::onClick);
        findViewById(R.id.Goback2).setOnClickListener(this::onClick);
        findViewById(R.id.Goback3).setOnClickListener(this::onClick);

    }

    public void CallApi_sendemail(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email",recover_email.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        progressDialog.setVisibility(View.VISIBLE);
        transparent_layer.setVisibility(View.VISIBLE);

        ApiRequest.Call_Api(this, Config.forgotPassword, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {

                progressDialog.setVisibility(View.GONE);
                transparent_layer.setVisibility(View.GONE);

                try {
                    JSONObject jsonResponse = new JSONObject(resp);

                    int code_id  = Integer.parseInt(jsonResponse.optString("code"));
                    if (code_id==200){

                        Toast.makeText(RecoverPasswordActivity.this,"Password sent to your given email",Toast.LENGTH_LONG).show();
                        viewFlipper.showNext();

                    }
                    else {
                        Toast.makeText(RecoverPasswordActivity.this,"Your email is not correct",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        });


    }


    public void CallApi_sendCode(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email",recover_email.getText().toString());
            jsonObject.put("code",code_edit.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        progressDialog.setVisibility(View.VISIBLE);
        transparent_layer.setVisibility(View.VISIBLE);


        ApiRequest.Call_Api(this, Config.verifyforgotPasswordCode, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {

                progressDialog.setVisibility(View.GONE);
                transparent_layer.setVisibility(View.GONE);

                try {
                    JSONObject jsonResponse = new JSONObject(resp);

                    int code_id  = Integer.parseInt(jsonResponse.optString("code"));
                    if (code_id==200){

                        JSONArray msg=jsonResponse.optJSONArray("msg");
                        JSONObject user_info=msg.getJSONObject(0).optJSONObject("UserInfo");
                        if(user_info!=null){
                             user_id=user_info.optString("user_id");
                             Toast.makeText(RecoverPasswordActivity.this, "Reset the Password", Toast.LENGTH_SHORT).show();
                             viewFlipper.showNext();
                        }
                    }
                    else {
                        Toast.makeText(RecoverPasswordActivity.this,"Your Code is not correct",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        });


    }



    String user_id;
    public void CallApi_ChangePassword(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id",user_id);
            jsonObject.put("password",ed_new_pass.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.setVisibility(View.VISIBLE);
        transparent_layer.setVisibility(View.VISIBLE);

        ApiRequest.Call_Api(this, Config.changePasswordForgot, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {

                progressDialog.setVisibility(View.GONE);
                transparent_layer.setVisibility(View.GONE);

                try {
                    JSONObject jsonResponse = new JSONObject(resp);

                    int code_id  = Integer.parseInt(jsonResponse.optString("code"));
                    if (code_id==200){
                        Toast.makeText(RecoverPasswordActivity.this, "Password Reset Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(RecoverPasswordActivity.this,"Password Reset Fail",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        });


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.Goback1:
                finish();
                break;

            case R.id.btn_recover:
                if(TextUtils.isEmpty(recover_email.getText().toString())){

                    Toast.makeText(this, "Please enter the email", Toast.LENGTH_SHORT).show();
                }else
                CallApi_sendemail();
                break;


            case R.id.Goback2:
                viewFlipper.showPrevious();
                break;

            case R.id.sendcode_btn:
                if(TextUtils.isEmpty(code_edit.getText().toString())){

                    Toast.makeText(this, "Please enter the Code", Toast.LENGTH_SHORT).show();
                }else
                    CallApi_sendCode();
                break;


            case R.id.Goback3:
                viewFlipper.showPrevious();
                break;

            case R.id.btn_change_pass:
                String pass=ed_new_pass.getText().toString();
                String confirm_pass=ed_confirm_pass.getText().toString();

                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(this, "Please enter the Password", Toast.LENGTH_SHORT).show();
                }if(TextUtils.isEmpty(confirm_pass)){
                    Toast.makeText(this, "Please enter the Confirm Password", Toast.LENGTH_SHORT).show();
                }if(!pass.equals(confirm_pass)){
                    Toast.makeText(this, "New Password and Confirm password not match", Toast.LENGTH_SHORT).show();
                 }else
                    CallApi_ChangePassword();
                break;





        }
    }


    @Override
    public void onBackPressed() {
        if(viewFlipper.getDisplayedChild()==0){
            finish();
        }else {
            viewFlipper.showPrevious();
        }
    }
}
