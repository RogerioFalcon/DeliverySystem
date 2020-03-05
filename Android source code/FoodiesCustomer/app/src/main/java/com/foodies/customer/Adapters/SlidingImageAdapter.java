package com.foodies.customer.Adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import androidx.viewpager.widget.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Models.ImageSliderModel;
import com.foodies.customer.Constants.Config;

import com.foodies.customer.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class SlidingImageAdapter extends PagerAdapter {

    private ArrayList<ImageSliderModel> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImageAdapter(Context context,ArrayList<ImageSliderModel> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final SimpleDraweeView imageView = (SimpleDraweeView) imageLayout
                .findViewById(R.id.image_slider);
        ImageSliderModel imageSliderModel = IMAGES.get(position);

        Uri uri = Uri.parse(Config.imgBaseURL+imageSliderModel.getSliderImageUrl());
        imageView.setImageURI(uri);

        Log.d(AllConstants.tag,Config.imgBaseURL+imageSliderModel.getSliderImageUrl());

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
