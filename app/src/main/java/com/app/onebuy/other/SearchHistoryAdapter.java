package com.app.onebuy.other;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.app.onebuy.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import my.MySharedPreferences;

/**
 * Created by asus01 on 2017/9/22.
 */

public class SearchHistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private MySharedPreferences sharedPreferences;

    public SearchHistoryAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }
    public SearchHistoryAdapter(@LayoutRes int layoutResId, @Nullable List<String> data ,MySharedPreferences sharedPreferences) {
        super(layoutResId, data);
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        if(helper.getAdapterPosition() == getItemCount()-1){
//        if(mContext.getString(R.string.clear_history_records).equals(item)){
           TextView tvName= helper.getView(R.id.tv_history_name);
            tvName.setGravity(Gravity.CENTER);
            tvName.setText(item);
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferences.clearInfoSearchHistory();
                    setNewData(sharedPreferences.getInfoSearchHistory());


                }
            });
            helper.setVisible(R.id.line,false);
            helper.setVisible(R.id.iv_history,false);
            helper.setVisible(R.id.iv_delete,false);
        }else {
            TextView tvName= helper.getView(R.id.tv_history_name);
            tvName.setGravity(Gravity.LEFT);
            tvName.setText(item);
            helper.setVisible(R.id.iv_history,true);
            helper.setVisible(R.id.iv_delete,true);
            helper.setVisible(R.id.line,true);
            helper.setOnClickListener(R.id.iv_delete, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferences.deleteInfoSearchHistory(getItem(helper.getAdapterPosition()));
                    setNewData(sharedPreferences.getInfoSearchHistory());

                }
            });
        }



    }

}

