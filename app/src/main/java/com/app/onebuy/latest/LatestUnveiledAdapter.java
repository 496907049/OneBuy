package com.app.onebuy.latest;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.app.onebuy.R;
import com.app.onebuy.bean.CommodityListBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by otis on 2018/4/12.
 */

public class LatestUnveiledAdapter extends BaseQuickAdapter<CommodityListBean,BaseViewHolder>{

    public LatestUnveiledAdapter(@LayoutRes int layoutResId, @Nullable List<CommodityListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityListBean item) {
        Glide.with(mContext).load(item.getImgUrl()).crossFade().into((ImageView) helper.getView(R.id.img_pic));
    }
}
