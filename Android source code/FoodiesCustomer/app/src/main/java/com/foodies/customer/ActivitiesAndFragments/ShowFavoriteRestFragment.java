package com.foodies.customer.ActivitiesAndFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.foodies.customer.Adapters.RestaurantsAdapter;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.RestaurantsModel;
import com.foodies.customer.R;
import com.foodies.customer.Utils.TabLayoutUtils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class ShowFavoriteRestFragment extends RootFragment {

    ArrayList<RestaurantsModel> GetDataAdapter1;

    RecyclerView restaurant_recycler_view;

    SwipeRefreshLayout refresh_layout;

    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RestaurantsAdapter recyclerViewadapter;

    CamomileSpinner progressBar;

    SharedPreferences sharedPreferences;

    ImageView back_icon;
    SearchView searchView;
    public static boolean FLAG_SHOW_FAV;
    public static boolean FROM_FAVORITE;

    RelativeLayout transparent_layer,progressDialog;
    String user_id;
   RelativeLayout no_job_div;

   View view;
   Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.show_favorite_fragment, container, false);
        context=getContext();

        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString(PreferenceClass.pre_user_id,"");
        restaurant_recycler_view = view.findViewById(R.id.restaurant_recycler_view);
        progressBar = view.findViewById(R.id.restaurantProgress);
        progressBar.start();
        restaurant_recycler_view.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(getContext());
        restaurant_recycler_view.setLayoutManager(recyclerViewlayoutManager);
        init(view);
        getRestaurantList(user_id);
        return view;

    }

    public void init(View v){
        no_job_div = v.findViewById(R.id.no_job_div);
        progressDialog = v.findViewById(R.id.progressDialog);
        transparent_layer = v.findViewById(R.id.transparent_layer);

        searchView = v.findViewById(R.id.floating_search_view);
        search(searchView);

        back_icon = v.findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                getActivity().onBackPressed();


            }
        });
        refresh_layout = v.findViewById(R.id.refresh_layout);
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getRestaurantList(user_id);
                refresh_layout.setRefreshing(false);
            }
        });

    }

    public void getRestaurantList(String user_id){


        GetDataAdapter1 = new ArrayList<>();


        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        progressBar.start();
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        ApiRequest.Call_Api(context, Config.SHOW_FAV_RESTAURANT, jsonObject, new Callback() {
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

                            JSONObject jsonObjRestaurant = json1.getJSONObject("Restaurant");
                            JSONObject jsonRestaurantFavorite = json1.getJSONObject("RestaurantFavourite");

                            JSONObject jsonObjCurrency = jsonObjRestaurant.getJSONObject("Currency");
                            String symbol = jsonObjCurrency.optString("symbol");
                            JSONObject jsonObjTax = jsonObjRestaurant.getJSONObject("Tax");
                            JSONObject jsonObjRating = null;
                            try {
                                jsonObjRating = json1.getJSONObject("TotalRatings");
                            }
                            catch (JSONException ignored){
                                ignored.getCause();
                            }


                            RestaurantsModel RestaurantObj = new RestaurantsModel();
                            RestaurantObj.restaurant_name=jsonObjRestaurant.optString("name");
                            RestaurantObj.restaurant_salgon=jsonObjRestaurant.optString("slogan");
                            RestaurantObj.restaurant_about=jsonObjRestaurant.optString("about");
                            RestaurantObj.restaurant_fee=symbol+jsonObjRestaurant.optString("delivery_fee");
                            RestaurantObj.restaurant_image=jsonObjRestaurant.optString("image");
                            RestaurantObj.restaurant_id=jsonObjRestaurant.optString("id");
                            RestaurantObj.restaurant_phone=jsonObjRestaurant.optString("phone");
                            RestaurantObj.restaurant_cover=jsonObjRestaurant.optString("cover_image");
                            RestaurantObj.min_order_price=jsonObjRestaurant.optString("min_order_price");
                            RestaurantObj.restaurant_isFav=jsonRestaurantFavorite.optString("favourite");
                            RestaurantObj.promoted=jsonObjRestaurant.optString("promoted");
                            RestaurantObj.preparation_time=jsonObjRestaurant.optString("preparation_time");

                            /*JSONObject jsonObjDistance = json1.getJSONObject("0");
                            String distance = jsonObjDistance.optString("distance");
                            String distanceKM = String.valueOf(new DecimalFormat("##.#").format(Double.parseDouble(distance) * 1.6)) + " KM";
                            RestaurantObj.restaurant_distance=distanceKM;
                           */

                            if(jsonObjRating==null) {

                                RestaurantObj.restaurant_avgRating="0.00";
                                RestaurantObj.restaurant_totalRating="0.00";
                            }
                            else {
                                RestaurantObj.restaurant_avgRating=jsonObjRating.optString("avg");
                            }
                            RestaurantObj.restaurant_currency=jsonObjCurrency.optString("symbol");
                            RestaurantObj.restaurant_tax=jsonObjTax.optString("tax");
                            RestaurantObj.delivery_fee_per_km=jsonObjTax.optString("delivery_fee_per_km");

                            RestaurantObj.restaurant_menu_style=jsonObjRestaurant.optString("menu_style");
                            RestaurantObj.deliveryFee_Range=jsonObjRestaurant.optString("delivery_free_range");



                            GetDataAdapter1.add(RestaurantObj);
                        }
                        if(GetDataAdapter1!=null && GetDataAdapter1.size()>0) {
                            recyclerViewadapter = new RestaurantsAdapter(GetDataAdapter1, getContext(), ShowFavoriteRestFragment.this, progressBar);
                            restaurant_recycler_view.setAdapter(recyclerViewadapter);
                            recyclerViewadapter.notifyDataSetChanged();

                            recyclerViewadapter.setOnItemClickListner(new RestaurantsAdapter.OnItemClickListner() {
                                @Override
                                public void OnItemClicked(View view, final int position) {

                                    RestaurantsModel model = GetDataAdapter1.get(position);

                                    FLAG_SHOW_FAV = true;

                                    Fragment restaurantMenuItemsFragment = new RestaurantMenuItemsFragment();
                                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                                    Bundle bundle=new Bundle();
                                    bundle.putSerializable("data",model);
                                    restaurantMenuItemsFragment.setArguments(bundle);
                                    transaction.addToBackStack(null);
                                    transaction.add(R.id.restaurent_main_layout, restaurantMenuItemsFragment, "parent").commit();
                                }
                            });
                        }
                        else {
                            recyclerViewadapter = new RestaurantsAdapter(GetDataAdapter1, getContext(), ShowFavoriteRestFragment.this, progressBar);
                            restaurant_recycler_view.setAdapter(recyclerViewadapter);
                            recyclerViewadapter.notifyDataSetChanged();
                            no_job_div.setVisibility(View.VISIBLE);
                        }

                    }else{
                        no_job_div.setVisibility(View.VISIBLE);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    no_job_div.setVisibility(View.VISIBLE);
                }


                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout, true);
                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);

            }
        });


    }


    private void search(androidx.appcompat.widget.SearchView searchView) {

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (recyclerViewadapter != null) recyclerViewadapter.getFilter().filter(newText);
                return true;
            }
        });
    }


}
