package com.app.onebuy.home.goods.info.fragment.img;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.bean.CommodityListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by otis on 2018/4/12.
 */

public class ImageTextInfoAdapter extends BaseQuickAdapter<CommodityListBean,BaseViewHolder>{

    public ImageTextInfoAdapter(@LayoutRes int layoutResId, @Nullable List<CommodityListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityListBean item) {
        BasisApp.loadImgAsbitmap(mContext,item.getImgUrl(),(ImageView) helper.getView(R.id.img_pic),R.mipmap.img_item_small_default);
    }
}
