package com.foodies.customer.ActivitiesAndFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodies.customer.Adapters.OrderAdapter;
import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.OrderModelClass;
import com.foodies.customer.Utils.FontHelper;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.foodies.customer.Utils.TabLayoutUtils;
import com.foodies.customer.R;
import com.gmail.samehadar.iosdialog.CamomileSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.foodies.customer.ActivitiesAndFragments.DealOrderFragment.DEAL_PLACED;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class OrdersFragment extends RootFragment {

    ImageView filter_search;
    public static boolean STATUS_INACTIVE,FLAG_ACCEPTED_ORDER;

    SharedPreferences sPre;

    RecyclerView order_history_recyclerview;

    RecyclerView.LayoutManager recyclerViewlayoutManager;
    OrderAdapter recyclerViewadapter;

    LinearLayout recycler_view_restaurant;
    CamomileSpinner orderProgressBar;
    SwipeRefreshLayout refresh_layout;
    String status_active = "1";
    String status_inactive = "2";

    ArrayList<OrderModelClass> orderArrayList;
   RelativeLayout no_job_div;
    TextView title_tv;
    RelativeLayout transparent_layer,progressDialog;

    public static boolean _hasLoadedOnce= false;


    View view;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.orders_fragment, container, false);
        context=getContext();

        FrameLayout frameLayout = view.findViewById(R.id.order_fragment_container);
        FontHelper.applyFont(getContext(),frameLayout, AllConstants.verdana);
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        initUI(view);
        sPre = getContext().getSharedPreferences(PreferenceClass.user,getContext().MODE_PRIVATE);
        FLAG_ACCEPTED_ORDER = true;
        order_history_recyclerview = view.findViewById(R.id.order_history_recyclerview);
        orderProgressBar = view.findViewById(R.id.orderProgress);
        orderProgressBar.start();

        order_history_recyclerview.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(getContext());
        order_history_recyclerview.setLayoutManager(recyclerViewlayoutManager);

        if( OrderDetailFragment.CALLBACK_ORDERFRAG||DEAL_PLACED){
            getAllOrderParser(status_active);
            OrderDetailFragment.CALLBACK_ORDERFRAG = false;
            DEAL_PLACED= false;
        }

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);

        if (this.isVisible()) {
               if (isFragmentVisible_ && !_hasLoadedOnce) {
                getAllOrderParser(status_active);
                _hasLoadedOnce = true;
            }
        }
    }

    private void initUI(View v){


        progressDialog = v.findViewById(R.id.progressDialog);
        transparent_layer = v.findViewById(R.id.transparent_layer);

        title_tv = v.findViewById(R.id.title_tv);
        title_tv.setText(getResources().getString(R.string.history));
        no_job_div = v.findViewById(R.id.no_job_div);
        refresh_layout = v.findViewById(R.id.refresh_layout);
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(FLAG_ACCEPTED_ORDER) {
                    getAllOrderParser(status_active);
                }
                else {
                    getAllOrderParser(status_inactive);
                }
                refresh_layout.setRefreshing(false);

            }
        });

       recycler_view_restaurant = v.findViewById(R.id.recycler_view_restaurant );
                filter_search = v.findViewById(R.id.filter_search);
        filter_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogbox();
            }
        });


    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        setUserVisibleHint(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUserVisibleHint(false);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);


    }

    private void getAllOrderParser(String status_){


        orderArrayList = new ArrayList<>();
        String user_id = sPre.getString(PreferenceClass.pre_user_id,"0");

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("user_id",user_id);
            jsonObject.put("status",status_);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        ApiRequest.Call_Api(context, Config.SHOW_ORDERS, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {

                try {
                    JSONObject  jsonResponse = new JSONObject(resp);

                      int code_id  = Integer.parseInt(jsonResponse.optString("code"));

                    if(code_id == 200) {

                        JSONObject json = new JSONObject(jsonResponse.toString());
                        JSONArray jsonarray = json.getJSONArray("msg");


                        if(jsonarray.length()==0){
                            recycler_view_restaurant.setVisibility(View.GONE);

                        }
                        else {
                            recycler_view_restaurant.setVisibility(View.VISIBLE);
                        }

                        for (int i = 0; i < jsonarray.length(); i++) {

                            JSONObject json1 = jsonarray.getJSONObject(i);

                            JSONObject jsonObjOrder = json1.getJSONObject("Order");
                            JSONObject jsonObjCurrency = jsonObjOrder.getJSONObject("Currency");
                            OrderModelClass orderModelClass = new OrderModelClass();
                            orderModelClass.setCurrency_symbol(jsonObjCurrency.optString("symbol"));
                            orderModelClass.setOrder_price(jsonObjOrder.optString("price"));
                            orderModelClass.setInstructions(jsonObjOrder.optString("instructions"));
                            orderModelClass.setRestaurant_name(jsonObjOrder.optString("name"));
                            orderModelClass.setOrder_quantity(jsonObjOrder.optString("quantity"));
                            orderModelClass.setOrder_id(jsonObjOrder.optString("id"));
                            orderModelClass.setOrder_created(jsonObjOrder.optString("created"));
                            orderModelClass.setDelivery(jsonObjOrder.optString("delivery"));
                            orderModelClass.setDeal_id(jsonObjOrder.optString("deal_id"));

                            if(jsonObjOrder.getJSONArray("OrderMenuItem")!=null && jsonObjOrder.getJSONArray("OrderMenuItem").length()>0) {
                                JSONArray jsonarrayOrder = jsonObjOrder.getJSONArray("OrderMenuItem");
                                JSONObject jsonObjectMenu = jsonarrayOrder.getJSONObject(0);

                                orderModelClass.setOrder_menu_id(jsonObjectMenu.optString("id"));
                                orderModelClass.setOrder_name(jsonObjectMenu.optString("name"));

                                JSONArray  jsonarrayExtraOrder = jsonObjectMenu.getJSONArray("OrderMenuExtraItem");

                                if(jsonarrayExtraOrder!=null && jsonarrayExtraOrder.length()>0) {
                                    JSONObject jsonObjectExtraMenu = jsonarrayExtraOrder.getJSONObject(0);
                                    orderModelClass.setOrder_extra_item_name(jsonObjectExtraMenu.optString("name"));

                                }

                            }

                            orderArrayList.add(orderModelClass);

                        }

                        if (orderArrayList!=null) {

                            if(orderArrayList.size()>0){
                                no_job_div.setVisibility(View.GONE);
                            }
                            else if(orderArrayList.size()==0) {
                                no_job_div.setVisibility(View.VISIBLE);
                            }

                            recyclerViewadapter = new OrderAdapter(orderArrayList, getActivity());
                            order_history_recyclerview.setAdapter(recyclerViewadapter);
                            recyclerViewadapter.notifyDataSetChanged();

                        }
                        recyclerViewadapter.setOnItemClickListner(new OrderAdapter.OnItemClickListner() {
                            @Override
                            public void OnItemClicked(View view, int position) {

                                SharedPreferences.Editor editor = sPre.edit();
                                editor.putString(PreferenceClass.ORDER_HEADER,orderArrayList.get(position).getOrder_name());
                                editor.putString(PreferenceClass.ORDER_ID,orderArrayList.get(position).getOrder_id());
                                editor.putString(PreferenceClass.ORDER_INS,orderArrayList.get(position).getInstructions());
                                editor.putString("delivery_",orderArrayList.get(position).getDelivery());
                                editor.putString(PreferenceClass.ORDER_QUANTITY,orderArrayList.get(position).getOrder_quantity());
                                editor.commit();

                                Fragment restaurantMenuItemsFragment = new OrderDetailFragment();
                                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                                transaction.addToBackStack(null);
                                transaction.add(R.id.order_fragment_container, restaurantMenuItemsFragment,"ParentFragment").commit();

                            }
                        });


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

    public void customDialogbox(){

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialoge_box);
        dialog.setTitle("Order Filter");


        RelativeLayout cancelDiv = (RelativeLayout) dialog.findViewById(R.id.forth);
        RelativeLayout currentOrderDiv = (RelativeLayout) dialog.findViewById(R.id.second);
        RelativeLayout pastOrderDiv = (RelativeLayout) dialog.findViewById(R.id.third);
        TextView first_tv = (TextView)dialog.findViewById(R.id.first_tv);
        TextView second_tv = (TextView)dialog.findViewById(R.id.second_tv);
        first_tv.setText("Current Orders");
        second_tv.setText("Past Orders");

        currentOrderDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                STATUS_INACTIVE = false;
                getAllOrderParser(status_active);
                dialog.dismiss();
                FLAG_ACCEPTED_ORDER = true;
            }
        });

        pastOrderDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                STATUS_INACTIVE = true;
               getAllOrderParser(status_inactive);
                dialog.dismiss();
                FLAG_ACCEPTED_ORDER = false;
            }
        });

        // if button is clicked, close the custom dialog
        cancelDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
