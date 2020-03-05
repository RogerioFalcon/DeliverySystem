package com.foodies.customer.ActivitiesAndFragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.foodies.customer.Utils.RelateToFragment_OnBack.OnBackPressListener;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.google.android.material.tabs.TabLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodies.customer.Adapters.AdapterPager;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.R;
import com.foodies.customer.Utils.CustomViewPager;
import com.foodies.customer.Utils.SwipeDirection;


public class PagerMainActivity extends RootFragment {
    public static TabLayout tabLayout;
    public static CustomViewPager viewPager;
    AdapterPager adapter;

    private int[] tabIcons1 = {R.drawable.ic_res_not_fil,
            R.drawable.ic_order_not_fil, R.drawable.ic_cart_not_fil, R.drawable.ic_acc_not_fil};

    private int[] tabIcons = {R.drawable.ic_res_fill,
            R.drawable.ic_order_fil, R.drawable.ic_cart_fil, R.drawable.ic_acc_fil};

    boolean mIsReceiverRegistered = false;
    MyBroadcastReceiver mReceiver = null;

    SharedPreferences sPref;
    int count;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_pager, container, false);

        sPref = getContext().getSharedPreferences(PreferenceClass.user,Context.MODE_PRIVATE);
        tabLayout = v.findViewById(R.id.tab_layout);

        viewPager = v.findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(4);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_res_fill));
        //tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_deals_not_filled));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_order_not_fil));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_cart_not_fil));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_acc_not_fil));

        if (tabLayout != null) {

            if (MainActivity.FLAG_MAIN) {

                for (int i = 1; i < tabIcons1.length; i++) {
                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_restaurant_fill);
                    tabLayout.getTabAt(i).setIcon(tabIcons1[i]);

                    MainActivity.FLAG_MAIN = false;

                }
            } else {
                for (int i = 0; i < tabIcons1.length; i++) {
                    tabLayout.getTabAt(i).setIcon(tabIcons1[i]);
                }

            }

            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab != null) tab.setCustomView(R.layout.tab_icon);

            }

            int getIfCarExist = sPref.getInt(PreferenceClass.CART_COUNT,0);
            count = sPref.getInt("count",0);

            if(count==0){
                TabLayout.Tab tab = tabLayout.getTabAt(2); // fourth tab
                View tabView = tab.getCustomView();
                TextView badgeText = (TextView) tabView.findViewById(R.id.tab_badge);
                badgeText.setVisibility(View.GONE);
                badgeText.setText(""+count);
            }
            else
            if(getIfCarExist==1){

                TabLayout.Tab tab = tabLayout.getTabAt(2); // fourth tab
                View tabView = tab.getCustomView();
                TextView badgeText = (TextView) tabView.findViewById(R.id.tab_badge);
                badgeText.setVisibility(View.VISIBLE);
                badgeText.setText(""+count);
            }


            adapter = new AdapterPager(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
            adapter.getRegisteredFragment(viewPager.getCurrentItem());

            viewPager.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
            viewPager.setAllowedSwipeDirection(SwipeDirection.none);

            //  setupTabIcons();
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tabSelected) {

                    tabSelected.setIcon(tabIcons[tabSelected.getPosition()]);

                    viewPager.setCurrentItem(tabSelected.getPosition());

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tabSelected) {

                    tabSelected.setIcon(tabIcons1[tabSelected.getPosition()]);

                }

                @Override
                public void onTabReselected(TabLayout.Tab tabSelected) {

                }
            });
        }

        return v;

    }

    void selectPage(int pageIndex) {
        viewPager.setCurrentItem(pageIndex);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIsReceiverRegistered) {
            getActivity().unregisterReceiver(mReceiver);
            mReceiver = null;
            mIsReceiverRegistered = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsReceiverRegistered) {
            if (mReceiver == null)
                mReceiver = new MyBroadcastReceiver();
            getActivity().registerReceiver(mReceiver, new IntentFilter("AddToCart"));
            mIsReceiverRegistered = true;
        }


        if (CartFragment.ORDER_PLACED) {
            selectPage(1);
            CartFragment.ORDER_PLACED = false;
        }

    }


    public boolean onBackPressed() {
        // currently visible tab Fragment
        OnBackPressListener currentFragment = (OnBackPressListener) adapter.getRegisteredFragment(viewPager.getCurrentItem());

        if (currentFragment != null) {
              return currentFragment.onBackPressed();
        }

        // this Fragment couldn't handle the onBackPressed call
        return false;
    }


    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //  updateUI(intent);
            count = sPref.getInt("count",0);
            TabLayout.Tab tab = tabLayout.getTabAt(2); // fourth tab
            View tabView = tab.getCustomView();
            TextView badgeText = (TextView) tabView.findViewById(R.id.tab_badge);
            if(CartFragment.FLAG_CLEAR_ORDER){
                badgeText.setVisibility(View.GONE);
                CartFragment.FLAG_CLEAR_ORDER=false;
            }
            else {
                badgeText.setVisibility(View.VISIBLE);
                badgeText.setText(""+count);
            }

            if(count==0){
                badgeText.setVisibility(View.GONE);
                badgeText.setText(""+count);
            }

        }

    }

}

