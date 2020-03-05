package com.foodies.customer.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.foodies.customer.ActivitiesAndFragments.ShowFavoriteRestFragment;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.RestaurantsModel;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> implements Filterable {

    ArrayList<RestaurantsModel> getDataAdapter;
    private ArrayList<RestaurantsModel> mFilteredList;
    Context context;
    ImageLoader imageLoader1;
    OnItemClickListner onItemClickListner;
    SharedPreferences sharedPreferences;
    CamomileSpinner progressBar;
    ShowFavoriteRestFragment fragment;

    public RestaurantsAdapter(ArrayList<RestaurantsModel> getDataAdapter, Context context,ShowFavoriteRestFragment fragment,CamomileSpinner progressBar){
        super();
        this.getDataAdapter = getDataAdapter;
        mFilteredList = getDataAdapter;
        this.context = context;
        this.progressBar = progressBar;
        this.fragment = fragment;
    }

    @Override
    public RestaurantsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_restaurants, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RestaurantsAdapter.ViewHolder holder, final int position) {

        final RestaurantsModel getDataAdapter1 =  mFilteredList.get(position);

        sharedPreferences = context.getSharedPreferences(PreferenceClass.user,Context.MODE_PRIVATE);
        imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();
        progressBar.start();


        Uri uri = Uri.parse(Config.imgBaseURL+getDataAdapter1.restaurant_image);
        holder.restaurant_img.setImageURI(uri);


        holder.title_restaurants.setText(getDataAdapter1.restaurant_name.trim());


        String symbol = getDataAdapter1.restaurant_currency;
        holder.salogon_restaurants.setText(getDataAdapter1.restaurant_salgon.trim());
        holder.item_price_tv.setText(getDataAdapter1.preparation_time+ " min");

        holder.ratingBar.setRating(Float.parseFloat(getDataAdapter1.restaurant_avgRating));
        holder.item_time_tv.setText(symbol+" "+getDataAdapter1.delivery_fee_per_km+" / over"+" "+symbol+" "+getDataAdapter1.min_order_price);

        String getFavoriteStatus = getDataAdapter1.restaurant_isFav;

        if (getFavoriteStatus.equalsIgnoreCase("1")){
            holder.favorite_icon.setImageResource(R.drawable.ic_heart_filled);
        }
        else {
            holder.favorite_icon.setImageResource(R.drawable.ic_heart_not_filled);
        }

        String getPromotedString = getDataAdapter1.promoted;

        if (getPromotedString.equalsIgnoreCase("1"))
        {
            holder.featured.setVisibility(View.VISIBLE);
        }
        else {
            holder.featured.setVisibility(View.GONE);
        }

        holder.distanse_restaurants.setText(getDataAdapter1.restaurant_distance);

        holder.restaurant_row_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onItemClickListner !=null){
                    int position = holder.getAdapterPosition();
                    String name = mFilteredList.get(position).restaurant_id;
                    for (int i=0 ; i <getDataAdapter.size() ; i++ ){
                        if(name.equals(getDataAdapter.get(i).restaurant_id)){
                            position = i;
                            break;
                        }
                    }
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListner.OnItemClicked(view,position);
                    }
                }
            }
        });


        holder.favorite_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addFavoriteRestaurant(getDataAdapter1.restaurant_id);


            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 1;
        else
            return 2;
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size() ;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = getDataAdapter;
                } else {
                    ArrayList<RestaurantsModel> filteredList = new ArrayList<>();
                    for (RestaurantsModel row : getDataAdapter) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.restaurant_name.toLowerCase().contains(charString.toLowerCase()) || row.restaurant_name.contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<RestaurantsModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };


    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title_restaurants,distanse_restaurants,salogon_restaurants,item_price_tv,item_time_tv;
        public SimpleDraweeView restaurant_img;
        public RelativeLayout restaurant_row_main;
        public RatingBar ratingBar;
        public ImageView favorite_icon,featured;

        public ViewHolder(View itemView) {

            super(itemView);
            title_restaurants = (TextView)itemView.findViewById(R.id.title_restaurants);
            salogon_restaurants = (TextView)itemView.findViewById(R.id.salogon_restaurants);
            distanse_restaurants = (TextView) itemView.findViewById(R.id.distanse_restaurants) ;
           // item_price_per_mile = itemView.findViewById(R.id.item_price_per_mile);
            item_price_tv = itemView.findViewById(R.id.item_delivery_time_tv);

            restaurant_img = itemView.findViewById(R.id.profile_image_restaurant) ;
            restaurant_row_main = itemView.findViewById(R.id.restaurant_row_main);
            ratingBar = itemView.findViewById(R.id.ruleRatingBar);
            favorite_icon = itemView.findViewById(R.id.favorite_icon);
            featured = itemView.findViewById(R.id.featured);
            item_time_tv = itemView.findViewById(R.id.item_time_tv);

        }
    }


    public interface OnItemClickListner {
        void OnItemClicked(View view, int position);
    }

    public void setOnItemClickListner(OnItemClickListner onCardClickListner) {
        this.onItemClickListner = onCardClickListner;
    }


    public void addFavoriteRestaurant(String res_id){
        progressBar.setVisibility(View.VISIBLE);
        final String user_id = sharedPreferences.getString(PreferenceClass.pre_user_id,"");
        RequestQueue queue = Volley.newRequestQueue(context);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("user_id",user_id);
            jsonObject.put("restaurant_id",res_id);
            jsonObject.put("favourite","1");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest favJsonRequest = new JsonObjectRequest(Request.Method.POST, Config.ADD_FAV_RESTAURANT, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String resposeStr = response.toString();
                JSONObject converResponseToJson = null;

                try {
                    converResponseToJson = new JSONObject(resposeStr);

                    int code_id  = Integer.parseInt(converResponseToJson.optString("code"));
                    if(code_id == 200) {

                       /* ArrayList<RestaurantsModel> tempModel=new ArrayList<RestaurantsModel>();
                        tempModel.clear();
                        tempModel.addAll(tempModel);
                        progressBar.setVisibility(View.GONE);*/

                        fragment.getRestaurantList(user_id);
                     //   progressBar.setVisibility(View.GONE);
                        ShowFavoriteRestFragment.FROM_FAVORITE = true;
                        notifyDataSetChanged();
                  /*      new Handler(Looper.getMainLooper()).post(new Runnable() {
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                notifyDataSetChanged();
                            }
                        });*/
                       // Toast.makeText(getApplicationContext(),converResponseToJson.optString("msg"),Toast.LENGTH_LONG).show();


                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("JSONPost", "Error: " + error.getMessage());
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("api-key", "2a5588cf-4cf3-4f1c-9548-cc1db4b54ae3");
                return headers;
            }
        };

        queue.add(favJsonRequest);
    }


}
