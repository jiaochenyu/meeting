package com.legend.roomreservation.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.legend.roomreservation.R;


/**
 * Created by Administrator on 2016/11/14.
 */
public class NetworkImageHolderView implements Holder {
    private ImageView mImageView;


    @Override
    public View createView(Context context) {
        mImageView = new ImageView(context);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Object data) {
        if (data instanceof String) {
            Glide.with(context).load(data). skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.pic_fail).error(R.drawable.pic_fail).into(mImageView);
        }
    }
}
