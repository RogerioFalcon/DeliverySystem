package com.foodies.customer.ActivitiesAndFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodies.customer.Adapters.CountryListAdapter;
import com.foodies.customer.Adapters.RestSpecialityAdapter;
import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.Fragment_Callback;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.SpecialityModel;

import com.foodies.customer.R;
import com.foodies.customer.Utils.FontHelper;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.foodies.customer.Utils.TabLayoutUtils;
import com.gmail.samehadar.iosdialog.CamomileSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class RestaurantSpecialityFrag extends RootFragment {

    ImageView close_country;
    TextView title_city_tv;

    ArrayList<SpecialityModel> specialityArray;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RestSpecialityAdapter recyclerViewadapter;
    RecyclerView card_recycler_view;

    CamomileSpinner pbHeaderProgress;
    SharedPreferences sharedPreferences;
    SearchView searchView;

    RelativeLayout transparent_layer,progressDialog;

    View view;
    Context context;


    public RestaurantSpecialityFrag (){

    }

    Fragment_Callback fragment_callback;
    public RestaurantSpecialityFrag (Fragment_Callback fragment_callback){
        this.fragment_callback=fragment_callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_screen, container, false);
        context=getContext();

        FontHelper.applyFont(getContext(),getActivity().getWindow().getDecorView().getRootView(), AllConstants.verdana);

        searchView = view.findViewById(R.id.simpleSearchView);

        String txt="<font color = #dddddd>" + "Search Restaurant Speciality" + "</font>";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            searchView.setQueryHint(Html.fromHtml(txt, Html.FROM_HTML_MODE_LEGACY));
        } else {
            searchView.setQueryHint(Html.fromHtml(txt));
        }


        TextView searchText = (TextView)
                view.findViewById(R.id.search_src_text);
        searchText.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        searchText.setPadding(0,0,0,0);
        LinearLayout searchEditFrame = (LinearLayout) searchView.findViewById(R.id.search_edit_frame); // Get the Linear Layout

        ((LinearLayout.LayoutParams) searchEditFrame.getLayoutParams()).leftMargin = 5;
        search(searchView);
        card_recycler_view = view.findViewById(R.id.countries_list);
        recyclerViewlayoutManager = new LinearLayoutManager(getContext());
        card_recycler_view.setLayoutManager(recyclerViewlayoutManager);
        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        pbHeaderProgress = view.findViewById(R.id.pbHeaderProgress);
        pbHeaderProgress.start();

        init(view);
        return view;

    }

    public void init(View v){


        progressDialog = v.findViewById(R.id.progressDialog);
        transparent_layer = v.findViewById(R.id.transparent_layer);

        specialityArray = new ArrayList<>();
        title_city_tv = v.findViewById(R.id.title_city_tv);


        title_city_tv.setText("Select Speciality");


        close_country = v.findViewById(R.id.close_country);
        close_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().onBackPressed();


            }
        });

        getRestSpecialityList();
    }


    public void getRestSpecialityList(){

        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        ApiRequest.Call_Api(context, Config.SHOW_REST_SPECIALITY_LIST, null, new Callback() {
            @Override
            public void Responce(String resp) {

                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);

                try {
                    JSONObject jsonResponse = new JSONObject(resp);

                    Log.d("JSONPost", jsonResponse.toString());

                    int code_id  = Integer.parseInt(jsonResponse.optString("code"));

                    if(code_id == 200) {

                        JSONObject json = new JSONObject(jsonResponse.toString());
                        JSONArray jsonarray = json.getJSONArray("msg");

                        for(int i = 0; i<jsonarray.length(); i++){

                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            JSONObject resJsonObject1 = jsonObject.getJSONObject("Restaurant");

                            SpecialityModel specialityModel = new SpecialityModel();

                            specialityModel.setName(resJsonObject1.optString("speciality"));
                            specialityModel.setId(resJsonObject1.optString("id"));


                            specialityArray.add(specialityModel);

                        }

                        if(specialityArray!=null) {
                            recyclerViewadapter = new RestSpecialityAdapter(specialityArray, getActivity());
                            card_recycler_view.setAdapter(recyclerViewadapter);
                            recyclerViewadapter.notifyDataSetChanged();
                        }

                        recyclerViewadapter.setOnItemClickListner(new CountryListAdapter.OnItemClickListner() {
                            @Override
                            public void OnItemClicked(View view, int position) {



                                if(fragment_callback!=null){
                                    Bundle bundle=new Bundle();
                                    bundle.putString("speciality",specialityArray.get(position).getName());
                                    fragment_callback.Responce(bundle);
                                }
                                getActivity().onBackPressed();

                            }
                        });
                    }


                }
                catch (JSONException e){
                    e.getMessage();

                }


            }
        });


    }

    private void search(androidx.appcompat.widget.SearchView searchView) {

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (recyclerViewadapter != null) recyclerViewadapter.getFilter().filter(newText);
                return true;
            }
        });
    }

}
