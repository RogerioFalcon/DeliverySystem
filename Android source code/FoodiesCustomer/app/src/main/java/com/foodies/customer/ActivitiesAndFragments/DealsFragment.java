package com.foodies.customer.ActivitiesAndFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;

import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.foodies.customer.Adapters.DealsAdapter;
import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.DealsModel;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.foodies.customer.Utils.TabLayoutUtils;

import com.foodies.customer.R;
import com.gmail.samehadar.iosdialog.CamomileSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class DealsFragment extends RootFragment {

    private RecyclerView deals_recyclerview;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    DealsAdapter recyclerViewadapter;
    CamomileSpinner dealsProgressBar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<DealsModel> delsArrayList;
    public static boolean FLAG_DEAL_FRAGMENT,DEAL;
    SharedPreferences dealsSharedPreferences;

    RelativeLayout no_job_div;
    RelativeLayout transparent_layer,progressDialog;
    public static boolean OPEN_DEALS;

    View view;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dealsSharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){

        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.deals_fragment, container, false);
         context=getContext();

        progressDialog = view.findViewById(R.id.progressDialog);
        transparent_layer = view.findViewById(R.id.transparent_layer);

        deals_recyclerview = view.findViewById(R.id.deals_recyclerview);
        dealsProgressBar = view.findViewById(R.id.dealsProgress);
        dealsProgressBar.start();
        deals_recyclerview.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getContext());
        deals_recyclerview.setLayoutManager(recyclerViewlayoutManager);




        initUI(view);
        getDealsList();
        return view;
    }

    private void initUI(View v){
        no_job_div = v.findViewById(R.id.no_job_div);

        mSwipeRefreshLayout = v.findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               getDealsList();


                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void getDealsList(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        delsArrayList = new ArrayList<>();
        String lat = dealsSharedPreferences.getString(PreferenceClass.LATITUDE,"");
        String long_ = dealsSharedPreferences.getString(PreferenceClass.LONGITUDE,"");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("lat", lat);
            jsonObject.put("long", long_);
            jsonObject.put("current_time",currentDateandTime);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        ApiRequest.Call_Api(context, Config.SHOW_DEALS, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {

                try {
                    JSONObject jsonResponse = new JSONObject(resp);

                    int code_id  = Integer.parseInt(jsonResponse.optString("code"));

                    if(code_id == 200) {

                        JSONObject json = new JSONObject(jsonResponse.toString());
                        JSONArray jsonarray = json.getJSONArray("msg");


                        for (int i = 0; i < jsonarray.length(); i++) {

                            JSONObject json1 = jsonarray.getJSONObject(i);

                            DealsModel dealsModel = new DealsModel();

                            JSONObject jsonObjDeal = json1.optJSONObject("Deal");
                            JSONObject jsonObjRestaurant = json1.optJSONObject("Restaurant");
                            JSONObject jsonObjCurrency = jsonObjRestaurant.optJSONObject("Currency");
                            JSONObject jsonObjTax = jsonObjRestaurant.optJSONObject("Tax");

                            dealsModel.promoted=jsonObjDeal.optString("promoted");
                            dealsModel.deal_cover_image=jsonObjDeal.optString("cover_image");
                            dealsModel.deal_image=jsonObjDeal.optString("image");
                            dealsModel.deal_desc=jsonObjDeal.optString("description");
                            dealsModel.deal_restaurant_id=jsonObjDeal.optString("restaurant_id");
                            dealsModel.deal_id=jsonObjDeal.optString("id");
                            dealsModel.deal_name=jsonObjDeal.optString("name");
                            dealsModel.deal_price=jsonObjDeal.optString("price");
                            dealsModel.deal_expiry_date=jsonObjDeal.optString("ending_time");

                            dealsModel.deal_symbol=jsonObjCurrency.optString("symbol");
                            dealsModel.restaurant_name=jsonObjRestaurant.optString("name");

                            if(jsonObjTax!=null) {
                                dealsModel.deal_tax = jsonObjTax.optString("tax");
                                dealsModel.deal_delivery_fee = jsonObjTax.optString("delivery_fee_per_km");
                            }

                            dealsModel.isDeliveryFree=jsonObjRestaurant.optString("tax_free");


                            delsArrayList.add(dealsModel);

                        }

                        if(delsArrayList!=null) {

                            if(delsArrayList.isEmpty()){
                                no_job_div.setVisibility(View.VISIBLE);
                            }
                            else {
                                no_job_div.setVisibility(View.GONE);
                            }

                            recyclerViewadapter = new DealsAdapter(delsArrayList, getActivity());
                            deals_recyclerview.setAdapter(recyclerViewadapter);
                            recyclerViewadapter.notifyDataSetChanged();
                            recyclerViewadapter.setOnItemClickListner(new DealsAdapter.OnItemClickListner() {
                                @Override
                                public void OnItemClicked(View view, int position) {


                                    DealsDetailFragment dealsDetailFragment = new DealsDetailFragment();
                                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                                    Bundle bundle=new Bundle();
                                    bundle.putSerializable("data",delsArrayList.get(position));
                                    dealsDetailFragment.setArguments(bundle);
                                    transaction.addToBackStack(null);
                                    transaction.add(R.id.DealsFragment, dealsDetailFragment, "parent").commit();
                                    OPEN_DEALS = true;

                                }
                            });

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);
                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);


            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
