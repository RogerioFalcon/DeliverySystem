package com.foodies.customer.ActivitiesAndFragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodies.customer.Constants.Fragment_Callback;
import com.foodies.customer.Utils.FontHelper;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.facebook.login.LoginManager;
import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.R;

import static android.content.Context.MODE_PRIVATE;
import static com.foodies.customer.ActivitiesAndFragments.DealOrderFragment.DEAL_ADDRESS;
import static com.foodies.customer.ActivitiesAndFragments.DealOrderFragment.DEAL_PAYMENT_METHOD;

/**
 * Created by Nabeel on 12/12/2017.
 */

public class UserAccountFragment extends RootFragment {

    ImageView back_icon_user_account;
    public static boolean FLAG_CART_USER_FRAGMENT;
    private LinearLayout user_login_div,user_not_login_div;
    private RelativeLayout user_log_out_div,user_sign_in_div,change_password_div,user_name_div,payment_method_div,
            dilervery_address_div,favourites_div,sign_up_div,terms_condition_div,terms_div,uan_div,uan_div2;
    SharedPreferences sharedPreferences;
    private TextView user_name;
    FrameLayout user_frag_main_container;

    public static boolean LOG_IN_FLAG,LOG_OUT_FLAG,FLAG_DELIVER_ADDRESS;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_account_fragment, container, false);

        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user,MODE_PRIVATE);
        initUI(view);
        checkLogInSession();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkLogInSession();
    }

    private void initUI(View v){
        uan_div = v.findViewById(R.id.uan_div);
        uan_div2 = v.findViewById(R.id.uan_div2);
        terms_div = v.findViewById(R.id.terms_div);
        terms_condition_div = v.findViewById(R.id.terms_condition_div);
        back_icon_user_account = v.findViewById(R.id.back_icon_user_account);
        user_frag_main_container = v.findViewById(R.id.user_frag_main_container);
        FontHelper.applyFont(getContext(),user_frag_main_container, AllConstants.verdana);
        user_frag_main_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });


        if(DEAL_ADDRESS||DEAL_PAYMENT_METHOD){
            back_icon_user_account.setVisibility(View.VISIBLE);

            back_icon_user_account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    getActivity().onBackPressed();
                }
            });
        }


        user_name = v.findViewById(R.id.user_name);
        String first_name = sharedPreferences.getString(PreferenceClass.pre_first, "");
        String last_name = sharedPreferences.getString(PreferenceClass.pre_last,"");
        user_name.setText(first_name + " "+ last_name);


        dilervery_address_div = v.findViewById(R.id.dilervery_address_div);
        user_login_div = v.findViewById(R.id.user_log_in_div);
        user_not_login_div = v.findViewById(R.id.user_not_log_in_div);
        user_log_out_div = v.findViewById(R.id.log_out_div);
        user_sign_in_div = v.findViewById(R.id.sign_in_div);
        change_password_div = v.findViewById(R.id.change_password_div);
        user_name_div = v.findViewById(R.id.user_name_div);
        payment_method_div= v.findViewById(R.id.payment_method_div);
        favourites_div = v.findViewById(R.id.favourites_div);
        sign_up_div = v.findViewById(R.id.sign_up_div);

        sign_up_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment restaurantMenuItemsFragment = new SingUpActivity();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.add(R.id.user_frag_main_container, restaurantMenuItemsFragment,"parent").commit();

                LOG_OUT_FLAG = true;
            }
        });

        favourites_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowFavoriteRestFragment showFavoriteRestFragment = new ShowFavoriteRestFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.add(R.id.user_frag_main_container, showFavoriteRestFragment,"parent").commit();
            }
        });


        dilervery_address_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddressListFragment addressListFragment = new AddressListFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.add(R.id.user_frag_main_container, addressListFragment,"parent").commit();
                FLAG_DELIVER_ADDRESS = true;
            }
        });

        payment_method_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPaymentFragment addPaymentFragment = new AddPaymentFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.add(R.id.user_frag_main_container, addPaymentFragment,"parent").commit();
                FLAG_CART_USER_FRAGMENT = true;
            }
        });

        user_name_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditAccountFragment editAccountFragment = new EditAccountFragment(new Fragment_Callback() {
                    @Override
                    public void Responce(Bundle bundle) {
                        String first_name = sharedPreferences.getString(PreferenceClass.pre_first, "");
                        String last_name = sharedPreferences.getString(PreferenceClass.pre_last,"");
                        user_name.setText(first_name + " "+ last_name);

                    }
                });
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.add(R.id.user_frag_main_container, editAccountFragment,"parent").commit();
                FLAG_CART_USER_FRAGMENT = true;
            }
        });

        change_password_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.add(R.id.user_frag_main_container, changePasswordFragment,"ParentFragment").commit();
                FLAG_CART_USER_FRAGMENT = true;
            }
        });

        user_sign_in_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment restaurantMenuItemsFragment = new LoginAcitvity();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.add(R.id.user_frag_main_container, restaurantMenuItemsFragment,"ParentFragment").commit();
                LOG_IN_FLAG = true;
            }
        });

        terms_condition_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment restaurantMenuItemsFragment = new PrivacyPolicyFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.add(R.id.user_frag_main_container, restaurantMenuItemsFragment,"ParentFragment").commit();
            }
        });
        terms_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment restaurantMenuItemsFragment = new PrivacyPolicyFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.add(R.id.user_frag_main_container, restaurantMenuItemsFragment,"ParentFragment").commit();
            }
        });

        uan_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        uan_div2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        user_log_out_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOutUser();
            }
        });




    }

    private void checkLogInSession(){
        boolean getLoINSession = sharedPreferences.getBoolean(PreferenceClass.IS_LOGIN,false);
        if(getLoINSession){
            user_login_div.setVisibility(View.VISIBLE);
            user_not_login_div.setVisibility(View.GONE);
        }
        else {
            user_not_login_div.setVisibility(View.VISIBLE);
            user_login_div.setVisibility(View.GONE);
        }

    }

    private void logOutUser(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PreferenceClass.pre_email, "");
        editor.putString(PreferenceClass.pre_pass, "");
        editor.putString(PreferenceClass.pre_first, "");
        editor.putString(PreferenceClass.pre_last, "");
        editor.putString(PreferenceClass.pre_contact, "");
        editor.putString(PreferenceClass.pre_user_id, "");
        editor.putBoolean(PreferenceClass.IS_LOGIN, false);
        editor.commit();

        user_not_login_div.setVisibility(View.VISIBLE);
        user_login_div.setVisibility(View.GONE);
        PagerMainActivity.viewPager.setCurrentItem(0);
        LoginManager.getInstance().logOut();

    }


    public void showDialog(){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setTitle("Call Us");
        builder1.setMessage("Press cal to dial our UAN number.");

        builder1.setPositiveButton(
                "Call",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onCall();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }


    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {


            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+getContext().getString(R.string.uan))));
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }


}
