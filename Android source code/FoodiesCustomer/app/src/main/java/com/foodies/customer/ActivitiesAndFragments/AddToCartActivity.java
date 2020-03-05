package com.foodies.customer.ActivitiesAndFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.foodies.customer.Adapters.AddToCartExpandable;
import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.CalculationModel;
import com.foodies.customer.Models.CartChildModel;
import com.foodies.customer.Models.CartParentModel;
import com.foodies.customer.Models.RestaurantChildModel;
import com.foodies.customer.Models.RestaurantsModel;
import com.foodies.customer.Utils.CustomExpandableListView;
import com.foodies.customer.Utils.FontHelper;

import com.foodies.customer.R;
import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.foodies.customer.ActivitiesAndFragments.CartFragment.UPDATE_NODE;
import static com.foodies.customer.ActivitiesAndFragments.PagerMainActivity.tabLayout;


/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class AddToCartActivity extends AppCompatActivity {

    ImageView clos_menu_items_detail;
    Button increament_btn,decrement_btn;
    TextView inc_dec_tv,grand_total_price_tv,total_price_tv,desc_tv,name_tv,cart_title_tv;
    int present_count = 1;
    SharedPreferences sPref;

    AddToCartExpandable listAdapter;
    CustomExpandableListView cartExpandableListView;
    ArrayList<CartParentModel> listDataHeader;
    ArrayList<CartChildModel> listChildData;
    private ArrayList<ArrayList<CartChildModel>> ListChild;
    String restaurant_menu_item_id;
    DatabaseReference mDatabase;
    private static FirebaseDatabase firebaseDatabase;
    private  String userId,udid,key_,name_,desc,price_,symbol,res_id,res_name,res_tax,res_fee;
    Double menuExtraItemObj,itemPrice,previousMenuObjPrice;
    int required = 0;
    Double totalExtraItemPrice,grandTotal;

    int randomNum,count;

    ArrayList<HashMap<String,String>> extraItem;

    ArrayList<Integer> arrayList = new ArrayList<>();

    public static boolean FLAG_ONCE_LOOP_ADD,FLAG_CART_ADD,FIRST_TIME_LOADER,WAS_IN_BG,IS_APP_OPEN;
    String previousCheck;
    RelativeLayout add_to_cart;
    EditText inst_text;
    public static TextView tab_badge,cart_btn_text;
    String prev_rest_id;
    CamomileSpinner progress;
    RelativeLayout transparent_layer,progressDialog;
    int showcartCount = 0;
    String min_order_price, descFinal;
    private static boolean UPDATE_CONFIRM;


    RestaurantsModel rest_model;
    RestaurantChildModel rest_child_model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        firebaseDatabase = FirebaseDatabase.getInstance();
        setContentView(R.layout.activity_add_to_cart);

        View view = LayoutInflater.from(AddToCartActivity.this).inflate(R.layout.custom_tab, null);
        tab_badge =  view.findViewById(R.id.tab_badge);

        progressDialog = findViewById(R.id.progressDialog);
        transparent_layer = findViewById(R.id.transparent_layer);

        progress = findViewById(R.id.addToCartProgress);
        progress.start();
        sPref = getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        udid = sPref.getString(PreferenceClass.UDID,"");


        transparent_layer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        if(UPDATE_NODE) {
            key_ = getIntent().getStringExtra("key");
        }

        Intent intent=getIntent();
        if(intent.hasExtra("rest_model")){
         rest_model=(RestaurantsModel) intent.getSerializableExtra("rest_model");
         rest_child_model=(RestaurantChildModel) intent.getSerializableExtra("rest_child_model");
        }

        restaurant_menu_item_id = rest_child_model.restaurant_menu_item_id;
        if(restaurant_menu_item_id==null){
            transparent_layer.setVisibility(View.GONE);
            progressDialog.setVisibility(View.GONE);

        }

        name_ = rest_child_model.child_title;
        desc = rest_child_model.child_sub_title;
        price_ = rest_child_model.price;
        symbol = rest_child_model.currency_symbol;


        res_id = rest_model.restaurant_id;
        res_name=rest_model.restaurant_name;
        res_tax = rest_model.restaurant_tax;
        min_order_price = rest_model.min_order_price;


        res_fee = "0";
        inst_text = findViewById(R.id.inst_text);
        Random r = new Random();
        randomNum = r.nextInt(1000 - 65) + 65;


        extraItem = new ArrayList<>();


        RelativeLayout main_add_to_cart = findViewById(R.id.main_add_to_cart);
        FontHelper.applyFont(getApplicationContext(),main_add_to_cart, AllConstants.verdana);
        mDatabase = firebaseDatabase.getReference().child(AllConstants.CALCULATION).child(udid);
        mDatabase.keepSynced(true);
        FLAG_CART_ADD = false;

        Query query = mDatabase;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    if(UPDATE_NODE){
                        mDatabase.child(key_).setValue(new CalculationModel(userId,restaurant_menu_item_id,
                                name_,price_,"","1","0",min_order_price,
                                extraItem,inst_text.getText().toString(),res_id,res_name,symbol,desc,res_fee,res_tax));
                    }
                    else {
                        userId = mDatabase.push().getKey();
                        mDatabase.child(userId).setValue(new CalculationModel(userId, restaurant_menu_item_id,
                                name_, price_, "", "1", "0", min_order_price,
                                extraItem, inst_text.getText().toString(), res_id,res_name, symbol, desc, res_fee, res_tax));
                    }
                }
                else {

                    if(UPDATE_NODE){
                        mDatabase.child(key_).setValue(new CalculationModel(userId,restaurant_menu_item_id,
                                name_,price_,"","1","0",min_order_price,
                                extraItem,inst_text.getText().toString(),res_id,res_name,symbol,desc,res_fee,res_tax));
                    }
                    else {
                        userId = mDatabase.push().getKey();
                        mDatabase.child(userId).setValue(new CalculationModel(userId, restaurant_menu_item_id,
                                name_, price_, "", "1", "0", min_order_price, extraItem, inst_text.getText().toString(), res_id,res_name, symbol, desc, res_fee, res_tax));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cartExpandableListView = (CustomExpandableListView ) findViewById(R.id.item_detail_list);
        cartExpandableListView .setExpanded(true);
        cartExpandableListView.setGroupIndicator(null);


        init();
        increamentDecFunc();

    }


    public void init(){
        cart_btn_text = findViewById(R.id.cart_btn_text);
        if(UPDATE_NODE){
            cart_btn_text.setText("Update Cart");

        }
        add_to_cart = findViewById(R.id.add_to_cart);
        increament_btn = findViewById(R.id.plus_btn);
        decrement_btn = findViewById(R.id.minus_btn);
        inc_dec_tv = findViewById(R.id.inc_dec_tv);
        clos_menu_items_detail = findViewById(R.id.clos_menu_items_detail);
        grand_total_price_tv = findViewById(R.id.grand_total_price_tv);
        total_price_tv = findViewById(R.id.total_price_tv);
        desc_tv = findViewById(R.id.desc_tv);
        name_tv = findViewById(R.id.name_tv);

        cart_title_tv = findViewById(R.id.cart_title_tv);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadCalculationDetail();
            }
        },500);

        clos_menu_items_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();


            }
        });


        descFinal = desc.replaceAll("&amp;", "&");


        name_tv.setText(name_.replaceAll("&amp;", "&"));

        desc_tv.setText(descFinal);
        total_price_tv.setText(symbol+price_);
        cart_title_tv.setText(name_.replaceAll("&amp;", "&"));
        getOrderDetailItems();

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(count==required){
                    Query lastQuery = mDatabase.orderByKey().limitToFirst(1);
                    mDatabase.keepSynced(true);
                    lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                            Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();

                            Collection<Object> values = td.values();

                            JSONArray jsonArray = new JSONArray(values);

                            int size = jsonArray.length();

                            for (int a = 0; a < 1; a++) {

                                JSONObject allJsonObject = null;
                                try {
                                    allJsonObject = jsonArray.getJSONObject(0);
                                    prev_rest_id = String.valueOf(allJsonObject.optString("restID"));

                                    if (!prev_rest_id.equalsIgnoreCase(res_id)) {
                                        showDialogIfChangeRest();

                                    } else {
                                        if (UPDATE_NODE) {
                                            mDatabase.child(key_).setValue(new CalculationModel(key_, restaurant_menu_item_id,
                                                    name_, price_, String.valueOf(grandTotal), inc_dec_tv.getText().toString(), "0",
                                                    min_order_price, extraItem, inst_text.getText().toString(), res_id, res_name, symbol, desc, res_fee, res_tax));
                                            AddPaymentFragment.FLAG_ADD_PAYMENT = false;
                                            AddressListFragment.FLAG_ADDRESS_LIST = false;
                                            UPDATE_NODE = false;
                                            UPDATE_CONFIRM = true;
                                        } else {
                                            mDatabase.child(userId).setValue(new CalculationModel(userId, restaurant_menu_item_id,
                                                    name_, price_, String.valueOf(grandTotal), inc_dec_tv.getText().toString(), "0",
                                                    min_order_price, extraItem, inst_text.getText().toString(), res_id, res_name, symbol, desc, res_fee, res_tax));
                                            AddPaymentFragment.FLAG_ADD_PAYMENT = false;
                                            AddressListFragment.FLAG_ADDRESS_LIST = false;
                                            SharedPreferences.Editor editor = sPref.edit();
                                            count = sPref.getInt("count", 0);
                                            showcartCount = count + 1;
                                            editor.putInt("count", showcartCount);
                                            editor.putInt(PreferenceClass.CART_COUNT, 1).commit();
                                            FLAG_CART_ADD = true;
                                            Intent data = new Intent();
                                            setResult(RESULT_OK, data);
                                        }
                                        finish();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });
                }
                else {
                    Toast.makeText(AddToCartActivity.this,"Select All Required Items",Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
       }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if(WAS_IN_BG) {
            mDatabase = firebaseDatabase.getReference().child(AllConstants.CALCULATION).child(udid);
            mDatabase.keepSynced(true);
            FLAG_CART_ADD = false;
            WAS_IN_BG = false;
            Query query = mDatabase;
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        // mDatabase.setValue(null);

                        if(UPDATE_NODE){
                            mDatabase.child(key_).setValue(new CalculationModel(key_, restaurant_menu_item_id,
                                    name_, price_, "", "1", "0", min_order_price,
                                    extraItem, inst_text.getText().toString(), res_id,res_name, symbol, desc, res_fee, res_tax));
                        }else {

                            mDatabase.child(userId).setValue(new CalculationModel(userId, restaurant_menu_item_id,
                                    name_, price_, "", "1", "0", min_order_price,
                                    extraItem, inst_text.getText().toString(), res_id,res_name, symbol, desc, res_fee, res_tax));
                        }
                    }
                    else {
                        if(UPDATE_NODE){
                            mDatabase.child(key_).setValue(new CalculationModel(key_, restaurant_menu_item_id,
                                    name_, price_, "", "1", "0", min_order_price,
                                    extraItem, inst_text.getText().toString(), res_id,res_name, symbol, desc, res_fee, res_tax));
                        }else {

                            mDatabase.child(userId).setValue(new CalculationModel(userId, restaurant_menu_item_id,
                                    name_, price_, "", "1", "0",
                                    min_order_price, extraItem, inst_text.getText().toString(), res_id,res_name, symbol, desc, res_fee, res_tax));
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    public void onBackPressed() {
        if(UPDATE_NODE){
            Toast.makeText(AddToCartActivity.this, "Please Select the items", Toast.LENGTH_SHORT).show();
        }else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        IS_APP_OPEN = true;

        if(UPDATE_CONFIRM || FLAG_CART_ADD){
            UPDATE_CONFIRM = false;
            FLAG_CART_ADD= false;
        }
        else {
            deleteCurrentNode();
        }

        UPDATE_NODE = false;
        RestaurantMenuItemsFragment.FLAG_SUGGESTION = false;
        finish();
    }

    public void increamentDecFunc(){

        increament_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    String presentValStr=inc_dec_tv.getText().toString();
                    present_count=Integer.parseInt(presentValStr);
                    present_count++;
                    inc_dec_tv.setText(String.valueOf(present_count));
                    SharedPreferences.Editor editor = sPref.edit();
                    editor.putInt(PreferenceClass.DEALS_QUANTITY,present_count).commit();

                    if(UPDATE_NODE){
                        mDatabase.child(key_).setValue(new CalculationModel(key_, restaurant_menu_item_id,
                                name_, price_, "", inc_dec_tv.getText().toString(), "0", min_order_price,
                                extraItem, inst_text.getText().toString(), res_id,res_name, symbol, desc, res_fee, res_tax));
                    }else {
                        mDatabase.child(userId).setValue(new CalculationModel(userId, restaurant_menu_item_id,
                                name_, price_, "", inc_dec_tv.getText().toString(), "0", min_order_price, extraItem, inst_text.getText().toString(), res_id,res_name, symbol, desc, res_fee, res_tax));
                    }
                    loadCalculationDetail();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        decrement_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    String presentValStr=inc_dec_tv.getText().toString();
                    present_count=Integer.parseInt(presentValStr);
                    if(presentValStr.equalsIgnoreCase(String.valueOf(Integer.parseInt("1")))) {
                        //  Toast.makeText(getContext(),"Can not Less than 1",Toast.LENGTH_LONG).show();
                    }
                    else {
                        present_count--;
                    }
                    inc_dec_tv.setText(String.valueOf(present_count));
                    SharedPreferences.Editor editor = sPref.edit();
                    editor.putInt(PreferenceClass.DEALS_QUANTITY,present_count).commit();
                    if(UPDATE_NODE){
                        mDatabase.child(key_).setValue(new CalculationModel(key_, restaurant_menu_item_id,
                                name_, price_, "", inc_dec_tv.getText().toString(), "0", min_order_price,
                                extraItem, inst_text.getText().toString(), res_id,res_name, symbol, desc, res_fee, res_tax));
                    }else {
                        mDatabase.child(userId).setValue(new CalculationModel(userId, restaurant_menu_item_id,
                                name_, price_, "", inc_dec_tv.getText().toString(), "0", min_order_price, extraItem, inst_text.getText().toString(), res_id,res_name, symbol, desc, res_fee, res_tax));
                    }
                    loadCalculationDetail();

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getOrderDetailItems(){

        listDataHeader = new ArrayList<CartParentModel>();
        ListChild = new ArrayList<>();
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        JSONObject params = new JSONObject();
        try {
            if(restaurant_menu_item_id!=null) {
                params.put("restaurant_menu_item_id", restaurant_menu_item_id);
            }

            params.put("restaurant_id",res_id);
        } catch (JSONException e) {
            e.printStackTrace();
            transparent_layer.setVisibility(View.GONE);
            progressDialog.setVisibility(View.GONE);
        }

        ApiRequest.Call_Api(this, Config.SHOW_MENU_EXTRA_ITEM, params, new Callback() {
            @Override
            public void Responce(String resp) {

               try {
                    JSONObject  jsonResponse = new JSONObject(resp);

                    int code_id  = Integer.parseInt(jsonResponse.optString("code"));
                    count  = Integer.parseInt(jsonResponse.optString("count"));

                    if(code_id == 200) {

                        JSONObject json = new JSONObject(jsonResponse.toString());
                        JSONArray jsonArray = json.getJSONArray("msg");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject allJsonObject = jsonArray.getJSONObject(i);
                            JSONObject restaurantMenuExtraSection = allJsonObject.getJSONObject("RestaurantMenuExtraSection");
                            JSONArray restaurantMenuExtraItem = restaurantMenuExtraSection.getJSONArray("RestaurantMenuExtraItem");
                            JSONObject restaurant = allJsonObject.getJSONObject("Restaurant");
                            JSONObject currency = restaurant.getJSONObject("Currency");
                            String symbol = currency.optString("symbol");

                            CartParentModel menuItemModel = new CartParentModel();

                            menuItemModel.setParentName(restaurantMenuExtraSection.optString("name"));
                            menuItemModel.setRequired(restaurantMenuExtraSection.optString("required"));
                            menuItemModel.setSymbol(symbol);

                            listDataHeader.add(menuItemModel);
                            listChildData = new ArrayList<>();

                            for (int j = 0; j < restaurantMenuExtraItem.length(); j++) {

                                JSONObject alljsonJsonObject2 = restaurantMenuExtraItem.getJSONObject(j);

                                CartChildModel menuItemExtraModel = new CartChildModel();

                                menuItemExtraModel.setChild_item_name(alljsonJsonObject2.optString("name"));
                                menuItemExtraModel.setChild_item_price(alljsonJsonObject2.optString("price"));
                                menuItemExtraModel.setExtra_item_id(alljsonJsonObject2.optString("id"));
                                menuItemExtraModel.setPos(j);
                                menuItemExtraModel.setSymbol(symbol);

                                listChildData.add(menuItemExtraModel);

                            }
                            ListChild.add(listChildData);


                            listAdapter = new AddToCartExpandable(getApplicationContext(), listDataHeader, ListChild);

                            cartExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                                @Override
                                public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {

                                    final CartChildModel item = (CartChildModel) listAdapter.getChild(groupPosition, childPosition);

                                    boolean iteIsRequired = item.isCheckRequired();

                                    if (!iteIsRequired) {

                                        CheckBox checkBox = view.findViewById(R.id.check_btn);

                                        if (checkBox != null) {
                                            //  checkBox.toggle();

                                            if (!checkBox.isChecked()) {
                                                //  item.setCheckBoxIsChecked(true);
                                                checkBox.setChecked(true);

                                                FLAG_ONCE_LOOP_ADD = true;
                                                addNewNode(item.getExtra_item_id(), item.getChild_item_name(), item.getChild_item_price());
                                                loadCalculationDetail();
                                            } else if (checkBox.isChecked()) {
                                                //   item.setCheckBoxIsChecked(false);
                                                checkBox.setChecked(false);
                                                FLAG_ONCE_LOOP_ADD = false;
                                                deleteNewNode(item.getExtra_item_id());
                                                //   loadCalculationDetail();

                                            }
                                        }
                                    } else {


                                        if (!item.isCheckedddd()) {

                                            String string = arrayList.toString();
                                            ArrayList<CartChildModel> childsList=listAdapter.getChilderns(groupPosition);
                                            for (CartChildModel model:childsList){
                                                if(model.isCheckedddd()){
                                                    previousCheck=model.getExtra_item_id();
                                                    break;
                                                }
                                            }

                                            if (arrayList.contains(groupPosition)) {
                                                //FLAG_CHECKBOX_TOGGLER = true;
                                                deleteNewNode(previousCheck);
                                                addNewNode(item.getExtra_item_id(), item.getChild_item_name(), item.getChild_item_price());
                                                previousCheck = item.getExtra_item_id();


                                            } else {
                                                //FLAG_CHECKBOX_TOGGLER = false;
                                                addNewNode(item.getExtra_item_id(), item.getChild_item_name(), item.getChild_item_price());
                                                previousCheck = item.getExtra_item_id();
                                                loadCalculationDetail();
                                                required = required + 1;
                                                // previousCheck = String.valueOf(previousValArray.get(groupPosition));
                                            }

                                            if (!arrayList.contains(groupPosition)) {
                                                arrayList.add(groupPosition);
                                                //  arrayPos.add(groupPosition,previousCheck);
                                            }

                                            upDateNotify(listAdapter.getChilderns(groupPosition));
                                            item.setCheckeddd(true);
                                            listAdapter.notifyDataSetChanged();
                                        }


                                    }
                                    return false;
                                }
                            });

                        }

                        cartExpandableListView.setAdapter(listAdapter);

                        for (int l = 0; l < listAdapter.getGroupCount(); l++)
                            cartExpandableListView.expandGroup(l);
                    }

                    transparent_layer.setVisibility(View.GONE);
                    progressDialog.setVisibility(View.GONE);


                }catch (Exception e){
                    e.getMessage();
                    transparent_layer.setVisibility(View.GONE);
                    progressDialog.setVisibility(View.GONE);
                }
            }
        });

    }

    public void upDateNotify(ArrayList<CartChildModel> child){
        for(int i=0; i<child.size(); i++) {
            child.get(i).setCheckeddd(false);
        }
    }

    public void addNewNode(String id, String name,String price){
        HashMap<String, String> names = new HashMap<>();
        names.put("menu_extra_item_id", id);
        names.put("menu_extra_item_name",name);
        names.put("menu_extra_item_price",price);
        names.put("menu_extra_item_quantity",inc_dec_tv.getText().toString());
        extraItem.add(names);
        if(UPDATE_NODE){
            mDatabase.child(key_).setValue(new CalculationModel(key_, restaurant_menu_item_id,
                    name_, price_, "", "1", "0", min_order_price,
                    extraItem, inst_text.getText().toString(), res_id,res_name, symbol, desc, res_fee, res_tax));
        }else {
            mDatabase.child(userId).setValue(new CalculationModel(userId, restaurant_menu_item_id,
                    name_, price_, "", inc_dec_tv.getText().toString(), "0",
                    min_order_price, extraItem, inst_text.getText().toString(), res_id,res_name, symbol, desc, res_fee, res_tax));
        }
    }

    public void deleteNewNode(final String id){

        DatabaseReference query = mDatabase;//mDatabase;//FirebaseDatabase.getInstance().getReference();
        Query lastQuery = query.orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();

                    Collection<Object> values = td.values();
                    String string = values.toString();

                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(values);

                        for (int a = 0; a < jsonArray.length(); a++) {

                            JSONObject allJsonObject = jsonArray.getJSONObject(a);
                            JSONArray extraItemArray = allJsonObject.getJSONArray("extraItem");

                            for (int b = 0; b < extraItemArray.length(); b++) {

                                JSONObject jsonObject = extraItemArray.getJSONObject(b);
                                String menuExtraItemObj = jsonObject.optString("menu_extra_item_id");

                                if (menuExtraItemObj.equalsIgnoreCase(id)) {

                                    //  int some = i;
                                    try {
                                        extraItem.remove(b);
                                        if (UPDATE_NODE) {
                                            mDatabase.child(key_).setValue(new CalculationModel(key_, restaurant_menu_item_id,
                                                    name_, price_, "", "1", "0", min_order_price,
                                                    extraItem, inst_text.getText().toString(), res_id, res_name, symbol, desc, res_fee, res_tax));
                                        } else {
                                            mDatabase.child(userId).setValue(new CalculationModel(userId, restaurant_menu_item_id,
                                                    name_, price_, "", inc_dec_tv.getText().toString(), "0",
                                                    min_order_price, extraItem, inst_text.getText().toString(), res_id, res_name, symbol, desc, res_fee, res_tax));
                                        } // loadCalculationDetail();
                                    } catch (IndexOutOfBoundsException e) {
                                        e.getMessage();
                                    }

                                }

                            }
                            loadCalculationDetail();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void loadCalculationDetail(){
        if(!FIRST_TIME_LOADER) {
            transparent_layer.setVisibility(View.VISIBLE);
            progressDialog.setVisibility(View.VISIBLE);
            FIRST_TIME_LOADER = true;
        }
        DatabaseReference query = mDatabase;
        Query lastQuery = query.orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);

                if(dataSnapshot.exists()){
                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();

                if(td!=null) {
                    Collection<Object> value = td.values();

                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(value);
                        grandTotal = 0.0;
                        totalExtraItemPrice = 0.0;
                        menuExtraItemObj = 0.0;
                        itemPrice = 0.0;
                        for (int a = 0; a < jsonArray.length(); a++) {

                            JSONObject allJsonObject = jsonArray.getJSONObject(a);
                            itemPrice = Double.parseDouble(allJsonObject.optString("mPrice"));
                            //  itemPr = itemPrice;

                            grandTotal = totalExtraItemPrice + itemPrice * Double.valueOf(inc_dec_tv.getText().toString());
                           // String str = grandTotal;
                           grand_total_price_tv.setText(symbol+grandTotal);
                            if (allJsonObject.getJSONArray("extraItem") != null) {

                                JSONArray extraItemArray = allJsonObject.getJSONArray("extraItem");

                                for (int b = 0; b < extraItemArray.length(); b++) {

                                    JSONObject jsonObject = extraItemArray.getJSONObject(b);
                                    menuExtraItemObj = Double.parseDouble(jsonObject.optString("menu_extra_item_price"));

                                    totalExtraItemPrice = totalExtraItemPrice + menuExtraItemObj;
                                    Double total2 = totalExtraItemPrice;

                                }
                                grandTotal = (totalExtraItemPrice + itemPrice) * Double.valueOf(inc_dec_tv.getText().toString());
                                grand_total_price_tv.setText(symbol+grandTotal);

                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                        transparent_layer.setVisibility(View.GONE);
                        progressDialog.setVisibility(View.GONE);
                    }catch (Exception e){

                    }
                }
            }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);


            }
        });

    }


    public void deleteCurrentNode(){

        DatabaseReference query = mDatabase;
        Query lastQuery = query.orderByKey().limitToLast(1);

        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    dataSnapshot1.getRef().setValue(null);
                }
            }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void deleteNodeExpectLast(){
       final Query query = mDatabase.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{

                    if (dataSnapshot.exists()) {
                        Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();

                        Collection<Object> values = td.values();

                        JSONArray jsonArray = new JSONArray(values);

                        Log.d(AllConstants.tag,jsonArray.toString());

                        Log.d(AllConstants.tag,""+jsonArray.length());

                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.optJSONObject(i);
                            if(userId!=null && !userId.equals(jsonObject.optString("key")))
                            mDatabase.child(jsonObject.optString("key")).setValue(null);
                        }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                add_to_cart.performClick();
                            }
                        },500);


                    }else {
                        add_to_cart.performClick();
                    }
                }catch (Exception e){
                }


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void showDialogIfChangeRest(){

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(AddToCartActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(AddToCartActivity.this);
        }
        builder.setTitle("Changing Restaurant")
                .setMessage("Would you like to change your order from"+""+" and start new order?")
                .setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                        deleteNodeExpectLast();

                        SharedPreferences.Editor editor = sPref.edit();
                        editor.putInt("count",0).commit();

                        TabLayout.Tab tab = tabLayout.getTabAt(2); // fourth tab
                        View tabView = tab.getCustomView();
                        TextView badgeText = (TextView) tabView.findViewById(R.id.tab_badge);
                        badgeText.setVisibility(View.GONE);
                        badgeText.setText("0");

                    }


                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }

}

