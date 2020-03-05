package com.foodies.customer.ActivitiesAndFragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Constants.Fragment_Callback;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.foodies.customer.Utils.TabLayoutUtils;
import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class AddPaymentDetailFragment extends RootFragment {
    private SharedPreferences sharedPreferences;
    String month,year;
    ImageView back_icon;
    Button cancle_credit_card_btn,save_payment_method_btn;
    private Calendar myCalendar;
    private EditText card_number_editText,card_validity,name_on_card,cvv,billing_address_card,city_card,card_state,card_zip;
    CamomileSpinner pbHeaderProgress;
    RelativeLayout transparent_layer,progressDialog;

    View view;
    Context context;


    public  AddPaymentDetailFragment(){

    }

    Fragment_Callback fragment_callback;
    public  AddPaymentDetailFragment(Fragment_Callback fragment_callback){
        this.fragment_callback=fragment_callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          view = inflater.inflate(R.layout.add_credit_card_detail, container, false);
         context=getContext();

         init(view);

        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);


        return view;
    }

    public void init(View v){
        myCalendar = Calendar.getInstance();
        back_icon = v.findViewById(R.id.back_icon);
        pbHeaderProgress = v.findViewById(R.id.pbHeaderProgress);
        pbHeaderProgress.start();
        progressDialog = v.findViewById(R.id.progressDialog);
        transparent_layer = v.findViewById(R.id.transparent_layer);

        cancle_credit_card_btn = v.findViewById(R.id.cancle_credit_card_btn);
        card_number_editText = v.findViewById(R.id.card_number_editText);
        card_validity = v.findViewById(R.id.card_validity);
        name_on_card = v.findViewById(R.id.name_on_card);
        cvv = v.findViewById(R.id.cvv);
        billing_address_card = v.findViewById(R.id.billing_address_card);
        city_card = v.findViewById(R.id.city_card);
        card_state = v.findViewById(R.id.card_state);
        card_zip = v.findViewById(R.id.card_zip);
        save_payment_method_btn = v.findViewById(R.id.save_payment_method_btn);

        save_payment_method_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitCard(view);
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                pbHeaderProgress.setVisibility(View.VISIBLE);
            }
        });

        if (AddPaymentFragment.FLAG_FRAGMENT){
            back_icon.setVisibility(View.VISIBLE);
            cancle_credit_card_btn.setVisibility(View.GONE);
            AddPaymentFragment.FLAG_FRAGMENT = false;
        }

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               getActivity().onBackPressed();
            }
        });

        card_number_editText.addTextChangedListener(new PaymentMethodActivity.FourDigitCardFormatWatcher());

        datePickerDialog();

    }

    private void datePickerDialog(){


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
               myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        card_validity.setInputType(0);
        card_validity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        card_validity.setText(sdf.format(myCalendar.getTime()));
    }


    public void submitCard(View view) {

        if(card_number_editText.getText().toString().isEmpty()){

            card_number_editText.setError("Not be empty");
        }
        else if(name_on_card.getText().toString().isEmpty()){
            name_on_card.setError("Not be empty");
        }
        else if(cvv.getText().toString().isEmpty()){
            cvv.setError("Not be empty");
        }
        else if(card_validity.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Select the Card validity", Toast.LENGTH_SHORT).show();
        }
        else {


        String monthField = card_validity.getText().toString();
        if(!monthField.isEmpty()) {
            month = monthField.substring(0, 2);
        }
        String yearField = card_validity.getText().toString();
        if(!yearField.isEmpty()) {
            year = yearField.substring(3, 5);
        }

            addPaymentMethodVollyCal();

    }

    }


    public void addPaymentMethodVollyCal(){
        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        String user_id = sharedPreferences.getString(PreferenceClass.pre_user_id,"");
        String card_number = card_number_editText.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(getContext());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("name",name_on_card.getText().toString());
            jsonObject.put("card",card_number.replace(" ",""));
            jsonObject.put("cvc",cvv.getText().toString());
            jsonObject.put("exp_month",month);
            jsonObject.put("exp_year",year);
            jsonObject.put("default","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiRequest.Call_Api(context, Config.ADD_PAYMENT_METHOD, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {


                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);


                try {
                    JSONObject jsonResponse = new JSONObject(resp);
                    int code_id  = Integer.parseInt(jsonResponse.optString("code"));

                    if(code_id == 200) {

                        if(fragment_callback!=null){
                            fragment_callback.Responce(new Bundle());
                        }
                        getActivity().onBackPressed();

                    }else {

                        Toast.makeText(getContext(), ""+jsonResponse.optString("msg"), Toast.LENGTH_SHORT).show();

                    }

                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });



    }


}
