package com.foodies.customer.ActivitiesAndFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.foodies.customer.Adapters.ServerImageParseAdapter;
import com.foodies.customer.Adapters.SlidingImageAdapter;
import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.Fragment_Callback;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.GoogleMapWork.MapsActivity;
import com.foodies.customer.Models.ImageSliderModel;
import com.foodies.customer.Models.RestaurantsModel;
import com.foodies.customer.R;
import com.foodies.customer.Utils.FontHelper;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.foodies.customer.Utils.TabLayoutUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.rd.PageIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class RestaurantsFragment extends RootFragment implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks{

    private static ViewPager mPager;
    private static int currentPage = 0;
    private ArrayList<ImageSliderModel> ImagesArray;
    private ImageView res_filter;


    private TextView title_city_tv;



    private ArrayList<RestaurantsModel> datalist=new ArrayList<>();
    private RecyclerView restaurant_recycler_view;
    private RecyclerViewHeader recyclerHeader;
    SwipeRefreshLayout refresh_layout;

    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RestaurantsAdapter recyclerViewadapter;

    CamomileSpinner progressBar;
    String currentLoc;
    static SharedPreferences sharedPreferences;
    SearchView searchView;
    public static Timer swipeTimer;
    View layout;

    Handler handler = new Handler();
    Runnable timeCounter;
    String lat,lon,user_id;

    RelativeLayout transparent_layer,progressDialog;
    PageIndicatorView pageIndicatorView;


    View view;
    Context context;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.resturent_fragment, container, false);
        context=getContext();


        FontHelper.applyFont(getContext(),getActivity().getWindow().getDecorView().getRootView(), AllConstants.verdana);

        FrameLayout frameLayout = view.findViewById(R.id.RestaurantsFragment);
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return true;
            }
        });

        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        currentLoc = sharedPreferences.getString(PreferenceClass.CURRENT_LOCATION_ADDRESS,"");
        lat = sharedPreferences.getString(PreferenceClass.LATITUDE,"");
        lon = sharedPreferences.getString(PreferenceClass.LONGITUDE,"");
         user_id = sharedPreferences.getString(PreferenceClass.pre_user_id,"");
        title_city_tv = view.findViewById(R.id.title_city_tv);
        if(currentLoc.isEmpty()){
            title_city_tv.setText("Kalma Chowk Lahore");
        }
        else {
            title_city_tv.setText(currentLoc);
        }

        if(lat.isEmpty()||lon.isEmpty()){
            lat = "31.4904023";
            lon = "74.2906989";
        }


        res_filter = view.findViewById(R.id.res_filter);
        res_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment restaurantMenuItemsFragment = new RestaurantSpecialityFrag(new Fragment_Callback() {
                    @Override
                    public void Responce(Bundle bundle) {
                        if(bundle!=null){
                            String speciality=bundle.getString("speciality");
                            getRestaurantListAgainstSpeciality(speciality);
                        }
                    }
                });
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.add(R.id.RestaurantsFragment, restaurantMenuItemsFragment,"ParentFragment").commit();
            }
        });

        initUI(view);


        return view;
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if(ShowFavoriteRestFragment.FROM_FAVORITE) {
                getRestaurantList();
                ShowFavoriteRestFragment.FROM_FAVORITE = false;
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private void initUI(final View v){
        progressDialog = v.findViewById(R.id.progressDialog);
        transparent_layer = v.findViewById(R.id.transparent_layer);
        recyclerWithHeader(v);

        searchView = v.findViewById(R.id.floating_search_view);


        String txt="<font color = #dddddd>Search Restaurant</font>";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            searchView.setQueryHint(Html.fromHtml(txt, Html.FROM_HTML_MODE_LEGACY));
        } else {
            searchView.setQueryHint(Html.fromHtml(txt));
        }


        TextView searchText = (TextView)
                v.findViewById(R.id.search_src_text);
        searchText.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        searchText.setPadding(0,0,0,0);
        LinearLayout searchEditFrame = (LinearLayout) searchView.findViewById(R.id.search_edit_frame); // Get the Linear Layout

        ((LinearLayout.LayoutParams) searchEditFrame.getLayoutParams()).leftMargin = 5;
        search(searchView,v);

        mPager = (ViewPager) v.findViewById(R.id.image_slider_pager);
        pageIndicatorView = v.findViewById(R.id.pageIndicatorView);
        progressBar = v.findViewById(R.id.restaurantProgress);
        progressBar.start();


         getRestaurantList();
         initPager(v);

        refresh_layout = v.findViewById(R.id.refresh_layout);
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(handler!=null && timeCounter!=null){
                    handler.removeCallbacks(timeCounter);
                }
                getRestaurantList();
                initPager(v);
                refresh_layout.setRefreshing(false);

            }
        });

        title_city_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              startActivity(new Intent(getContext(),MapsActivity.class));
            }


        });

    }


    public void getRestaurantList(){
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("lat", lat);
            jsonObject.put("long", lon);
            jsonObject.put("current_time",formattedDate);
            jsonObject.put("user_id",user_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        ApiRequest.Call_Api(context, Config.SHOW_RESTAURANTS, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {

                try {
                    JSONObject jsonResponse = new JSONObject(resp);

                     int code_id = Integer.parseInt(jsonResponse.optString("code"));

                    if (code_id == 200) {

                        ArrayList<RestaurantsModel> tem_list=new ArrayList<>();

                        JSONObject json = new JSONObject(jsonResponse.toString());
                        JSONArray jsonarray = json.getJSONArray("msg");

                        JSONArray promoted=json.optJSONArray("promoted");
                        if(promoted!=null) {
                            for (int i = 0; i < promoted.length(); i++) {

                                JSONObject json1 = promoted.getJSONObject(i);

                                JSONObject jsonObjRestaurant = json1.getJSONObject("Restaurant");
                                JSONObject jsonObjCurrency = json1.getJSONObject("Currency");
                                String symbol = jsonObjCurrency.optString("symbol");
                                JSONObject jsonObjTax = json1.getJSONObject("Tax");
                                JSONObject jsonObjRating = null;
                                try {
                                    jsonObjRating = json1.getJSONObject("TotalRatings");
                                } catch (JSONException ignored) {
                                    ignored.getCause();
                                }

                                JSONObject jsonObjDistance = json1.getJSONObject("0");
                                String distance = jsonObjDistance.optString("distance");
                                RestaurantsModel RestaurantObj = new RestaurantsModel();
                                RestaurantObj.restaurant_name=jsonObjRestaurant.optString("name");
                                RestaurantObj.restaurant_salgon = jsonObjRestaurant.optString("slogan");
                                RestaurantObj.restaurant_about = jsonObjRestaurant.optString("about");
                                RestaurantObj.restaurant_fee = symbol + jsonObjRestaurant.optString("delivery_fee");
                                RestaurantObj.restaurant_image = jsonObjRestaurant.optString("image");
                                RestaurantObj.restaurant_id=jsonObjRestaurant.optString("id");
                                RestaurantObj.restaurant_phone = jsonObjRestaurant.optString("phone");
                                RestaurantObj.restaurant_cover = jsonObjRestaurant.optString("cover_image");
                                RestaurantObj.restaurant_isFav = jsonObjRestaurant.optString("favourite");
                                RestaurantObj.promoted = "1";
                                RestaurantObj.preparation_time = jsonObjRestaurant.optString("preparation_time");
                                String distanceKM = String.valueOf(new DecimalFormat("##.#").format(Double.parseDouble(distance) * 1.6)) + " KM";
                                RestaurantObj.restaurant_distance = distanceKM;

                                if (jsonObjRating == null) {

                                    RestaurantObj.restaurant_avgRating = "0.00";
                                    RestaurantObj.restaurant_totalRating = "0.00";
                                } else {
                                    RestaurantObj.restaurant_avgRating = jsonObjRating.optString("avg");
                                }
                                RestaurantObj.restaurant_currency = jsonObjCurrency.optString("symbol");
                                RestaurantObj.restaurant_tax = jsonObjTax.optString("tax");
                                String tax = jsonObjTax.optString("tax");
                                RestaurantObj.delivery_fee_per_km = jsonObjTax.optString("delivery_fee_per_km");
                                RestaurantObj.deliveryTime = jsonObjTax.getString("delivery_time");
                                RestaurantObj.min_order_price = jsonObjRestaurant.optString("min_order_price");
                                RestaurantObj.restaurant_menu_style = jsonObjRestaurant.optString("menu_style");
                                RestaurantObj.deliveryFee_Range = jsonObjRestaurant.optString("delivery_free_range");


                                tem_list.add(RestaurantObj);
                            }
                        }

                        for (int i = 0; i < jsonarray.length(); i++) {

                            JSONObject json1 = jsonarray.getJSONObject(i);

                            JSONObject jsonObjRestaurant = json1.getJSONObject("Restaurant");
                            JSONObject jsonObjCurrency = json1.getJSONObject("Currency");
                            String symbol = jsonObjCurrency.optString("symbol");
                            JSONObject jsonObjTax = json1.getJSONObject("Tax");
                            JSONObject jsonObjRating = null;
                            try {
                                jsonObjRating = json1.getJSONObject("TotalRatings");
                            } catch (JSONException ignored) {
                                ignored.getCause();
                            }

                            JSONObject jsonObjDistance = json1.getJSONObject("0");
                            String distance = jsonObjDistance.optString("distance");
                            RestaurantsModel RestaurantObj = new RestaurantsModel();
                            RestaurantObj.restaurant_name=jsonObjRestaurant.optString("name");
                            RestaurantObj.restaurant_salgon=jsonObjRestaurant.optString("slogan");
                            RestaurantObj.restaurant_about=jsonObjRestaurant.optString("about");
                            RestaurantObj.restaurant_fee=symbol + jsonObjRestaurant.optString("delivery_fee");
                            RestaurantObj.restaurant_image=jsonObjRestaurant.optString("image");
                            RestaurantObj.restaurant_id=jsonObjRestaurant.optString("id");
                            RestaurantObj.restaurant_phone=jsonObjRestaurant.optString("phone");
                            RestaurantObj.restaurant_cover=jsonObjRestaurant.optString("cover_image");
                            RestaurantObj.restaurant_isFav=jsonObjRestaurant.optString("favourite");
                            RestaurantObj.promoted="0";
                            RestaurantObj.preparation_time=jsonObjRestaurant.optString("preparation_time");
                            String distanceKM = String.valueOf(new DecimalFormat("##.#").format(Double.parseDouble(distance) * 1.6)) + " KM";
                            RestaurantObj.restaurant_distance=distanceKM;

                            if (jsonObjRating == null) {

                                RestaurantObj.restaurant_avgRating="0.00";
                                RestaurantObj.restaurant_totalRating="0.00";
                            } else {
                                RestaurantObj.restaurant_avgRating=jsonObjRating.optString("avg");
                            }
                            RestaurantObj.restaurant_currency=jsonObjCurrency.optString("symbol");
                            RestaurantObj.restaurant_tax=jsonObjTax.optString("tax");
                            String tax = jsonObjTax.optString("tax");
                            RestaurantObj.delivery_fee_per_km=jsonObjTax.optString("delivery_fee_per_km");
                            RestaurantObj.deliveryTime=jsonObjTax.getString("delivery_time");
                            RestaurantObj.min_order_price=jsonObjRestaurant.optString("min_order_price");
                            RestaurantObj.restaurant_menu_style=jsonObjRestaurant.optString("menu_style");
                            RestaurantObj.deliveryFee_Range=jsonObjRestaurant.optString("delivery_free_range");


                            tem_list.add(RestaurantObj);
                        }

                        datalist=tem_list;

                        if (datalist != null) {

                            recyclerViewadapter = new RestaurantsAdapter(datalist, getContext());
                            restaurant_recycler_view.setAdapter(recyclerViewadapter);
                            recyclerViewadapter.notifyDataSetChanged();
                            recyclerViewadapter.setOnItemClickListner(new OnItemClickListner() {
                                @Override
                                public void OnItemClicked(View view, final int position) {

                                       RestaurantsModel model = datalist.get(position);

                                    try {

                                        Fragment restaurantMenuItemsFragment = new RestaurantMenuItemsFragment();
                                        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                                        Bundle bundle=new Bundle();
                                        bundle.putSerializable("data",model);
                                        restaurantMenuItemsFragment.setArguments(bundle);
                                        transaction.addToBackStack(null);
                                        transaction.add(R.id.RestaurantsFragment, restaurantMenuItemsFragment, "parent").commit();
                                        if(swipeTimer!=null){
                                            swipeTimer.cancel();
                                            swipeTimer.purge();
                                        }
                                    }catch (IndexOutOfBoundsException e){
                                        e.getCause();
                                    }
                                }
                            });

                        }


                    } else {
                        JSONObject json = new JSONObject(jsonResponse.toString());
                        Toast.makeText(getApplicationContext(), json.optString("msg"), Toast.LENGTH_SHORT).show();
                    }

                } catch(JSONException e){
                    e.printStackTrace();

                }

                progressDialog.setVisibility(View.GONE);
                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                transparent_layer.setVisibility(View.GONE);


            }
        });

    }


    public void getRestaurantListAgainstSpeciality(String speciality){

        datalist = new ArrayList<>();

        String lat = sharedPreferences.getString(PreferenceClass.LATITUDE,"");
        String lon = sharedPreferences.getString(PreferenceClass.LONGITUDE,"");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("lat",lat);
            jsonObject.put("long",lon);
            jsonObject.put("user_id","");
            jsonObject.put("speciality",speciality);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        ApiRequest.Call_Api(context, Config.SHOW_REST_AGAINST_SPECIALITY, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {

                try {
                    JSONObject jsonResponse = new JSONObject(resp);


                    int code_id = Integer.parseInt(jsonResponse.optString("code"));

                    if (code_id == 200) {

                        JSONObject json = new JSONObject(jsonResponse.toString());
                        JSONArray jsonarray = json.getJSONArray("msg");

                        for (int i = 0; i < jsonarray.length(); i++) {

                            JSONObject json1 = jsonarray.getJSONObject(i);

                            JSONObject jsonObjRestaurant = json1.getJSONObject("Restaurant");
                            JSONObject jsonObjCurrency = json1.getJSONObject("Currency");
                            String symbol = jsonObjCurrency.optString("symbol");
                            JSONObject jsonObjTax = json1.getJSONObject("Tax");
                            JSONObject jsonObjRating = null;
                            try {
                                jsonObjRating = json1.getJSONObject("TotalRatings");
                            } catch (JSONException ignored) {
                                ignored.getCause();
                            }

                            JSONObject jsonObjDistance = json1.getJSONObject("0");
                             String distance = jsonObjDistance.optString("distance");
                            RestaurantsModel RestaurantObj = new RestaurantsModel();
                            RestaurantObj.restaurant_name=jsonObjRestaurant.optString("name");
                            RestaurantObj.restaurant_salgon=jsonObjRestaurant.optString("slogan");
                            RestaurantObj.restaurant_about=jsonObjRestaurant.optString("about");
                            RestaurantObj.restaurant_fee=symbol + jsonObjRestaurant.optString("delivery_fee");
                            RestaurantObj.restaurant_image=jsonObjRestaurant.optString("image");
                            RestaurantObj.restaurant_id=jsonObjRestaurant.optString("id");
                            RestaurantObj.restaurant_phone=jsonObjRestaurant.optString("phone");
                            RestaurantObj.restaurant_cover=jsonObjRestaurant.optString("cover_image");
                            RestaurantObj.restaurant_isFav=jsonObjRestaurant.optString("favourite");
                            RestaurantObj.promoted=jsonObjRestaurant.optString("promoted");
                            RestaurantObj.preparation_time=jsonObjRestaurant.optString("preparation_time");
                            String distanceKM = String.valueOf(new DecimalFormat("##.#").format(Double.parseDouble(distance) * 1.6)) + " KM";
                            RestaurantObj.restaurant_distance=distanceKM;

                            if (jsonObjRating == null) {

                                RestaurantObj.restaurant_avgRating="0.00";
                                RestaurantObj.restaurant_totalRating="0.00";
                            } else {
                                RestaurantObj.restaurant_avgRating=jsonObjRating.optString("avg");
                            }
                            RestaurantObj.restaurant_currency=jsonObjCurrency.optString("symbol");
                            RestaurantObj.restaurant_tax=jsonObjTax.optString("tax");
                            String tax = jsonObjTax.optString("tax");
                            RestaurantObj.delivery_fee_per_km=jsonObjTax.optString("delivery_fee_per_km");
                            RestaurantObj.deliveryTime=jsonObjTax.getString("delivery_time");
                            RestaurantObj.min_order_price=jsonObjRestaurant.optString("min_order_price");
                            RestaurantObj.restaurant_menu_style=jsonObjRestaurant.optString("menu_style");
                            RestaurantObj.deliveryFee_Range=jsonObjRestaurant.optString("delivery_free_range");

                            datalist.add(RestaurantObj);
                        }

                        if (datalist != null) {

                            recyclerViewadapter = new RestaurantsAdapter(datalist, getContext());
                            restaurant_recycler_view.setAdapter(recyclerViewadapter);
                            recyclerViewadapter.notifyDataSetChanged();

                            recyclerViewadapter.setOnItemClickListner(new OnItemClickListner() {
                                @Override
                                public void OnItemClicked(View view, final int position) {

                                         RestaurantsModel model = datalist.get(position);

                                        Fragment restaurantMenuItemsFragment = new RestaurantMenuItemsFragment();
                                        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                                        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
                                        Bundle bundle=new Bundle();
                                        bundle.putSerializable("data",model);
                                        restaurantMenuItemsFragment.setArguments(bundle);
                                        transaction.addToBackStack(null);
                                        transaction.add(R.id.RestaurantsFragment, restaurantMenuItemsFragment, "parent").commit();

                                    }
                            });

                        }


                    }
                } catch(JSONException e){
                    e.printStackTrace();
                }

                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);

            }
        });


    }



    private void initPager(final View v) {

        ImagesArray = new ArrayList<ImageSliderModel>();
         ApiRequest.Call_Api(context, Config.SHOW_SLIDER, null, new Callback() {
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

                            JSONObject jsonObjRestaurant = json1.getJSONObject("AppSlider");
                            ImageSliderModel imageSliderModel = new ImageSliderModel();
                            imageSliderModel.setSliderImageUrl(jsonObjRestaurant.optString("image"));
                            ImagesArray.add(imageSliderModel);
                        }


                        try {
                            pageIndicatorView.setCount(ImagesArray.size());
                            mPager.setAdapter(new SlidingImageAdapter(getContext(),ImagesArray));
                        }
                        catch (NullPointerException e){
                            e.getCause();
                        }

                        PageIndicatorView indicator = (PageIndicatorView) v.findViewById(R.id.pageIndicatorView);
                        indicator.setViewPager(mPager);
                        try {

                            timeCounter = new Runnable() {

                                @Override
                                public void run() {
                                    if((currentPage+1)>ImagesArray.size() ){
                                        currentPage=0;
                                    }else{
                                        currentPage++;
                                    }
                                    mPager.setCurrentItem(currentPage);
                                    handler.postDelayed(timeCounter, 5*1000);

                                }
                            };
                            handler.post(timeCounter);

                        }
                        catch (IllegalStateException e){
                            e.getCause();
                        }



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void search(final androidx.appcompat.widget.SearchView searchView, final View v) {

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() > 0) {
                    // Search
                    if (recyclerViewadapter != null)
                        recyclerViewadapter.getFilter().filter(newText);


                    RelativeLayout.LayoutParams parms =
                            new RelativeLayout.LayoutParams(0,0);
                    recyclerHeader.setLayoutParams(parms);


                }

                else {
                    RelativeLayout.LayoutParams parms =
                            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,AllConstants.height/3);
                    recyclerHeader.setLayoutParams(parms);



                    if (recyclerViewadapter != null) {
                        recyclerViewadapter.setmFilteredList(datalist);
                        recyclerViewadapter.notifyDataSetChanged();
                    }
                }


                return true;

            }
        });
    }


    public void recyclerWithHeader(View view){

        restaurant_recycler_view = view.findViewById(R.id.restaurant_recycler_view);

        recyclerViewlayoutManager = new LinearLayoutManager(getContext());

        restaurant_recycler_view.setLayoutManager(recyclerViewlayoutManager);

        recyclerHeader = (RecyclerViewHeader) view.findViewById(R.id.header);
        recyclerHeader.attachTo(restaurant_recycler_view);

        RelativeLayout.LayoutParams parms =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,AllConstants.height/3);
        recyclerHeader.setLayoutParams(parms);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(timeCounter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(timeCounter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(timeCounter);
    }

    @Override
    public void onPause() {
        super.onPause();

        handler.removeCallbacks(timeCounter);


    }

    @Override
    public void onResume() {
        super.onResume();

        if(MapsActivity.SAVE_LOCATION) {

            lat = sharedPreferences.getString(PreferenceClass.LATITUDE, "");
            lon = sharedPreferences.getString(PreferenceClass.LONGITUDE, "");

            Address locationAddress;

            locationAddress = getAddress(Double.parseDouble(lat), Double.parseDouble(lon));
            if (locationAddress != null) {

                String city = locationAddress.getLocality();

                String country = locationAddress.getCountryName();

                String address = city + " " + country;

                title_city_tv.setText(address);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(PreferenceClass.CURRENT_LOCATION_ADDRESS, address).commit();
                getRestaurantList();
                MapsActivity.SAVE_LOCATION = false;


            }
        }


    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }



    public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> implements Filterable {

        ArrayList<RestaurantsModel> getDataAdapter;
        private ArrayList<RestaurantsModel> mFilteredList;
        Context context;
        ImageLoader imageLoader1;
        OnItemClickListner onItemClickListner;
        SharedPreferences sharedPreferences;


        public RestaurantsAdapter(ArrayList<RestaurantsModel> getDataAdapter, Context context){
            super();
            this.getDataAdapter = getDataAdapter;
            this.mFilteredList = getDataAdapter;
            this.context = context;
        }

        public ArrayList<RestaurantsModel> getmFilteredList() {
            return mFilteredList;
        }

        public void setmFilteredList(ArrayList<RestaurantsModel> mFilteredList) {
            this.mFilteredList = mFilteredList;
        }

        @Override
        public RestaurantsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = null;

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_restaurants, parent, false);

            ViewHolder viewHolder = new ViewHolder(v);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final RestaurantsAdapter.ViewHolder holder, final int position) {

            final RestaurantsModel getDataAdapter1 =  getmFilteredList().get(position);

            sharedPreferences = context.getSharedPreferences(PreferenceClass.user,Context.MODE_PRIVATE);
            imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();

            holder.favorite_icon.setTag(getDataAdapter1);
            RestaurantsModel checkWetherToShow=(RestaurantsModel)holder.favorite_icon.getTag();


            Uri uri = Uri.parse(Config.imgBaseURL+getDataAdapter1.restaurant_image);
            holder.restaurant_img.setImageURI(uri);

            holder.title_restaurants.setText(getDataAdapter1.restaurant_name.trim());



            String symbol = getDataAdapter1.restaurant_currency;
            holder.salogon_restaurants.setText(getDataAdapter1.restaurant_salgon.trim());
            holder.baked_time_tv.setText(getDataAdapter1.preparation_time+ " min");
            holder.item_delivery_time_tv.setText(getDataAdapter1.deliveryTime+" min");

            holder.ratingBar.setRating(Float.parseFloat(getDataAdapter1.restaurant_avgRating));

            if(getDataAdapter1.min_order_price.equalsIgnoreCase("0.00")){
                holder.item_time_tv.setText(symbol + " " + getDataAdapter1.delivery_fee_per_km + " /km");
            }
            else {
                holder.item_time_tv.setText(symbol + " " + getDataAdapter1.delivery_fee_per_km+ " /km- Free over" + " " + symbol + " " + getDataAdapter1.min_order_price);

            }

            if (checkWetherToShow.restaurant_isFav.equalsIgnoreCase("1")){
                holder.favorite_icon.setImageResource(R.drawable.ic_heart_filled);
            }
            else {
                holder.favorite_icon.setImageResource(R.drawable.ic_heart_not_filled);
            }

            String getPromotedString = getDataAdapter1.promoted;

            if (getPromotedString.equalsIgnoreCase("1"))
            {
                holder.featured.setVisibility(View.VISIBLE);
            }
            else {
                holder.featured.setVisibility(View.GONE);
            }

            holder.distanse_restaurants.setText(getDataAdapter1.restaurant_distance);

            holder.restaurant_row_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (onItemClickListner !=null){
                        int position = holder.getAdapterPosition();
                        String name = mFilteredList.get(position).restaurant_id;
                        for (int i=0 ; i <getDataAdapter.size() ; i++ ){
                            if(name.equals(getDataAdapter.get(i).restaurant_id)){
                                position = i;
                                break;
                            }
                        }
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListner.OnItemClicked(view,position);
                        }
                    }
                }
            });


            holder.favorite_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     boolean getLoINSession = sharedPreferences.getBoolean(PreferenceClass.IS_LOGIN,false);
                    if(!getLoINSession){}
                    else {
                        addFavoriteRestaurant(getDataAdapter1.restaurant_id);

                    }

                }
            });

        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0)
                return 1;
            else
                return 2;
        }

        @Override
        public int getItemCount() {
            return mFilteredList.size() ;
        }



        @Override
        public Filter getFilter() {

            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        mFilteredList = getDataAdapter;
                    } else {
                        ArrayList<RestaurantsModel> filteredList = new ArrayList<>();
                        for (RestaurantsModel row : getDataAdapter) {

                              if (row.restaurant_name.toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                        mFilteredList = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mFilteredList;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    mFilteredList = (ArrayList<RestaurantsModel>) filterResults.values;
                    notifyDataSetChanged();
                }
            };

        }

        class ViewHolder extends RecyclerView.ViewHolder{

            public TextView title_restaurants,distanse_restaurants,salogon_restaurants,item_price_tv,item_time_tv,baked_time_tv,
                    item_delivery_time_tv;
            public SimpleDraweeView restaurant_img;
            public RelativeLayout restaurant_row_main;
            public RatingBar ratingBar;
            public ImageView favorite_icon,featured;

            public ViewHolder(View itemView) {

                super(itemView);
                title_restaurants = (TextView)itemView.findViewById(R.id.title_restaurants);
                salogon_restaurants = (TextView)itemView.findViewById(R.id.salogon_restaurants);
                distanse_restaurants = (TextView) itemView.findViewById(R.id.distanse_restaurants) ;
                item_price_tv = itemView.findViewById(R.id.item_price_tv);

                restaurant_img =  itemView.findViewById(R.id.profile_image_restaurant) ;
                restaurant_row_main = itemView.findViewById(R.id.restaurant_row_main);
                ratingBar = itemView.findViewById(R.id.ruleRatingBar);
                favorite_icon = itemView.findViewById(R.id.favorite_icon);
                featured = itemView.findViewById(R.id.featured);
                item_time_tv = itemView.findViewById(R.id.item_time_tv);
                baked_time_tv = itemView.findViewById(R.id.baked_time_tv);
                item_delivery_time_tv = itemView.findViewById(R.id.item_delivery_time_tv);

            }
        }


        public void setOnItemClickListner(OnItemClickListner onCardClickListner) {
            this.onItemClickListner = onCardClickListner;
        }



        public void addFavoriteRestaurant(String res_id){

            String user_id = sharedPreferences.getString(PreferenceClass.pre_user_id,"");

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("user_id",user_id);
                jsonObject.put("restaurant_id",res_id);
                jsonObject.put("favourite","1");

            } catch (JSONException e) {
                e.printStackTrace();
            }


            TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
            transparent_layer.setVisibility(View.VISIBLE);
            progressDialog.setVisibility(View.VISIBLE);

            ApiRequest.Call_Api(context, Config.ADD_FAV_RESTAURANT, jsonObject, new Callback() {
                @Override
                public void Responce(String resp) {

                    TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                    transparent_layer.setVisibility(View.GONE);
                    progressDialog.setVisibility(View.GONE);

                    try {
                        JSONObject  converResponseToJson = new JSONObject(resp);

                        int code_id  = Integer.parseInt(converResponseToJson.optString("code"));
                        if(code_id == 200) {
                            getRestaurantList();
                            notifyDataSetChanged();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });


        }


    }

    public interface OnItemClickListner {
        void OnItemClicked(View view, int position);
    }

}
