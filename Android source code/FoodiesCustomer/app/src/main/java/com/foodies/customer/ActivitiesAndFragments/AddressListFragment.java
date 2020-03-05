package com.foodies.customer.ActivitiesAndFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.foodies.customer.Adapters.AddressListAdapter;
import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.Fragment_Callback;
import com.foodies.customer.Constants.Functions;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.AddressListModel;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.foodies.customer.Utils.TabLayoutUtils;
import com.foodies.customer.R;
import com.gmail.samehadar.iosdialog.CamomileSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class AddressListFragment extends RootFragment {

    Button cancel;
    ImageView back_icon;
    RelativeLayout add_address_div;
    public static boolean FLAG_ADDRESS_LIST,FLAG_NO_ADRESS_CHOSE,CART_NOT_LOAD;
    SharedPreferences sharedPreferences;

    ArrayList<AddressListModel> arrayListAddress;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    AddressListAdapter recyclerViewadapter;
    RecyclerView recycler_view;
    CamomileSpinner addresListProgress;
    FrameLayout address_list_container;

    RelativeLayout transparent_layer,progressDialog;

    View view;
    Context context;

    Bundle bundle;



    public AddressListFragment(){

    }

    Fragment_Callback fragment_callback;
    public AddressListFragment(Fragment_Callback fragment_callback){
        this.fragment_callback=fragment_callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.activity_add_address, container, false);
         context=getContext();

         bundle=getArguments();

         sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        address_list_container = view.findViewById(R.id.address_list_container);
        address_list_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });




        initUI(view);

        getAddressList();

        return view;
    }

    public void initUI(View v){

        progressDialog = v.findViewById(R.id.progressDialog_address);
        transparent_layer = v.findViewById(R.id.transparent_layer_address);
        addresListProgress = v.findViewById(R.id.addresListProgress);
        addresListProgress.start();
        recycler_view = v.findViewById(R.id.list_address);
        recyclerViewlayoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(recyclerViewlayoutManager);

        cancel = v.findViewById(R.id.cancle_address_btn);
        back_icon = v.findViewById(R.id.back_icon);
        add_address_div = v.findViewById(R.id.add_address_div);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        add_address_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment restaurantMenuItemsFragment = new AddressDetailFragment(new Fragment_Callback() {
                    @Override
                    public void Responce(Bundle bundle) {
                        getAddressList();
                    }
                });
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.add(R.id.address_list_container, restaurantMenuItemsFragment,"parent").commit();
                FLAG_ADDRESS_LIST = true;

            }
        });

        if(UserAccountFragment.FLAG_DELIVER_ADDRESS || DealOrderFragment.DEAL_ADDRESS){
            back_icon.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
            UserAccountFragment.FLAG_DELIVER_ADDRESS = false;
            back_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                       Functions.Hide_keyboard(getActivity());

                       getActivity().onBackPressed();

                }
            });

        }

    }
    String res_id="",sub_total="";
    public void getAddressList(){
        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);
        arrayListAddress = new ArrayList<>();
        String user_id = sharedPreferences.getString(PreferenceClass.pre_user_id,"");


        JSONObject addressJsonObject = new JSONObject();

        try {

            addressJsonObject.put("user_id", user_id);

            if(bundle!=null) {
                res_id = bundle.getString("rest_id");
                sub_total = bundle.getString("grand_total");
                addressJsonObject.put("restaurant_id", res_id);
                addressJsonObject.put("sub_total", sub_total);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        ApiRequest.Call_Api(context, Config.GET_DELIVERY_ADDRESES, addressJsonObject, new Callback() {
            @Override
            public void Responce(String resp) {

                try {
                    JSONObject jsonResponse = new JSONObject(resp);

                    int code_id  = Integer.parseInt(jsonResponse.optString("code"));

                    if(code_id == 200) {

                        JSONObject json = new JSONObject(jsonResponse.toString());
                        JSONArray jsonarray = json.getJSONArray("msg");

                        for (int i=0;i<jsonarray.length();i++){

                            JSONObject addressJson = jsonarray.getJSONObject(i);
                            JSONObject jsonObjAdd = addressJson.getJSONObject("Address");

                            AddressListModel addressListModel = new AddressListModel();

                            if(jsonObjAdd.optString("apartment").isEmpty()){
                                addressListModel.setApartment("");
                            }
                            else {
                                addressListModel.setApartment(jsonObjAdd.optString("apartment"));
                            }

                            if(jsonObjAdd.optString("city").isEmpty()){
                                addressListModel.setCity("");
                            }
                            else {
                                addressListModel.setCity(jsonObjAdd.optString("city"));
                            }
                            if(jsonObjAdd.optString("state").isEmpty()){
                                addressListModel.setState("");
                            }
                            else {
                                addressListModel.setState(jsonObjAdd.optString("state"));
                            }
                            if(jsonObjAdd.optString("street").isEmpty()){
                                addressListModel.setStreet("");
                            }
                            else {
                                addressListModel.setStreet(jsonObjAdd.optString("street"));
                            }
                            addressListModel.setAddress_id(jsonObjAdd.optString("id"));
                            addressListModel.setDelivery_fee(jsonObjAdd.optString("delivery_fee"));
                            String del = jsonObjAdd.optString("delivery_fee");


                            arrayListAddress.add(addressListModel);

                        }

                        if(arrayListAddress!=null) {

                            recyclerViewadapter = new AddressListAdapter(arrayListAddress, getActivity());
                            recycler_view.setAdapter(recyclerViewadapter);
                            recyclerViewadapter.notifyDataSetChanged();
                            recyclerViewadapter.setOnItemClickListner(new AddressListAdapter.OnItemClickListner() {
                                @Override
                                public void OnItemClicked(View view, int position) {

                                    AddressListModel addressListModel=arrayListAddress.get(position);

                                    if(fragment_callback!=null){
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("data",addressListModel);
                                        fragment_callback.Responce(bundle);
                                        getActivity().onBackPressed();
                                    }


                                }
                            });
                        }

                    }



                }
                catch (JSONException e){
                    e.printStackTrace();
                }

                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);

            }
        });
    }
}
