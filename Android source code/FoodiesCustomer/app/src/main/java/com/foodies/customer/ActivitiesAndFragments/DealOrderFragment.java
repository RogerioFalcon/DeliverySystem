package com.foodies.customer.ActivitiesAndFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.Fragment_Callback;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.AddressListModel;
import com.foodies.customer.Models.DealsModel;
import com.foodies.customer.Utils.FontHelper;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.foodies.customer.Utils.TabLayoutUtils;
import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.foodies.customer.R;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class DealOrderFragment extends RootFragment implements View.OnClickListener {

    SharedPreferences deal_order_pref;

    ImageView back_icon;
    String deal_name, deals_tax, deal_price, deal_currency, delivery_fee, delivery_address_street, delivery_address_state, delivery_address_city,
            apartment, card_number, card_brand, deal_desc, user_id, payment_id, address_id, rest_name,riderTip,res_id,formattedDate,deal_id;
    TextView rest_name_tv, deal_desc_tv, deal_price_tv, sub_total_price_tv, tax_tv, total_delivery_fee_tv, total_deal_order_tv, delivery_address_tv,
            credit_card_number_tv,total_tex_tv,deal_name_tv,total_sum_tv,rider_tip,rider_tip_price_tv,decline_tv,accept_tv;
    RelativeLayout deal_payment_method_div, deal_address_div, cart_check_out_div,tip_div,accept_div,decline_div,cart_address_div;
    public static boolean DEALS;
    int deal_quantity;
    double getTax, getFinalPrice,grandTotal;
    boolean getLoINSession,PICK_UP;

    Double previousRiderTip = 0.0;
    public static boolean DEAL_ADDRESS,DEAL_LOGIN,DEAL_PAYMENT_METHOD,DEAL_PLACED;

    CamomileSpinner pbHeaderProgress;

    RelativeLayout transparent_layer,progressDialog;
    View view;
    Context context;


    DealsModel dealsModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.deals_report_layout, container, false);
        context=getContext();

        Bundle bundle=getArguments();
        if(bundle!=null){
            dealsModel=(DealsModel) bundle.getSerializable("data");
        }

        deal_order_pref = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        getLoINSession = deal_order_pref.getBoolean(PreferenceClass.IS_LOGIN,false);
        grandTotal = 0.0;
        riderTip = "0";
        FrameLayout frameLayout = view.findViewById(R.id.deal_order_main_container);
        FontHelper.applyFont(getContext(), frameLayout, AllConstants.verdana);
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        DEALS = true;

        initUI(view);
        return view;

    }

    public void initUI(View v){
        decline_div = v.findViewById(R.id.decline_div);
        accept_div = v.findViewById(R.id.accept_div);
        decline_tv = v.findViewById(R.id.decline_tv);
        accept_tv = v.findViewById(R.id.accept_tv);
        rider_tip_price_tv = v.findViewById(R.id.rider_tip_price_tv);
        rider_tip = v.findViewById(R.id.rider_tip);

        deal_id =dealsModel.deal_id;
        deal_name = dealsModel.deal_name;
        deal_desc =dealsModel.deal_desc;

        deal_price =dealsModel.deal_price;
        deal_currency =dealsModel.deal_symbol;
        if(dealsModel.isDeliveryFree.equalsIgnoreCase("1")) {
            deals_tax = "0";
        }
        else {
            deals_tax =dealsModel.deal_tax;
        }


        res_id =dealsModel.deal_restaurant_id;
        rest_name =dealsModel.restaurant_name;


        delivery_address_street = deal_order_pref.getString(PreferenceClass.STREET,"");
        delivery_address_state = deal_order_pref.getString(PreferenceClass.STATE,"");
        delivery_address_city = deal_order_pref.getString(PreferenceClass.CITY,"");
        apartment = deal_order_pref.getString(PreferenceClass.APARTMENT,"");
        user_id = deal_order_pref.getString(PreferenceClass.pre_user_id,"");
        payment_id = deal_order_pref.getString(PreferenceClass.PAYMENT_ID,"");
        address_id = deal_order_pref.getString(PreferenceClass.ADDRESS_ID,"");


        card_number = deal_order_pref.getString(PreferenceClass.CREDIT_CARD_ARRAY,"");
        card_brand = deal_order_pref.getString(PreferenceClass.CREDIT_CARD_BRAND,"");



        deal_quantity = deal_order_pref.getInt(PreferenceClass.DEALS_QUANTITY,1);

        getFinalPrice = Double.parseDouble(deal_price)*Double.parseDouble(String.valueOf(deal_quantity));


        progressDialog = v.findViewById(R.id.progressDialog);
        transparent_layer = v.findViewById(R.id.transparent_layer);
        pbHeaderProgress = v.findViewById(R.id.dealOrderProgress);
        pbHeaderProgress.start();
        credit_card_number_tv = v.findViewById(R.id.credit_card_number_tv);
        deal_payment_method_div = v.findViewById(R.id.deal_payment_method_div);
        deal_address_div = v.findViewById(R.id.deal_address_div);
        delivery_address_tv = v.findViewById(R.id.delivery_address_tv);
        rest_name_tv = v.findViewById(R.id.rest_name_tv);
        rest_name_tv.setText(rest_name);
        deal_desc_tv = v.findViewById(R.id.deal_desc_tv);
        deal_desc_tv.setText(deal_desc);
        sub_total_price_tv = v.findViewById(R.id.sub_total_price_tv);
        sub_total_price_tv.setText(deal_currency+""+getFinalPrice);

        credit_card_number_tv.setText("Select Payment Method");



        deal_price_tv = v.findViewById(R.id.deal_price_tv);
        deal_price_tv.setText(deal_currency+deal_price);

        tax_tv = v.findViewById(R.id.tax_tv);
        tax_tv.setText("("+deals_tax+"%)");
        total_tex_tv = v.findViewById(R.id.total_tex_tv);
        getTax = getFinalPrice*Double.parseDouble(deals_tax)/100;
        total_tex_tv.setText(deal_currency+getTax);

        deal_name_tv = v.findViewById(R.id.deal_name_tv);
        deal_name_tv.setText(deal_name + " (x"+deal_quantity+")");

        total_delivery_fee_tv = v.findViewById(R.id.total_delivery_fee_tv);


        delivery_address_tv.setText("Select Delivery Address");
        total_delivery_fee_tv.setText("0");
        delivery_fee = "0";


        total_sum_tv = v.findViewById(R.id.total_sum_tv);
        grandTotal = Double.parseDouble(delivery_fee)+getFinalPrice+getTax;
        total_sum_tv.setText(deal_currency+new DecimalFormat("##.##").format(grandTotal));

        tip_div = v.findViewById(R.id.tip_div);
        tip_div.setOnClickListener(this);

        back_icon = v.findViewById(R.id.back_icon);
        back_icon.setOnClickListener(this);

        deal_address_div.setOnClickListener(this);
        deal_payment_method_div.setOnClickListener(this);
        cart_check_out_div = v.findViewById(R.id.cart_check_out_div);
        cart_check_out_div.setOnClickListener(this);


        pickUpOrDelivery();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.back_icon:
                getActivity().onBackPressed();
                break;

            case R.id.tip_div:
                addRiderTip();
                break;

            case R.id.deal_address_div:
                if(!getLoINSession){

                    TabLayout.Tab tab =PagerMainActivity.tabLayout.getTabAt(3);
                    tab.select();

                }
                else {
                    Fragment restaurantMenuItemsFragment = new AddressListFragment(new Fragment_Callback() {
                        @Override
                        public void Responce(Bundle bundle) {
                            if(bundle!=null){
                                AddressListModel addressListModel=(AddressListModel)bundle.getSerializable("data");

                                delivery_address_street =addressListModel.getStreet();
                                delivery_address_city =addressListModel.getCity();
                                delivery_address_state =addressListModel.getState();
                                apartment =addressListModel.getApartment();
                                address_id=addressListModel.getAddress_id();
                                credit_card_number_tv.setTextColor(ContextCompat.getColor(context,R.color.black));

                                delivery_address_tv.setText(delivery_address_street + " " + delivery_address_city + " " + delivery_address_state);
                                delivery_address_tv.setTextColor(ContextCompat.getColor(context,R.color.black));

                            }
                        }
                    });

                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    Bundle bundle=new Bundle();
                    bundle.putString("grand_total",String.valueOf(grandTotal));
                    bundle.putString("rest_id",res_id);
                    transaction.addToBackStack(null);
                    transaction.add(R.id.deal_order_main_container, restaurantMenuItemsFragment, "parent").commit();

                }
                break;


            case R.id.deal_payment_method_div:
                if(!getLoINSession){

                    TabLayout.Tab tab =PagerMainActivity.tabLayout.getTabAt(3);
                    tab.select();

                }
                else {
                    Fragment restaurantMenuItemsFragment = new AddPaymentFragment(new Fragment_Callback() {
                        @Override
                        public void Responce(Bundle bundle) {
                            if(bundle!=null){

                                payment_id=bundle.getString("card_id");
                                card_number=bundle.getString("card_number");
                                credit_card_number_tv.setText(card_number);
                                credit_card_number_tv.setTextColor(ContextCompat.getColor(context,R.color.black));
                            }
                        }
                    });
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.add(R.id.deal_order_main_container, restaurantMenuItemsFragment, "parent").commit();
                }
                break;


            case R.id.cart_check_out_div:
                if(delivery_address_tv.getText().toString().equalsIgnoreCase("Select Delivery Address")
                        || credit_card_number_tv.getText().toString().equalsIgnoreCase("Select Payment Method")
                )
                {
                    Toast.makeText(getContext(),"Delivery Address OR Payment Method is Missed",Toast.LENGTH_LONG).show();
                }else {
                    dealOrder();
                }
                break;



        }
    }




    public void addRiderTip(){

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog_cart);

        final EditText ed_text = dialog.findViewById(R.id.ed_text);
        ed_text.setInputType(InputType.TYPE_CLASS_NUMBER);
        TextView title = dialog.findViewById(R.id.title);
        title.setText("Add Rider Tip");
        ed_text.setHint("Enter Tip Here");

        Button cancelDiv = (Button) dialog.findViewById(R.id.cancel_btn);
        Button done_btn =  (Button) dialog.findViewById(R.id.done_btn);

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                riderTip = ed_text.getText().toString();
                PICK_UP = false;
                getTotalSumTip(riderTip,PICK_UP);
                rider_tip_price_tv.setText(deal_currency+riderTip);
                rider_tip.setText(deal_currency+riderTip);
                dialog.dismiss();
            }
        });


        cancelDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void getTotalSumTip(String riderTip,boolean rider_tip_pick_up){
        if(rider_tip_pick_up){
            grandTotal = Double.parseDouble(String.valueOf(grandTotal-Double.parseDouble(riderTip)));
        }
        else {

            grandTotal = Double.parseDouble(String.valueOf(grandTotal + Double.parseDouble(riderTip)));
            grandTotal = grandTotal-previousRiderTip;
            previousRiderTip = Double.parseDouble(riderTip);

        }
        total_sum_tv.setText(deal_currency+new DecimalFormat("##.##").format(grandTotal));

    }

    public void pickUpOrDelivery(){
        decline_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decline_div.setBackground(ContextCompat.getDrawable(context,R.drawable.round_shape_btn_login));
                accept_div.setBackground(ContextCompat.getDrawable(context,R.drawable.round_shape_btn_grey));
                decline_tv.setTextColor(ContextCompat.getColor(context,R.color.colorWhite));
                accept_tv.setTextColor(ContextCompat.getColor(context,R.color.or_color_name));
                rider_tip_price_tv.setText(deal_currency+"0");
                total_delivery_fee_tv.setText(deal_currency+"0");
                rider_tip.setText(deal_currency+"0");
                delivery_address_tv.setText("Pick Up");
                PICK_UP = true;
                getTotalSumDeliveryFee(delivery_fee,PICK_UP);
                getTotalSumTip(riderTip,PICK_UP);
                deal_address_div.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });

                tip_div.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });

            }
        });

        accept_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decline_div.setBackground(ContextCompat.getDrawable(context,R.drawable.round_shape_btn_grey));
                accept_div.setBackground(ContextCompat.getDrawable(context,R.drawable.round_shape_btn_login));
                decline_tv.setTextColor(ContextCompat.getColor(context,R.color.or_color_name));
                accept_tv.setTextColor(ContextCompat.getColor(context,R.color.colorWhite));

                rider_tip_price_tv.setText(deal_currency+riderTip);
                total_delivery_fee_tv.setText(deal_currency+delivery_fee);
                rider_tip.setText(deal_currency+riderTip);
                if(delivery_address_street.isEmpty()&&apartment.isEmpty()&&delivery_address_city.isEmpty()&&delivery_address_state.isEmpty()){
                    delivery_address_tv.setText("Select Delivery Address");
                }
                else {
                    delivery_address_tv.setText(delivery_address_street + " " + apartment + " " + delivery_address_city + " " + delivery_address_state);
                }
                PICK_UP = false;

                previousRiderTip = 0.0;
                getTotalSumDeliveryFee(delivery_fee,PICK_UP);
                getTotalSumTip(riderTip,PICK_UP);

                deal_address_div.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return false;
                    }
                });

                tip_div.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return false;
                    }
                });
            }
        });
    }


    public void getTotalSumDeliveryFee(String deliveryFee,boolean picu_up){

        if(picu_up){
            grandTotal = Double.parseDouble(String.valueOf(grandTotal-Double.parseDouble(deliveryFee)));
        }
        else {
            grandTotal = Double.parseDouble(String.valueOf(grandTotal + Double.parseDouble(deliveryFee)));
        }
        total_sum_tv.setText(deal_currency+new DecimalFormat("##.##").format(grandTotal));

    }


    public void dealOrder() {

        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name", deal_name);
            jsonObject.put("description", deal_desc);
            jsonObject.put("price", grandTotal);
            if (AddPaymentFragment.FLAG_PAYMENT_METHOD) {
                jsonObject.put("cod", "1");
                jsonObject.put("payment_id", "0");
                AddPaymentFragment.FLAG_PAYMENT_METHOD = false;
            } else {
                jsonObject.put("cod", "0");
                jsonObject.put("payment_id", payment_id);
            }

            jsonObject.put("order_time", formattedDate);
            jsonObject.put("user_id", user_id);
            jsonObject.put("quantity",String.valueOf(deal_quantity));
            jsonObject.put("tax",getTax);
            jsonObject.put("sub_total",getFinalPrice);
            jsonObject.put("delivery_fee",delivery_fee);
            jsonObject.put("restaurant_id",res_id);
            jsonObject.put("device","android");
            jsonObject.put("deal_id",deal_id);
            jsonObject.put("version",SplashScreen.VERSION_CODE);
            if(rider_tip.getText().toString().equalsIgnoreCase("Add Rider Tip")){
                jsonObject.put("rider_tip","0");
            }
            else {
                jsonObject.put("rider_tip", riderTip);
            }
            if(delivery_address_tv.getText().toString().equalsIgnoreCase("Pick Up"))
            {
                jsonObject.put("delivery","0");
                jsonObject.put("address_id", "0");
            }
            else {
                jsonObject.put("delivery","1");
                jsonObject.put("address_id", address_id);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        ApiRequest.Call_Api(context, Config.ORDER_DEAL, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {

                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);

                try {
                    JSONObject jsonResponse = new JSONObject(resp);

                    int code_id  = Integer.parseInt(jsonResponse.optString("code"));

                    if(code_id == 200) {

                        SharedPreferences.Editor editor = deal_order_pref.edit();
                        editor.putString(PreferenceClass.ADDRESS_DELIVERY_FEE,"0").commit();
                        CartFragment.ORDER_PLACED = true;
                        DEAL_PLACED = true;
                        getActivity().startActivity(new Intent(getContext(),MainActivity.class));
                        getActivity().finish();

                    }else {
                        Toast.makeText(context, ""+jsonResponse.optString("msg"), Toast.LENGTH_LONG).show();
                    }

                }catch (JSONException e){
                    e.getCause();
                }
            }
        });


    }


}