package com.foodies.customer.ActivitiesAndFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.foodies.customer.Adapters.RestaurantMenuAdapter;
import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.RestaurantChildModel;
import com.foodies.customer.Models.RestaurantParentModel;
import com.foodies.customer.Models.RestaurantsModel;
import com.foodies.customer.Utils.CustomExpandableListView;
import com.foodies.customer.Utils.FontHelper;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.foodies.customer.Utils.TabLayoutUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.foodies.customer.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gmail.samehadar.iosdialog.CamomileSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;




/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class RestaurantMenuItemsFragment extends RootFragment {

    ImageView backIcon,about_icon,close_suggestion;
    RelativeLayout about_restaurant_div,review_restaurant_div,suggestion_div,suggestion_txt,rest_open_div;
    CustomExpandableListView expandableListView,restaurant_menu_item_list_suggestion;
    TextView rastaurant_menu_item_title_tv,restaurant_name_tv,salogon_tv,miles_desc_tv;

    SimpleDraweeView restaurant_img,cover_image;

    SharedPreferences sharedPreferences;
    RestaurantMenuAdapter restaurantMenuAdapter;
    ArrayList<RestaurantParentModel> listDataHeader;
    ArrayList<RestaurantChildModel> listChildData;
    private ArrayList<ArrayList<RestaurantChildModel>> ListChild;
    CamomileSpinner res_menu_item_progress;
    RatingBar rating;
    SearchView searchView;
    RelativeLayout upper_header;
    LinearLayout about_div;
    String udid, key;

    public static boolean FLAG_RES_MENU_FRAG,FLAG_SUGGESTION;


    RelativeLayout transparent_layer,progressDialog;
    public static final int  PERMISSION_DATA_CART_ADED = 5;
    String restarant_open;


    View view;
    Context context;

    RestaurantsModel item_model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restaurant_menu_items_fragment, container, false);
        context=getContext();

        Bundle bundle=getArguments();
        if(bundle!=null){
            item_model=(RestaurantsModel) bundle.get("data");
        }

        FrameLayout frameLayout = view.findViewById(R.id.resaurant_menu_items_main_layout);
        FontHelper.applyFont(getContext(),frameLayout.getRootView(), AllConstants.verdana);

        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        udid = sharedPreferences.getString(PreferenceClass.UDID,"");
        initUI(view);

        expandableListView = (CustomExpandableListView) view.findViewById(R.id.restaurant_menu_item_list);
        expandableListView.setExpanded(true);
        expandableListView.setGroupIndicator(null);

        restaurant_menu_item_list_suggestion = (CustomExpandableListView)view.findViewById(R.id.restaurant_menu_item_list_suggestion);
        restaurant_menu_item_list_suggestion.setExpanded(true);
        restaurant_menu_item_list_suggestion.setGroupIndicator(null);


       restaurantMenuDetail_for_listview();



        return view;
    }


    public void initUI(View v){

     progressDialog = v.findViewById(R.id.progressDialog);
     transparent_layer = v.findViewById(R.id.transparent_layer);


        suggestion_txt = v.findViewById(R.id.suggestion_txt);
        close_suggestion = v.findViewById(R.id.close_suggestion);
        suggestion_div = v.findViewById(R.id.suggestion_div);
        suggestion_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 FLAG_SUGGESTION = true;

                restaurantMenuDetail_for_listview();

                close_suggestion.setVisibility(View.VISIBLE);
                suggestion_txt.setVisibility(View.VISIBLE);
                suggestion_div.setVisibility(View.GONE);

                upper_header.setVisibility(View.GONE);
                review_restaurant_div.setVisibility(View.GONE);
                about_restaurant_div.setVisibility(View.GONE);
                about_div.setVisibility(View.GONE);
            }
        });

        close_suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FLAG_SUGGESTION = false;
                upper_header.setVisibility(View.VISIBLE);
                review_restaurant_div.setVisibility(View.VISIBLE);
                about_restaurant_div.setVisibility(View.VISIBLE);
                about_div.setVisibility(View.VISIBLE);
                close_suggestion.setVisibility(View.GONE);
                suggestion_txt.setVisibility(View.GONE);
                suggestion_div.setVisibility(View.VISIBLE);

                restaurantMenuDetail_for_listview();



            }
        });


     miles_desc_tv = v.findViewById(R.id.miles_desc_tv);
     searchView = v.findViewById(R.id.floating_search_view);
     String txt="<font color = #dddddd>" + "Search Restaurant Menu" + "</font>";
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
     search(searchView);

     upper_header = v.findViewById(R.id.upper_header);
     about_div = v.findViewById(R.id.about_div);

     about_icon = v.findViewById(R.id. about_icon);

     res_menu_item_progress = v.findViewById(R.id.res_menu_item_progress);
     res_menu_item_progress.start();
     rating = v.findViewById(R.id.rating);
     rastaurant_menu_item_title_tv = v.findViewById(R.id.rastaurant_menu_item_title_tv);
     restaurant_name_tv = v.findViewById(R.id.restaurant_name_tv);
     salogon_tv = v.findViewById(R.id.salogon_tv);
     restaurant_img = v.findViewById(R.id.restaurant_image);
     cover_image = v.findViewById(R.id.cover_image);


     rating.setRating(Float.parseFloat(item_model.restaurant_avgRating));

     rastaurant_menu_item_title_tv.setText(item_model.restaurant_name);
     restaurant_name_tv.setText(item_model.restaurant_name);
     salogon_tv.setText(item_model.restaurant_salgon);

     if(item_model.deliveryFee_Range.equalsIgnoreCase("0")){
         miles_desc_tv.setText(item_model.restaurant_currency + " " + item_model.delivery_fee_per_km + " /km");
     }
        else {
         miles_desc_tv.setText(item_model.restaurant_currency + " " + item_model.delivery_fee_per_km + " /km- free delivery over " + item_model.restaurant_currency + " " + item_model.min_order_price + " within " + item_model.deliveryFee_Range + " km");
     }


        Uri uri = Uri.parse(Config.imgBaseURL+item_model.restaurant_image);
        restaurant_img.setImageURI(uri);


     about_icon.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             new MaterialDialog.Builder(getContext())
                     .title(item_model.restaurant_name)
                     .content(item_model.restaurant_about)
                     .positiveText("OK")
                     .show();
         }
     });

        backIcon = v.findViewById(R.id.back_icon_menu_option);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               getActivity().onBackPressed();

            }
        });

        about_restaurant_div = (RelativeLayout)v.findViewById(R.id.about_restaurant_div);
        about_restaurant_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FLAG_RES_MENU_FRAG = true;
                RestaurantDealsFragment restaurantDealsFragment = new RestaurantDealsFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",item_model);
                restaurantDealsFragment.setArguments(bundle);
                transaction.addToBackStack(null);
                transaction.replace(R.id.resaurant_menu_items_main_layout, restaurantDealsFragment,"ParentFragment_MenuItems").commit();

            }
        });

     review_restaurant_div = (RelativeLayout)v.findViewById(R.id.review_restaurant_div);
     review_restaurant_div.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Fragment reviewListFragment = new ReviewListFragment();
             FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
             Bundle bundle=new Bundle();
             bundle.putSerializable("data",item_model);
             reviewListFragment.setArguments(bundle);
             transaction.addToBackStack(null);
             transaction.replace(R.id.resaurant_menu_items_main_layout, reviewListFragment,"ParentFragment_MenuItems").commit();
         }
     });


     upper_header.setVisibility(View.VISIBLE);
     review_restaurant_div.setVisibility(View.VISIBLE);
     about_restaurant_div.setVisibility(View.VISIBLE);
     about_div.setVisibility(View.VISIBLE);

        rest_open_div = v.findViewById(R.id.rest_open_div);




 }

    @Override
    public void onResume() {
        super.onResume();
        upper_header.setVisibility(View.VISIBLE);
        review_restaurant_div.setVisibility(View.VISIBLE);
        about_restaurant_div.setVisibility(View.VISIBLE);
        about_div.setVisibility(View.VISIBLE);
    }


    private void search(final androidx.appcompat.widget.SearchView searchView) {


        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {



                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.equalsIgnoreCase("")){
                    FLAG_SUGGESTION = false;
                    upper_header.setVisibility(View.VISIBLE);
                    review_restaurant_div.setVisibility(View.VISIBLE);
                    about_restaurant_div.setVisibility(View.VISIBLE);
                    about_div.setVisibility(View.VISIBLE);

                    restaurantMenuDetail_for_listview();
                }
                else {
                    if (restaurantMenuAdapter != null)
                        restaurantMenuAdapter.getFilter().filter(newText);
                    upper_header.setVisibility(View.GONE);
                    review_restaurant_div.setVisibility(View.GONE);
                    about_restaurant_div.setVisibility(View.GONE);
                    about_div.setVisibility(View.GONE);
                    close_suggestion.setVisibility(View.GONE);
                    suggestion_txt.setVisibility(View.GONE);


                }
                return true;
            }
        });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {

            @Override
            public boolean onClose() {

                Log.i("SearchView:", "onClose");
                searchView.onActionViewCollapsed();
                upper_header.setVisibility(View.VISIBLE);
                review_restaurant_div.setVisibility(View.VISIBLE);
                about_restaurant_div.setVisibility(View.VISIBLE);
                about_div.setVisibility(View.VISIBLE);
                for(int m=0; m < restaurantMenuAdapter.getGroupCount(); m++)
                    expandableListView.expandGroup(m);

                return false;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();

            }
        });

        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {

            @Override
            public void onViewDetachedFromWindow(View arg0) {

                upper_header.setVisibility(View.VISIBLE);
            }

            @Override
            public void onViewAttachedToWindow(View arg0) {
            }
        });
    }



    public void restaurantMenuDetail_for_listview(){

        if(FLAG_SUGGESTION){
            expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {
                    return false; // This way the expander cannot be collapsed
                }
            });
        }
        else {
            expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {
                    return true; // This way the expander cannot be collapsed
                }
            });
        }


        expandableListView.setVisibility(View.VISIBLE);
        listDataHeader = new ArrayList<>();
        ListChild = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id",item_model.restaurant_id);
            jsonObject.put("current_time",formattedDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        ApiRequest.Call_Api(context, Config.SHOW_RESTAURANT_MENU, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {

                try {
                    JSONObject jsonResponse = new JSONObject(resp);


                    int code_id = Integer.parseInt(jsonResponse.optString("code"));

                    if (code_id == 200) {

                        JSONObject json = new JSONObject(jsonResponse.toString());
                        JSONArray jsonArray = json.getJSONArray("msg");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject allJsonObject1 = jsonArray.getJSONObject(i);
                            JSONObject currency = allJsonObject1.getJSONObject("Currency");
                            String currency_symbol = currency.optString("symbol");
                            JSONObject coverImage = allJsonObject1.getJSONObject("Restaurant");
                            String coverImgURL = coverImage.optString("cover_image");
                            restarant_open = coverImage.optString("open");


                            Uri uri = Uri.parse(Config.imgBaseURL+coverImgURL);
                            cover_image.setImageURI(uri);


                            final JSONArray resMenuArray = allJsonObject1.getJSONArray("RestaurantMenu");

                            for (int j =0; j<resMenuArray.length();j++){

                                JSONObject resMenuAllObj = resMenuArray.getJSONObject(j);

                                RestaurantParentModel restaurantParentModel = new RestaurantParentModel();

                                restaurantParentModel.setTitle(resMenuAllObj.optString("name"));
                                restaurantParentModel.setSub_title(resMenuAllObj.optString("description"));

                                listDataHeader.add(restaurantParentModel);

                                listChildData = new ArrayList<>();

                                JSONArray menuItemArray = resMenuAllObj.getJSONArray("RestaurantMenuItem");
                                Log.d("Count",String.valueOf(menuItemArray.length()));
                                for(int k=0;k<menuItemArray.length();k++){
                                    JSONObject menuItemArrayObj = menuItemArray.getJSONObject(k);

                                    RestaurantChildModel restaurantChildModel = new RestaurantChildModel();


                                    restaurantChildModel.restaurant_menu_item_id=menuItemArrayObj.optString("id");
                                    restaurantChildModel.child_title=menuItemArrayObj.optString("name");
                                    restaurantChildModel.child_sub_title=menuItemArrayObj.optString("description");
                                    restaurantChildModel.order_detail=menuItemArrayObj.optString("out_of_order");
                                    restaurantChildModel.price=menuItemArrayObj.optString("price");
                                    restaurantChildModel.currency_symbol=currency_symbol;

                                    listChildData.add(restaurantChildModel);


                                }
                                ListChild.add(listChildData);

                            }


                            restaurant_menu_item_list_suggestion.setVisibility(View.GONE);
                            restaurantMenuAdapter = new RestaurantMenuAdapter(getContext(), listDataHeader, ListChild);
                            expandableListView.setAdapter(restaurantMenuAdapter);


                            expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                                @Override
                                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                                    FLAG_SUGGESTION = false;

                                    return false;
                                }
                            });



                            if(restarant_open.equalsIgnoreCase("1")) {

                                expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                                    @Override
                                    public boolean onChildClick(ExpandableListView expandableListView, View view,
                                                                int groupPosition, int childPosition, long l) {
                                        RestaurantChildModel item = (RestaurantChildModel) restaurantMenuAdapter.getChild(groupPosition, childPosition);


                                        if (item.order_detail.equalsIgnoreCase("1")) {

                                        } else {
                                            Intent intent = new Intent(getActivity(), AddToCartActivity.class);

                                            intent.putExtra("rest_model",item_model);
                                            intent.putExtra("rest_child_model",item);

                                            getActivity().startActivityForResult(intent, PERMISSION_DATA_CART_ADED);

                                        }

                                        return false;
                                    }
                                });
                            }
                            else {
                                rest_open_div.setVisibility(View.VISIBLE);
                                about_restaurant_div.setClickable(false);
                                expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                                    @Override
                                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                                        return true;
                                    }
                                });

                            }
                        }

                        if(!FLAG_SUGGESTION){
                            for (int m = 0; m < restaurantMenuAdapter.getGroupCount(); m++)
                                expandableListView.expandGroup(m);

                        }

                    }
                }
                catch (Exception e){
                    e.getMessage();

                }

                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);
            }
        });


    }




}
