package com.foodies.customer.Utils;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    private final int bottomPadding;

    public SpacesItemDecoration(int bottomPadding) {
        this.bottomPadding = bottomPadding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.set(0, 0, 0, bottomPadding);
        }
    }
}