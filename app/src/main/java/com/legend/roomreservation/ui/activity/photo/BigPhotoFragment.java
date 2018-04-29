package com.legend.roomreservation.ui.activity.photo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.legend.roomreservation.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by JCY on 2017/11/2.
 * 说明：
 */

public class BigPhotoFragment extends Fragment {
    String path;
    @BindView(R.id.photo_view)
    PhotoView mPhotoView;


//    public BigPhotoFragment() {
//    }

    public static final BigPhotoFragment newInstance(String path) {
        BigPhotoFragment fragment = new BigPhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", path);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getArguments().getString("path");
    }

//    public BigPhotoFragment(String path) {
//        this.path = path;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_big_img, container, false);
        ButterKnife.bind(this, v);
        initView();
        return v;
    }

    private void initView() {
        Glide.with(getActivity())
                .load(path)
                .error(R.drawable.pic_fail)
                .into(mPhotoView);
        mPhotoView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                getActivity().finish();
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
