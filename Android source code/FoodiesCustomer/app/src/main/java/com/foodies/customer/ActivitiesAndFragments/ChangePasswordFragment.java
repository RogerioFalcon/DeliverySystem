package com.foodies.customer.ActivitiesAndFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.foodies.customer.Utils.TabLayoutUtils;
import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.foodies.customer.R;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class ChangePasswordFragment extends RootFragment {

    ImageView back_icon;
    EditText old_password,new_password,confirm_password;
    Button btn_change_pass;
    SharedPreferences sharedPreferences;

    CamomileSpinner changePassProgress;
    RelativeLayout transparent_layer,progressDialog;

    View view;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.change_password_fragment, container, false);
         context=getContext();

         sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);

        init(view);

        return view;
    }

    public void init(View v){
        changePassProgress = v.findViewById(R.id.changePassProgress);
        changePassProgress.start();
        progressDialog = v.findViewById(R.id.progressDialog);
        transparent_layer = v.findViewById(R.id.transparent_layer);
        btn_change_pass = v.findViewById(R.id.btn_change_pass);

        old_password = v.findViewById(R.id.ed_old_pass);
        new_password = v.findViewById(R.id.ed_new_pass);
        confirm_password = v.findViewById(R.id.ed_confirm_pass);

        btn_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (old_password.getText().toString().trim().equals("") || old_password.getText().toString().length()<6) {

                    Toast.makeText(getContext(), "Check password length can not be shorter than 6!", Toast.LENGTH_SHORT).show();
                    old_password.setError("Check password length can not be shorter than 6!");

                } else if (new_password.getText().toString().trim().equals("") || new_password.getText().toString().length()<6) {

                    Toast.makeText(getContext(), "Check password length can not be shorter than 6!", Toast.LENGTH_SHORT).show();
                    new_password.setError("Check password length can not be shorter than 6!");
                }else if (confirm_password.getText().toString().trim().equals("") || confirm_password.getText().toString().length()<6) {

                    Toast.makeText(getContext(), "Check password length can not be shorter than 6!", Toast.LENGTH_SHORT).show();
                    confirm_password.setError("Check password length can not be shorter than 6!");
                }
        else {
                    if (new_password.getText().toString().equals(confirm_password.getText().toString())) {

                        changePasswordVollyRequest();
                    } else {
                        Toast.makeText(getContext(), "Password does not match", Toast.LENGTH_LONG).show();
                        confirm_password.setError("Password does not match");
                        new_password.setError("Password does not match");
                        //passwords not matching.please try again
                    }
                }
            }
        });
        back_icon = v.findViewById(R.id.back_icon);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try  {
                    InputMethodManager imm = (InputMethodManager)getContext().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }

                getActivity().onBackPressed();



            }
        });


    }

  public void changePasswordVollyRequest(){
        String getUser_id = sharedPreferences.getString(PreferenceClass.pre_user_id,"");
       JSONObject jsonObject = new JSONObject();
      try {
          jsonObject.put("user_id",getUser_id);
          jsonObject.put("old_password",old_password.getText().toString());
          jsonObject.put("new_password",new_password.getText().toString());
      } catch (JSONException e) {
          e.printStackTrace();
      }


      TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
      transparent_layer.setVisibility(View.VISIBLE);
      progressDialog.setVisibility(View.VISIBLE);

      ApiRequest.Call_Api(context, Config.CHANGE_PASSWORD, jsonObject, new Callback() {
          @Override
          public void Responce(String resp) {

              TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
              transparent_layer.setVisibility(View.GONE);
              progressDialog.setVisibility(View.GONE);

              try {
                  JSONObject  jsonResponse = new JSONObject(resp);

                  int code_id  = Integer.parseInt(jsonResponse.optString("code"));
                  if (code_id==200){
                       Toast.makeText(getContext(),"Password Changed Successfully",Toast.LENGTH_LONG).show();

                      UserAccountFragment userAccountFragment = new UserAccountFragment();
                      FragmentTransaction transaction = getFragmentManager().beginTransaction();
                      transaction.replace(R.id.change_pass_main_container, userAccountFragment);
                      transaction.addToBackStack(null);
                      transaction.commit();

                  }
                  else {
                       Toast.makeText(getContext(),"Password Not Changed",Toast.LENGTH_LONG).show();
                  }

              } catch (JSONException e) {
                  e.printStackTrace();
              }
          }
      });



  }


}
