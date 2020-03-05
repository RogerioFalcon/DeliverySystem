package com.foodies.customer.ActivitiesAndFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Utils.FontHelper;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.foodies.customer.Utils.TabLayoutUtils;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.foodies.customer.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class LoginAcitvity extends RootFragment implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener,
            GoogleApiClient.ConnectionCallbacks{

    SharedPreferences sPref;
    RelativeLayout fb_div;


    FrameLayout login_main_div;

    CamomileSpinner logInProgress;
    RelativeLayout transparent_layer,progressDialog,google_sign_in_div;

    Button log_in_now;
    TextView fb_btn;

    TextView loginText,tv_email,tv_pass,sign_up_txt,tv_forget_password,tv_signed_up_now,tv_sign_up;

    EditText ed_email,ed_password;
    LoginButton login_button_fb;

    ImageView back_icon;


    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    CallbackManager callbackManager;
    public static GoogleSignInClient  mGoogleSignInClient;

    View view;
    Context context;

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.login_activity, container, false);
         context=getContext();

        sPref = getContext().getSharedPreferences(PreferenceClass.user,Context.MODE_PRIVATE);


        ed_email = (EditText)view.findViewById(R.id.ed_email);
        ed_password =(EditText)view.findViewById(R.id.ed_password);
        log_in_now = (Button)view.findViewById(R.id.btn_login);


      

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                 .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);


        FacebookSdk.sdkInitialize(getContext());
        AppEventsLogger.activateApp(getContext());

        callbackManager = CallbackManager.Factory.create();

        /// End

        tv_sign_up = view.findViewById(R.id.tv_sign_up);
        tv_signed_up_now = view.findViewById(R.id.tv_signed_up_now);
        FontHelper.applyFont(getContext(),tv_sign_up, AllConstants.verdana);

        fb_btn = view.findViewById(R.id.fb_btn);
        fb_div = view.findViewById(R.id.fb_div);
        fb_div.setOnClickListener(this);
        fb_btn.setOnClickListener(this);
        login_button_fb = (LoginButton) view.findViewById(R.id.login_button_fb);
        login_button_fb.setReadPermissions(Arrays.asList("email"));

        // If using in a fragment
        login_button_fb.setFragment(this);
        // Callback registration

        back_icon = view.findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginManager.getInstance().logOut();

                try  {
                    InputMethodManager imm = (InputMethodManager)getContext().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }

                 getActivity().onBackPressed();
                LoginManager.getInstance().logOut();

            }
        });



        login_button_fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                final AccessToken accessToken = loginResult.getAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                        String useremail = user.optString("email");


                        ed_email.setText(useremail);



                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "last_name,first_name,email");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getContext(),"Cancle",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });

        login_main_div = view.findViewById(R.id.login_main_div);
        login_main_div.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                return false;
            }
        });

        google_sign_in_div = view.findViewById(R.id.google_sign_in_div);
        google_sign_in_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
                if (acct != null) {
                     String Email = acct.getEmail();

                    ed_email.setText(Email);


                }
                else {
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, 123);
                }
            }
        });



        tv_email = (TextView)view.findViewById(R.id.tv_email);
        tv_pass = (TextView)view.findViewById(R.id.tv_password);
        sign_up_txt = (TextView)view.findViewById(R.id.tv_sign_up);

        logInProgress = view.findViewById(R.id.logInProgress);
        logInProgress.start();
        progressDialog = view.findViewById(R.id.progressDialog);
        transparent_layer = view.findViewById(R.id.transparent_layer);

        loginText = (TextView)view.findViewById(R.id.login_title);
        tv_forget_password = view.findViewById(R.id.tv_forget_password);
        tv_forget_password.setOnClickListener(this);
        FontHelper.applyFont(getContext(),tv_forget_password, AllConstants.arial);

        //

        log_in_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean valid = checkEmail(ed_email.getText().toString());

                if (ed_email.getText().toString().trim().equals("")) {

                    Toast.makeText(getContext(), "Enter Email!", Toast.LENGTH_SHORT).show();

                } else if (ed_password.getText().toString().trim().equals("")) {

                    Toast.makeText(getContext(), "Enter Password!", Toast.LENGTH_SHORT).show();
                }else if (ed_password.getText().toString().length()<6) {

                    Toast.makeText(getContext(), "Enter Password Atleat 6 Charaters!", Toast.LENGTH_SHORT).show();
                }
                else if (!valid) {

                    Toast.makeText(getContext(), "Enter Valid Email!", Toast.LENGTH_SHORT).show();
                }else {

                   String this_email = ed_email.getText().toString();
                   String this_password = ed_password.getText().toString();
                    AddressListFragment.CART_NOT_LOAD = true;
                    login(this_email,this_password);

                }
            }
        });


        tv_signed_up_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SingUpActivity singUpActivity = new SingUpActivity();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.add(R.id.login_main_div, singUpActivity,"parent").commit();

                LoginManager.getInstance().logOut();
            }
        });





        return view;
    }

    private void login(String email,String pass){

        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);


        String _lat = sPref.getString(PreferenceClass.LATITUDE,"");
        String _long = sPref.getString(PreferenceClass.LONGITUDE,"");
        String device_tocken = sPref.getString(PreferenceClass.device_token,"");


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", pass);
            jsonObject.put("device_token", device_tocken);
            jsonObject.put("role","user");

            if(_lat.isEmpty()){
                jsonObject.put("lat", "31.5042483");
            }else {
                jsonObject.put("lat", _lat);
            }
            if(_long.isEmpty()){
                jsonObject.put("long", "74.3307944");
            }else {
                jsonObject.put("long", _long);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        ApiRequest.Call_Api(context,  Config.LOGIN_URL, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {
                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);


                try {
                    JSONObject jsonResponse = new JSONObject(resp);

                    int code_id  = Integer.parseInt(jsonResponse.optString("code"));

                    if(code_id == 200) {
                        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                        transparent_layer.setVisibility(View.GONE);
                        progressDialog.setVisibility(View.GONE);
                        JSONObject json = new JSONObject(jsonResponse.toString());
                        JSONObject resultObj = json.getJSONObject("msg");
                        JSONObject json1 = new JSONObject(resultObj.toString());
                        JSONObject resultObj1 = json1.getJSONObject("UserInfo");
                        JSONObject resultObj2 = json1.getJSONObject("User");

                        SharedPreferences.Editor editor = sPref.edit();
                        editor.putString(PreferenceClass.pre_email, ed_email.getText().toString());
                        editor.putString(PreferenceClass.pre_pass, ed_password.getText().toString());
                        editor.putString(PreferenceClass.pre_first, resultObj1.optString("first_name"));
                        editor.putString(PreferenceClass.pre_last, resultObj1.optString("last_name"));
                        editor.putString(PreferenceClass.pre_contact, resultObj1.optString("phone"));
                        editor.putString(PreferenceClass.pre_user_id, resultObj1.optString("user_id"));

                        editor.putBoolean(PreferenceClass.IS_LOGIN, true);
                        editor.commit();

                        OrderDetailFragment.CALLBACK_ORDERFRAG = true;

                        editor.putString(PreferenceClass.USER_TYPE,resultObj2.optString("role"));
                        editor.commit();

                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();


                    }else{

                        try  {
                            InputMethodManager imm = (InputMethodManager)getContext().getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {

                        }

                        JSONObject json = new JSONObject(jsonResponse.toString());
                        Toast.makeText(getContext(),json.optString("msg"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }



    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }


    @Override
    public void onClick(View view) {

        if(view == fb_div){
            login_button_fb.performClick();
        }

        if(view==fb_btn){
            login_button_fb.performClick();
        }


        else if(view==tv_forget_password){
            startActivity(new Intent(getActivity(),RecoverPasswordActivity.class));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("handleSignInResult", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

            String Email = acct.getEmail();

            ed_email.setText(Email);

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==123){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);


        }
        else {
            login_button_fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    final AccessToken accessToken = loginResult.getAccessToken();
                    GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                            String useremail = user.optString("email");

                            ed_email.setText(useremail);

                            Toast.makeText(getContext(), "Successfull", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancel() {
                    // App code
                    Toast.makeText(getContext(),"Cancle",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }







}
