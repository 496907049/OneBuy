package com.app.onebuy.home;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.app.onebuy.R;
import com.app.onebuy.basis.BasisApp;
import com.app.onebuy.bean.GoodsTypeBean;
import com.app.onebuy.view.roundprogressbar.RxRoundProgressBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by otis on 2018/4/12.
 */

public class GoodsAdapter extends BaseQuickAdapter<GoodsTypeBean,BaseViewHolder>{

    public GoodsAdapter(@LayoutRes int layoutResId, @Nullable List<GoodsTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsTypeBean item) {
        helper.setText(R.id.tv_prod_name,item.getGoods_name());
        helper.setText(R.id.tv_prod_progress,mContext.getString(R.string.open_progress)+item.getFinish_ratio_desc());
        BasisApp.loadImg(mContext,item.getImg_url(),(ImageView) helper.getView(R.id.iv_product_icon),R.mipmap.img_item_small_default);
        RxRoundProgressBar progressBar = helper.getView(R.id.progress);
        progressBar.setProgress((float) item.getFinish_ratio()*100);
    }
}
