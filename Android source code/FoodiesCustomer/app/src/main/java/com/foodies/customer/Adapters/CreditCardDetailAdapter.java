package com.foodies.customer.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.foodies.customer.Models.CardDetailModel;
import com.foodies.customer.R;


import java.util.ArrayList;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class CreditCardDetailAdapter extends RecyclerView.Adapter<CreditCardDetailAdapter.ViewHolder>  {

    ArrayList<CardDetailModel> getDataAdapter;
    Context context;
    ImageLoader imageLoader1;
    OnItemClickListner onItemClickListner;

    public CreditCardDetailAdapter(ArrayList<CardDetailModel> getDataAdapter, Context context){
        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public CreditCardDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_payment_details, parent, false);

        CreditCardDetailAdapter.ViewHolder viewHolder = new CreditCardDetailAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        CardDetailModel getDataAdapter1 =  getDataAdapter.get(position);

        String card_name = getDataAdapter1.getCard_name();
        holder.card_number.setText("**** **** **** "+getDataAdapter1.getCard_number());
        holder.card_image.setImageResource(getDataAdapter1.getCard_image());

        if(card_name.equalsIgnoreCase("visa")){
            holder.card_image.setImageResource(R.drawable.visa);
        }
        else if (card_name.equalsIgnoreCase("master")){
            holder.card_image.setImageResource(R.drawable.master_card);
        }

        holder.payment_main_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onItemClickListner.OnItemClicked(view,position);
            }
        });


    }


    @Override
    public int getItemCount() {
        return getDataAdapter.size() ;
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView card_number;
        public ImageView card_image;
        public RelativeLayout payment_main_div;

        public ViewHolder(View itemView) {

            super(itemView);


            card_image = itemView.findViewById(R.id.card_image);
            card_number = itemView.findViewById(R.id.credit_card_number_tv);
            payment_main_div = itemView.findViewById(R.id.payment_main_div);

        }
    }

    public interface OnItemClickListner {
        void OnItemClicked(View view, int position);
    }

    public void setOnItemClickListner(OnItemClickListner onCardClickListner) {
        this.onItemClickListner = onCardClickListner;
    }
}
