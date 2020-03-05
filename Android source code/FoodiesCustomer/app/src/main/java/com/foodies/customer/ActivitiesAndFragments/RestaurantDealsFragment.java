package com.foodies.customer.ActivitiesAndFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.foodies.customer.Adapters.DealsAdapter;
import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.DealsModel;

import com.foodies.customer.Models.RestaurantsModel;
import com.foodies.customer.R;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.foodies.customer.Utils.TabLayoutUtils;
import com.gmail.samehadar.iosdialog.CamomileSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class RestaurantDealsFragment extends RootFragment {

    private RecyclerView deals_recyclerview;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    DealsAdapter recyclerViewadapter;
    CamomileSpinner dealsProgressBar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<DealsModel> delsArrayList;
    SharedPreferences dealsSharedPreferences;
    ImageView back_icon;
    String lat,lon;
   RelativeLayout no_job_div;

    public static boolean RESTAUNT_DEALS_FRAG;

    RelativeLayout transparent_layer,progressDialog;

    View view;
    Context context;

    RestaurantsModel item_model;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.deals_fragment, container, false);
         context=getContext();

        Bundle bundle=getArguments();
        if(bundle!=null){
            item_model=(RestaurantsModel) bundle.get("data");
        }


        dealsSharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);

        progressDialog = view.findViewById(R.id.progressDialog);
        transparent_layer = view.findViewById(R.id.transparent_layer);
        deals_recyclerview = view.findViewById(R.id.deals_recyclerview);
        dealsProgressBar = view.findViewById(R.id.dealsProgress);
        dealsProgressBar.start();

        deals_recyclerview.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getContext());
        deals_recyclerview.setLayoutManager(recyclerViewlayoutManager);
        RESTAUNT_DEALS_FRAG = true;

        initUI(view);
        getDealsList();

        return view;

    }

    private void initUI(View v){
        no_job_div = v.findViewById(R.id.no_job_div);
        back_icon = v.findViewById(R.id.back_icon);

        if(RESTAUNT_DEALS_FRAG){
            back_icon.setVisibility(View.VISIBLE);

        }

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RESTAUNT_DEALS_FRAG = false;
               getActivity().onBackPressed();
            }
        });

        mSwipeRefreshLayout = v.findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDealsList();


                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }


    private void getDealsList(){


       lat = dealsSharedPreferences.getString(PreferenceClass.LATITUDE,"");
        lon = dealsSharedPreferences.getString(PreferenceClass.LONGITUDE,"");
        delsArrayList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject();
        try {


            jsonObject.put("id",item_model.restaurant_id);
            jsonObject.put("lat", lat);
            jsonObject.put("long", lon);

            Log.e("Obj",jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        ApiRequest.Call_Api(context, Config.SHOW_RESTAURANT_DEALS, jsonObject, new Callback() {
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

                            JSONObject jsonObjDeal = json1.getJSONObject("Deal");
                            JSONObject jsonObjRestaurant = json1.getJSONObject("Restaurant");
                            JSONObject jsonObjCurrency = jsonObjRestaurant.getJSONObject("Currency");
                            JSONObject jsonObjTax = jsonObjRestaurant.getJSONObject("Tax");

                            DealsModel dealsModel = new DealsModel();
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
                            dealsModel.deal_tax=jsonObjTax.optString("tax");
                            dealsModel.deal_delivery_fee=jsonObjTax.optString("delivery_fee_per_km");
                            dealsModel.isDeliveryFree=jsonObjRestaurant.optString("tax_free");

                            delsArrayList.add(dealsModel);

                        }
                        if (delsArrayList!=null&&delsArrayList.size()>0) {

                            recyclerViewadapter = new DealsAdapter(delsArrayList, getActivity());
                            deals_recyclerview.setAdapter(recyclerViewadapter);
                            recyclerViewadapter.notifyDataSetChanged();
                            recyclerViewadapter.setOnItemClickListner(new DealsAdapter.OnItemClickListner() {
                                @Override
                                public void OnItemClicked(View view, int position) {



                                    DealsDetailFragment dealsDetailRestFragment = new DealsDetailFragment();
                                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                                    Bundle bundle=new Bundle();
                                    bundle.putSerializable("data",delsArrayList.get(position));
                                    dealsDetailRestFragment.setArguments(bundle);
                                    transaction.addToBackStack(null);
                                    transaction.add(R.id.DealsFragment, dealsDetailRestFragment, "parent").commit();


                                }
                            });
                        }
                        else {
                               no_job_div.setVisibility(View.VISIBLE);
                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);
            }
        });


    }


}
