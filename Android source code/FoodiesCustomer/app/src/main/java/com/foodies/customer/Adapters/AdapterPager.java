package com.foodies.customer.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.foodies.customer.ActivitiesAndFragments.RestaurantsFragment;
import com.foodies.customer.ActivitiesAndFragments.UserAccountFragment;
import com.foodies.customer.ActivitiesAndFragments.CartFragment;
import com.foodies.customer.ActivitiesAndFragments.DealsFragment;
import com.foodies.customer.ActivitiesAndFragments.OrdersFragment;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */
public class AdapterPager extends FragmentStatePagerAdapter {
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    int mNumOfTabs;


        public AdapterPager(FragmentManager fragmentManager, int tabCount) {
        super(fragmentManager);
        this.mNumOfTabs=tabCount;
        }
        @Override
        public Fragment getItem(int position) {
        Fragment fm=null;

        switch (position) {

            case 0:
                fm = new RestaurantsFragment();
                break;
            case 1:
                fm = new OrdersFragment();
                break;

            case 2:
                fm = new CartFragment();
                break;
            case 3:
                fm = new UserAccountFragment();
                break;

        }
        return fm;
        }

        @Override
        public int getCount() {
        return mNumOfTabs;
        }

       /* @Override
        public Parcelable saveState() {
        // Do Nothing
        return saveState();
        }*/
        @Override
        public int getItemPosition(Object object) {
        return POSITION_NONE;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
        }

        }


