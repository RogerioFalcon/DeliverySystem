package com.foodies.customer.ActivitiesAndFragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.Fragment_Callback;
import com.foodies.customer.Constants.Functions;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.foodies.customer.Utils.TabLayoutUtils;
import com.foodies.customer.GoogleMapWork.MapsActivity;
import com.foodies.customer.R;
import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class AddressDetailFragment extends RootFragment implements GoogleApiClient.OnConnectionFailedListener {

    Button cancle_add_address_btn,save_address_btn;
    ImageView back_icon;
    CamomileSpinner pbHeaderProgress;

    RelativeLayout transparent_layer,progressDialog;
    SharedPreferences sharedPreferences;
    EditText st_address,add_city,add_instructions;
    String add_state;
    String latitude,longitude;


    RelativeLayout add_loc_div;
    public static TextView add_loc_tv;

    View view;
    Context context;


    public  AddressDetailFragment(){

    }

    Fragment_Callback fragment_callback;
    public  AddressDetailFragment(Fragment_Callback fragment_callback){
        this.fragment_callback=fragment_callback;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.add_address_detail, container, false);
         context=getContext();

         sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);

        initUI(view);

       add_city.setFocusable(false);

        return view;
    }


    public void initUI(View v){
        progressDialog = v.findViewById(R.id.progressDialog);
        transparent_layer = v.findViewById(R.id.transparent_layer);
        st_address = v.findViewById(R.id.st_address);
        add_city = v.findViewById(R.id.add_city);
        add_instructions = v.findViewById(R.id.add_instructions);
        add_loc_div = v.findViewById(R.id.add_loc_div);

        add_loc_tv  = v.findViewById(R.id.add_loc_tv);

        if(SearchFragment.FLAG_COUNTRY_NAME) {

            st_address.setText(sharedPreferences.getString(PreferenceClass.STREET,""));


            add_state = sharedPreferences.getString(PreferenceClass.STATE,"");
             add_instructions.setText(sharedPreferences.getString(PreferenceClass.INSTRUCTIONS,""));
            SearchFragment.FLAG_COUNTRY_NAME=false;
        }
        else {
        }

        cancle_add_address_btn = v.findViewById(R.id.cancle_add_address_btn);
        back_icon = v.findViewById(R.id.back_icon);
        save_address_btn = v.findViewById(R.id.save_address_btn);
        pbHeaderProgress = v.findViewById(R.id.pbHeaderProgress);
        pbHeaderProgress.start();

        save_address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveAddressRequest();
            }
        });

        if(AddressListFragment.FLAG_ADDRESS_LIST){
            cancle_add_address_btn.setVisibility(View.GONE);
            back_icon.setVisibility(View.VISIBLE);
            AddressListFragment.FLAG_ADDRESS_LIST = false;
            UserAccountFragment.FLAG_DELIVER_ADDRESS = true;

            back_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Functions.Hide_keyboard(getActivity());
                    getActivity().onBackPressed();
                }
            });

            add_loc_div.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                 startActivity(new Intent(getContext(),MapsActivity.class));
                }
            });

        }

        cancle_add_address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

    }

    public void saveAddressRequest(){
        latitude = sharedPreferences.getString(PreferenceClass.LATITUDE, "");
        longitude = sharedPreferences.getString(PreferenceClass.LONGITUDE, "");

        String user_id = sharedPreferences.getString(PreferenceClass.pre_user_id,"");


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("default","1");
            jsonObject.put("street",st_address.getText().toString());
            jsonObject.put("apartment","4ho");
            jsonObject.put("city",add_city.getText().toString());
            jsonObject.put("state","state");
            jsonObject.put("country","0");
            jsonObject.put("zip","0");
            jsonObject.put("instruction",add_instructions.getText().toString());
            jsonObject.put("lat",""+latitude);
            jsonObject.put("long",""+longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        ApiRequest.Call_Api(context, Config.ADD_DELIVERY_ADDRESS, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {

                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);

                try {
                    JSONObject jsonResponse = new JSONObject(resp);

                    int code_id  = Integer.parseInt(jsonResponse.optString("code"));

                    if(code_id == 200) {

                        if(fragment_callback!=null)
                            fragment_callback.Responce(new Bundle());

                        getActivity().onBackPressed();

                    }

                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });




    }



    @Override
    public void onResume() {
        super.onResume();
        if(MapsActivity.SAVE_LOCATION_ADDRESS) {
            MapsActivity.SAVE_LOCATION_ADDRESS = false;
            latitude = sharedPreferences.getString(PreferenceClass.LATITUDE, "");
            longitude = sharedPreferences.getString(PreferenceClass.LONGITUDE, "");

            Address locationAddress;

            locationAddress = getAddress(Double.parseDouble(latitude), Double.parseDouble(longitude));
            if (locationAddress != null) {

                String city = locationAddress.getLocality();

                String country = locationAddress.getCountryName();

                String address = city + " " + country;

                add_loc_tv.setText(latitude+","+longitude);



                add_city.setText(city);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(PreferenceClass.LATITUDE,latitude);
                editor.putString(PreferenceClass.LONGITUDE,longitude);
                editor.putString(PreferenceClass.CURRENT_LOCATION_ADDRESS, address).commit();

            }
        }

     }



    public Address getAddress(double latitude, double longitude)
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude,longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(),"On Failed",Toast.LENGTH_SHORT).show();
    }

   }
