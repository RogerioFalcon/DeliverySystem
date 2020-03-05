package com.foodies.customer.ActivitiesAndFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Models.RestaurantsModel;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;
import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.foodies.customer.Adapters.ShowReviewListAdapter;
import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.RatingListModel;
import com.foodies.customer.R;
import com.foodies.customer.Utils.FontHelper;
import com.foodies.customer.Utils.TabLayoutUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class ReviewListFragment extends RootFragment {

    ImageView back_icon;
    SharedPreferences sPref;
    ArrayList<RatingListModel> listDataReview;

    RecyclerView.LayoutManager recyclerViewlayoutManager;
    ShowReviewListAdapter recyclerViewadapter;
    RecyclerView review_recycler_view;
    SwipeRefreshLayout refresh_layout;

    CamomileSpinner progressBar;
    RelativeLayout transparent_layer,progressDialog;

    TextView total_review_tv;


    View view;
    Context context;


    RestaurantsModel item_model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_list_fragment, container, false);
        context=getContext();

        Bundle bundle=getArguments();
        if(bundle!=null){
            item_model=(RestaurantsModel) bundle.get("data");
        }

        FrameLayout frameLayout = view.findViewById(R.id.review_list_main);

        FontHelper.applyFont(getContext(),frameLayout, AllConstants.verdana);

        sPref = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        total_review_tv = view.findViewById(R.id.total_review_tv);
        initUI(view);
        showRatingList();

        return view;
    }

    public void initUI(View v){

        review_recycler_view = v.findViewById(R.id.review_list_recycler_view);
        progressBar = v.findViewById(R.id.reviewProgress);
         progressBar.start();
        progressDialog = v.findViewById(R.id.progressDialog);
        transparent_layer = v.findViewById(R.id.transparent_layer);

        review_recycler_view.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(getContext());
        review_recycler_view.setLayoutManager(recyclerViewlayoutManager);

        refresh_layout = v.findViewById(R.id.swipe_refresh);
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                showRatingList();

                refresh_layout.setRefreshing(false);
            }
        });



        back_icon = v.findViewById(R.id.back_icon_review_list);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                 getActivity().onBackPressed();

            }
        });


    }


    public void showRatingList(){


          listDataReview = new ArrayList<>();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("restaurant_id", item_model.restaurant_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,false);
        transparent_layer.setVisibility(View.VISIBLE);
        progressDialog.setVisibility(View.VISIBLE);

        ApiRequest.Call_Api(context, Config.SHOE_TOTAL_RATINGS, jsonObject, new Callback() {
            @Override
            public void Responce(String resp) {

                try {
                    JSONObject jsonResponse = new JSONObject(resp);

                    int code_id = Integer.parseInt(jsonResponse.optString("code"));

                    if (code_id == 200) {

                        JSONObject json = new JSONObject(jsonResponse.toString());
                        JSONArray jsonarray = json.getJSONArray("msg");

                        for (int i = 0; i < jsonarray.length(); i++) {

                            JSONObject jsonObject1 = jsonarray.getJSONObject(i);
                            JSONArray commentArray = jsonObject1.getJSONArray("comments");

                            for (int j = 0; j < commentArray.length(); j++) {

                                JSONObject jsonObject2 = commentArray.getJSONObject(j);
                                JSONObject restaurantRating;

                                restaurantRating = jsonObject2.getJSONObject("RestaurantRating");

                                JSONObject userInfo = jsonObject2.getJSONObject("UserInfo");

                                RatingListModel ratingListModel = new RatingListModel();

                                ratingListModel.setComment(restaurantRating.optString("comment"));
                                ratingListModel.setCreated(restaurantRating.optString("created"));
                                ratingListModel.setRating(restaurantRating.optString("star"));
                                ratingListModel.setF_name(userInfo.optString("first_name"));
                                ratingListModel.setL_name(userInfo.optString("last_name"));

                                listDataReview.add(ratingListModel);
                            }

                        }

                        if (listDataReview != null && listDataReview.size()>0) {
                            view.findViewById(R.id.no_job_div).setVisibility(View.GONE);

                            recyclerViewadapter = new ShowReviewListAdapter(listDataReview, getContext());
                            review_recycler_view.setAdapter(recyclerViewadapter);
                            recyclerViewadapter.notifyDataSetChanged();

                            total_review_tv.setText(String.valueOf(listDataReview.size()) + " REVIEWS");
                        }else {
                            view.findViewById(R.id.no_job_div).setVisibility(View.VISIBLE);
                        }

                    }else {

                        view.findViewById(R.id.no_job_div).setVisibility(View.VISIBLE);

                    }

                }catch (JSONException e){
                    e.getCause();
                    view.findViewById(R.id.no_job_div).setVisibility(View.VISIBLE);

                }

                TabLayoutUtils.enableTabs(PagerMainActivity.tabLayout,true);
                transparent_layer.setVisibility(View.GONE);
                progressDialog.setVisibility(View.GONE);
            }
        });

    }

}
