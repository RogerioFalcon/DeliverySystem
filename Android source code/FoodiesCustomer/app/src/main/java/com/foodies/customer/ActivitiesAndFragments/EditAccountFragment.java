package com.foodies.customer.ActivitiesAndFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.Fragment_Callback;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.foodies.customer.Utils.TabLayoutUtils;
import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.foodies.customer.R;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class EditAccountFragment extends RootFragment {

    ImageView back_icon;
    EditText first_name,last_name,phone_number,ed_email;
    Button btn_edit_done;
    String first_name_str,last_name_str,user_id,email,phone;
    SharedPreferences sharedPreferences;

    CamomileSpinner editAccountProgress;
    RelativeLayout transparent_layer,progressDialog;

    View view;
    Context context;


    public EditAccountFragment(){

    }

    Fragment_Callback fragment_callback;
    public EditAccountFragment(Fragment_Callback fragment_callback){
        this.fragment_callback=fragment_callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.edit_account_fragment,container,false);
         context=getContext();

         sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
         init(view);
         return view;
    }

    public void init(View v){
        editAccountProgress = v.findViewById(R.id.editAccountProgress);
        editAccountProgress.start();
        progressDialog = v.findViewById(R.id.progressDialog);
        transparent_layer = v.findViewById(R.id.transparent_layer);
        first_name = v.findViewById(R.id.first_name);
        last_name = v.findViewById(R.id.last_name);
        phone_number = v.findViewById(R.id.ed_phone_number);
        ed_email = v.findViewById(R.id.ed_edit_email);

        btn_edit_done = v.findViewById(R.id.btn_edit_done);
        btn_edit_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUserProfile();
            }
        });

        first_name_str = sharedPreferences.getString(PreferenceClass.pre_first,"");
        last_name_str = sharedPreferences.getString(PreferenceClass.pre_last,"");
        user_id = sharedPreferences.getString(PreferenceClass.pre_user_id,"");
        email = sharedPreferences.getString(PreferenceClass.pre_email,"");
        phone = sharedPreferences.getString(PreferenceClass.pre_contact,"");


        first_name.setText(first_name_str);
        last_name.setText(last_name_str);
        ed_email.setText(email);
        phone_number.setText(phone);


        back_icon = v.findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getActivity().onBackPressed();

            }
        });

    }


    public void editUserProfile(){
        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        JSONObject params = new JSONObject();

        try {

            params.put("user_id",user_id);
            if(first_name.getText().toString().isEmpty()){
                params.put("first_name",first_name_str);
            }
            else {
                params.put("first_name", first_name.getText().toString());
            }
            if(last_name.getText().toString().isEmpty()) {
                params.put("last_name", last_name_str);
            }
            else {
                params.put("last_name", last_name.getText().toString());
            }
            params.put("email",email);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ApiRequest.Call_Api(context, Config.EDIT_PROFILE, params, new Callback() {
            @Override
            public void Responce(String resp) {

                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);

                try {
                    JSONObject jsonResponse = new JSONObject(resp);

                    int code_id  = Integer.parseInt(jsonResponse.optString("code"));

                    if(code_id == 200) {

                        JSONObject json = new JSONObject(jsonResponse.toString());
                        JSONObject resultObj = json.getJSONObject("msg");
                        JSONObject json1 = new JSONObject(resultObj.toString());
                        JSONObject resultObj1 = json1.getJSONObject("UserInfo");

                        Toast.makeText(getContext(),"Profile updated successfully",Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(PreferenceClass.pre_first,resultObj1.optString("first_name"));
                        editor.putString(PreferenceClass.pre_last,resultObj1.optString("last_name"));
                        editor.commit();

                        if(fragment_callback!=null)
                        fragment_callback.Responce(new Bundle());


                        getActivity().onBackPressed();


                    }


                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });


    }


}
