package com.app.onebuy.home.goods.info;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import com.app.onebuy.R;
import com.app.onebuy.bean.Top100PeriodNumber;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by asus01 on 2017/9/22.
 */

public class CalculationAdapter extends BaseQuickAdapter<Top100PeriodNumber, BaseViewHolder> {

    public CalculationAdapter(@LayoutRes int layoutResId, @Nullable List<Top100PeriodNumber> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, Top100PeriodNumber item) {
        if(helper.getAdapterPosition() == 0){
            helper.setText(R.id.tv_join_time,""+mContext.getString(R.string.join_time));
            helper.setText(R.id.tv_conversion_data,""+mContext.getString(R.string.conversion_data));
            helper.setText(R.id.tv_nick_name,""+mContext.getString(R.string.nickname));
        }else {
            helper.setText(R.id.tv_join_time,""+item.getDatetime());
            helper.setText(R.id.tv_conversion_data,""+item.getConvert_val());
            helper.setText(R.id.tv_nick_name,""+item.getUser_id());
        }
    }
}

