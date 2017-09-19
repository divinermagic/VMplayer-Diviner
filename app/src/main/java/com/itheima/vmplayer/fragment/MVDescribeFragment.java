package com.itheima.vmplayer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itheima.vmplayer.R;
import com.itheima.vmplayer.bean.MVDetailBean;


import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mr.Wang
 * Date  2016/9/6.
 * Email 1198190260@qq.com
 */
public class MVDescribeFragment extends Fragment {


    @Bind(R.id.profile_image)
    CircleImageView profileImage;
    @Bind(R.id.tv_yiren)
    TextView tvYiren;
    @Bind(R.id.tv_artist_name)
    TextView tvArtistName;
    @Bind(R.id.tv_play_count)
    TextView tvPlayCount;
    @Bind(R.id.tv_pc_count)
    TextView tvPcCount;
    @Bind(R.id.tv_moble_count)
    TextView tvMobleCount;
    @Bind(R.id.tv_describe)
    TextView tvDescribe;
    private MVDetailBean mvDetailBean;
    private View rootView;

    public static MVDescribeFragment newInstance(MVDetailBean mvDetailBean) {
        MVDescribeFragment mvDescribeFragment = new MVDescribeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("mvDetailBean", mvDetailBean);
        mvDescribeFragment.setArguments(bundle);
        return mvDescribeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvDetailBean = getArguments().getParcelable("mvDetailBean");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(R.layout.fragment_describe, container, false);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {
        Glide.with(this).load(mvDetailBean.getArtists().get(0).getArtistAvatar()).centerCrop().into(profileImage);
        tvArtistName.setText(mvDetailBean.getArtistName());
        tvPlayCount.setText("播放次数："+String.valueOf(mvDetailBean.getTotalViews()));
        tvPcCount.setText("PC端："+String.valueOf(mvDetailBean.getTotalPcViews()));
        tvMobleCount.setText("移动："+String.valueOf(mvDetailBean.getTotalMobileViews()));
        tvDescribe.setText(mvDetailBean.getDescription());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
